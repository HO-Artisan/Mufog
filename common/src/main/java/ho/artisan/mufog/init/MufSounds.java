package ho.artisan.mufog.init;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import ho.artisan.mufog.MufogMod;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.SoundEvent;

public class MufSounds {
    private static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(MufogMod.MOD_ID, RegistryKeys.SOUND_EVENT);

    public static final RegistrySupplier<SoundEvent> FORGING;
    public static final RegistrySupplier<SoundEvent> FORGING_FAIL;

    public static void init() {
        SOUNDS.register();
    }

    static {
        FORGING = SOUNDS.register("forging", () -> SoundEvent.of(MufogMod.genID("forging")));
        FORGING_FAIL = SOUNDS.register("forging_fail", () -> SoundEvent.of(MufogMod.genID("forging_fail")));
    }
}
