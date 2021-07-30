package moriyashiine.houraielixir.common;

import moriyashiine.houraielixir.api.component.HouraiComponent;
import moriyashiine.houraielixir.common.item.HouraiElixirItem;
import moriyashiine.houraielixir.common.world.HEUniversalWorldState;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.dimension.v1.FabricDimensions;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.TeleportTarget;

@SuppressWarnings("ConstantConditions")
public class HouraiElixir implements ModInitializer {
	public static final String MODID = "houraielixir";
	
	public static final SoundEvent ENTITY_GENERIC_RESURRECT = new SoundEvent(new Identifier(MODID, "entity.generic.resurrect"));
	
	@Override
	public void onInitialize() {
		Registry.register(Registry.ITEM, new Identifier(MODID, "hourai_elixir"), new HouraiElixirItem(new Item.Settings().group(ItemGroup.MISC).rarity(Rarity.EPIC).maxCount(1)));
		Registry.register(Registry.SOUND_EVENT, new Identifier(MODID, "entity.generic.resurrect"), ENTITY_GENERIC_RESURRECT);
	}
	
	public static boolean isImmortal(LivingEntity entity) {
		return HEUniversalWorldState.get(entity.world).immortalEntities.contains(entity.getUuid());
	}
	
	public static float handleDamage(LivingEntity entity, DamageSource source, float amount) {
		if (!entity.world.isClient && isImmortal(entity) && entity.getHealth() - amount <= 0) {
			entity.world.playSound(null, entity.getBlockPos(), ENTITY_GENERIC_RESURRECT, entity.getSoundCategory(), 1, 1);
			if (entity.getY() <= -64 && source == DamageSource.OUT_OF_WORLD) {
				ServerWorld world;
				BlockPos worldSpawnPos;
				if (entity instanceof ServerPlayerEntity player) {
					world = player.world.getServer().getWorld(player.getSpawnPointDimension());
					worldSpawnPos = player.getSpawnPointPosition() == null ? world.getSpawnPos() : player.getSpawnPointPosition();
				}
				else {
					world = entity.world.getServer().getOverworld();
					worldSpawnPos = world.getSpawnPos();
				}
				FabricDimensions.teleport(entity, world, new TeleportTarget(Vec3d.of(worldSpawnPos), Vec3d.ZERO, entity.headYaw, entity.getPitch()));
			}
			entity.setHealth(entity.getMaxHealth());
			HouraiComponent.maybeGet(entity).ifPresent(houraiComponent -> houraiComponent.setWeaknessTimer(Math.min(houraiComponent.getWeaknessTimer() + 400, 1600)));
			return 0;
		}
		return amount;
	}
}
