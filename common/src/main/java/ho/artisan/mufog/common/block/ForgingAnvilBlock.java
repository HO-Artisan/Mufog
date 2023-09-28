package ho.artisan.mufog.common.block;

import dev.architectury.hooks.item.ItemStackHooks;
import ho.artisan.mufog.common.blockentity.ForgingAnvilBlockEntity;
import ho.artisan.mufog.common.item.HammerItem;
import ho.artisan.mufog.init.MufSounds;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

import java.util.Stack;

public class ForgingAnvilBlock extends BlockWithEntity implements BlockEntityProvider {
    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;

    public ForgingAnvilBlock(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(FACING, Direction.NORTH));
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult result) {
        if (hand != Hand.MAIN_HAND)
            return ActionResult.PASS;
        if (!(world.getBlockEntity(pos) instanceof ForgingAnvilBlockEntity anvil))
            return ActionResult.PASS;

        ItemStack handStack = player.getStackInHand(hand);
        Stack<ItemStack> stack = anvil.getItemStack();
        anvil.blueprint = player.getStackInHand(Hand.OFF_HAND);

        if (player.isSneaking()) {
            if (stack.isEmpty()) {
                return ActionResult.PASS;
            }
            if (!world.isClient())
                ItemStackHooks.giveItem((ServerPlayerEntity) player, stack.pop());
            anvil.markDirty();
            return ActionResult.SUCCESS;
        }else {
            if (handStack.isEmpty()) {
                return ActionResult.PASS;
            } else if (HammerItem.isHammer(handStack)) {
                boolean flag = anvil.process(world, handStack);
                if (flag) {
                    handStack.damage(1, player, (user) -> user.sendToolBreakStatus(Hand.MAIN_HAND));
                    if (!player.getAbilities().creativeMode) {
                        player.getItemCooldownManager().set(handStack.getItem(), 40);
                    }
                    player.playSound(MufSounds.FORGING.get(), 1.0f, 1.0f);
                    return ActionResult.SUCCESS;
                }
                player.playSound(MufSounds.FORGING_FAIL.get(), 1.0f, 1.0f);
                return ActionResult.FAIL;
            }else if (stack.size() < 3) {
                stack.push(handStack.split(1));
                anvil.markDirty();
                return ActionResult.SUCCESS;
            }else {
                return ActionResult.FAIL;
            }
        }
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(Properties.HORIZONTAL_FACING);
    }

    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
        return false;
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return sideCoversSmallSquare(world, pos.down(), Direction.UP);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().rotateYClockwise());
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext ctx) {
        Direction dir = state.get(FACING);
        return switch (dir) {
            case NORTH, SOUTH -> VoxelShapes.cuboid(2.5 / 16, 0, 0, 13.5 / 16, 11.0 / 16, 1);
            case EAST, WEST -> VoxelShapes.cuboid(0, 0, 2.5 / 16, 1, 11.0 / 16, 13.5 / 16);
            default -> VoxelShapes.fullCube();
        };
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new ForgingAnvilBlockEntity(pos, state);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.isOf(newState.getBlock())) {
            return;
        }
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof ForgingAnvilBlockEntity) {
            if (world instanceof ServerWorld) {
                ItemScatterer.spawn(world, pos, (Inventory) blockEntity);
            }
            world.updateComparators(pos, this);
        }
        super.onStateReplaced(state, world, pos, newState, moved);
    }
}
