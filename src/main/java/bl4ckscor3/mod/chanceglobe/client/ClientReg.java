package bl4ckscor3.mod.chanceglobe.client;

import bl4ckscor3.mod.chanceglobe.ChanceGlobe;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@EventBusSubscriber(modid=ChanceGlobe.MODID, value=Dist.CLIENT, bus=Bus.MOD)
public class ClientReg
{
	@SubscribeEvent
	public static void onFMLClientSetup(FMLClientSetupEvent event)
	{
		event.enqueueWork(() ->	BlockEntityRenderers.register(ChanceGlobe.CHANCE_GLOBE_BLOCK_ENTITY.get(), ChanceGlobeTileEntityRenderer::new));
		ItemBlockRenderTypes.setRenderLayer(ChanceGlobe.CHANCE_GLOBE.get(), RenderType.cutoutMipped());
	}
}
