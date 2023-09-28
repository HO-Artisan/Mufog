package ho.artisan.mufog.forge;

import dev.architectury.platform.forge.EventBuses;
import ho.artisan.mufog.MufogMod;
import ho.artisan.mufog.client.MufogModClient;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(MufogMod.MOD_ID)
public class MufogForgeMod {
    public MufogForgeMod() {
        EventBuses.registerModEventBus(MufogMod.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        MufogMod.init();
    }

    @SubscribeEvent
    public void onClientSetup(FMLClientSetupEvent event) {
        MufogModClient.init();
    }
}