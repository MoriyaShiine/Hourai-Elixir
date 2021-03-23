package moriyashiine.houraielixir.common;

import ladysnake.requiem.api.v1.possession.PossessionComponent;
import moriyashiine.houraielixir.api.accessor.HouraiAccessor;
import moriyashiine.houraielixir.common.integration.requiem.HERequiemCompat;
import moriyashiine.houraielixir.common.item.HouraiElixirItem;
import moriyashiine.houraielixir.common.world.HEUniversalWorldState;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;

import java.util.UUID;

@SuppressWarnings("ConstantConditions")
public class HouraiElixir implements ModInitializer {
	public static final String MODID = "houraielixir";
	
	private static boolean isRequiemLoaded = false;
	
	public static SoundEvent ENTITY_GENERIC_RESURRECT = new SoundEvent(new Identifier(MODID, "entity.generic.resurrect"));
	
	@Override
	public void onInitialize() {
		ServerPlayerEvents.COPY_FROM.register((oldPlayer, newPlayer, alive) -> ((HouraiAccessor) newPlayer).setWeaknessTimer(((HouraiAccessor) oldPlayer).getWeaknessTimer()));
		Registry.register(Registry.ITEM, new Identifier(MODID, "hourai_elixir"), new HouraiElixirItem(new Item.Settings().group(ItemGroup.MISC).rarity(Rarity.EPIC).maxCount(1)));
		Registry.register(Registry.SOUND_EVENT, new Identifier(MODID, "entity.generic.resurrect"), ENTITY_GENERIC_RESURRECT);
		isRequiemLoaded = FabricLoader.getInstance().isModLoaded("requiem");
		if (isRequiemLoaded) {
			HERequiemCompat.init();
		}
	}
	
	public static LivingEntity getActualEntity(PlayerEntity player) {
		if (isRequiemLoaded) {
			MobEntity possessedEntity = PossessionComponent.getPossessedEntity(player);
			if (possessedEntity != null) {
				return possessedEntity;
			}
		}
		return player;
	}
	
	public static boolean isImmortal(LivingEntity entity) {
		if (!entity.world.isClient) {
			HEUniversalWorldState worldState = HEUniversalWorldState.get(entity.world);
			UUID uuid = entity.getUuid();
			if (isRequiemLoaded && entity instanceof MobEntity) {
				for (PlayerEntity player : entity.getServer().getPlayerManager().getPlayerList()) {
					if (PossessionComponent.get(player).getPossessedEntity() == entity) {
						uuid = player.getUuid();
					}
				}
			}
			return worldState.immortalEntities.contains(uuid);
		}
		return false;
	}
	
	@SuppressWarnings("ConstantConditions")
	public static float handleDamage(LivingEntity entity, DamageSource source, float amount) {
		if (!entity.world.isClient && isImmortal(entity) && entity.getHealth() - amount <= 0) {
			entity.world.playSound(null, entity.getBlockPos(), ENTITY_GENERIC_RESURRECT, entity.getSoundCategory(), 1, 1);
			LivingEntity actualEntity = entity;
			boolean outOfWorld = entity.getY() <= -64 && source == DamageSource.OUT_OF_WORLD;
			if (entity instanceof ServerPlayerEntity) {
				ServerPlayerEntity player = (ServerPlayerEntity) entity;
				actualEntity = getActualEntity(player);
				if (outOfWorld) {
					ServerWorld serverWorld = entity.world.getServer().getWorld(player.getSpawnPointDimension());
					if (serverWorld != null) {
						BlockPos spawnPos = player.getSpawnPointPosition();
						if (spawnPos == null) {
							spawnPos = serverWorld.getSpawnPos();
						}
						actualEntity.setWorld(serverWorld);
						actualEntity.teleport(spawnPos.getX(), spawnPos.getY(), spawnPos.getZ());
						if (actualEntity != player) {
							player.setWorld(serverWorld);
							player.teleport(spawnPos.getX(), spawnPos.getY(), spawnPos.getZ());
						}
					}
				}
			}
			else if (outOfWorld) {
				ServerWorld overworld = entity.world.getServer().getOverworld();
				BlockPos spawnPos = overworld.getSpawnPos();
				entity.setWorld(overworld);
				entity.teleport(spawnPos.getX(), spawnPos.getY(), spawnPos.getZ());
			}
			actualEntity.setHealth(actualEntity.getMaxHealth());
			((HouraiAccessor) actualEntity).setWeaknessTimer(Math.min(((HouraiAccessor) actualEntity).getWeaknessTimer() + 400, 1600));
			return 0;
		}
		return amount;
	}
}
