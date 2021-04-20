package bl4ckscor3.mod.chanceglobe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import bl4ckscor3.mod.chanceglobe.blocks.ChanceGlobeBlock;
import bl4ckscor3.mod.chanceglobe.tileentity.ChanceGlobeTileEntity;
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
	@ObjectHolder(MODID + ":" + ChanceGlobeBlock.NAME)
	public static final Block CHANCE_GLOBE = null;
	@ObjectHolder(MODID + ":" + ChanceGlobeBlock.NAME)
	public static TileEntityType<ChanceGlobeTileEntity> teTypeGlobe;
	public static List<ItemStack> blocksAndItems = new ArrayList<>();

	public ChanceGlobe()
	{
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Configuration.CONFIG_SPEC);
	}

	@SubscribeEvent
	public static void onRegistryEventRegisterBlock(RegistryEvent.Register<Block> event)
	{
		event.getRegistry().register(new ChanceGlobeBlock());
	}

	@SubscribeEvent
	public static void onRegistryEventRegisterTileEntityType(RegistryEvent.Register<TileEntityType<?>> event)
	{
		event.getRegistry().register(TileEntityType.Builder.<ChanceGlobeTileEntity>create(ChanceGlobeTileEntity::new, CHANCE_GLOBE).build(null).setRegistryName(CHANCE_GLOBE.getRegistryName()));
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
		List<ItemStack> newBlocksAndItems = new ArrayList<>();

		NonNullList<ItemStack> temp = NonNullList.create();

		//collect all blocks as stacks, respecting filter configs
		blockLoop: for(Block block : ForgeRegistries.BLOCKS)
		{
			if(Configuration.CONFIG.enableFilter.get())
			{
				switch(Configuration.CONFIG.filterMode.get())
				{
					case 0: //blacklist
						if(Configuration.CONFIG.filteredMods.get().contains(block.getRegistryName().getNamespace()) || Configuration.CONFIG.filteredBlocks.get().contains(block.getRegistryName().toString()))
							continue blockLoop;
						break;
					case 1: //whitelist
						if(!Configuration.CONFIG.filteredMods.get().contains(block.getRegistryName().getNamespace()) && !Configuration.CONFIG.filteredBlocks.get().contains(block.getRegistryName().toString()))
							continue blockLoop;
						break;
				}
			}

			temp.add(new ItemStack(block));
		}

		//collect all items as stacks, respecting filter configs
		itemLoop: for(Item item : ForgeRegistries.ITEMS)
		{
			if(item instanceof BlockItem) //blocks were already added
				continue;

			if(Configuration.CONFIG.enableFilter.get())
			{
				switch(Configuration.CONFIG.filterMode.get())
				{
					case 0: //blacklist
						if(Configuration.CONFIG.filteredMods.get().contains(item.getRegistryName().getNamespace()) || Configuration.CONFIG.filteredItems.get().contains(item.getRegistryName().toString()))
							continue itemLoop;
						break;
					case 1: //whitelist
						if(!Configuration.CONFIG.filteredMods.get().contains(item.getRegistryName().getNamespace()) && !Configuration.CONFIG.filteredItems.get().contains(item.getRegistryName().toString()))
							continue itemLoop;
						break;
				}
			}

			temp.add(new ItemStack(item));
		}

		//add the previously collected stacks to the resulting list one by one, ignoring any duplicates on the way
		outer: for(ItemStack stack : temp)
		{
			if(stack == null || stack.isEmpty())
				continue outer;

			for(ItemStack bi : newBlocksAndItems)
			{
				if(bi == null || stack.isItemEqual(bi))
					continue outer;
			}

			newBlocksAndItems.add(stack);
		}

		Collections.shuffle(newBlocksAndItems); //randomize list
		blocksAndItems.clear(); //clear old collected stacks
		blocksAndItems.addAll(newBlocksAndItems); //add all newly collected stacks to the list the tile entity pulls from
	}
}
