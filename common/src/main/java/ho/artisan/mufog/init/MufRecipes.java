package ho.artisan.mufog.init;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import ho.artisan.mufog.MufogMod;
import ho.artisan.mufog.common.recipe.ForgingRecipeSerializer;
import ho.artisan.mufog.common.recipe.ForgingRecipeType;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.RegistryKeys;

public class MufRecipes {
    private static final DeferredRegister<RecipeType<?>> TYPES = DeferredRegister.create(MufogMod.MOD_ID, RegistryKeys.RECIPE_TYPE);
    private static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS = DeferredRegister.create(MufogMod.MOD_ID, RegistryKeys.RECIPE_SERIALIZER);

    public static final RegistrySupplier<RecipeType<?>> FORGING_RECIPE_TYPE;

    public static final RegistrySupplier<RecipeSerializer<?>> FORGING_RECIPE_SERIALIZER;

    public static void init() {
        TYPES.register();
        SERIALIZERS.register();
    }

    static {
        FORGING_RECIPE_TYPE = TYPES.register("forging", ForgingRecipeType::new);
        FORGING_RECIPE_SERIALIZER = SERIALIZERS.register("forging", ForgingRecipeSerializer::new);
    }
}
