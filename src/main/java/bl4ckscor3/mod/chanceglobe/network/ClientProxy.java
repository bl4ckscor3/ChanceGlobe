package bl4ckscor3.mod.chanceglobe.network;

import bl4ckscor3.mod.chanceglobe.renderer.TileEntityChanceGlobeRenderer;
import bl4ckscor3.mod.chanceglobe.tileentity.TileEntityChanceGlobe;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class ClientProxy implements IProxy
{
	@Override
	public void registerRenderers()
	{
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityChanceGlobe.class, new TileEntityChanceGlobeRenderer());
	}
}
