/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.houraielixir.common;

import moriyashiine.houraielixir.common.event.HouraiEvent;
import moriyashiine.houraielixir.common.init.ModConsumeEffectTypes;
import moriyashiine.houraielixir.common.init.ModItems;
import moriyashiine.houraielixir.common.init.ModSoundEvents;
import moriyashiine.houraielixir.common.init.ModWorldComponents;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;

public class HouraiElixir implements ModInitializer {
	public static final String MOD_ID = "houraielixir";

	@Override
	public void onInitialize() {
		ModConsumeEffectTypes.init();
		ModItems.init();
		ModSoundEvents.init();
		ServerLivingEntityEvents.ALLOW_DEATH.register(new HouraiEvent());
	}

	public static Identifier id(String value) {
		return Identifier.of(MOD_ID, value);
	}

	public static boolean isImmortal(Entity entity) {
		return ModWorldComponents.IMMORTAL_ENTITIES.get(entity.getServer().getOverworld()).getImmortalEntities().contains(entity.getUuid());
	}
}
