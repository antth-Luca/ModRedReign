package io.github.anttluca.red_reign.entities.red_queens_avatar;

import com.mojang.blaze3d.vertex.PoseStack;
import io.github.anttluca.red_reign.RedReign;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ARGB;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class RedQueensAvatarRenderer extends MobRenderer<RedQueensAvatar, RedQueensAvatarRenderState, RedQueensAvatarModel<RedQueensAvatarRenderState>> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(Identifier.fromNamespaceAndPath(RedReign.MODID, "red_queens_avatar"), "main");

    private static final Identifier RQA_LOCATION = Identifier.fromNamespaceAndPath(RedReign.MODID, "textures/entity/red_queens_avatar/red_queens_avatar.png");
    private static final float DEFAULT_SCALE = 2.0F;
    private static final float HALF_SQRT_3 = (float) (Math.sqrt(3.0) / 2.0);

    public RedQueensAvatarRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new RedQueensAvatarModel<>(ctx.bakeLayer(LAYER_LOCATION)), 1.0F);
    }

    @Override
    protected int getBlockLightLevel(RedQueensAvatar entity, BlockPos blockPos) {
        return 15;
    }

    @Override
    public Identifier getTextureLocation(RedQueensAvatarRenderState state) {
        return RQA_LOCATION;
    }

    @Override
    public RedQueensAvatarRenderState createRenderState() {
        return new RedQueensAvatarRenderState();
    }

    @Override
    public void extractRenderState(RedQueensAvatar entity, RedQueensAvatarRenderState state, float partialTicks) {
        super.extractRenderState(entity, state, partialTicks);
        state.deathTicks = entity.getDeathTicks() > 0 ? entity.getDeathTicks() + partialTicks : 0.0F;
    }

    @Override
    protected void scale(RedQueensAvatarRenderState state, PoseStack poseStack) {
        poseStack.scale(DEFAULT_SCALE, DEFAULT_SCALE, DEFAULT_SCALE);
    }

    @Override
    public void submit(RedQueensAvatarRenderState state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState camera) {
        super.submit(state, poseStack, submitNodeCollector, camera);

        if (state.deathTicks > 0.0F) {
            float deathProgress = state.deathTicks / 200.0F;
            poseStack.pushPose();
            poseStack.translate(0.0F, 1.5F * DEFAULT_SCALE, 0.0F);
            submitRays(poseStack, deathProgress, submitNodeCollector, RenderTypes.dragonRays());
            submitRays(poseStack, deathProgress, submitNodeCollector, RenderTypes.dragonRaysDepth());
            poseStack.popPose();
        }
    }

    private static void submitRays(PoseStack poseStack, float deathTime, SubmitNodeCollector submitNodeCollector, RenderType renderType) {
        submitNodeCollector.submitCustomGeometry(
            poseStack,
            renderType,
            (pose, buffer) -> {
                float overDrive = Math.min(deathTime > 0.8F ? (deathTime - 0.8F) / 0.2F : 0.0F, 1.0F);
                int innerColor = ARGB.colorFromFloat(1.0F - overDrive, 1.0F, 1.0F, 1.0F);
                int outerColor = ARGB.colorFromFloat(0.0F, 1.0F, 0.0F, 0.0F);
                RandomSource random = RandomSource.createThreadLocalInstance(432L);
                Vector3f origin = new Vector3f();
                Vector3f outerLeft = new Vector3f();
                Vector3f outerRight = new Vector3f();
                Vector3f outerBottom = new Vector3f();
                Quaternionf rayRotation = new Quaternionf();
                int rayCount = Mth.floor((deathTime + deathTime * deathTime) / 2.0F * 60.0F);

                for (int i = 0; i < rayCount; i++) {
                    rayRotation.rotationXYZ(
                            random.nextFloat() * (float) (Math.PI * 2), random.nextFloat() * (float) (Math.PI * 2), random.nextFloat() * (float) (Math.PI * 2)
                        )
                        .rotateXYZ(
                            random.nextFloat() * (float) (Math.PI * 2),
                            random.nextFloat() * (float) (Math.PI * 2),
                            random.nextFloat() * (float) (Math.PI * 2) + deathTime * (float) (Math.PI / 2)
                        );
                    pose.rotate(rayRotation);
                    float length = (random.nextFloat() * 20.0F + 5.0F + overDrive * 10.0F) * DEFAULT_SCALE;
                    float width = (random.nextFloat() * 2.0F + 1.0F + overDrive * 2.0F) * DEFAULT_SCALE;
                    outerLeft.set(-HALF_SQRT_3 * width, length, -0.5F * width);
                    outerRight.set(HALF_SQRT_3 * width, length, -0.5F * width);
                    outerBottom.set(0.0F, length, width);
                    buffer.addVertex(pose, origin).setColor(innerColor);
                    buffer.addVertex(pose, outerLeft).setColor(outerColor);
                    buffer.addVertex(pose, outerRight).setColor(outerColor);
                    buffer.addVertex(pose, origin).setColor(innerColor);
                    buffer.addVertex(pose, outerRight).setColor(outerColor);
                    buffer.addVertex(pose, outerBottom).setColor(outerColor);
                    buffer.addVertex(pose, origin).setColor(innerColor);
                    buffer.addVertex(pose, outerBottom).setColor(outerColor);
                    buffer.addVertex(pose, outerLeft).setColor(outerColor);
                }
            }
        );
    }
}
