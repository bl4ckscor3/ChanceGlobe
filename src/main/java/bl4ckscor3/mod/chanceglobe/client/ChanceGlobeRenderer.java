package bl4ckscor3.mod.chanceglobe.client;

import org.jetbrains.annotations.Nullable;

import com.mojang.blaze3d.vertex.PoseStack;

import bl4ckscor3.mod.chanceglobe.block.ChanceGlobeBlockEntity;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.phys.Vec3;

public class ChanceGlobeRenderer implements BlockEntityRenderer<ChanceGlobeBlockEntity, ChanceGlobeRenderState> {
	private final ItemModelResolver itemModelResolver;

	public ChanceGlobeRenderer(BlockEntityRendererProvider.Context ctx) {
		this.itemModelResolver = ctx.itemModelResolver();
	}

	@Override
	public void submit(ChanceGlobeRenderState state, PoseStack pose, SubmitNodeCollector submitNodeCollector, CameraRenderState camera) {
		pose.translate(0.5D, 0.5D, 0.5D);
		pose.scale(0.2F, 0.2F, 0.2F);
		state.item.submit(pose, submitNodeCollector, state.lightCoords, OverlayTexture.NO_OVERLAY, 0);
	}

	@Override
	public ChanceGlobeRenderState createRenderState() {
		return new ChanceGlobeRenderState();
	}

	@Override
	public void extractRenderState(ChanceGlobeBlockEntity be, ChanceGlobeRenderState state, float partialTick, Vec3 cameraPosition, @Nullable ModelFeatureRenderer.CrumblingOverlay breakProgress) {
		ItemStackRenderState itemStackRenderState = new ItemStackRenderState();

		itemModelResolver.updateForTopItem(itemStackRenderState, be.getClientItem(), ItemDisplayContext.FIXED, be.getLevel(), null, (int) be.getBlockPos().asLong());
		state.item = itemStackRenderState;
		BlockEntityRenderer.super.extractRenderState(be, state, partialTick, cameraPosition, breakProgress);
	}
}
