package moriyashiine.houraielixir.common.registry;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistryV3;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import dev.onyxstudios.cca.api.v3.entity.RespawnCopyStrategy;
import moriyashiine.houraielixir.api.component.HouraiComponent;
import moriyashiine.houraielixir.common.HouraiElixir;
import net.minecraft.util.Identifier;

public class HEComponents implements EntityComponentInitializer {
	public static final ComponentKey<HouraiComponent> HOURAI_COMPONENT = ComponentRegistryV3.INSTANCE.getOrCreate(new Identifier(HouraiElixir.MODID, "hourai"), HouraiComponent.class);
	
	@Override
	public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
		registry.registerForPlayers(HOURAI_COMPONENT, HouraiComponent::new, RespawnCopyStrategy.LOSSLESS_ONLY);
	}
}
