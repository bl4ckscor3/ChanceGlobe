package bl4ckscor3.mod.chanceglobe.client;

import com.mojang.blaze3d.vertex.PoseStack;

import bl4ckscor3.mod.chanceglobe.block.ChanceGlobeBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms.TransformType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.resources.model.BakedModel;

public class ChanceGlobeRenderer implements BlockEntityRenderer<ChanceGlobeBlockEntity> {
	public ChanceGlobeRenderer(BlockEntityRendererProvider.Context ctx) {}

	@Override
	public void render(ChanceGlobeBlockEntity te, float partialTicks, PoseStack stack, MultiBufferSource buffer, int p_225616_5_, int p_225616_6_) {
		if (te.getClientItem().isEmpty())
			return;

		BakedModel model = Minecraft.getInstance().getItemRenderer().getModel(te.getClientItem(), te.getLevel(), null, 0);

		stack.translate(0.5D, 0.5D, 0.5D);
		stack.scale(0.4F, 0.4F, 0.4F);
		Minecraft.getInstance().getItemRenderer().render(te.getClientItem(), TransformType.GROUND, false, stack, buffer, p_225616_5_, p_225616_6_, model);
	}
}
