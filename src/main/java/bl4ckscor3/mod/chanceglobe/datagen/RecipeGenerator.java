package bl4ckscor3.mod.chanceglobe.datagen;

import java.util.concurrent.CompletableFuture;

import bl4ckscor3.mod.chanceglobe.ChanceGlobe;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.neoforged.neoforge.common.Tags;

public class RecipeGenerator extends RecipeProvider {
	public RecipeGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
		super(output, lookupProvider);
	}

	@Override
	protected final void buildRecipes(RecipeOutput recipeOutput) {
		//@formatter:off
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ChanceGlobe.CHANCE_GLOBE, 5)
		.pattern("GGG")
		.pattern("D E")
		.pattern("PRP")
		.define('G', Tags.Items.GLASS_BLOCKS)
		.define('D', Tags.Items.GEMS_DIAMOND)
		.define('E', Tags.Items.GEMS_EMERALD)
		.define('P', ItemTags.PLANKS)
		.define('R', Tags.Items.STORAGE_BLOCKS_REDSTONE)
		.unlockedBy("has_diamond", has(Tags.Items.GEMS_DIAMOND))
		.save(recipeOutput);
	}
}
