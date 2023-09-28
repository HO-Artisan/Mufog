package ho.artisan.mufog.common.recipe;

import ho.artisan.mufog.common.blockentity.ForgingAnvilBlockEntity;
import ho.artisan.mufog.init.MufBlocks;
import ho.artisan.mufog.init.MufRecipes;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

import java.util.List;

public class ForgingRecipe implements Recipe<ForgingAnvilBlockEntity> {
    protected final Ingredient[] ingredients;
    protected final Ingredient blueprint;
    protected final ItemStack result;
    protected final ItemStack failure;
    protected final float experience;
    protected final int processtime;
    protected final int level;
    protected final float chance;
    protected final Identifier id;

    public ForgingRecipe(Identifier id, Ingredient[] ingredients, Ingredient blueprint, ItemStack result, ItemStack failure, int processtime, int level, float chance, float experience) {
        this.id = id;
        this.ingredients = ingredients;
        this.blueprint = blueprint;
        this.result = result;
        this.processtime = processtime;
        this.level = level;
        this.chance = chance;
        this.experience = experience;
        this.failure = failure;
    }

    @Override
    public boolean matches(ForgingAnvilBlockEntity anvil, World world) {
        boolean flag1 = this.blueprint.test(anvil.blueprint);
        boolean flag2 = this.ingredients.length == anvil.size();
        boolean flag3 = true;
        boolean flag4 = true;
        int process = -1;
        for (int i = 0; i < anvil.size(); i++) {
            ItemStack itemStack = anvil.getStack(i);
            flag3 = flag3 && this.ingredients[i].test(itemStack);
            if (anvil.hasForgingNBT(itemStack)) {
                flag1 = flag1 && this.blueprint.test(anvil.getBlueprint(itemStack));
                if (process < 0) {
                    process = anvil.getProcessTime(itemStack);
                }else {
                    flag4 = flag4 && process == anvil.getProcessTime(itemStack);
                }
            }
        }
        return flag1 && flag2 && flag3 && flag4;
    }

    @Override
    public ItemStack craft(ForgingAnvilBlockEntity inventory, DynamicRegistryManager registryManager) {
        return this.result.copy();
    }

    @Override
    public ItemStack getOutput(DynamicRegistryManager registryManager) {
        return this.result;
    }

    @Override
    public boolean fits(int width, int height) {
        return true;
    }

    @Override
    public Identifier getId() {
        return this.id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return MufRecipes.FORGING_RECIPE_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return MufRecipes.FORGING_RECIPE_TYPE.get();
    }

    @Override
    public boolean isIgnoredInRecipeBook() {
        return true;
    }

    @Override
    public DefaultedList<Ingredient> getIngredients() {
        DefaultedList<Ingredient> list = DefaultedList.of();
        list.addAll(List.of(this.ingredients));
        list.add(this.blueprint);
        return list;
    }

    @Override
    public ItemStack createIcon() {
        return new ItemStack(MufBlocks.FORGING_ANVIL.get());
    }

    public Ingredient getBlueprint() {
        return blueprint;
    }

    public int getProcesstime() {
        return processtime;
    }

    public float getChance() {
        return chance;
    }

    public float getExperience() {
        return experience;
    }

    public int getLevel() {
        return level;
    }

    public ItemStack getFailure() {
        return failure;
    }

    public boolean hasBlueprint() {
        return this.blueprint != Ingredient.EMPTY;
    }

    public boolean hasFailure() {
        return this.failure != ItemStack.EMPTY;
    }
}
