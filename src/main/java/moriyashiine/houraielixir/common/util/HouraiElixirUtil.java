package moriyashiine.houraielixir.common.util;

import moriyashiine.houraielixir.common.component.entity.HouraiComponent;
import moriyashiine.houraielixir.common.registry.ModEntityComponents;
import moriyashiine.houraielixir.common.registry.ModSoundEvents;
import moriyashiine.houraielixir.common.registry.ModWorldComponents;
import net.fabricmc.fabric.api.dimension.v1.FabricDimensions;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.TeleportTarget;

public class HouraiElixirUtil {
	public static boolean isImmortal(LivingEntity entity) {
		return entity.getServer().getOverworld().getComponent(ModWorldComponents.IMMORTAL_ENTITIES).getImmortalEntities().contains(entity.getUuid());
	}

	@SuppressWarnings("ConstantConditions")
	public static float handleDamage(LivingEntity entity, DamageSource source, float amount) {
		if (!entity.world.isClient && isImmortal(entity) && entity.getHealth() - amount <= 0) {
			entity.world.playSound(null, entity.getBlockPos(), ModSoundEvents.ENTITY_GENERIC_RESURRECT, entity.getSoundCategory(), 1, 1);
			if (entity.getY() <= entity.world.getBottomY() && source == DamageSource.OUT_OF_WORLD) {
				ServerWorld world = entity.world.getServer().getOverworld();
				BlockPos worldSpawnPos = world.getSpawnPos();
				if (entity instanceof ServerPlayerEntity player) {
					world = entity.world.getServer().getWorld(player.getSpawnPointDimension());
					worldSpawnPos = player.getSpawnPointPosition() == null ? worldSpawnPos : player.getSpawnPointPosition();
				}
				FabricDimensions.teleport(entity, world, new TeleportTarget(Vec3d.of(worldSpawnPos), Vec3d.ZERO, entity.headYaw, entity.getPitch()));
			}
			entity.setHealth(entity.getMaxHealth());
			HouraiComponent houraiComponent = entity.getComponent(ModEntityComponents.HOURAI);
			houraiComponent.setWeaknessTimer(Math.min(houraiComponent.getWeaknessTimer() + 400, 1600));
			return 0;
		}
		return amount;
	}
}
