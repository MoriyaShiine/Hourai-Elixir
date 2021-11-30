package moriyashiine.houraielixir.common.registry;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import dev.onyxstudios.cca.api.v3.entity.RespawnCopyStrategy;
import moriyashiine.houraielixir.common.HouraiElixir;
import moriyashiine.houraielixir.common.component.entity.HouraiComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;

public class ModComponents implements EntityComponentInitializer {
	public static final ComponentKey<HouraiComponent> HOURAI_COMPONENT = ComponentRegistry.getOrCreate(new Identifier(HouraiElixir.MOD_ID, "hourai"), HouraiComponent.class);
	
	@Override
	public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
		registry.beginRegistration(LivingEntity.class, HOURAI_COMPONENT).respawnStrategy(RespawnCopyStrategy.LOSSLESS_ONLY).end(HouraiComponent::new);
	}
}
