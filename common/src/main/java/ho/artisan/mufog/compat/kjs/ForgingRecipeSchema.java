package ho.artisan.mufog.compat.kjs;

import dev.latvian.mods.kubejs.item.InputItem;
import dev.latvian.mods.kubejs.item.OutputItem;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.ItemComponents;
import dev.latvian.mods.kubejs.recipe.component.NumberComponent;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;

public class ForgingRecipeSchema {
    private static final RecipeKey<Float> XP = NumberComponent.FLOAT.key("experience").optional(0F).preferred("xp");
    private static final RecipeKey<Integer> PROCESSTIME = NumberComponent.INT.key("processtime").optional(5).preferred("processTime");
    private static final RecipeKey<InputItem[]> INGREDIENTS = ItemComponents.UNWRAPPED_INPUT_ARRAY.key("ingredients");
    private static final RecipeKey<InputItem> BLUEPRINT = ItemComponents.INPUT.key("blueprint").optional(InputItem.EMPTY);
    private static final RecipeKey<OutputItem> RESULT = ItemComponents.OUTPUT_ID_WITH_COUNT.key("result");
    private static final RecipeKey<OutputItem> FAILURE = ItemComponents.OUTPUT_ID_WITH_COUNT.key("failure").optional(OutputItem.EMPTY);

    public static final RecipeSchema SCHEMA = new RecipeSchema(RESULT, INGREDIENTS, PROCESSTIME, BLUEPRINT, XP, FAILURE).uniqueOutputId(RESULT);
}
