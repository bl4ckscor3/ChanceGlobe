package bl4ckscor3.mod.chanceglobe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import bl4ckscor3.mod.chanceglobe.blocks.BlockChanceGlobe;
import bl4ckscor3.mod.chanceglobe.tileentity.TileEntityChanceGlobe;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.NonNullList;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ObjectHolder;

@Mod(ChanceGlobe.MODID)
@EventBusSubscriber(bus=Bus.MOD)
public class ChanceGlobe
{
	public static final String MODID = "chanceglobe";
	public static final String NAME = "Chance Globe";
	@ObjectHolder(MODID + ":" + BlockChanceGlobe.NAME)
	public static final Block CHANCE_GLOBE = null;
	@ObjectHolder(MODID + ":" + BlockChanceGlobe.NAME)
	public static TileEntityType<TileEntityChanceGlobe> teTypeGlobe;
	public static List<ItemStack> blocksAndItems = new ArrayList<>();

	public ChanceGlobe()
	{
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Configuration.CONFIG_SPEC);
	}

	@SubscribeEvent
	public static void onRegistryEventRegisterBlock(RegistryEvent.Register<Block> event)
	{
		event.getRegistry().register(new BlockChanceGlobe());
	}

	@SubscribeEvent
	public static void onRegistryEventRegisterTileEntityType(RegistryEvent.Register<TileEntityType<?>> event)
	{
		event.getRegistry().register(TileEntityType.Builder.<TileEntityChanceGlobe>create(TileEntityChanceGlobe::new, CHANCE_GLOBE).build(null).setRegistryName(CHANCE_GLOBE.getRegistryName()));
	}

	@SubscribeEvent
	public static void onRegistryEventRegisterItem(RegistryEvent.Register<Item> event)
	{
		event.getRegistry().register(new BlockItem(ChanceGlobe.CHANCE_GLOBE, new Item.Properties().group(ItemGroup.MISC)).setRegistryName(CHANCE_GLOBE.getRegistryName()));
	}

	@SubscribeEvent
	public static void onInterModProcess(InterModProcessEvent event)
	{
		generateItemStacks();
	}

	@SubscribeEvent
	public static void onModConfigReloading(ModConfig.Reloading event)
	{
		if(event.getConfig().getModId().equals(ChanceGlobe.MODID))
			generateItemStacks();
	}

	private static void generateItemStacks()
	{
		blocksAndItems = new ArrayList<>();

		if(Configuration.CONFIG.enableFilter.get())
		{
			NonNullList<ItemStack> temp = NonNullList.create();

			blockLoop: for(Block block : ForgeRegistries.BLOCKS)
			{
				switch(Configuration.CONFIG.filterMode.get())
				{
					//blacklist
					case 0: if(Configuration.CONFIG.filteredMods.get().contains(block.getRegistryName().getNamespace()) || Configuration.CONFIG.filteredBlocks.get().contains(block.getRegistryName().toString())) continue blockLoop; break;
					//whitelist
					case 1: if(!Configuration.CONFIG.filteredMods.get().contains(block.getRegistryName().getNamespace()) && !Configuration.CONFIG.filteredBlocks.get().contains(block.getRegistryName().toString())) continue blockLoop; break;
				}

				temp.add(new ItemStack(block, 1));
			}

			itemLoop: for(Item item : ForgeRegistries.ITEMS)
			{
				if(item instanceof BlockItem) //blocks were already added
					continue;

				switch(Configuration.CONFIG.filterMode.get())
				{
					//blacklist
					case 0: if(Configuration.CONFIG.filteredMods.get().contains(item.getRegistryName().getNamespace()) || Configuration.CONFIG.filteredItems.get().contains(item.getRegistryName().toString())) continue itemLoop; break;
					//whitelist
					case 1: if(!Configuration.CONFIG.filteredMods.get().contains(item.getRegistryName().getNamespace()) && !Configuration.CONFIG.filteredItems.get().contains(item.getRegistryName().toString())) continue itemLoop; break;
				}

				temp.add(new ItemStack(item, 1));
			}

			outer: for(ItemStack stack : temp)
			{
				List<ItemStack> copy = new ArrayList<>(blocksAndItems);

				for(ItemStack bi : copy)
				{
					if(bi == null || stack.isItemEqual(bi))
						continue outer;
				}

				blocksAndItems.add(stack);
			}

			blocksAndItems = blocksAndItems.stream().filter(stack -> stack != null && !stack.isEmpty()).collect(Collectors.toList());
			Collections.shuffle(blocksAndItems);
		}
	}
}
