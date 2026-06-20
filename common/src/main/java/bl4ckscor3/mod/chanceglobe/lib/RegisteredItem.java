package bl4ckscor3.mod.chanceglobe.lib;

import java.util.function.Supplier;

import com.google.common.base.Suppliers;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;

public record RegisteredItem<T extends Item>(ResourceKey<Item> key, Supplier<T> object) implements RegistryObject<Item, T> {
	public static <I extends Item> RegisteredItem<I> item(Identifier id, ItemConstructor<I> itemConstructor, Supplier<Item.Properties> properties) {
		ResourceKey<Item> key = ResourceKey.create(Registries.ITEM, id);
		return new RegisteredItem<>(
			key,
			Suppliers.memoize(() -> itemConstructor.construct(properties.get().setId(key)))
		);
	}

	public static RegisteredItem<BlockItem> blockItem(RegisteredBlock<?> block, Supplier<Item.Properties> properties) {
		return item(block.key().identifier(), p -> new BlockItem(block.get(), p), () -> properties.get().useBlockDescriptionPrefix());
	}

	@FunctionalInterface
	public interface ItemConstructor<I extends Item> {
		I construct(Item.Properties properties);
	}
}
