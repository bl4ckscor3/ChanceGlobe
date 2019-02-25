package bl4ckscor3.mod.chanceglobe.blocks;

import java.util.Random;

import bl4ckscor3.mod.chanceglobe.ChanceGlobe;
import bl4ckscor3.mod.chanceglobe.tileentity.TileEntityChanceGlobe;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockChanceGlobe extends Block
{
	public static final String NAME = "chance_globe";
	public BlockChanceGlobe()
	{
		super(Material.WOOD);

		setCreativeTab(CreativeTabs.MISC);
		setHardness(5.0F);
		setResistance(10.0F);
		setLightLevel(0.2F);
		setSoundType(SoundType.WOOD);
		setTranslationKey(ChanceGlobe.MODID + ":" + NAME);
		setRegistryName(ChanceGlobe.MODID + ":" + NAME);
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		float px = 1.0F / 16.0F;
		return new AxisAlignedBB(px, 0, px, 1.0F - px, 12 * px, 1.0F - px);
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return new ItemStack(this).getItem();
	}

	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state)
	{
		return false;
	}

	@Override
	public BlockRenderLayer getRenderLayer()
	{
		return BlockRenderLayer.CUTOUT;
	}

	@Override
	public boolean hasTileEntity(IBlockState state)
	{
		return true;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState state)
	{
		return new TileEntityChanceGlobe();
	}
}
