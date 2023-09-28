package ho.artisan.mufog.common.item;

import net.minecraft.item.Item;
import net.minecraft.util.Rarity;

public class BlueprintItem extends Item {
    public BlueprintItem(Settings settings) {
        super(settings.maxCount(1).rarity(Rarity.UNCOMMON));
    }
}
