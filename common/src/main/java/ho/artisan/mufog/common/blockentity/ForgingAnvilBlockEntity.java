package ho.artisan.mufog.common.blockentity;

import ho.artisan.mufog.common.item.HammerItem;
import ho.artisan.mufog.common.recipe.ForgingRecipe;
import ho.artisan.mufog.init.MufBlockEntityTypes;
import ho.artisan.mufog.init.MufItems;
import ho.artisan.mufog.init.MufRecipes;
import net.minecraft.block.BlockState;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.player.PlayerEntity;
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

    public boolean canProcess(World world, ItemStack hammer, PlayerEntity player) {
        Optional<ForgingRecipe> optional = world.getRecipeManager().getFirstMatch(MufRecipes.FORGING_RECIPE_TYPE.get(), this, world);
        if (optional.isPresent()) {
            ForgingRecipe recipe = optional.get();
            return HammerItem.getLevel(hammer) >= recipe.getLevel() && !player.getItemCooldownManager().isCoolingDown(hammer.getItem());
        }
        return false;
    }

    public boolean process(World world, ItemStack hammer) {
        Optional<ForgingRecipe> optional = world.getRecipeManager().getFirstMatch(MufRecipes.FORGING_RECIPE_TYPE.get(), this, world);
        if (optional.isPresent()) {
            boolean flag = true;
            ForgingRecipe recipe = optional.get();
            Ingredient recipeBlueprint = recipe.getBlueprint();

            for (ItemStack stack : getItemStack()) {
                if (hasForgingNBT(stack)) {
                    processTime = getProcessTime(stack);
                    processTimeTotal = getProcessTimeTotal(stack);
                }
            }

            processTimeTotal = recipe.getProcesstime();
            processTime++;

            for (ItemStack stack : getItemStack()) {
                stack.setSubNbt("Forging", createForgingNBT(processTime, processTimeTotal, blueprint, recipe.getOutput(world.getRegistryManager())));
            }

            markDirty();

            if (processTime == processTimeTotal) {
                clear();

                if (world.getGameRules().getBoolean(GameRules.DO_TILE_DROPS) && world instanceof ServerWorld serverWorld) {
                    ExperienceOrbEntity.spawn(serverWorld, Vec3d.ofCenter(pos), 1 + (int) Math.floor(5 * recipe.getExperience()));
                }

                boolean success = world.random.nextFloat() < recipe.getChance();

                if (success) {
                    stack.push(recipe.craft(this, world.getRegistryManager()));
                }else {
                    if (recipe.getFailure() != ItemStack.EMPTY)
                        stack.push(recipe.getFailure());
                    flag = false;
                }

                processTimeTotal = 0;
                processTime = 0;
            }

            markDirty();
            return flag;
        }
        return false;
    }

    public ItemStack getBlueprint(ItemStack stack) {
        return ItemStack.fromNbt(getForgingNBT(stack).getCompound("Blueprint"));
    }

    public int getProcessTime(ItemStack stack) {
        return getForgingNBT(stack).getInt("ProcessTime");
    }

    public int getProcessTimeTotal(ItemStack stack) {
        return getForgingNBT(stack).getInt("ProcessTimeTotal");
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
        NbtCompound nbt2 = new NbtCompound();
        new ItemStack(MufItems.FORGING_ANVIL.get()).writeNbt(nbt2);
        nbt.put("Blueprint", nbt2);
        return nbt;
    }

    public NbtCompound createForgingNBT(int processTime, int processTimeTotal, ItemStack blueprint, ItemStack result) {
        NbtCompound nbt = new NbtCompound();
        nbt.putInt("ProcessTime", processTime);
        nbt.putInt("ProcessTimeTotal", processTimeTotal);

        NbtCompound nbt2 = new NbtCompound();
        blueprint.writeNbt(nbt2);
        nbt.put("Blueprint", nbt2);

        NbtCompound nbt3 = new NbtCompound();
        result.writeNbt(nbt3);
        nbt.put("Result", nbt3);

        nbt.putFloat("Progress", ((float) processTime / (float) processTimeTotal));

        return nbt;
    }
}
