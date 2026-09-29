// Made with Blockbench 5.2.1
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports

package io.github.anttluca.red_reign.entities.red_queen_power;

import com.mojang.math.Axis;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import org.joml.Quaternionf;

public class RedQueenPowerModel extends Model<RedQueenPowerRenderState> {
	private static final String HEAD_PART_NAME = "head";
	private static final String AURA1_PART_NAME = "aura1";
	private static final String AURA2_PART_NAME = "aura2";

	private static final float SIN_45 = (float)Math.sin((Math.PI / 4D));

	private final ModelPart head;
	private final ModelPart aura1;
	private final ModelPart aura2;

	public RedQueenPowerModel(ModelPart root) {
		super(root, RenderTypes::entityTranslucent);
		this.head = root.getChild(HEAD_PART_NAME);
		this.aura1 = root.getChild(AURA1_PART_NAME);
		this.aura2 = root.getChild(AURA2_PART_NAME);
	}

	public static LayerDefinition createHeadLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		// Head
		partdefinition.addOrReplaceChild(
			HEAD_PART_NAME,
			CubeListBuilder.create()
					.texOffs(0, 35)
					.addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F),
			PartPose.ZERO.withScale(0.765625F)
		);
		// Aura 1
		partdefinition.addOrReplaceChild(
			AURA1_PART_NAME,
			CubeListBuilder.create()
					.texOffs(0, 19)
					.addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F),
			PartPose.ZERO.withScale(0.875F)
		);
		// Aura 2
		partdefinition.addOrReplaceChild(
			AURA2_PART_NAME,
			CubeListBuilder.create()
					.texOffs(0, 19)
					.addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F),
			PartPose.ZERO
		);

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	public void setupAnim(RedQueenPowerRenderState state) {
		super.setupAnim(state);
		this.head.yRot = state.yRot * ((float)Math.PI / 180F);
		this.head.xRot = state.xRot * ((float)Math.PI / 180F);

		float animationSpeed = state.ageInTicks * 3.0F;

		this.head.rotateBy((new Quaternionf()).setAngleAxis(((float)Math.PI / 3F), SIN_45, 0.0F, SIN_45).rotateY(animationSpeed * ((float)Math.PI / 180F)));
		this.aura1.rotateBy((new Quaternionf()).setAngleAxis(((float)Math.PI / 3F), SIN_45, 0.0F, SIN_45).rotateY(animationSpeed * ((float)Math.PI / 180F)));
		this.aura2.rotateBy(Axis.YP.rotationDegrees(animationSpeed).rotateAxis(((float)Math.PI / 3F), SIN_45, 0.0F, SIN_45));
	}
}