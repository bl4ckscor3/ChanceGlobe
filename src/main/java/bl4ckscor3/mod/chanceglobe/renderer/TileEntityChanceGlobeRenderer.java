package bl4ckscor3.mod.chanceglobe.renderer;

import org.lwjgl.opengl.GL11;

import bl4ckscor3.mod.chanceglobe.tileentity.TileEntityChanceGlobe;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraftforge.client.ForgeHooksClient;

public class TileEntityChanceGlobeRenderer extends TileEntitySpecialRenderer<TileEntityChanceGlobe>
{
	@Override
	public void render(TileEntityChanceGlobe te, double x, double y, double z, float partialTicks, int destroyStage, float alpha)
	{
		if(te.getClientItem().isEmpty())
			return;

		IBakedModel model = Minecraft.getMinecraft().getRenderItem().getItemModelWithOverrides(te.getClientItem(), te.getWorld(), null);

		GlStateManager.enableRescaleNormal();
		GlStateManager.alphaFunc(GL11.GL_GREATER, 0.1F);
		GlStateManager.enableBlend();
		RenderHelper.enableStandardItemLighting();
		GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
		GlStateManager.pushMatrix();
		GlStateManager.translate(x + 0.5D, y + 0.5D, z + 0.5D);
		GlStateManager.scale(0.4D, 0.4D, 0.4D);
		GlStateManager.rotate((te.getWorld().getTotalWorldTime() + partialTicks) * 2.9F, 0.0F, 1.0F, 0.0F);
		model = ForgeHooksClient.handleCameraTransforms(model, TransformType.GROUND, false);
		Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
		Minecraft.getMinecraft().getRenderItem().renderItem(te.getClientItem(), model);
		GlStateManager.popMatrix();
		GlStateManager.disableRescaleNormal();
		GlStateManager.disableBlend();
	}
}
