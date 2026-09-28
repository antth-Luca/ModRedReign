package io.github.anttluca.red_reign.entities.red_queens_avatar;

import com.mojang.blaze3d.vertex.PoseStack;
import io.github.anttluca.red_reign.RedReign;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;

public class RedQueensAvatarRenderer extends MobRenderer<RedQueensAvatar, LivingEntityRenderState, RedQueensAvatarModel<LivingEntityRenderState>> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(Identifier.fromNamespaceAndPath(RedReign.MODID, "red_queens_avatar"), "main");

    private static final Identifier RQA_LOCATION = Identifier.fromNamespaceAndPath(RedReign.MODID, "textures/entity/red_queens_avatar/red_queens_avatar.png");
    private static final float DEFAULT_SCALE = 2.0F;

    public RedQueensAvatarRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new RedQueensAvatarModel(ctx.bakeLayer(LAYER_LOCATION)), 1.0F);
    }

    @Override
    protected int getBlockLightLevel(RedQueensAvatar entity, BlockPos blockPos) { return 15; }

    @Override
    public Identifier getTextureLocation(LivingEntityRenderState livingEntityRenderState) { return RQA_LOCATION; }

    @Override
    public LivingEntityRenderState createRenderState() { return new LivingEntityRenderState(); }

    @Override
    protected void scale(LivingEntityRenderState state, PoseStack poseStack) {
        poseStack.scale(DEFAULT_SCALE, DEFAULT_SCALE, DEFAULT_SCALE);
    }
}
