package bl4ckscor3.mod.chanceglobe.datagen;

import bl4ckscor3.mod.chanceglobe.ChanceGlobe;
import net.minecraft.advancements.Advancement;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Recipe;

public class RecipeGenerator extends RecipeProvider {
	private static final TagKey<Item> GLASS_BLOCKS = TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("c", "glass_blocks"));
	private static final TagKey<Item> GEMS_DIAMOND = TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("c", "gems/diamond"));
	private static final TagKey<Item> GEMS_EMERALD = TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("c", "gems/emerald"));
	private static final TagKey<Item> STORAGE_BLOCKS_REDSTONE = TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("c", "storage_blocks/redstone"));
	private final HolderGetter<Item> items;

	public RecipeGenerator(BootstrapContext<Recipe<?>> recipeOutput, BootstrapContext<Advancement> advancementOutput) {
		super(recipeOutput, advancementOutput);
		items = recipeOutput.lookup(Registries.ITEM);
	}

	@Override
	public final void buildRecipes() {
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
}
