package ho.artisan.mufog.init;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import ho.artisan.mufog.MufogMod;
import ho.artisan.mufog.common.blockentity.ForgingAnvilBlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.RegistryKeys;

public class MufBlockEntityTypes {
    private static final DeferredRegister<BlockEntityType<?>> TYPES = DeferredRegister.create(MufogMod.MOD_ID, RegistryKeys.BLOCK_ENTITY_TYPE);

    public static final RegistrySupplier<BlockEntityType<ForgingAnvilBlockEntity>> FORGING_ANVIL;

    public static void init() {
        TYPES.register();
    }

    static {
        FORGING_ANVIL = TYPES.register("forging_anvil",
                () -> BlockEntityType.Builder.create(ForgingAnvilBlockEntity::new, MufBlocks.FORGING_ANVIL.get())
                        .build(null));
    }
}
