package ho.artisan.mufog.fabric;

import ho.artisan.mufog.MufogMod;
import ho.artisan.mufog.client.MufogModClient;
import ho.artisan.mufog.util.MFForgingHelper;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.minecraft.nbt.NbtCompound;

public class MufogFabricMod implements ModInitializer, ClientModInitializer {
    @Override
    public void onInitialize() {
        MufogMod.init();
    }

    @Override
    public void onInitializeClient() {
        MufogModClient.init();

        ItemTooltipCallback.EVENT.register((stack, context, lines) -> {
            NbtCompound tag = stack.getSubNbt("Forging");
            if (tag != null) {
                MFForgingHelper.blueprintTip(tag.getCompound("Blueprint"), lines);
                MFForgingHelper.resultTip(tag.getCompound("Result"), lines);
                MFForgingHelper.progressTip(tag.getFloat("Progress"), lines);
            }
        });
    }
}