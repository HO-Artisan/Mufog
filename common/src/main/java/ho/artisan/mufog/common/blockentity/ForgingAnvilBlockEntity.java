package ho.artisan.mufog.common.blockentity;

import ho.artisan.mufog.common.item.HammerItem;
import ho.artisan.mufog.common.recipe.ForgingRecipe;
import ho.artisan.mufog.init.MufBlockEntityTypes;
import ho.artisan.mufog.init.MufItems;
import ho.artisan.mufog.init.MufRecipes;
import net.minecraft.block.BlockState;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

import java.util.Optional;

public class ForgingAnvilBlockEntity extends StackStoreTile {
    public ItemStack blueprint = ItemStack.EMPTY;
    public int processTime = 0;
    public int processTimeTotal = 20;

    public ForgingAnvilBlockEntity(BlockPos pos, BlockState state) {
        super(MufBlockEntityTypes.FORGING_ANVIL.get(), pos, state);
    }

    @Override
    public void read(NbtCompound nbt, boolean isClient) {
        readItems(nbt, this.stack);
        this.blueprint = ItemStack.fromNbt(nbt.getCompound("Blueprint"));
        this.processTime = nbt.getInt("ProcessTime");
        this.processTimeTotal = nbt.getInt("ProcessTimeTotal");
    }

    @Override
    public void write(NbtCompound nbt, boolean isClient) {
        writeItems(nbt, this.stack);

        NbtCompound blueprint = new NbtCompound();
        this.blueprint.writeNbt(blueprint);
        nbt.put("Blueprint", blueprint);

        nbt.putInt("ProcessTime", this.processTime);
        nbt.putInt("ProcessTimeTotal", this.processTimeTotal);
    }

    public boolean process(World world, ItemStack hammer) {
        Optional<ForgingRecipe> optional = world.getRecipeManager().getFirstMatch(MufRecipes.FORGING_RECIPE_TYPE.get(), this, world);
        if (optional.isPresent()) {
            ForgingRecipe recipe = optional.get();
            Ingredient recipeBlueprint = recipe.getBlueprint();

            if (HammerItem.getLevel(hammer) < recipe.getLevel())
                return false;

            processTimeTotal = recipe.getProcesstime();
            processTime++;

            for (ItemStack stack : getItemStack()) {
                stack.setSubNbt("Forging", createForgingNBT(processTime, processTimeTotal, blueprint));
            }
            if (processTime == processTimeTotal) {
                clear();
                if (world.random.nextFloat() < recipe.getChance()) {
                    stack.push(recipe.craft(this, world.getRegistryManager()));
                }else {
                    stack.push(recipe.getFailure());
                }
                markDirty();
                if (world.getGameRules().getBoolean(GameRules.DO_TILE_DROPS) && world instanceof ServerWorld serverWorld) {
                    ExperienceOrbEntity.spawn(serverWorld, Vec3d.ofCenter(pos), 1 + (int) Math.floor(5 * recipe.getExperience()));
                }
            }
            markDirty();
            return true;
        }
        return false;
    }

    public ItemStack getBlueprint(ItemStack stack) {
        return ItemStack.fromNbt(getForgingNBT(stack).getCompound("Blueprint"));
    }

    public int getProcessTime(ItemStack stack) {
        return getForgingNBT(stack).getInt("ProcessTime");
    }

    public NbtCompound getForgingNBT(ItemStack stack) {
        NbtCompound nbt = stack.getOrCreateNbt();
        if (nbt.isEmpty() || !nbt.contains("Forging"))
            stack.setSubNbt("Forging", createForgingNBT());
        return stack.getOrCreateNbt().getCompound("Forging");
    }

    public boolean hasForgingNBT(ItemStack stack) {
        return stack.getOrCreateNbt().contains("Forging");
    }

    public NbtCompound createForgingNBT() {
        NbtCompound nbt = new NbtCompound();
        nbt.putInt("ProcessTime", 0);
        nbt.putInt("ProcessTimeTotal", 20);
        new ItemStack(MufItems.FORGING_ANVIL.get()).writeNbt(nbt);
        return nbt;
    }

    public NbtCompound createForgingNBT(int processTime, int processTimeTotal, ItemStack blueprint) {
        NbtCompound nbt = new NbtCompound();
        nbt.putInt("ProcessTime", processTime);
        nbt.putInt("ProcessTimeTotal", processTimeTotal);
        blueprint.writeNbt(nbt);
        return nbt;
    }
}
