package moriyashiine.houraielixir.common.mixin;

import com.mojang.authlib.GameProfile;
import moriyashiine.houraielixir.common.api.accessor.HouraiAccessor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class HouraiHandler extends Entity implements HouraiAccessor {
	private static final TrackedData<Boolean> IMMORTAL = DataTracker.registerData(LivingEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
	
	private static final TrackedData<Integer> WEAKNESS_TIMER = DataTracker.registerData(LivingEntity.class, TrackedDataHandlerRegistry.INTEGER);
	
	protected HouraiHandler(EntityType<? extends LivingEntity> entityType, World world) {
		super(entityType, world);
	}
	
	@Override
	public boolean getImmortal() {
		return dataTracker.get(IMMORTAL);
	}
	
	@Override
	public void setImmortal(boolean immortal) {
		dataTracker.set(IMMORTAL, immortal);
	}
	
	@Override
	public int getWeaknessTimer() {
		return dataTracker.get(WEAKNESS_TIMER);
	}
	
	@Override
	public void setWeaknessTimer(int weaknessTimer) {
		dataTracker.set(WEAKNESS_TIMER, weaknessTimer);
	}
	
	@Shadow
	public abstract float getHealth();
	
	@Shadow
	public abstract float getMaxHealth();
	
	@Shadow
	public abstract void setHealth(float amount);
	
	@SuppressWarnings("UnusedReturnValue")
	@Shadow
	public abstract boolean addStatusEffect(StatusEffectInstance effect);
	
	@Inject(method = "tick", at = @At("HEAD"))
	private void tick(CallbackInfo callbackInfo) {
		if (!world.isClient) {
			int weaknessTimer = getWeaknessTimer();
			if (weaknessTimer > 0) {
				setWeaknessTimer(--weaknessTimer);
				addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, 10, 0, true, false));
				if (weaknessTimer >= 400) {
					addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, 10, 1, true, false));
					addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 10, 0, true, false));
					if (weaknessTimer >= 800) {
						addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, 10, 1, true, false));
						addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 10, 1, true, false));
						addStatusEffect(new StatusEffectInstance(StatusEffects.MINING_FATIGUE, 10, 0, true, false));
						if (weaknessTimer >= 1200) {
							addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, 10, 2, true, false));
							addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 10, 2, true, false));
							addStatusEffect(new StatusEffectInstance(StatusEffects.MINING_FATIGUE, 10, 1, true, false));
						}
					}
				}
			}
		}
	}
	
	@Inject(method = "canHaveStatusEffect", at = @At("HEAD"), cancellable = true)
	private void canHaveStatusEffect(StatusEffectInstance effect, CallbackInfoReturnable<Boolean> callbackInfo) {
		if (getImmortal() && getWeaknessTimer() == 0 && !effect.getEffectType().isBeneficial()) {
			callbackInfo.setReturnValue(false);
		}
	}
	
	@Inject(method = "damage", at = @At("HEAD"), cancellable = true)
	private void cancelDeath(DamageSource source, float amount, CallbackInfoReturnable<Boolean> callbackInfo) {
		if (!world.isClient) {
			if (getImmortal() && getHealth() - amount <= 0) {
				world.playSound(null, getBlockPos(), SoundEvents.BLOCK_CHORUS_FLOWER_GROW, SoundCategory.PLAYERS, 1, 1);
				setHealth(getMaxHealth());
				damage(DamageSource.OUT_OF_WORLD, 0.05f);
				setWeaknessTimer(Math.min(getWeaknessTimer() + 400, 1600));
				Object obj = this;
				//noinspection ConstantConditions
				if (getY() <= -64 && source == DamageSource.OUT_OF_WORLD && obj instanceof ServerPlayerEntity) {
					ServerPlayerEntity player = (ServerPlayerEntity) obj;
					ServerWorld serverWorld = world.getServer().getWorld(player.getSpawnPointDimension());
					if (serverWorld != null) {
						BlockPos spawnPos = player.getSpawnPointPosition();
						player.teleport(serverWorld, spawnPos.getX(), spawnPos.getY(), spawnPos.getZ(), player.yaw, player.pitch);
					}
				}
				callbackInfo.cancel();
			}
		}
	}
	
	@Inject(method = "readCustomDataFromTag", at = @At("TAIL"))
	private void readCustomDataFromTag(CompoundTag tag, CallbackInfo callbackInfo) {
		setImmortal(tag.getBoolean("Immortal"));
		setWeaknessTimer(tag.getInt("WeaknessTimer"));
	}
	
	@Inject(method = "writeCustomDataToTag", at = @At("TAIL"))
	private void writeCustomDataToTag(CompoundTag tag, CallbackInfo callbackInfo) {
		tag.putBoolean("Immortal", getImmortal());
		tag.putInt("WeaknessTimer", getWeaknessTimer());
	}
	
	@Inject(method = "initDataTracker", at = @At("TAIL"))
	private void initDataTracker(CallbackInfo callbackInfo)
	{
		dataTracker.startTracking(IMMORTAL, false);
		dataTracker.startTracking(WEAKNESS_TIMER, 0);
	}
	
	@Mixin(ServerPlayerEntity.class)
	private static abstract class Server extends PlayerEntity implements HouraiAccessor {
		public Server(World world, BlockPos blockPos, GameProfile gameProfile) {
			super(world, blockPos, gameProfile);
		}
		
		@Inject(method = "copyFrom", at = @At("TAIL"))
		private void copyFrom(ServerPlayerEntity oldPlayer, boolean alive, CallbackInfo callbackInfo) {
			HouraiAccessor oldHourai = ((HouraiAccessor) oldPlayer);
			setImmortal(oldHourai.getImmortal());
			setWeaknessTimer(oldHourai.getWeaknessTimer());
		}
	}
}