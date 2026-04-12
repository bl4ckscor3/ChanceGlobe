package bl4ckscor3.mod.chanceglobe;

import bl4ckscor3.mod.chanceglobe.client.ChanceGlobeRenderer;
import fuzs.forgeconfigapiport.fabric.api.v5.client.ConfigScreenFactoryRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;

public class FabricClientEntrypoint implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		ConfigScreenFactoryRegistry.INSTANCE.register(ChanceGlobe.MODID, ConfigurationScreen::new);
		BlockEntityRenderers.register(ChanceGlobe.CHANCE_GLOBE_BLOCK_ENTITY.get(), ChanceGlobeRenderer::new);
	}
}
