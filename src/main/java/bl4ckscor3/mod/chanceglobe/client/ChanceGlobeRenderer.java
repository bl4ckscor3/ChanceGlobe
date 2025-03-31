package bl4ckscor3.mod.chanceglobe.client;

import com.mojang.blaze3d.vertex.PoseStack;

import bl4ckscor3.mod.chanceglobe.block.ChanceGlobeBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.phys.Vec3;

public class ChanceGlobeRenderer implements BlockEntityRenderer<ChanceGlobeBlockEntity> {
	public ChanceGlobeRenderer(BlockEntityRendererProvider.Context ctx) {}

	@Override
	public void render(ChanceGlobeBlockEntity te, float partialTicks, PoseStack stack, MultiBufferSource buffer, int light, int overlay, Vec3 cameraPosition) {
		if (te.getClientItem().isEmpty())
			return;

		ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();

		stack.translate(0.5D, 0.5D, 0.5D);
		stack.scale(0.4F, 0.4F, 0.4F);
		itemRenderer.renderStatic(te.getClientItem(), ItemDisplayContext.GROUND, light, overlay, stack, buffer, te.getLevel(), 0);
	}
}
