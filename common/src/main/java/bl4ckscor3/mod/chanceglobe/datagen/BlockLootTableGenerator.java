package bl4ckscor3.mod.chanceglobe.datagen;

import java.util.Set;

import bl4ckscor3.mod.chanceglobe.ChanceGlobe;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.world.flag.FeatureFlags;

public class BlockLootTableGenerator extends BlockLootSubProvider {
	protected BlockLootTableGenerator(LootTableSubProvider.Context output) {
		super(Set.of(), FeatureFlags.REGISTRY.allFlags(), output);
	}

	@Override
	public void generate() {
		dropSelf(ChanceGlobe.CHANCE_GLOBE.get());
	}
}
