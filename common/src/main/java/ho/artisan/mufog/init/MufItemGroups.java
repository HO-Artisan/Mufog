package ho.artisan.mufog.init;

import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import ho.artisan.mufog.MufogMod;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;

public class MufItemGroups {
    private static final DeferredRegister<ItemGroup> TABS =
            DeferredRegister.create(MufogMod.MOD_ID, RegistryKeys.ITEM_GROUP);

    public static final RegistrySupplier<ItemGroup> MAIN;

    public static void init() {
        TABS.register();
    }

    static {
        MAIN = TABS.register(
                "main",
                () -> CreativeTabRegistry.create(
                        Text.translatable("itemGroup.mufog.main"),
                        () -> new ItemStack(MufItems.NETHERITE_HAMMER.get())
                )
        );

        MufItems.ITEMS.iterator().forEachRemaining(item ->
                CreativeTabRegistry.appendStack(MAIN.getKey(), new ItemStack(item.get())));
    }
}
