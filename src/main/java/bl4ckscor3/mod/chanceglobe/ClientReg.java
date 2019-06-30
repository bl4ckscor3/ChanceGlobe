package bl4ckscor3.mod.chanceglobe;

import bl4ckscor3.mod.chanceglobe.renderer.TileEntityChanceGlobeRenderer;
import bl4ckscor3.mod.chanceglobe.tileentity.TileEntityChanceGlobe;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@EventBusSubscriber(modid=ChanceGlobe.MODID, value=Dist.CLIENT, bus=Bus.MOD)
public class ClientReg
{
	@SubscribeEvent
	public static void onFMLClientSetup(FMLClientSetupEvent event)
	{
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityChanceGlobe.class, new TileEntityChanceGlobeRenderer());
	}
}
