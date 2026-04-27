package de.thetechnicboy.create_wells.block.mechanical_well.entity;

import com.simibubi.create.api.equipment.goggles.IHaveGoggleInformation;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.ValueBoxTransform;
import com.simibubi.create.foundation.blockEntity.behaviour.filtering.FilteringBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.fluid.SmartFluidTankBehaviour;
import de.thetechnicboy.create_wells.Config;
import de.thetechnicboy.create_wells.block.mechanical_well.MechanicalWellBlock;
import de.thetechnicboy.create_wells.recipe.FluidExtractionRecipe;
import net.createmod.catnip.math.VecHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public abstract class MechanicalWellEntity extends KineticBlockEntity implements IHaveGoggleInformation {

    public static int tankCapacity = Config.MECHANICAL_WELL_CAPACITY.get();
    private boolean initialized;
    private SmartFluidTankBehaviour tank;
    private FilteringBehaviour filtering;


    public MechanicalWellEntity(BlockEntityType<?> type, BlockPos pos, BlockState state){
        super(type, pos, state);
        setLazyTickRate(20);
    }

    @Override
    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        super.addToGoggleTooltip(tooltip, isPlayerSneaking);
        return containedFluidTooltip(tooltip, isPlayerSneaking, this.getCapability(ForgeCapabilities.FLUID_HANDLER));
    }

    @Override
    public float calculateStressApplied() {
        float impact = Config.MECHANICAL_WELL_STRESS.get();
        this.lastStressApplied = impact;
        return impact;
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side){
        if(cap == ForgeCapabilities.FLUID_HANDLER && side != Direction.DOWN && side != Direction.UP) return tank.getCapability().cast();
        return super.getCapability(cap, side);
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap){
        if(cap == ForgeCapabilities.FLUID_HANDLER) return tank.getCapability().cast();
        return super.getCapability(cap);
    }

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviour){
        tank = SmartFluidTankBehaviour.single(this, tankCapacity);
        tank.getPrimaryHandler().setValidator(fluid -> {return true;});
        behaviour.add(tank);
        filtering = new FilteringBehaviour(this, new WellValueBox(this))
                .withCallback(newFilter -> {
                    cachedFluidOutput = null;
                })
                .forFluids()
                .withPredicate(stack -> {
                    IFluidHandlerItem handler = stack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).resolve().orElseThrow();
                    return handler.getTanks() > 0 && !handler.getFluidInTank(0).isEmpty();
                });
        behaviour.add(filtering);
        super.addBehaviours(behaviour);
    }

    public ArrayList<Fluid> getFilteredFluid() {
        ArrayList<Fluid> fluids = new ArrayList<>();
        if (level != null && level.isClientSide()) return fluids;

        ItemStack filterStack = filtering.getFilter();
        if (filterStack.isEmpty()) return fluids;

        IFluidHandlerItem handler = filterStack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).resolve().orElseThrow();
