package ho.artisan.mufog.init;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import ho.artisan.mufog.MufogMod;
import ho.artisan.mufog.common.block.ForgingAnvilBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.registry.RegistryKeys;

public class MufBlocks {
    private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(MufogMod.MOD_ID, RegistryKeys.BLOCK);

    public static final RegistrySupplier<Block> FORGING_ANVIL;

    public static void init() {
        BLOCKS.register();
    }

    static {
        FORGING_ANVIL = BLOCKS.register("forging_anvil",
                () -> new ForgingAnvilBlock(AbstractBlock.Settings.copy(Blocks.ANVIL)));
    }
}
