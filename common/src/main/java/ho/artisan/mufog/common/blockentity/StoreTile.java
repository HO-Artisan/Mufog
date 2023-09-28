package ho.artisan.mufog.common.blockentity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public abstract class StoreTile extends BlockEntity {
    public StoreTile(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public abstract void read(NbtCompound nbt, boolean isClient);

    public abstract void write(NbtCompound nbt, boolean isClient);

    @Override
    public void readNbt(NbtCompound nbt) {
        this.read(nbt, false);
        super.readNbt(nbt);
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        this.write(nbt, false);
        super.writeNbt(nbt);
    }


    @Override
    public NbtCompound toInitialChunkDataNbt() {
        NbtCompound nbt = super.createNbt();
        write(nbt, true);
        return nbt;
    }

    @NotNull
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        NbtCompound nbt = new NbtCompound();
        write(nbt, true);
        return BlockEntityUpdateS2CPacket.create(this);
    }

    public void readItems(NbtCompound nbt, List<ItemStack> stacks) {
        NbtList nbtList = nbt.getList("Inventory", 10);
        for(int i = 0; i < nbtList.size(); ++i) {
            NbtCompound nbtCompound = nbtList.getCompound(i);
            int j = nbtCompound.getByte("Slot") & 255;
            if (j < stacks.size()) {
                stacks.set(j, ItemStack.fromNbt(nbtCompound));
            }
        }
    }

    public void writeItems(NbtCompound nbt, List<ItemStack> stacks) {
        NbtList nbtList = new NbtList();

        for(int i = 0; i < stacks.size(); ++i) {
            ItemStack itemStack = stacks.get(i);
            if (!itemStack.isEmpty()) {
                NbtCompound nbtCompound = new NbtCompound();
                nbtCompound.putByte("Slot", (byte)i);
                itemStack.writeNbt(nbtCompound);
                nbtList.add(nbtCompound);
            }
        }

        nbt.put("Inventory", nbtList);
    }
}
