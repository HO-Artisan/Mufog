package ho.artisan.mufog.compat.rei.forging;

import com.google.common.collect.ImmutableList;
import ho.artisan.mufog.common.item.HammerItem;
import ho.artisan.mufog.common.recipe.ForgingRecipe;
import ho.artisan.mufog.compat.rei.MufogREIPlugin;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static ho.artisan.mufog.util.MFForgingHelper.translateChanceTip;


@Environment(EnvType.CLIENT)
public class ForgingREIDisplay extends BasicDisplay {

    private final EntryIngredient blueprint;
    private final EntryIngredient hammer;
    private final int hammerTimes;

    public ForgingREIDisplay(ForgingRecipe recipe) {
        super(EntryIngredients.ofIngredients(recipe.getIngredients()), Collections.singletonList(EntryIngredients.of(recipe.getOutput(null)).map(stack -> stack.copy().tooltip(translateChanceTip(recipe.getChance())))));

        blueprint = EntryIngredients.ofIngredient(recipe.getBlueprint()).map(stack -> stack.copy().tooltip(Text.translatable("tip.mufog.manage.blueprint").formatted(Formatting.BLUE)));

        hammer = EntryIngredients.ofItemTag(HammerItem.matchLevel(recipe.getLevel()));
        hammerTimes = recipe.getProcesstime();
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return MufogREIPlugin.FORGING;
    }

    @Override
    public List<EntryIngredient> getRequiredEntries() {
        List<EntryIngredient> requiredEntries = new ArrayList<>(super.getRequiredEntries());
        requiredEntries.add(getBlueprint());
        requiredEntries.add(getHammer());

        return ImmutableList.copyOf(requiredEntries);
    }

    @Override
    public List<EntryIngredient> getInputEntries() {
        List<EntryIngredient> inputEntryList = new ArrayList<>(super.getInputEntries());
        inputEntryList.add(getBlueprint());
        inputEntryList.add(getHammer());

        return ImmutableList.copyOf(inputEntryList);
    }

    public EntryIngredient getBlueprint() {
        return blueprint;
    }

    public List<EntryIngredient> getIngredientEntries() {
        return super.getInputEntries();
    }

    public EntryIngredient getHammer() {
        return hammer;
    }

    public int getHammerTimes() {
        return hammerTimes;
    }

}
