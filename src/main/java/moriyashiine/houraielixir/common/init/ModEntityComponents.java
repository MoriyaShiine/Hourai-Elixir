/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.houraielixir.common.init;

import moriyashiine.houraielixir.common.HouraiElixir;
import moriyashiine.houraielixir.common.component.entity.HouraiComponent;
import net.minecraft.entity.LivingEntity;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentFactoryRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentInitializer;

public class ModEntityComponents implements EntityComponentInitializer {
	public static final ComponentKey<HouraiComponent> HOURAI = ComponentRegistry.getOrCreate(HouraiElixir.id("hourai"), HouraiComponent.class);

	@Override
	public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
		registry.registerFor(LivingEntity.class, HOURAI, HouraiComponent::new);
	}
}
