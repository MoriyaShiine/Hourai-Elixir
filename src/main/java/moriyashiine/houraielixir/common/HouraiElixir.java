/*
 * All Rights Reserved (c) 2022 MoriyaShiine
 */

package moriyashiine.houraielixir.common;

import moriyashiine.houraielixir.common.registry.ModItems;
import moriyashiine.houraielixir.common.registry.ModSoundEvents;
import net.fabricmc.api.ModInitializer;

public class HouraiElixir implements ModInitializer {
	public static final String MOD_ID = "houraielixir";

	@Override
	public void onInitialize() {
		ModItems.init();
		ModSoundEvents.init();
	}
}
