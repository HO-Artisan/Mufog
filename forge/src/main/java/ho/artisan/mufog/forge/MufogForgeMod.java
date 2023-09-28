package ho.artisan.mufog.forge;

import dev.architectury.platform.forge.EventBuses;
import ho.artisan.mufog.MufogMod;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(MufogMod.MOD_ID)
public class MufogForgeMod {
    public MufogForgeMod() {
        EventBuses.registerModEventBus(MufogMod.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        MufogMod.init();
    }
}