package bl4ckscor3.mod.chanceglobe.client;

import bl4ckscor3.mod.chanceglobe.ChanceGlobe;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(modid=ChanceGlobe.MODID, value=Dist.CLIENT, bus=Bus.MOD)
public class ClientReg
{
	@SubscribeEvent
	public static void onEntityRenderersRegisterRenderers(EntityRenderersEvent.RegisterRenderers event)
	{
		event.registerBlockEntityRenderer(ChanceGlobe.CHANCE_GLOBE_BLOCK_ENTITY.get(), ChanceGlobeTileEntityRenderer::new);
	}
}
