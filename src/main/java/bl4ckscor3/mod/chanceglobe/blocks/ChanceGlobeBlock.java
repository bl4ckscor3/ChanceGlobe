package bl4ckscor3.mod.chanceglobe.blocks;

import bl4ckscor3.mod.chanceglobe.ChanceGlobe;
import bl4ckscor3.mod.chanceglobe.tileentity.ChanceGlobeTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;

public class ChanceGlobeBlock extends Block implements IWaterLoggable
{
	public static final String NAME = "chance_globe";
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	public static final VoxelShape SHAPE;

	static
	{
		VoxelShape returnShape = Block.box(1, 0, 1, 15, 6, 15);
		VoxelShape[] allShapes = {
				Block.box(6, 6, 2, 10, 7, 14),
				Block.box(2, 6, 6, 14, 7, 10),
				Block.box(4, 6, 3, 12, 7, 13),
				Block.box(3, 6, 4, 13, 7, 12),
				Block.box(7, 7, 2, 9, 8, 14),
				Block.box(2, 7, 7, 14, 8, 9),
				Block.box(3, 7, 5, 13, 9, 11),
				Block.box(5, 7, 3, 11, 9, 13),
				Block.box(3, 7, 4, 13, 8, 12),
				Block.box(4, 7, 3, 12, 8, 13),
				Block.box(4, 8, 4, 12, 9, 12),
				Block.box(3, 9, 6, 13, 10, 10),
				Block.box(6, 9, 3, 10, 10, 13),
				Block.box(4, 9, 5, 12, 10, 11),
				Block.box(5, 9, 4, 11, 10, 12),
				Block.box(4, 10, 6, 12, 11, 10),
				Block.box(6, 10, 4, 10, 11, 12),
				Block.box(5, 10, 5, 11, 11, 11),
				Block.box(6, 11, 7, 10, 12, 9),
				Block.box(7, 11, 6, 9, 12, 10),
		};

		for(VoxelShape shape : allShapes)
		{
			returnShape = VoxelShapes.or(returnShape, shape);
		}

		SHAPE = returnShape;
	}

	public ChanceGlobeBlock()
	{
		super(Block.Properties.of(Material.WOOD).strength(5.0F, 10.0F).lightLevel(state -> 3).sound(SoundType.WOOD));

		setRegistryName(ChanceGlobe.MODID + ":" + NAME);
		registerDefaultState(stateDefinition.any().setValue(WATERLOGGED, false));
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader source, BlockPos pos, ISelectionContext ctx)
	{
		return SHAPE;
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context)
	{
		return defaultBlockState().setValue(WATERLOGGED, context.getLevel().getFluidState(context.getClickedPos()).getType() == Fluids.WATER);
	}

	@Override
	public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, IWorld world, BlockPos currentPos, BlockPos facingPos)
	{
		if(state.getValue(WATERLOGGED))
			world.getLiquidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(world));

		return super.updateShape(state, facing, facingState, world, currentPos, facingPos);
	}

	@Override
	public FluidState getFluidState(BlockState state)
	{
		return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
	}

	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder)
	{
		builder.add(WATERLOGGED);
	}

	@Override
	public boolean hasTileEntity(BlockState state)
	{
		return true;
	}

	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world)
	{
		return new ChanceGlobeTileEntity();
	}
}
