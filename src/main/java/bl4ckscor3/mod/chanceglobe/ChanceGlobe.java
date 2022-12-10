package bl4ckscor3.mod.chanceglobe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import bl4ckscor3.mod.chanceglobe.block.ChanceGlobeBlock;
import bl4ckscor3.mod.chanceglobe.block.ChanceGlobeBlockEntity;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod(ChanceGlobe.MODID)
@EventBusSubscriber(bus = Bus.MOD)
public class ChanceGlobe {
	public static final String MODID = "chanceglobe";
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
	public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MODID);
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
	public static final RegistryObject<ChanceGlobeBlock> CHANCE_GLOBE = BLOCKS.register("chance_globe", () -> new ChanceGlobeBlock(Block.Properties.of(Material.WOOD).strength(5.0F, 10.0F).lightLevel(state -> 3).sound(SoundType.WOOD)));
	public static final RegistryObject<BlockEntityType<ChanceGlobeBlockEntity>> CHANCE_GLOBE_BLOCK_ENTITY = BLOCK_ENTITY_TYPES.register("chance_globe", () -> BlockEntityType.Builder.<ChanceGlobeBlockEntity>of(ChanceGlobeBlockEntity::new, CHANCE_GLOBE.get()).build(null));
	public static final RegistryObject<BlockItem> CHANCE_GLOBE_ITEM = ITEMS.register("chance_globe", () -> new BlockItem(CHANCE_GLOBE.get(), new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
	public static List<ItemStack> blocksAndItems = new ArrayList<>();

	public ChanceGlobe() {
		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

		BLOCKS.register(modEventBus);
		BLOCK_ENTITY_TYPES.register(modEventBus);
		ITEMS.register(modEventBus);
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Configuration.CONFIG_SPEC);
	}

	@SubscribeEvent
	public static void onInterModProcess(InterModProcessEvent event) {
		generateItemStacks();
	}

	@SubscribeEvent
	public static void onModConfigReloading(ModConfigEvent.Reloading event) {
		if (event.getConfig().getModId().equals(ChanceGlobe.MODID))
			generateItemStacks();
	}

	private static void generateItemStacks() {
		List<ItemStack> newBlocksAndItems = new ArrayList<>();
		NonNullList<ItemStack> temp = NonNullList.create();

		//collect all blocks as stacks, respecting filter configs
		blockLoop: for (Block block : ForgeRegistries.BLOCKS) {
			if (Configuration.CONFIG.enableFilter.get()) {
				ResourceLocation registryName = ForgeRegistries.BLOCKS.getKey(block);

				switch (Configuration.CONFIG.filterMode.get()) {
					case 0: //blacklist
						if (Configuration.CONFIG.filteredMods.get().contains(registryName.getNamespace()) || Configuration.CONFIG.filteredBlocks.get().contains(registryName.toString()))
							continue blockLoop;
						break;
					case 1: //whitelist
						if (!Configuration.CONFIG.filteredMods.get().contains(registryName.getNamespace()) && !Configuration.CONFIG.filteredBlocks.get().contains(registryName.toString()))
							continue blockLoop;
						break;
				}
			}

			temp.add(new ItemStack(block));
		}

		//collect all items as stacks, respecting filter configs
		itemLoop: for (Item item : ForgeRegistries.ITEMS) {
			if (item instanceof BlockItem) //blocks were already added
				continue;

			if (Configuration.CONFIG.enableFilter.get()) {
				ResourceLocation registryName = ForgeRegistries.ITEMS.getKey(item);

				switch (Configuration.CONFIG.filterMode.get()) {
					case 0: //blacklist
						if (Configuration.CONFIG.filteredMods.get().contains(registryName.getNamespace()) || Configuration.CONFIG.filteredItems.get().contains(registryName.toString()))
							continue itemLoop;
						break;
					case 1: //whitelist
						if (!Configuration.CONFIG.filteredMods.get().contains(registryName.getNamespace()) && !Configuration.CONFIG.filteredItems.get().contains(registryName.toString()))
							continue itemLoop;
						break;
				}
			}

			temp.add(new ItemStack(item));
		}

		//add the previously collected stacks to the resulting list one by one, ignoring any duplicates on the way
		outer: for (ItemStack stack : temp) {
			if (stack == null || stack.isEmpty())
				continue outer;

			for (ItemStack bi : newBlocksAndItems) {
				if (bi == null || stack.sameItem(bi))
					continue outer;
			}

			newBlocksAndItems.add(stack);
		}

		Collections.shuffle(newBlocksAndItems); //randomize list
		blocksAndItems.clear(); //clear old collected stacks
		blocksAndItems.addAll(newBlocksAndItems); //add all newly collected stacks to the list the tile entity pulls from
	}
}
