// Made with Blockbench 5.2.1
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports

package io.github.anttluca.red_reign.entities.red_queens_avatar;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.util.Mth;

public class RedQueensAvatarModel<T extends LivingEntityRenderState> extends EntityModel<T> {
	private static final String HEAD_PART_NAME = "head1";
	private static final String CHEST_PART_NAME = "body1";
	private static final String RIBCAGE_PART_NAME = "body2";
	private static final String STOMACH_PART_NAME = "stomach";
	private static final String BONE_PART_NAME = "bone";
	private static final String TAIL_PART_NAME = "body3";

	private static final float RIBCAGE_X_ROT_OFFSET = 0.065F;
	private static final float TAIL_X_ROT_OFFSET = 0.265F;

	private final ModelPart head;
	private final ModelPart ribcage;
	private final ModelPart tail;

	public RedQueensAvatarModel(ModelPart root) {
		super(root);
		this.head = root.getChild(HEAD_PART_NAME);
		this.ribcage = root.getChild(RIBCAGE_PART_NAME);
		this.tail = root.getChild(TAIL_PART_NAME);
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		// Head
		partdefinition.addOrReplaceChild(
			HEAD_PART_NAME,
			CubeListBuilder.create()
					.texOffs(0, 0)
					.addBox(-6.0F, -7.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)),
			PartPose.offset(0.0F, 0.0F, 0.0F)
		);
		// Chest
		partdefinition.addOrReplaceChild(
			CHEST_PART_NAME,
			CubeListBuilder.create()
					.texOffs(32, 5)
					.addBox(-6.0F, 0.9F, -2.5F, 11.0F, 6.0F, 5.0F, new CubeDeformation(0.0F))
							.texOffs(24, 16)
							.addBox(5.0F, 0.0F, -3.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
							.texOffs(24, 42)
							.addBox(5.0F, 6.0F, -3.0F, 6.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
							.texOffs(0, 16)
							.addBox(-12.0F, 0.0F, -3.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
							.texOffs(0, 42)
							.addBox(-12.0F, 6.0F, -3.0F, 6.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
		// Ribcage
		PartDefinition ribcage = partdefinition.addOrReplaceChild(
			RIBCAGE_PART_NAME,
			CubeListBuilder.create()
					.texOffs(0, 29)
					.addBox(0.0F, 0.0F, 0.0F, 3.0F, 10.0F, 3.0F, new CubeDeformation(0.0F)),
			PartPose.offset(-2.0F, 6.9F, -0.5F)
		);

		ribcage.addOrReplaceChild(
			BONE_PART_NAME,
			CubeListBuilder.create()
					.texOffs(24, 29)
					.addBox(-6.0F, -15.6F, 0.0F, 11.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
							.texOffs(24, 29)
							.addBox(-6.0F, -13.1F, 0.0F, 11.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
							.texOffs(24, 29)
							.addBox(-6.0F, -10.6F, 0.0F, 11.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
			PartPose.offset(2.0F, 17.1F, 0.5F)
		);

		ribcage.addOrReplaceChild(
			STOMACH_PART_NAME,
			CubeListBuilder.create()
					.texOffs(24, 33)
					.addBox(-2.0F, 3.0F, -2.0F, 5.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)),
			PartPose.offset(1.0F, -3.0F, 0.0F)
		);
		// Tail
		partdefinition.addOrReplaceChild(
			TAIL_PART_NAME,
			CubeListBuilder.create()
					.texOffs(12, 29)
					.addBox(0.0F, 0.0F, 0.0F, 3.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)),
			PartPose.offset(-2.0F, 16.9F, -0.5F)
		);

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(T state) {
		super.setupAnim(state);
		float anim = Mth.cos((double) (state.ageInTicks * 0.1F));
		this.ribcage.xRot = (RIBCAGE_X_ROT_OFFSET + 0.05F * anim) * (float) Math.PI;
		this.tail.setPos(-2.0F, 6.9F + Mth.cos((double) this.ribcage.xRot) * 10.0F, -0.5F + Mth.sin((double) this.ribcage.xRot) * 10.0F);
		this.tail.xRot = (TAIL_X_ROT_OFFSET + 0.1F * anim) * (float) Math.PI;
		this.head.yRot = state.yRot * ((float) Math.PI / 180F);
		this.head.xRot = state.xRot * ((float) Math.PI / 180F);
	}
}