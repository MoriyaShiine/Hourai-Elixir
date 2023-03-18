/*
 * All Rights Reserved (c) 2022 MoriyaShiine
 */

package moriyashiine.houraielixir.common;

import moriyashiine.houraielixir.common.event.HouraiEvent;
import moriyashiine.houraielixir.common.registry.ModItems;
import moriyashiine.houraielixir.common.registry.ModSoundEvents;
import moriyashiine.houraielixir.common.registry.ModWorldComponents;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;

public class HouraiElixir implements ModInitializer {
	public static final String MOD_ID = "houraielixir";

	@Override
	public void onInitialize() {
		ModItems.init();
		ModSoundEvents.init();
		ServerLivingEntityEvents.ALLOW_DEATH.register(new HouraiEvent());
	}

	public static Identifier id(String value) {
		return new Identifier(MOD_ID, value);
	}

	public static boolean isImmortal(LivingEntity entity) {
		return ModWorldComponents.IMMORTAL_ENTITIES.get(entity.getServer().getOverworld()).getImmortalEntities().contains(entity.getUuid());
	}
}
