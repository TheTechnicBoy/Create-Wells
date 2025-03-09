package de.thetechnicboy.create_wells.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.kinetics.base.ShaftRenderer;
import de.thetechnicboy.create_wells.block.mechanical_well.MechanicalWellBlock;
import de.thetechnicboy.create_wells.block.mechanical_well.entity.MechanicalWellEntity;
import net.createmod.catnip.render.CachedBuffers;
import net.createmod.catnip.render.SuperByteBuffer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidType;
import org.joml.Matrix4f;

public class WellRenderer extends ShaftRenderer<MechanicalWellEntity> {

    public WellRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void renderSafe(MechanicalWellEntity well, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        FluidStack fluid = well.getTank().getPrimaryHandler().getFluid();
        if (!fluid.isEmpty()) {
            int amount = fluid.getAmount();
            int capacity = MechanicalWellEntity.tankCapacity;
            boolean upsideDown = well.isUpsideDown();

            Level level = well.getLevel();
            BlockPos pos = well.getBlockPos();

            FluidType fluidType = fluid.getFluid().getFluidType();
            IClientFluidTypeExtensions fluidEx = IClientFluidTypeExtensions.of(fluidType.getStateForPlacement(level, pos, fluid));

            TextureAtlasSprite sprite = Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS)
                    .apply(fluidEx.getStillTexture(fluid));

            int color = fluidEx.getTintColor(fluidType.getStateForPlacement(level, pos, fluid), level, pos);

            float corner = 3F / 16F;
            float height = MechanicalWellBlock.getFluidRenderHeight(amount, capacity, upsideDown);

            float minU = sprite.getU(3);
            float maxU = sprite.getU(13);
            float minV = sprite.getV(3);
            float maxV = sprite.getV(13);

            VertexConsumer builder = bufferSource.getBuffer(RenderType.translucent());
            Matrix4f matrix = poseStack.last().pose();

            if (upsideDown) {
                builder.vertex(matrix, 1 - corner, height, corner).color(color).uv(maxU, minV).uv2(packedLight).normal(0, -1, 0).endVertex();
                builder.vertex(matrix, 1 - corner, height, 1 - corner).color(color).uv(maxU, maxV).uv2(packedLight).normal(0, -1, 0).endVertex();
                builder.vertex(matrix, corner, height, 1 - corner).color(color).uv(minU, maxV).uv2(packedLight).normal(0, -1, 0).endVertex();
                builder.vertex(matrix, corner, height, corner).color(color).uv(minU, minV).uv2(packedLight).normal(0, -1, 0).endVertex();
            } else {
                builder.vertex(matrix, corner, height, corner).color(color).uv(minU, minV).uv2(packedLight).normal(0, 1, 0).endVertex();
                builder.vertex(matrix, corner, height, 1 - corner).color(color).uv(minU, maxV).uv2(packedLight).normal(0, 1, 0).endVertex();
                builder.vertex(matrix, 1 - corner, height, 1 - corner).color(color).uv(maxU, maxV).uv2(packedLight).normal(0, 1, 0).endVertex();
                builder.vertex(matrix, 1 - corner, height, corner).color(color).uv(maxU, minV).uv2(packedLight).normal(0, 1, 0).endVertex();
            }

        }


        VertexConsumer vb = bufferSource.getBuffer(RenderType.solid());

        int packedLightmapCoords = LevelRenderer.getLightColor(well.getLevel(), well.getBlockPos());

        SuperByteBuffer shaft = CachedBuffers.partial(AllPartialModels.SHAFT, well.getBlockState());
        Direction.Axis axis = getRotationAxisOf(well);

        shaft
                .rotateCentered(axis == Direction.Axis.Z ? 0 : 90*(float)Math.PI/180f, Direction.NORTH)
                .rotateCentered(axis == Direction.Axis.X ? 0 : 90*(float)Math.PI/180f, Direction.EAST)
                .translate(0, 0, 0)
                .rotateCentered(getAngleForBe(well, well.getBlockPos(), axis), Direction.UP)
                .light(packedLightmapCoords)
                .renderInto(poseStack, vb);

    }

    @Override
    public boolean shouldRenderOffScreen(MechanicalWellEntity be) {
        return true;
    }

}