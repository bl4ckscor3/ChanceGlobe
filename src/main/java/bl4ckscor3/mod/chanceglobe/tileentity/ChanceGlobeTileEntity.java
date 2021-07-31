package bl4ckscor3.mod.chanceglobe.tileentity;

import java.util.Random;

import bl4ckscor3.mod.chanceglobe.ChanceGlobe;
import bl4ckscor3.mod.chanceglobe.Configuration;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.TickableBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;

public class ChanceGlobeTileEntity extends BlockEntity implements TickableBlockEntity
{
	public static final Random random = new Random(System.currentTimeMillis());
	private ItemStack clientItem = ItemStack.EMPTY; //just for display purposes
	public ItemStack serverItem = ItemStack.EMPTY; //will be dropped or placed
	public final double secondsUntilDrop = 10 * Configuration.CONFIG.durationMultiplier.get();
	public double tickToDrop = secondsUntilDrop * 20;
	public int ticksUntilDrop = 0;
	public int ticksUntilChange = getNextChangeTick(ticksUntilDrop);

	public ChanceGlobeTileEntity()
	{
		super(ChanceGlobe.teTypeGlobe);
	}

	@Override
	public void tick()
	{
		if(ChanceGlobe.blocksAndItems.size() <= 0)
			return;

		if(level.isClientSide) //client logic
		{
			if(ticksUntilChange == 0 || clientItem.isEmpty())
			{
				ticksUntilChange = getNextChangeTick(ticksUntilDrop);
				clientItem = ChanceGlobe.blocksAndItems.get(random.nextInt(ChanceGlobe.blocksAndItems.size()));
			}
			else
				ticksUntilChange--;

			if(ticksUntilDrop != tickToDrop)
				ticksUntilDrop++;
		}
		else //server logic
		{
			if(serverItem.isEmpty())
				serverItem = ChanceGlobe.blocksAndItems.get(random.nextInt(ChanceGlobe.blocksAndItems.size()));

			if(ticksUntilDrop++ == tickToDrop)
			{
				level.destroyBlock(worldPosition, false);

				if(serverItem.getItem() instanceof BlockItem)
					level.setBlockAndUpdate(worldPosition, ((BlockItem)serverItem.getItem()).getBlock().defaultBlockState());
				else
					Block.popResource(level, worldPosition, serverItem);
			}
		}
	}

	/**
	 * Gets the item currently being display
	 * @return The currently displayed item as a 1-sized ItemStack
	 */
	public ItemStack getClientItem()
	{
		return clientItem;
	}

	/**
	 * Gets the next tick the item should change based on the remaining ticks until the globe drops the item. Used to speed up the item changing towards the end.
	 * @param remainingTicksToDrop The amount of ticks until the globe drops the item
	 * @return The amount of ticks until the next time the item in the display will change
	 */
	public int getNextChangeTick(int remainingTicksToDrop)
	{
		return (int)(Math.pow(remainingTicksToDrop / 20 - secondsUntilDrop, 2) / 2.5); //a parabola with f(x)=0 for x=secondsUntilDrop*20
	}
}
