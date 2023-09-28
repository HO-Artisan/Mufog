package ho.artisan.mufog.common.recipe;


import com.google.gson.*;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.ShapedRecipe;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;

import java.util.ArrayList;
import java.util.List;


public class ForgingRecipeSerializer implements RecipeSerializer<ForgingRecipe> {
    public ForgingRecipeSerializer() {}

    @Override
    public ForgingRecipe read(Identifier id, JsonObject jsonObject) {
        Ingredient[] ingredients;
        Ingredient blueprint = Ingredient.EMPTY;
        ItemStack failure = ItemStack.EMPTY;
        ItemStack result = ShapedRecipe.outputFromJson(JsonHelper.getObject(jsonObject, "result"));
        int processtime = JsonHelper.getInt(jsonObject, "processtime");
        int level = JsonHelper.getInt(jsonObject, "level");
        float chance = 1f;
        float experience = 0f;

        if (jsonObject.has("blueprint"))
            blueprint = Ingredient.fromJson(jsonObject.get("blueprint"), false);
        if (jsonObject.has("failure"))
            failure = ShapedRecipe.outputFromJson(JsonHelper.getObject(jsonObject, "failure"));
        if (jsonObject.has("chance"))
            chance = JsonHelper.getFloat(jsonObject, "chance");
        if (jsonObject.has("experience"))
            experience = JsonHelper.getFloat(jsonObject, "experience");

        if (jsonObject.get("ingredients") instanceof JsonArray array) {
            List<Ingredient> list = new ArrayList<>();
            for (JsonElement element : array) {
                var ingredient = Ingredient.fromJson(element, false);
                if (!ingredient.isEmpty())
                    list.add(ingredient);
            }
            ingredients = list.toArray(list.toArray(new Ingredient[0]));
        } else {
            throw new JsonParseException("No ingredients for forging recipe");
        }

        return new ForgingRecipe(id, ingredients, blueprint, result, failure, processtime, level, chance, experience);
    }

    @Override
    public void write(PacketByteBuf buf, ForgingRecipe recipe) {
        buf.writeInt(recipe.ingredients.length);
        for (Ingredient ingredient : recipe.ingredients) {
            ingredient.write(buf);
        }

        recipe.blueprint.write(buf);
        buf.writeItemStack(recipe.result);
        buf.writeItemStack(recipe.failure);

        buf.writeInt(recipe.processtime);
        buf.writeInt(recipe.level);
        buf.writeFloat(recipe.chance);
        buf.writeFloat(recipe.experience);
    }

    @Override
    public ForgingRecipe read(Identifier id, PacketByteBuf buf) {
        Ingredient[] ingredients = new Ingredient[buf.readInt()];
        for (int i = 0; i < ingredients.length; i++) {
            ingredients[i] = Ingredient.fromPacket(buf);
        }

        Ingredient blueprint = Ingredient.fromPacket(buf);
        ItemStack result = buf.readItemStack();
        ItemStack failure = buf.readItemStack();

        int processtime = buf.readInt();
        int level = buf.readInt();
        float chance = buf.readFloat();
        float experience = buf.readFloat();

        return new ForgingRecipe(id, ingredients, blueprint, result, failure, processtime, level, chance, experience);
    }
}
