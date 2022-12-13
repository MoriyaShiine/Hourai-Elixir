/*
 * All Rights Reserved (c) 2022 MoriyaShiine
 */

package moriyashiine.houraielixir.common;

import moriyashiine.houraielixir.common.registry.ModItems;
import moriyashiine.houraielixir.common.registry.ModSoundEvents;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;

public class HouraiElixir implements ModInitializer {
	public static final String MOD_ID = "houraielixir";

	@Override
	public void onInitialize() {
		ModItems.init();
		ModSoundEvents.init();
	}

	public static Identifier id(String value) {
		return new Identifier(MOD_ID, value);
	}
}
