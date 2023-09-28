package ho.artisan.mufog.fabric;

import ho.artisan.mufog.MufogMod;
import net.fabricmc.api.ModInitializer;

public class MufogFabricMod implements ModInitializer {
    @Override
    public void onInitialize() {
        MufogMod.init();
    }
}