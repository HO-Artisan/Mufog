package ho.artisan.mufog.init;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import ho.artisan.mufog.MufogMod;
import ho.artisan.mufog.common.item.HammerItem;
import ho.artisan.mufog.common.item.HammerMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.RegistryKeys;

public class MufItems {
    protected static final DeferredRegister<Item> ITEMS = DeferredRegister.create(MufogMod.MOD_ID, RegistryKeys.ITEM);

    public static final ToolMaterial COPPER_MATERIAL;
    public static final RegistrySupplier<Item> COPPER_HAMMER;
    public static final RegistrySupplier<Item> IRON_HAMMER;
    public static final RegistrySupplier<Item> GOLDEN_HAMMER;
    public static final RegistrySupplier<Item> DIAMOND_HAMMER;
    public static final RegistrySupplier<Item> NETHERITE_HAMMER;

    public static void init() {
        ITEMS.register();
    }

    static {
        COPPER_MATERIAL = new HammerMaterial(2, 204, 5.0F, 1.5F, 9,
                Ingredient.ofItems(Items.COPPER_INGOT));

        COPPER_HAMMER = ITEMS.register("copper_hammer", () -> new HammerItem(COPPER_MATERIAL));
        IRON_HAMMER = ITEMS.register("iron_hammer", () -> new HammerItem(COPPER_MATERIAL));
        GOLDEN_HAMMER = ITEMS.register("golden_hammer", () -> new HammerItem(COPPER_MATERIAL));
        DIAMOND_HAMMER = ITEMS.register("diamond_hammer", () -> new HammerItem(COPPER_MATERIAL));
        NETHERITE_HAMMER = ITEMS.register("netherite_hammer", () -> new HammerItem(COPPER_MATERIAL));
    }
}
