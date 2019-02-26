package bl4ckscor3.mod.chanceglobe.renderer;

import org.lwjgl.opengl.GL11;

import bl4ckscor3.mod.chanceglobe.tileentity.TileEntityChanceGlobe;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraftforge.client.ForgeHooksClient;

public class TileEntityChanceGlobeRenderer extends TileEntityRenderer<TileEntityChanceGlobe>
{
	@Override
	public void render(TileEntityChanceGlobe te, double x, double y, double z, float partialTicks, int destroyStage)
	{
		if(te.getClientItem().isEmpty())
			return;

		IBakedModel model = Minecraft.getInstance().getItemRenderer().getItemModelWithOverrides(te.getClientItem(), te.getWorld(), null);

		GlStateManager.enableRescaleNormal();
		GlStateManager.alphaFunc(GL11.GL_GREATER, 0.1F);
		GlStateManager.enableBlend();
		RenderHelper.enableStandardItemLighting();
		GlStateManager.blendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
		GlStateManager.pushMatrix();
		GlStateManager.translated(x + 0.5D, y + 0.5D, z + 0.5D);
		GlStateManager.scaled(0.4D, 0.4D, 0.4D);
		GlStateManager.rotatef((te.getWorld().getWorldInfo().getGameTime() + partialTicks) * 2.9F, 0.0F, 1.0F, 0.0F);
		model = ForgeHooksClient.handleCameraTransforms(model, TransformType.GROUND, false);
		Minecraft.getInstance().getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
		Minecraft.getInstance().getItemRenderer().renderItem(te.getClientItem(), model);
		GlStateManager.popMatrix();
		GlStateManager.disableRescaleNormal();
		GlStateManager.disableBlend();
	}
}
