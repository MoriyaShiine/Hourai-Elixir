/*
 * All Rights Reserved (c) 2022 MoriyaShiine
 */

package moriyashiine.houraielixir.common.registry;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import dev.onyxstudios.cca.api.v3.world.WorldComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.world.WorldComponentInitializer;
import moriyashiine.houraielixir.common.HouraiElixir;
import moriyashiine.houraielixir.common.component.world.ImmortalEntitiesComponent;
import net.minecraft.util.Identifier;

public class ModWorldComponents implements WorldComponentInitializer {
	public static final ComponentKey<ImmortalEntitiesComponent> IMMORTAL_ENTITIES = ComponentRegistry.getOrCreate(new Identifier(HouraiElixir.MOD_ID, "immortal_entities"), ImmortalEntitiesComponent.class);

	@Override
	public void registerWorldComponentFactories(WorldComponentFactoryRegistry registry) {
		registry.register(IMMORTAL_ENTITIES, world -> new ImmortalEntitiesComponent());
	}
}
