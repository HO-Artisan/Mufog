package ho.artisan.mufog.client;

import dev.architectury.registry.client.rendering.BlockEntityRendererRegistry;
import ho.artisan.mufog.client.renderer.ForgingAnvilRenderer;
import ho.artisan.mufog.init.MufBlockEntityTypes;

public class MufogModClient {
    public static void init() {
        BlockEntityRendererRegistry.register(MufBlockEntityTypes.FORGING_ANVIL.get(), ForgingAnvilRenderer::new);

    }
}
