package bl4ckscor3.mod.chanceglobe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

import com.google.common.base.Suppliers;

import bl4ckscor3.mod.chanceglobe.block.ChanceGlobeBlock;
import bl4ckscor3.mod.chanceglobe.block.ChanceGlobeBlockEntity;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class ChanceGlobe {
	public static final String MODID = "chanceglobe";
	private static Platform platform;
	public static final RegistryObject<ChanceGlobeBlock> CHANCE_GLOBE = RegistryObject.block("chance_globe", ChanceGlobeBlock::new, () -> BlockBehaviour.Properties.of().strength(5.0F, 10.0F).lightLevel(state -> 3).sound(SoundType.WOOD));
	public static final Supplier<BlockEntityType<ChanceGlobeBlockEntity>> CHANCE_GLOBE_BLOCK_ENTITY = Suppliers.memoize(() -> platform.createBlockEntity(ChanceGlobeBlockEntity::new, CHANCE_GLOBE.get()));
	public static final RegistryObject<BlockItem> CHANCE_GLOBE_ITEM = RegistryObject.item("chance_globe", p -> new BlockItem(CHANCE_GLOBE.get(), p), () -> new Item.Properties().useBlockDescriptionPrefix());
	public static List<ItemStackTemplate> blocksAndItems = new ArrayList<>();

	public synchronized static void initialize(Platform platform) {
		if (ChanceGlobe.platform != null) {
			throw new IllegalArgumentException(MODID + " platform has already been initialized");
		}

		ChanceGlobe.platform = platform;
		platform.register(Registries.BLOCK, CHANCE_GLOBE);
		platform.register(Registries.BLOCK_ENTITY_TYPE, CHANCE_GLOBE_BLOCK_ENTITY, "chance_globe");
		platform.register(Registries.ITEM, CHANCE_GLOBE_ITEM);
	}

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MODID, path);
	}

	public static Platform platform() {
		return platform;
	}

	public static void generateItemStacks() {
		List<ItemStackTemplate> newBlocksAndItems = new ArrayList<>();
		NonNullList<ItemStackTemplate> temp = NonNullList.create();

		//collect all blocks as stacks, respecting filter configs
		blockLoop:
		for (Block block : BuiltInRegistries.BLOCK) {
			if (Configuration.CONFIG.enableFilter.get()) {
				Identifier registryName = BuiltInRegistries.BLOCK.getKey(block);

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

			Item blockAsItem = block.asItem();
			if (blockAsItem != Items.AIR)
				temp.add(new ItemStackTemplate(blockAsItem));
		}

		//collect all items as stacks, respecting filter configs
		itemLoop:
		for (Item item : BuiltInRegistries.ITEM) {
			if (item instanceof BlockItem) //blocks were already added
				continue;

			if (Configuration.CONFIG.enableFilter.get()) {
				Identifier registryName = BuiltInRegistries.ITEM.getKey(item);

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

			if (item != Items.AIR)
				temp.add(new ItemStackTemplate(item));
		}

		//add the previously collected stacks to the resulting list one by one, ignoring any duplicates on the way
		outer:
		for (ItemStackTemplate stack : temp) {
			if (stack == null)
				continue outer;

			for (ItemStackTemplate bi : newBlocksAndItems) {
				if (bi == null || stack.is(bi.item()))
					continue outer;
			}

			newBlocksAndItems.add(stack);
		}

		Collections.shuffle(newBlocksAndItems); //randomize list
		blocksAndItems.clear(); //clear old collected stacks
		blocksAndItems.addAll(newBlocksAndItems); //add all newly collected stacks to the list the block entity pulls from
	}
}
