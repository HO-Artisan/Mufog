package ho.artisan.mufog.util;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.text.NumberFormat;
import java.util.List;

public class MFForgingHelper {
    public static void blueprintTip(NbtCompound tag, List<Text> tooltip) {
        ItemStack blueprint = ItemStack.fromNbt(tag);
        String key = !blueprint.isEmpty() ? blueprint.getTranslationKey() : "tip.mufog.forging.blueprint.air";
        var text = Text.translatable("tip.mufog.forging.blueprint");
        text.append(Text.translatable(key)).formatted(Formatting.BLUE);
        tooltip.add(text);
    }

    public static void resultTip(NbtCompound tag, List<Text> tooltip) {
        ItemStack result = ItemStack.fromNbt(tag);
        String key = !result.isEmpty() ? result.getTranslationKey() : "tip.mufog.forging.blueprint.air";
        var text = Text.translatable("tip.mufog.forging.output");
        text.append(Text.translatable(key)).formatted(Formatting.GREEN);
        tooltip.add(text);
    }

    public static void progressTip(float progress, List<Text> tooltip) {
        NumberFormat fmt = NumberFormat.getPercentInstance();
        fmt.setMaximumFractionDigits(1);
        var text = Text.translatable("tip.mufog.forging.progress");
        text.append(fmt.format(progress)).formatted(Formatting.YELLOW);
        tooltip.add(text);
    }

    public static Text translateChanceTip(float chance) {
        NumberFormat fmt = NumberFormat.getPercentInstance();
        fmt.setMaximumFractionDigits(2);
        Formatting color = chance < 0.25F ? Formatting.RED : Formatting.YELLOW;
        return Text.translatable("tip.mufog.manage.chance").append(fmt.format(chance)).append(Text.translatable("tip.mufog.manage.full_stop")).formatted(color);
    }
}
