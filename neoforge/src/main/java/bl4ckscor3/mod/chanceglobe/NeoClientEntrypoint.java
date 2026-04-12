package bl4ckscor3.mod.chanceglobe;

import bl4ckscor3.mod.chanceglobe.client.ChanceGlobeRenderer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(value = ChanceGlobe.MODID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = ChanceGlobe.MODID, value = Dist.CLIENT)
public class NeoClientEntrypoint {
	public NeoClientEntrypoint(ModContainer modContainer) {
		modContainer.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
	}

	@SubscribeEvent
	public static void onEntityRenderersRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
		event.registerBlockEntityRenderer(ChanceGlobe.CHANCE_GLOBE_BLOCK_ENTITY.get(), ChanceGlobeRenderer::new);
	}
}
