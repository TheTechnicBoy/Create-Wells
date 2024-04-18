package de.thetechnicboy.create_wells.block.entity;

import de.thetechnicboy.create_wells.block.MechanicalWellBlock;
import de.thetechnicboy.create_wells.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.FluidHandlerBlockEntity;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import org.jetbrains.annotations.NotNull;

public class MechanicalWellBlockEntity extends FluidHandlerBlockEntity {

    public int tankCapacity = 4000;
    public int delayUntilNextBucket = 0;
    public int fillTick = 0;
    public boolean initialized;

    public MechanicalWellBlockEntity(BlockPos pos, BlockState state){
        super(ModBlocks.MECHANICAL_WELL_BLOCKENTITY.get(), pos, state);
        tank = new WellFluidTank(this, tankCapacity);
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, MechanicalWellBlockEntity be){
        if(be.delayUntilNextBucket > 0){
            be.delayUntilNextBucket--;
        }

        if(be.fillTick > 0){
            be.fillTick--;
            be.setChanged();
        }

        if(be.fillTick <= 0){
            FluidStack fluidToFill = be.getFluidToFill();
            int result = 0;
            if(fluidToFill != null){
                result = be.tank.fill(fluidToFill, IFluidHandler.FluidAction.EXECUTE);
            }
            if(result > 0){
                be.initFillTick();
                be.setChanged();
            }
        }
    }
    @Override
    public void onLoad(){
        if(!initialized){
            initialized = true;
            if(!level.isClientSide){
                initFillTick();
            }
        }

        if(((WellFluidTank) tank).updateLight(tank.getFluid())){
            level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), Block.UPDATE_ALL);
        }
    }
    protected FluidStack getFluidToFill(){
        return new FluidStack(Fluids.LAVA, 10);
    }
    protected void initFillTick(){
        fillTick = 1;
    }


    public boolean isUpsideDown(){
        return this.getBlockState().getValue(MechanicalWellBlock.UPSIDE_DOWN);
    }
    public Biome getBiome(){ return this.getLevel().getBiome(this.getBlockPos()).get();}
    public int getYPos(){ return this.getBlockPos().getY() ;}
    public DimensionType getDimension(){ return this.getLevel().dimensionType();}


    @Override
    public CompoundTag getUpdateTag() {
        return saveWithoutMetadata();
    }
    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        fillTick = tag.getInt("FillTick");
        initialized = tag.getBoolean("Initialized");
    }
    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putInt("FillTick", fillTick);
        tag.putBoolean("Initialized", initialized);
    }
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        FluidStack oldFluid = tank.getFluid();
        handleUpdateTag(pkt.getTag());
        FluidStack newFluid = tank.getFluid();

        boolean wasEmpty = newFluid != null && oldFluid == null;
        boolean wasFull = newFluid == null && oldFluid != null;

        // update renderer and light level if needed
        if (wasEmpty || wasFull || newFluid != null && newFluid.getAmount() != oldFluid.getAmount()) {
            if (newFluid != null) ((WellFluidTank) tank).updateLight(newFluid);
            else ((WellFluidTank) tank).updateLight(oldFluid);
            level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), Block.UPDATE_ALL);
        }
    }


    public FluidTank getTank() {
        return tank;
    }
    public static class WellFluidTank extends FluidTank{
        private MechanicalWellBlockEntity well;
        public WellFluidTank(MechanicalWellBlockEntity well, int capacity){
            super(capacity);
            this.well = well;
            setValidator(fluid -> { return true; });
        }
        @Override
        public int fill(FluidStack resource, FluidAction action) {
            int fill = well.getFluidToFill().getFluid() == resource.getFluid() ? super.fill(resource, action) : 0;
            if(action.execute() && fill > 0 ){
                BlockState state = well.getBlockState();
                well.getLevel().sendBlockUpdated(well.getBlockPos(), state, state, Block.UPDATE_ALL);
                updateLight(resource);
            }
            return fill;
        }
        @Override
        public FluidStack drain(int maxDrain, FluidAction action){
            FluidStack rescource = super.drain(maxDrain, action);
            if(rescource != null && action.execute()){
                BlockState state = well.getBlockState();
                well.getLevel().sendBlockUpdated(well.getBlockPos(), state, state, Block.UPDATE_ALL);
                updateLight(rescource);
            }
            return rescource;
        }
        protected boolean updateLight(FluidStack resource){
            if(resource != null){
                if(resource.getFluid().getFluidType().getLightLevel(resource.getFluid().defaultFluidState(), well.getLevel(), well.getBlockPos()) > 0){
                    well.getLevel().getLightEngine().checkBlock(well.getBlockPos());
                    return true;
                }
            }
            return false;
        }
    }
}
