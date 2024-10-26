/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.houraielixir.common.init;

import moriyashiine.houraielixir.common.HouraiElixir;
import moriyashiine.houraielixir.common.component.world.ImmortalEntitiesComponent;
import net.minecraft.world.World;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistry;
import org.ladysnake.cca.api.v3.world.WorldComponentFactoryRegistry;
import org.ladysnake.cca.api.v3.world.WorldComponentInitializer;

public class ModWorldComponents implements WorldComponentInitializer {
	public static final ComponentKey<ImmortalEntitiesComponent> IMMORTAL_ENTITIES = ComponentRegistry.getOrCreate(HouraiElixir.id("immortal_entities"), ImmortalEntitiesComponent.class);

	@Override
	public void registerWorldComponentFactories(WorldComponentFactoryRegistry registry) {
		registry.registerFor(World.OVERWORLD, IMMORTAL_ENTITIES, world -> new ImmortalEntitiesComponent());
	}
}
