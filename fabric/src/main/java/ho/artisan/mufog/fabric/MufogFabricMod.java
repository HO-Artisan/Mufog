package ho.artisan.mufog.fabric;

import ho.artisan.mufog.MufogMod;
import ho.artisan.mufog.client.MufogModClient;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;

public class MufogFabricMod implements ModInitializer, ClientModInitializer {
    @Override
    public void onInitialize() {
        MufogMod.init();
    }

    @Override
    public void onInitializeClient() {
        MufogModClient.init();
    }
}