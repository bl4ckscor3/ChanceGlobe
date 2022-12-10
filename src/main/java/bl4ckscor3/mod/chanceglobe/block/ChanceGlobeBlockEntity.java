package bl4ckscor3.mod.chanceglobe.block;

import java.util.Random;

import bl4ckscor3.mod.chanceglobe.ChanceGlobe;
import bl4ckscor3.mod.chanceglobe.Configuration;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class ChanceGlobeBlockEntity extends BlockEntity {
	public static final Random random = new Random(System.currentTimeMillis());
	private ItemStack clientItem = ItemStack.EMPTY; //just for display purposes
	public ItemStack serverItem = ItemStack.EMPTY; //will be dropped or placed
	public final double secondsUntilDrop = 10 * Configuration.CONFIG.durationMultiplier.get();
	public double tickToDrop = secondsUntilDrop * 20;
	public int ticksUntilDrop = 0;
	public int ticksUntilChange = getNextChangeTick(ticksUntilDrop);

	public ChanceGlobeBlockEntity(BlockPos pos, BlockState state) {
		super(ChanceGlobe.CHANCE_GLOBE_BLOCK_ENTITY.get(), pos, state);
	}

	public static void clientTick(Level level, BlockPos pos, BlockState state, ChanceGlobeBlockEntity te) {
		if (ChanceGlobe.blocksAndItems.size() <= 0)
			return;

		if (te.ticksUntilChange == 0 || te.clientItem.isEmpty()) {
			te.ticksUntilChange = te.getNextChangeTick(te.ticksUntilDrop);
			te.clientItem = ChanceGlobe.blocksAndItems.get(random.nextInt(ChanceGlobe.blocksAndItems.size()));
		}
		else
			te.ticksUntilChange--;

		if (te.ticksUntilDrop != te.tickToDrop)
			te.ticksUntilDrop++;
	}

	public static void serverTick(Level level, BlockPos pos, BlockState state, ChanceGlobeBlockEntity te) {
		if (ChanceGlobe.blocksAndItems.size() <= 0)
			return;

		if (te.serverItem.isEmpty())
			te.serverItem = ChanceGlobe.blocksAndItems.get(random.nextInt(ChanceGlobe.blocksAndItems.size()));

		if (te.ticksUntilDrop++ == te.tickToDrop) {
			level.destroyBlock(te.worldPosition, false);

			if (te.serverItem.getItem() instanceof BlockItem blockItem)
				level.setBlockAndUpdate(te.worldPosition, blockItem.getBlock().defaultBlockState());
			else
				Block.popResource(level, te.worldPosition, te.serverItem);
		}
	}

	/**
	 * Gets the item currently being displayed
	 *
	 * @return The currently displayed item as a 1-sized ItemStack
	 */
	public ItemStack getClientItem() {
		return clientItem;
	}

	/**
	 * Gets the next tick the item should change based on the remaining ticks until the globe drops the item. Used to speed up
	 * the item changing towards the end.
	 *
	 * @param remainingTicksToDrop The amount of ticks until the globe drops the item
	 * @return The amount of ticks until the next time the item in the display will change
	 */
	public int getNextChangeTick(int remainingTicksToDrop) {
		return (int) (Math.pow(remainingTicksToDrop / 20 - secondsUntilDrop, 2) / 2.5); //a parabola with f(x)=0 for x=secondsUntilDrop*20
	}
}
