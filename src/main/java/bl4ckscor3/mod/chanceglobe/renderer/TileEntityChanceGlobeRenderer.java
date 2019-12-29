package bl4ckscor3.mod.chanceglobe.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;

import bl4ckscor3.mod.chanceglobe.tileentity.TileEntityChanceGlobe;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;

public class TileEntityChanceGlobeRenderer extends TileEntityRenderer<TileEntityChanceGlobe>
{
	public TileEntityChanceGlobeRenderer(TileEntityRendererDispatcher terd)
	{
		super(terd);
	}

	@Override
	public void func_225616_a_(TileEntityChanceGlobe te, float partialTicks, MatrixStack stack, IRenderTypeBuffer buffer, int p_225616_5_, int p_225616_6_) //render
	{
		if(te.getClientItem().isEmpty())
			return;

		IBakedModel model = Minecraft.getInstance().getItemRenderer().getItemModelWithOverrides(te.getClientItem(), te.getWorld(), null);

		stack.func_227861_a_(0.5D, 0.5D, 0.5D); //translate
		stack.func_227862_a_(0.4F, 0.4F, 0.4F); //scale
		Minecraft.getInstance().getItemRenderer().func_229111_a_(te.getClientItem(), TransformType.GROUND, false, stack, buffer, p_225616_5_, p_225616_6_, model); //renderItem
	}
}
