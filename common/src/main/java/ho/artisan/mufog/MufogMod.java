package ho.artisan.mufog;

import ho.artisan.mufog.init.*;
import net.minecraft.util.Identifier;

public class MufogMod {
	public static final String MOD_ID = "mufog";

	public static void init() {
		MufBlocks.init();
		MufBlockEntityTypes.init();
		MufItems.init();
		MufSounds.init();
		MufRecipes.init();
		MufItemGroups.init();
	}

	public static Identifier genID(String path) {
		return new Identifier(MOD_ID, path);
	}
}
