package io.github.anttluca.red_reign.entities.red_queen_power;

import com.mojang.blaze3d.vertex.PoseStack;
import io.github.anttluca.red_reign.RedReign;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;

public class RedQueenPowerRenderer extends EntityRenderer<RedQueenPower, RedQueenPowerRenderState> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(Identifier.fromNamespaceAndPath(RedReign.MODID, "red_queen_power"), "main");

    private static final Identifier RQP_LOCATION = Identifier.fromNamespaceAndPath(RedReign.MODID, "textures/entity/red_queen_power/red_queen_power.png");
    private static final float DEFAULT_SCALE = -1.0F;

    private final RedQueenPowerModel model;

    public RedQueenPowerRenderer(EntityRendererProvider.Context ctx) {
        super(ctx);
        this.model = new RedQueenPowerModel(ctx.bakeLayer(LAYER_LOCATION));
    }

    @Override
    protected int getBlockLightLevel(RedQueenPower entity, BlockPos blockPos) { return 15; }

    @Override
    public RedQueenPowerRenderState createRenderState() { return new RedQueenPowerRenderState(); }

    @Override
    public void extractRenderState(RedQueenPower entity, RedQueenPowerRenderState state, float partialTicks) {
        super.extractRenderState(entity, state, partialTicks);

        state.ageInTicks = entity.time + partialTicks;

        state.animationPos = 0.0F;
        state.yRot = entity.getYRot(partialTicks);
        state.xRot = entity.getXRot(partialTicks);
    }

    @Override
    public void submit(RedQueenPowerRenderState state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState camera) {
        poseStack.pushPose();
        poseStack.scale(DEFAULT_SCALE, DEFAULT_SCALE, DEFAULT_SCALE);
        poseStack.translate(0.0F, -0.5F, 0.0F);
        submitNodeCollector.submitModel(
            this.model, state, poseStack, RQP_LOCATION, state.lightCoords, OverlayTexture.NO_OVERLAY, state.outlineColor, null
        );
        poseStack.popPose();
        super.submit(state, poseStack, submitNodeCollector, camera);
    }
}
