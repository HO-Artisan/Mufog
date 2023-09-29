package ho.artisan.mufog.common.item;

import net.minecraft.item.*;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;

import static ho.artisan.mufog.MufogMod.genID;


public class HammerItem extends MiningToolItem {
    public static final TagKey<Item> HAMMER = TagKey.of(RegistryKeys.ITEM, genID("hammer"));
    public static final TagKey<Item> LEVEL_1 = TagKey.of(RegistryKeys.ITEM, genID("hammer/level_1"));
    public static final TagKey<Item> LEVEL_2 = TagKey.of(RegistryKeys.ITEM, genID("hammer/level_2"));
    public static final TagKey<Item> LEVEL_3 = TagKey.of(RegistryKeys.ITEM, genID("hammer/level_3"));
    public static final TagKey<Item> LEVEL_4 = TagKey.of(RegistryKeys.ITEM, genID("hammer/level_4"));

    public HammerItem(ToolMaterial material) {
        super(5.5F, -3.2F, material, BlockTags.PICKAXE_MINEABLE, new Item.Settings());
    }

    public static boolean isHammer(ItemStack item) {
        return item.isIn(HAMMER) && item.isDamageable();
    }

    public static int getLevel(ItemStack item) {
        int level = 0;
        if (isHammer(item)) {
            if (item.isIn(LEVEL_4))
                level = 4;
            else if (item.isIn(LEVEL_3))
                level = 3;
            else if (item.isIn(LEVEL_2))
                level = 2;
            else if (item.isIn(LEVEL_1))
                level = 1;
            else if (item.getItem() instanceof ToolItem toolItem) {
                level = toolItem.getMaterial().getMiningLevel();
            }
        }
        return level;
    }

    public static TagKey<Item> matchLevel(int level) {
        return switch (level) {
            default -> LEVEL_1;
            case 2 -> LEVEL_2;
            case 3 -> LEVEL_3;
            case 4 -> LEVEL_4;
        };
    }
}
