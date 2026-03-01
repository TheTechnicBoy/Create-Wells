package de.thetechnicboy.create_wells.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.kinetics.base.ShaftRenderer;
import com.simibubi.create.foundation.blockEntity.behaviour.filtering.FilteringRenderer;
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
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.FluidType;
import org.joml.Matrix4f;

public class WellRenderer extends ShaftRenderer<MechanicalWellEntity> {

    public WellRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void renderSafe(MechanicalWellEntity well, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        //super.renderSafe(well, partialTick, poseStack, bufferSource, packedLight, packedOverlay);
        FluidStack fluid = well.getTank().getPrimaryHandler().getFluid();

        if (!fluid.isEmpty()) {
            renderFluid(fluid, well, poseStack, bufferSource, packedLight, packedOverlay);
        }

        renderShaft(well, poseStack, bufferSource, packedLight);

        FilteringRenderer.renderOnBlockEntity(well, partialTick, poseStack, bufferSource, packedLight, packedOverlay);

    }

    @Override
    public boolean shouldRenderOffScreen(MechanicalWellEntity be) {
        return true;
    }

    private void renderFluid(FluidStack fluid, MechanicalWellEntity well, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        int amount = fluid.getAmount();
        int capacity = MechanicalWellEntity.tankCapacity;
        boolean upsideDown = well.isUpsideDown();

        Level level = well.getLevel();
        BlockPos pos = well.getBlockPos();

        FluidType fluidType = fluid.getFluid().getFluidType();
        IClientFluidTypeExtensions fluidEx = IClientFluidTypeExtensions.of(fluidType);

        ResourceLocation stillTexture = fluidEx.getStillTexture(fluid);

        if (stillTexture == null) {
            stillTexture = fluidEx.getStillTexture();
        }

        TextureAtlasSprite sprite = Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS)
                .apply(stillTexture);

        int color = getFluidColor(fluidEx, fluidType, fluid, level, pos);

        float corner = 3F / 16F;
        float height = MechanicalWellBlock.getFluidRenderHeight(amount, capacity, upsideDown);

        float minU = sprite.getU0();
        float maxU = sprite.getU1();
        float minV = sprite.getV0();
        float maxV = sprite.getV1();

        float uvWidth = maxU - minU;
        float uvHeight = maxV - minV;

        float uvStartX = minU + (uvWidth * 3f / 16f);
        float uvEndX = minU + (uvWidth * 13f / 16f);
        float uvStartY = minV + (uvHeight * 3f / 16f);
        float uvEndY = minV + (uvHeight * 13f / 16f);

        VertexConsumer builder = bufferSource.getBuffer(RenderType.translucent());
        Matrix4f matrix = poseStack.last().pose();

        int alpha = (color >> 24) & 0xFF;
        if (alpha == 0) {
            alpha = 255;
        }

        int rgb = color & 0xFFFFFF;
        if (rgb == 0) {
            rgb = 0xFFFFFF;
        }

        color = (alpha << 24) | rgb;

        int adjustedLight = 0xF000F0;

        if (upsideDown) {
            builder.addVertex(matrix, 1 - corner, height, corner)
                    .setColor(color)
                    .setUv(uvEndX, uvStartY)
                    .setUv2(adjustedLight, packedOverlay)
                    .setNormal(0, -1, 0);
            builder.addVertex(matrix, 1 - corner, height, 1 - corner)
                    .setColor(color)
                    .setUv(uvEndX, uvEndY)
                    .setUv2(adjustedLight, packedOverlay)
                    .setNormal(0, -1, 0);
            builder.addVertex(matrix, corner, height, 1 - corner)
                    .setColor(color)
                    .setUv(uvStartX, uvEndY)
                    .setUv2(adjustedLight, packedOverlay)
                    .setNormal(0, -1, 0);
            builder.addVertex(matrix, corner, height, corner)
                    .setColor(color)
                    .setUv(uvStartX, uvStartY)
                    .setUv2(adjustedLight, packedOverlay)
                    .setNormal(0, -1, 0);
        } else {
            builder.addVertex(matrix, corner, height, corner)
                    .setColor(color)
                    .setUv(uvStartX, uvStartY)
                    .setUv2(adjustedLight, packedOverlay)
                    .setNormal(0, 1, 0);
            builder.addVertex(matrix, corner, height, 1 - corner)
                    .setColor(color)
                    .setUv(uvStartX, uvEndY)
                    .setUv2(adjustedLight, packedOverlay)
                    .setNormal(0, 1, 0);
            builder.addVertex(matrix, 1 - corner, height, 1 - corner)
                    .setColor(color)
                    .setUv(uvEndX, uvEndY)
                    .setUv2(adjustedLight, packedOverlay)
                    .setNormal(0, 1, 0);
            builder.addVertex(matrix, 1 - corner, height, corner)
                    .setColor(color)
                    .setUv(uvEndX, uvStartY)
                    .setUv2(adjustedLight, packedOverlay)
                    .setNormal(0, 1, 0);
        }
    }

    private int getFluidColor(IClientFluidTypeExtensions fluidEx, FluidType fluidType, FluidStack fluid, Level level, BlockPos pos) {
        try {
            net.minecraft.world.level.material.FluidState fluidState = fluidType.getStateForPlacement(level, pos, fluid);

            int color = fluidEx.getTintColor(fluidState, level, pos);
            if (color != 0 && (color & 0xFFFFFF) != 0) {
                return color;
            }

            color = fluidEx.getTintColor(fluid);
            if (color != 0 && (color & 0xFFFFFF) != 0) {
                return color;
            }

            color = fluidEx.getTintColor();
            if (color != 0 && (color & 0xFFFFFF) != 0) {
                return color;
            }

        } catch (Exception e) {
            System.err.println("Error getting fluid color: " + e.getMessage());
        }

        return 0xFFFFFFFF;
    }

    private void renderShaft(MechanicalWellEntity well, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
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
}