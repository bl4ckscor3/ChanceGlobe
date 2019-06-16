package bl4ckscor3.mod.chanceglobe;

import bl4ckscor3.mod.chanceglobe.renderer.TileEntityChanceGlobeRenderer;
import bl4ckscor3.mod.chanceglobe.tileentity.TileEntityChanceGlobe;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid=ChanceGlobe.MODID, value=Dist.CLIENT)
public class ClientReg
{
	@SubscribeEvent
	public static void onModelRegistry(ModelRegistryEvent event)
	{
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityChanceGlobe.class, new TileEntityChanceGlobeRenderer());
	}
}
