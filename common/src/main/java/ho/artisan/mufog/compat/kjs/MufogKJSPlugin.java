package ho.artisan.mufog.compat.kjs;

import dev.latvian.mods.kubejs.KubeJSPlugin;
import dev.latvian.mods.kubejs.recipe.schema.RegisterRecipeSchemasEvent;
import ho.artisan.mufog.MufogMod;

public class MufogKJSPlugin extends KubeJSPlugin {
    @Override
    public void registerRecipeSchemas(RegisterRecipeSchemasEvent event) {
        event.namespace(MufogMod.MOD_ID).register("forging", ForgingRecipeSchema.SCHEMA);
    }
}
