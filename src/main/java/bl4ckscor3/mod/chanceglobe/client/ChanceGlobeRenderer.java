package bl4ckscor3.mod.chanceglobe.client;

import bl4ckscor3.mod.chanceglobe.block.ChanceGlobeBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.item.ItemDisplayContext;

public class ChanceGlobeRenderer implements BlockEntityRenderer<ChanceGlobeBlockEntity> {
	public ChanceGlobeRenderer(BlockEntityRendererProvider.Context ctx) {}

	@Override
	public void render(ChanceGlobeBlockEntity te, float partialTicks, PoseStack stack, MultiBufferSource buffer, int light, int overlay) {
		if (te.getClientItem().isEmpty())
			return;

		ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
		BakedModel model = itemRenderer.getModel(te.getClientItem(), te.getLevel(), null, 0);

		stack.translate(0.5D, 0.5D, 0.5D);
		stack.scale(0.4F, 0.4F, 0.4F);
		itemRenderer.render(te.getClientItem(), ItemDisplayContext.GROUND, false, stack, buffer, light, overlay, model);
	}
}
