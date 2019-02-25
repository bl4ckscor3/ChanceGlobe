package bl4ckscor3.mod.chanceglobe;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Comment;
import net.minecraftforge.common.config.Config.Name;
import net.minecraftforge.common.config.Config.RangeInt;

@Config(modid=ChanceGlobe.MODID)
public class Configuration
{
	@Name("enable_filter")
	@Comment("Enable black- or whitelisting items and blocks")
	public static boolean enableFilter = true;
	@Name("filter_mode")
	@Comment("Set to 0 for blacklist (all blocks/items EXCEPT the ones listed will be used for randomization), 1 for whitelist (only the blocks/items listed will be used for randomization)." + " " +
			"Make sure to set enable_filter to true if you want to use the lists.")
	@RangeInt(min=0, max=1)
	public static int filterMode = 0;
	@Name("filtered_blocks")
	@Comment("These blocks will be filtered if enable_filter is set to true. Whether to use white- or blacklisting is defined by the filter_mode option." + " " +
			"Use the block's registry name. E.g. to filter grass, use minecraft:grass")
	public static String[] filteredBlocks = {
			"minecraft:barrier",
			"minecraft:bedrock",
			"minecraft:dragon_egg",
			"minecraft:chain_command_block",
			"minecraft:command_block",
			"minecraft:mob_spawner",
			"minecraft:repeating_command_block",
			"minecraft:structure_block",
			"minecraft:structure_void"
	};
	@Name("filtered_items")
	@Comment("These items will be filtered if enable_filter is set to true. Whether to use white- or blacklisting is defined by the filter_mode option." + " " +
			"Use the item's registry name. E.g. to filter sticks, use minecraft:stick")
	public static String[] filteredItems = {
			"minecraft:command_block_minecart",
			"minecraft:knowledge_book"
	};
}
