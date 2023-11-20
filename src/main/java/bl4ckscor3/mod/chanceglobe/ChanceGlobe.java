package bl4ckscor3.mod.chanceglobe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import bl4ckscor3.mod.chanceglobe.block.ChanceGlobeBlock;
import bl4ckscor3.mod.chanceglobe.block.ChanceGlobeBlockEntity;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.common.Mod.EventBusSubscriber;
import net.neoforged.fml.common.Mod.EventBusSubscriber.Bus;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.fml.event.lifecycle.InterModProcessEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

@Mod(ChanceGlobe.MODID)
@EventBusSubscriber(bus = Bus.MOD)
public class ChanceGlobe {
	public static final String MODID = "chanceglobe";
	public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MODID);
	public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, MODID);
	public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);
	public static final DeferredBlock<ChanceGlobeBlock> CHANCE_GLOBE = BLOCKS.register("chance_globe", () -> new ChanceGlobeBlock(Block.Properties.of().strength(5.0F, 10.0F).lightLevel(state -> 3).sound(SoundType.WOOD)));
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ChanceGlobeBlockEntity>> CHANCE_GLOBE_BLOCK_ENTITY = BLOCK_ENTITY_TYPES.register("chance_globe", () -> BlockEntityType.Builder.<ChanceGlobeBlockEntity>of(ChanceGlobeBlockEntity::new, CHANCE_GLOBE.get()).build(null));
	public static final DeferredItem<BlockItem> CHANCE_GLOBE_ITEM = ITEMS.registerBlockItem("chance_globe", CHANCE_GLOBE);
	public static List<ItemStack> blocksAndItems = new ArrayList<>();

	public ChanceGlobe(IEventBus modEventBus) {
		BLOCKS.register(modEventBus);
		BLOCK_ENTITY_TYPES.register(modEventBus);
		ITEMS.register(modEventBus);
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Configuration.CONFIG_SPEC);
	}

	@SubscribeEvent
	public static void onCreativeModeTabBuildContents(BuildCreativeModeTabContentsEvent event) {
		if (event.getTabKey() == CreativeModeTabs.FUNCTIONAL_BLOCKS)
			event.accept(CHANCE_GLOBE_ITEM.get());
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
		blockLoop:
		for (Block block : BuiltInRegistries.BLOCK) {
			if (Configuration.CONFIG.enableFilter.get()) {
				ResourceLocation registryName = BuiltInRegistries.BLOCK.getKey(block);

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
		itemLoop:
		for (Item item : BuiltInRegistries.ITEM) {
			if (item instanceof BlockItem) //blocks were already added
				continue;

			if (Configuration.CONFIG.enableFilter.get()) {
				ResourceLocation registryName = BuiltInRegistries.ITEM.getKey(item);

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
		outer:
		for (ItemStack stack : temp) {
			if (stack == null || stack.isEmpty())
				continue outer;

			for (ItemStack bi : newBlocksAndItems) {
				if (bi == null || stack.is(bi.getItem()))
					continue outer;
			}

			newBlocksAndItems.add(stack);
		}

		Collections.shuffle(newBlocksAndItems); //randomize list
		blocksAndItems.clear(); //clear old collected stacks
		blocksAndItems.addAll(newBlocksAndItems); //add all newly collected stacks to the list the tile entity pulls from
	}
}
