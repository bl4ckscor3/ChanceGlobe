package bl4ckscor3.mod.chanceglobe.blocks;

import bl4ckscor3.mod.chanceglobe.ChanceGlobe;
import bl4ckscor3.mod.chanceglobe.tileentity.TileEntityChanceGlobe;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class BlockChanceGlobe extends Block
{
	public static final String NAME = "chance_globe";
	public static final VoxelShape SHAPE;

	static
	{
		VoxelShape returnShape = Block.makeCuboidShape(1, 0, 1, 15, 6, 15);
		VoxelShape[] allShapes = {
				Block.makeCuboidShape(6, 6, 2, 10, 7, 14),
				Block.makeCuboidShape(2, 6, 6, 14, 7, 10),
				Block.makeCuboidShape(4, 6, 3, 12, 7, 13),
				Block.makeCuboidShape(3, 6, 4, 13, 7, 12),
				Block.makeCuboidShape(7, 7, 2, 9, 8, 14),
				Block.makeCuboidShape(2, 7, 7, 14, 8, 9),
				Block.makeCuboidShape(3, 7, 5, 13, 9, 11),
				Block.makeCuboidShape(5, 7, 3, 11, 9, 13),
				Block.makeCuboidShape(3, 7, 4, 13, 8, 12),
				Block.makeCuboidShape(4, 7, 3, 12, 8, 13),
				Block.makeCuboidShape(4, 8, 4, 12, 9, 12),
				Block.makeCuboidShape(3, 9, 6, 13, 10, 10),
				Block.makeCuboidShape(6, 9, 3, 10, 10, 13),
				Block.makeCuboidShape(4, 9, 5, 12, 10, 11),
				Block.makeCuboidShape(5, 9, 4, 11, 10, 12),
				Block.makeCuboidShape(4, 10, 6, 12, 11, 10),
				Block.makeCuboidShape(6, 10, 4, 10, 11, 12),
				Block.makeCuboidShape(5, 10, 5, 11, 11, 11),
				Block.makeCuboidShape(6, 11, 7, 10, 12, 9),
				Block.makeCuboidShape(7, 11, 6, 9, 12, 10),
		};

		for(VoxelShape shape : allShapes)
		{
			returnShape = VoxelShapes.or(returnShape, shape);
		}

		SHAPE = returnShape;
	}

	public BlockChanceGlobe()
	{
		super(Block.Properties.create(Material.WOOD).hardnessAndResistance(5.0F, 10.0F).lightValue(3).sound(SoundType.WOOD));

		setRegistryName(ChanceGlobe.MODID + ":" + NAME);
	}

	@Override
	public VoxelShape getShape(IBlockState state, IBlockReader source, BlockPos pos)
	{
		return SHAPE;
	}

	@Override
	public Item getItemDropped(IBlockState state, World world, BlockPos pos, int fortune)
	{
		return asItem();
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
	public TileEntity createTileEntity(IBlockState state, IBlockReader world)
	{
		return new TileEntityChanceGlobe();
	}
}
