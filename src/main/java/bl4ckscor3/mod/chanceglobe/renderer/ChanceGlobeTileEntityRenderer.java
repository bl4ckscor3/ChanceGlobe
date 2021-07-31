package bl4ckscor3.mod.chanceglobe.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;

import bl4ckscor3.mod.chanceglobe.tileentity.ChanceGlobeTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;

public class ChanceGlobeTileEntityRenderer extends TileEntityRenderer<ChanceGlobeTileEntity>
{
	public ChanceGlobeTileEntityRenderer(TileEntityRendererDispatcher terd)
	{
		super(terd);
	}

	@Override
	public void render(ChanceGlobeTileEntity te, float partialTicks, MatrixStack stack, IRenderTypeBuffer buffer, int p_225616_5_, int p_225616_6_)
	{
		if(te.getClientItem().isEmpty())
			return;

		IBakedModel model = Minecraft.getInstance().getItemRenderer().getModel(te.getClientItem(), te.getLevel(), null);

		stack.translate(0.5D, 0.5D, 0.5D);
		stack.scale(0.4F, 0.4F, 0.4F);
		Minecraft.getInstance().getItemRenderer().render(te.getClientItem(), TransformType.GROUND, false, stack, buffer, p_225616_5_, p_225616_6_, model);
	}
}
