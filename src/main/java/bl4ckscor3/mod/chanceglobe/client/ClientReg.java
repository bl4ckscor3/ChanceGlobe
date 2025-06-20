package bl4ckscor3.mod.chanceglobe.client;

import bl4ckscor3.mod.chanceglobe.ChanceGlobe;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber(modid = ChanceGlobe.MODID, value = Dist.CLIENT)
public class ClientReg {
	@SubscribeEvent
	public static void onEntityRenderersRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
		event.registerBlockEntityRenderer(ChanceGlobe.CHANCE_GLOBE_BLOCK_ENTITY.get(), ChanceGlobeRenderer::new);
	}
}
