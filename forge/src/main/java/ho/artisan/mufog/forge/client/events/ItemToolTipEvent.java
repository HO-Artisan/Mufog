package ho.artisan.mufog.forge.client.events;

import ho.artisan.mufog.util.MFForgingHelper;
import net.minecraft.nbt.NbtCompound;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class ItemToolTipEvent {
    @SubscribeEvent
    public static void addItemTooltip(ItemTooltipEvent event) {
         var lines = event.getToolTip();
         NbtCompound tag = event.getItemStack().getSubNbt("Forging");
        if (tag != null) {
            MFForgingHelper.blueprintTip(tag.getCompound("Blueprint"), lines);
            MFForgingHelper.resultTip(tag.getCompound("Result"), lines);
            MFForgingHelper.progressTip(tag.getFloat("Progress"), lines);
        }
    }

}
