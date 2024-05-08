package de.thetechnicboy.create_wells.block.mechanical_well;

import com.jozufozu.flywheel.backend.Backend;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.simibubi.create.content.kinetics.base.ShaftRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.state.BlockState;

public class MechanicalWellRenderer extends ShaftRenderer<MechanicalWellEntity> {
    public MechanicalWellRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public boolean shouldRenderOffScreen(MechanicalWellEntity be) {
        return true;
    }

    @Override
    protected void renderSafe(MechanicalWellEntity be, float partialTicks, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {

        if (Backend.canUseInstancing(be.getLevel())) return;
        super.renderSafe(be, partialTicks, ms, buffer, light, overlay);
        BlockState blockState = be.getBlockState();
        VertexConsumer vb = buffer.getBuffer(RenderType.solid());

    }


}
