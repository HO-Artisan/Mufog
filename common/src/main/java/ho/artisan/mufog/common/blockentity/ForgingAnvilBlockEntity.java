package ho.artisan.mufog.common.blockentity;

import ho.artisan.mufog.init.MufBlockEntityTypes;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;

public class ForgingAnvilBlockEntity extends StackStoreTile {
    public ItemStack blueprint = ItemStack.EMPTY;

    public ForgingAnvilBlockEntity(BlockPos pos, BlockState state) {
        super(MufBlockEntityTypes.FORGING_ANVIL.get(), pos, state);
    }

    @Override
    public void read(NbtCompound nbt, boolean isClient) {
        readItems(nbt, this.stack);
        blueprint = ItemStack.fromNbt(nbt.getCompound("Blueprint"));
    }

    @Override
    public void write(NbtCompound nbt, boolean isClient) {
        writeItems(nbt, this.stack);

        NbtCompound blueprint = new NbtCompound();
        this.blueprint.writeNbt(blueprint);
        nbt.put("Blueprint", blueprint);
    }
}
