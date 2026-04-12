package bl4ckscor3.mod.chanceglobe.datagen;

import java.util.concurrent.CompletableFuture;

import bl4ckscor3.mod.chanceglobe.ChanceGlobe;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class RecipeGenerator extends RecipeProvider {
	private static final TagKey<Item> GLASS_BLOCKS = TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("c", "glass_blocks"));
	private static final TagKey<Item> GEMS_DIAMOND = TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("c", "gems/diamond"));
	private static final TagKey<Item> GEMS_EMERALD = TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("c", "gems/emerald"));
	private static final TagKey<Item> STORAGE_BLOCKS_REDSTONE = TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("c", "storage_blocks/redstone"));
	private final HolderGetter<Item> items;

	public RecipeGenerator(HolderLookup.Provider lookupProvider, RecipeOutput output) {
		super(lookupProvider, output);
		items = lookupProvider.lookupOrThrow(Registries.ITEM);
	}

	@Override
	public final void buildRecipes() {
		//@formatter:off
		ShapedRecipeBuilder.shaped(items, RecipeCategory.MISC, ChanceGlobe.CHANCE_GLOBE.get(), 5)
		.pattern("GGG")
		.pattern("D E")
		.pattern("PRP")
		.define('G', GLASS_BLOCKS)
		.define('D', GEMS_DIAMOND)
		.define('E', GEMS_EMERALD)
		.define('P', ItemTags.PLANKS)
		.define('R', STORAGE_BLOCKS_REDSTONE)
		.unlockedBy("has_diamond", has(GEMS_DIAMOND))
		.save(output);
	}

	public static final class Runner extends RecipeProvider.Runner {
		public Runner(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
			super(output, lookupProvider);
		}

		@Override
		protected RecipeProvider createRecipeProvider(HolderLookup.Provider lookupProvider, RecipeOutput output) {
			return new RecipeGenerator(lookupProvider, output);
		}

		@Override
		public String getName() {
			return "ChanceGlobe recipes";
		}
	}
}
