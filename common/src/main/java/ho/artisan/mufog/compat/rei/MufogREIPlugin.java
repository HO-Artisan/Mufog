package ho.artisan.mufog.compat.rei;

import ho.artisan.mufog.MufogMod;
import ho.artisan.mufog.common.recipe.ForgingRecipe;
import ho.artisan.mufog.compat.rei.forging.ForgingREICategory;
import ho.artisan.mufog.compat.rei.forging.ForgingREIDisplay;
import ho.artisan.mufog.init.MufItems;
import ho.artisan.mufog.init.MufRecipes;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class MufogREIPlugin implements REIClientPlugin {
    public static final CategoryIdentifier<ForgingREIDisplay> FORGING = CategoryIdentifier.of(MufogMod.MOD_ID, "plugins/cutting");

    @Override
    public void registerCategories(CategoryRegistry registry) {
        registry.add(new ForgingREICategory());
        registry.addWorkstations(FORGING, EntryStacks.of(MufItems.FORGING_ANVIL.get()));
    }

    @Override
    public void registerDisplays(DisplayRegistry registry) {
        registry.registerRecipeFiller(ForgingRecipe.class, MufRecipes.FORGING_RECIPE_TYPE.get(), ForgingREIDisplay::new);
    }
}
