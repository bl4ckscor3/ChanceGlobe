package bl4ckscor3.mod.chanceglobe.datagen;

import java.util.concurrent.CompletableFuture;

import bl4ckscor3.mod.chanceglobe.ChanceGlobe;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.VanillaBlockTagsProvider;
import net.minecraft.tags.BlockTags;

public class BlockTagGenerator extends VanillaBlockTagsProvider {
	public BlockTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
		super(output, lookupProvider);
	}

	@Override
	protected void addTags(HolderLookup.Provider provider) {
		tag(BlockTags.MINEABLE_WITH_AXE).add(ChanceGlobe.CHANCE_GLOBE.get());
	}
}
