package bl4ckscor3.mod.chanceglobe;

import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import com.google.common.collect.Lists;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.common.ForgeConfigSpec.DoubleValue;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;

public class Configuration
{
	public static final ForgeConfigSpec CONFIG_SPEC;
	public static final Configuration CONFIG;

	public final BooleanValue enableFilter;
	public final IntValue filterMode;
	public final ConfigValue<List<? extends String>> filteredBlocks;
	public final ConfigValue<List<? extends String>> filteredItems;
	public final ConfigValue<List<? extends String>> filteredMods;
	public final DoubleValue durationMultiplier;

	static
	{
		Pair<Configuration,ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Configuration::new);

		CONFIG_SPEC = specPair.getRight();
		CONFIG = specPair.getLeft();
	}

	Configuration(ForgeConfigSpec.Builder builder)
	{
		enableFilter = builder
				.comment("Enable black- or whitelisting items and blocks")
				.define("enable_filter", true);
		filterMode = builder
				.comment("Set to 0 for blacklist (all blocks/items EXCEPT the ones listed will be used for randomization), 1 for whitelist (only the blocks/items listed will be used for randomization). Make sure to set enable_filter to true if you want to use the lists.")
				.defineInRange("filter_mode", 0, 0, 1);
		filteredBlocks = builder
				.comment("These blocks will be filtered if enable_filter is set to true. Whether to use white- or blacklisting is defined by the filter_mode option. Use the block's registry name. E.g. to filter grass, use minecraft:grass")
				.defineList("filtered_blocks", Lists.newArrayList(
						"minecraft:barrier",
						"minecraft:bedrock",
						"minecraft:dragon_egg",
						"minecraft:chain_command_block",
						"minecraft:command_block",
						"minecraft:spawner",
						"minecraft:repeating_command_block",
						"minecraft:structure_block",
						"minecraft:structure_void"), e -> e instanceof String);
		filteredItems = builder
				.comment("These items will be filtered if enable_filter is set to true. Whether to use white- or blacklisting is defined by the filter_mode option. Use the item's registry name. E.g. to filter sticks, use minecraft:stick")
				.defineList("filtered_items", Lists.newArrayList(
						"minecraft:command_block_minecart",
						"minecraft:knowledge_book"), e -> e instanceof String);
		filteredMods = builder
				.comment("These mods will be filtered according to filter_mode if enable_filter is set to true. This list contains modids.")
				.defineList("filtered_mods", Lists.newArrayList(), e -> e instanceof String);
		durationMultiplier = builder
				.comment("The default duration until a block gets placed/an item drops is 10 seconds. With this multiplier, you can change the timing. E.g. setting the value to 2 will make the duration twice as long (20 seconds).")
				.defineInRange("duration_multiplier", 1.0D, Double.MIN_VALUE, Double.MAX_VALUE);
	}
}
