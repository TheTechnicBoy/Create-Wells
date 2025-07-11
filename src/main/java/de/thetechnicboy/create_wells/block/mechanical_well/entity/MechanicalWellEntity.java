package de.thetechnicboy.create_wells.block.mechanical_well.entity;

import com.simibubi.create.api.equipment.goggles.IHaveGoggleInformation;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.fluid.SmartFluidTankBehaviour;
import de.thetechnicboy.create_wells.Config;
import de.thetechnicboy.create_wells.block.mechanical_well.MechanicalWellBlock;
import de.thetechnicboy.create_wells.recipe.FluidExtractionRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;

public abstract class MechanicalWellEntity extends KineticBlockEntity implements IHaveGoggleInformation {

    public static int tankCapacity = Config.MECHANICAL_WELL_CAPACITY.get();
    private boolean initialized;
    private SmartFluidTankBehaviour tank;


    public MechanicalWellEntity(BlockEntityType<?> type, BlockPos pos, BlockState state){
        super(type, pos, state);

    }

    @Override
    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        //ObservePacket.send(worldPosition, 0);
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
        super.addBehaviours(behaviour);
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

    private int tickCounter = 0;
    private FluidExtractionRecipe.FluidOutput cachedFluidOutput;

    @Override
    public void tick(){
        super.tick();

        if(tickCounter % 20 == 0 || cachedFluidOutput == null) {
            cachedFluidOutput = this.getFluidToFill();
        }
        tickCounter++;

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

        List<FluidExtractionRecipe> _AllRecipes = new ArrayList<>();
        List<FluidExtractionRecipe> _Recipes = new ArrayList<>();
        FluidExtractionRecipe.FluidOutput OUTPUT = new FluidExtractionRecipe.FluidOutput(FluidStack.EMPTY.getFluid(), 0);

        level.getRecipeManager().getRecipes().forEach(recipe -> {
            if(recipe instanceof FluidExtractionRecipe) _AllRecipes.add( (FluidExtractionRecipe) recipe);
        });

        for(int i = 0; i < _AllRecipes.size(); i++){
            FluidExtractionRecipe recipe    = _AllRecipes.get(i);
            if(checkConditions(recipe.getCondition())) _Recipes.add(recipe);
        };

        if(!_Recipes.isEmpty()) OUTPUT = _Recipes.get(0).getOutput();
        return OUTPUT;
    }

    private boolean checkConditions(FluidExtractionRecipe.Condition conditions) {
        boolean Success = true;

        if(getYPos() < conditions.getYMin() && conditions.getYMin() != -255) Success = false;
        if(getYPos() > conditions.getYMax() && conditions.getYMax() != -255) Success = false;

        if(conditions.getDirection() == FluidExtractionRecipe.Direction.NORMAL && isUpsideDown()) Success = false;
        if(conditions.getDirection() == FluidExtractionRecipe.Direction.UPSIDE_DOWN && !isUpsideDown()) Success = false;

        if(!conditions.getDimension().isEmpty() && !conditions.getDimension().contains(getDimension())) Success = false;
        if(!conditions.getBiome().isEmpty() && !conditions.getBiome().contains(getBiome())) Success = false;

        if(!conditions.isBlockTag() && conditions.getBlock() != null && !conditions.getBlock().equals(getBelowBlock())) Success = false;

        if(conditions.isBlockTag() && conditions.getBlock() != null){
            List<Block> blocks = ForgeRegistries.BLOCKS.tags().getTag(TagKey.create(Registries.BLOCK, conditions.getBlock())).stream().toList();
            Block block = ForgeRegistries.BLOCKS.getValue(getBelowBlock());

            if(!blocks.contains(block)) Success = false;
        }

        if(Math.abs(getSpeed()) < conditions.getRPM() && conditions.getRPM() != -255) Success = false;

        //TODO CHECK NBT

        return Success;
    }


    public boolean isUpsideDown(){
        return this.getBlockState().getValue(MechanicalWellBlock.UPSIDE_DOWN);
    }
    public ResourceLocation getBiome(){ return this.getLevel().registryAccess().registryOrThrow(Registries.BIOME).getKey(this.getLevel().getBiome(this.getBlockPos()).get()); }
    public int getYPos(){ return this.getBlockPos().getY() ;}
    public ResourceLocation getDimension(){ return this.getLevel().dimension().location();}
    public ResourceLocation getBelowBlock() {
        BlockPos otherPos = this.getBlockPos().below(isUpsideDown() ? -1 : 1);
        Block block = level.getBlockState(otherPos).getBlock();
        return this.getLevel().registryAccess().registryOrThrow(Registries.BLOCK).getKey(block);
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



}
