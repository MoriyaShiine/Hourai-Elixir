/*
 * All Rights Reserved (c) 2022 MoriyaShiine
 */

package moriyashiine.houraielixir.common.registry;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import moriyashiine.houraielixir.common.HouraiElixir;
import moriyashiine.houraielixir.common.component.entity.HouraiComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;

public class ModEntityComponents implements EntityComponentInitializer {
	public static final ComponentKey<HouraiComponent> HOURAI = ComponentRegistry.getOrCreate(new Identifier(HouraiElixir.MOD_ID, "hourai"), HouraiComponent.class);

	@Override
	public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
		registry.registerFor(LivingEntity.class, HOURAI, HouraiComponent::new);
	}
}
