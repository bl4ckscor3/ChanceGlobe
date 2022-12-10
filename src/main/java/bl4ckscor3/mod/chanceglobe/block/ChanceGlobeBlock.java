package bl4ckscor3.mod.chanceglobe.block;

import bl4ckscor3.mod.chanceglobe.ChanceGlobe;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ChanceGlobeBlock extends BaseEntityBlock implements SimpleWaterloggedBlock {
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	public static final VoxelShape SHAPE;

	static {
		VoxelShape returnShape = Block.box(1, 0, 1, 15, 6, 15);
		VoxelShape[] allShapes = {
				//@formatter:off
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
		//@formatter:on

		for (VoxelShape shape : allShapes) {
			returnShape = Shapes.or(returnShape, shape);
		}

		SHAPE = returnShape;
	}

	public ChanceGlobeBlock(Properties properties) {
		super(properties);

		registerDefaultState(stateDefinition.any().setValue(WATERLOGGED, false));
	}

	@Override
	public RenderShape getRenderShape(BlockState state) {
		return RenderShape.MODEL;
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter source, BlockPos pos, CollisionContext ctx) {
		return SHAPE;
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return defaultBlockState().setValue(WATERLOGGED, context.getLevel().getFluidState(context.getClickedPos()).getType() == Fluids.WATER);
	}

	@Override
	public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor world, BlockPos currentPos, BlockPos facingPos) {
		if (state.getValue(WATERLOGGED))
			world.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(world));

		return super.updateShape(state, facing, facingState, world, currentPos, facingPos);
	}

	@Override
	public FluidState getFluidState(BlockState state) {
		return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
	}

	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
		builder.add(WATERLOGGED);
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new ChanceGlobeBlockEntity(pos, state);
	}

	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
		return createTickerHelper(type, ChanceGlobe.CHANCE_GLOBE_BLOCK_ENTITY.get(), level.isClientSide ? ChanceGlobeBlockEntity::clientTick : ChanceGlobeBlockEntity::serverTick);
	}
}
