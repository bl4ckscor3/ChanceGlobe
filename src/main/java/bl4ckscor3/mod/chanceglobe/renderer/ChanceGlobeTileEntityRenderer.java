package bl4ckscor3.mod.chanceglobe.renderer;

import com.mojang.blaze3d.vertex.PoseStack;

import bl4ckscor3.mod.chanceglobe.tileentity.ChanceGlobeTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.renderer.block.model.ItemTransforms.TransformType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;

public class ChanceGlobeTileEntityRenderer extends BlockEntityRenderer<ChanceGlobeTileEntity>
{
	public ChanceGlobeTileEntityRenderer(BlockEntityRenderDispatcher terd)
	{
		super(terd);
	}

	@Override
	public void render(ChanceGlobeTileEntity te, float partialTicks, PoseStack stack, MultiBufferSource buffer, int p_225616_5_, int p_225616_6_)
	{
		if(te.getClientItem().isEmpty())
			return;

		BakedModel model = Minecraft.getInstance().getItemRenderer().getModel(te.getClientItem(), te.getLevel(), null);

		stack.translate(0.5D, 0.5D, 0.5D);
		stack.scale(0.4F, 0.4F, 0.4F);
		Minecraft.getInstance().getItemRenderer().render(te.getClientItem(), TransformType.GROUND, false, stack, buffer, p_225616_5_, p_225616_6_, model);
	}
}