//        if (handler == null) return fluids;

        FluidStack fluid = handler.getFluidInTank(0);
        if (fluid.isEmpty()) return fluids;

        fluids.add(fluid.getFluid());
        return fluids;
    }

    @Override
    public void read(CompoundTag compoundTag, boolean clientPacket){
        super.read(compoundTag, clientPacket);
        tank.read(compoundTag, clientPacket);
    }
    @Override
    public void write(CompoundTag compoundTag, boolean clientPacket){
        super.write(compoundTag, clientPacket);
        tank.write(compoundTag, clientPacket);
    }


    private FluidExtractionRecipe.FluidOutput cachedFluidOutput;

    @Override
    public void lazyTick() {
        super.lazyTick();
        cachedFluidOutput = this.getFluidToFill();
    }

    @Override
    public void tick(){
        super.tick();

        if (level != null && level.isClientSide()) return;

        if(cachedFluidOutput == null) {
            cachedFluidOutput = this.getFluidToFill();
        }

        FluidStack oldFluid = tank.getPrimaryHandler().getFluid();
        FluidStack newFluid = new FluidStack(cachedFluidOutput.getFluid(), cachedFluidOutput.getAmount() + oldFluid.getAmount());

        if(!oldFluid.isEmpty() && oldFluid.getFluid() != newFluid.getFluid()) return;
        if(oldFluid.getAmount() >= tankCapacity) return;

        boolean hasChanged = false;
        if(cachedFluidOutput.getAmount() + oldFluid.getAmount() > tankCapacity) {
            this.tank.getPrimaryHandler().setFluid(new FluidStack(cachedFluidOutput.getFluid(), tankCapacity));
            hasChanged = true;
        } else if(cachedFluidOutput.getAmount() > 0) {
            this.tank.getPrimaryHandler().setFluid(newFluid);
            hasChanged = true;
        }

        if(hasChanged) {
            this.getLevel().sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), Block.UPDATE_ALL);
            this.setChanged();
        }
    }


    @Override
    public void onLoad(){
        if(!initialized){
            initialized = true;
        }
    }

    protected FluidExtractionRecipe.FluidOutput getFluidToFill(){

        List<Fluid> filteredFluids = getFilteredFluid();

        List<FluidExtractionRecipe> _AllRecipes = new ArrayList<>();
        List<FluidExtractionRecipe> _Recipes = new ArrayList<>();
        FluidExtractionRecipe.FluidOutput OUTPUT = new FluidExtractionRecipe.FluidOutput(FluidStack.EMPTY.getFluid(), 0);

        level.getRecipeManager().getRecipes().forEach(recipe -> {
            if(recipe instanceof FluidExtractionRecipe) _AllRecipes.add( (FluidExtractionRecipe) recipe);
        });

        _AllRecipes.forEach(recipe -> {
            if (!checkConditions(recipe.getCondition())) return;

            if(!filteredFluids.isEmpty() && !filteredFluids.contains(recipe.getOutput().getFluid())) return;

            _Recipes.add(recipe);
        });

        if(!_Recipes.isEmpty()) OUTPUT = _Recipes.get(0).getOutput();
        return OUTPUT;
    }

    private boolean checkConditions(FluidExtractionRecipe.Condition conditions) {
        if(getYPos() < conditions.getYMin() && conditions.getYMin() != -255) return false;
        if(getYPos() > conditions.getYMax() && conditions.getYMax() != -255) return false;

        if(conditions.getDirection() == FluidExtractionRecipe.Direction.NORMAL && isUpsideDown()) return false;
        if(conditions.getDirection() == FluidExtractionRecipe.Direction.UPSIDE_DOWN && !isUpsideDown()) return false;

        if(!conditions.getDimension().isEmpty() && !conditions.getDimension().contains(getDimension())) return false;
        if(!conditions.getBiome().isEmpty() && !conditions.getBiome().contains(getBiome())) return false;

        BlockState blockBelowState = getBelowBlock();
        Block blockBelow = blockBelowState.getBlock();
        ResourceLocation blockBelowRes = this.getLevel().registryAccess().registryOrThrow(Registries.BLOCK).getKey(blockBelow);

        if(!conditions.isBlockTag() && conditions.getBlock() != null && !conditions.getBlock().equals(blockBelowRes)) return false;

        if(conditions.isBlockTag() && conditions.getBlock() != null){
            Optional<? extends HolderSet.Named<Block>> tagOptional =
                    BuiltInRegistries.BLOCK.getTag(TagKey.create(Registries.BLOCK, conditions.getBlock()));
            if(tagOptional.isPresent()) {
                List<Block> blocks = tagOptional.get().stream()
                        .map(holder -> holder.value())
                        .toList();
                Block block = BuiltInRegistries.BLOCK.get(blockBelowRes);

                if(!blocks.contains(block))  return false;
            } else {
                return false;
            }
        }

        if(Math.abs(getSpeed()) < conditions.getRPM() && conditions.getRPM() != -255) return false;

        Map<String, String> requiredProperties = conditions.requiredProperties();

        if(requiredProperties.isEmpty()) System.out.println("NO REQUIRED PROPERTIES");

        for (Map.Entry<String, String> requiredProperty : requiredProperties.entrySet()) {
            String propertyName = requiredProperty.getKey();
            String requiredValue = requiredProperty.getValue();

            System.out.println("Need property: " + propertyName + " with value: " + requiredValue);

            boolean propertyFound = false;
            boolean valueMatches = false;

            for (var property : blockBelowState.getProperties()) {
                System.out.println("Found property: " + propertyName + " with value: " + blockBelowState.getValue(property).toString());
                if (property.getName().equals(propertyName)) {
                    System.out.println("FOUND PROPERTY: " + propertyName);
                    propertyFound = true;
                    String actualValue = blockBelowState.getValue(property).toString();
                    valueMatches = actualValue.equals(requiredValue);

                    if(valueMatches) {
                        System.out.println("VALUE MATCHES: " + actualValue);
                    } else {
                        System.out.println("VALUE DOES NOT MATCH: " + actualValue);
                    }

                    break;
                }
            }

            if (!propertyFound) {
                return false;
            }

            if (!valueMatches) {
                return false;
            }
        }

        return true;
    }


    public boolean isUpsideDown(){
        return this.getBlockState().getValue(MechanicalWellBlock.UPSIDE_DOWN);
    }
    public ResourceLocation getBiome(){ return this.getLevel().registryAccess().registryOrThrow(Registries.BIOME).getKey(this.getLevel().getBiome(this.getBlockPos()).get()); }
    public int getYPos(){ return this.getBlockPos().getY() ;}
    public ResourceLocation getDimension(){ return this.getLevel().dimension().location();}
    public BlockState getBelowBlock() {
        BlockPos otherPos = this.getBlockPos().below(isUpsideDown() ? -1 : 1);
        return level.getBlockState(otherPos);
    }

    public SmartFluidTankBehaviour getTank(){return tank;}

    @Override
    public CompoundTag getUpdateTag() {
        return saveWithoutMetadata();
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        FluidStack oldFluid = tank.getPrimaryHandler().getFluid();
        handleUpdateTag(pkt.getTag());
        FluidStack newFluid = tank.getPrimaryHandler().getFluid();

         boolean wasEmpty = newFluid != null && oldFluid == null;
        boolean wasFull = newFluid == null && oldFluid != null;

        if (wasEmpty || wasFull || newFluid != null && newFluid.getAmount() != oldFluid.getAmount()) {
            level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), Block.UPDATE_ALL);
        }
    }


    public static class WellValueBox extends ValueBoxTransform.Sided {

        private MechanicalWellEntity be;

        public WellValueBox(MechanicalWellEntity be) {
            this.be = be;
        }

        @Override
        protected Vec3 getSouthLocation() {
            return VecHelper.voxelSpace(8, 12, 16.05);
        }

        @Override
        protected boolean isSideActive(BlockState state, Direction direction) {
            Direction.Axis axis = be.getBlockState().getValue(MechanicalWellBlock.AXIS);
            return direction.getAxis() == axis;
        }

    }
}
