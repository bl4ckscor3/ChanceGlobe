package bl4ckscor3.mod.chanceglobe.datagen;

import java.util.Set;

import bl4ckscor3.mod.chanceglobe.ChanceGlobe;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredHolder;

public class BlockLootTableGenerator extends BlockLootSubProvider {
	protected BlockLootTableGenerator() {
		super(Set.of(), FeatureFlags.REGISTRY.allFlags());
	}

	@Override
	public void generate() {
		dropSelf(ChanceGlobe.CHANCE_GLOBE.get());
	}

	@Override
	protected Iterable<Block> getKnownBlocks() {
		return (Iterable<Block>) ChanceGlobe.BLOCKS.getEntries().stream().map(DeferredHolder::get).toList();
	}
}
