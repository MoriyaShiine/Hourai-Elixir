package moriyashiine.houraielixir.api;

import moriyashiine.houraielixir.common.world.ModUniversalWorldState;
import net.minecraft.entity.LivingEntity;

public class HouraiElixirAPI {
	public static boolean isImmortal(LivingEntity entity) {
		return ModUniversalWorldState.get(entity.world).immortalEntities.contains(entity.getUuid());
	}
}
