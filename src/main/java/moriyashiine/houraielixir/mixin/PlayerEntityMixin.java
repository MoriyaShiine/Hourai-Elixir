package moriyashiine.houraielixir.mixin;

import moriyashiine.houraielixir.api.accessor.HouraiAccessor;
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
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@SuppressWarnings("ConstantConditions")
@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity implements HouraiAccessor {
	private boolean immortal = false;
	private int weaknessTimer = 0;
	
	protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
		super(entityType, world);
	}
	
	@Override
	public boolean getImmortal() {
		return immortal;
	}
	
	@Override
	public void setImmortal(boolean immortal) {
		this.immortal = immortal;
	}
	
	@Override
	public int getWeaknessTimer() {
		return weaknessTimer;
	}
	
	@Override
	public void setWeaknessTimer(int weaknessTimer) {
		this.weaknessTimer = weaknessTimer;
	}
	
	@Inject(method = "tick", at = @At("TAIL"))
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
	
	@ModifyVariable(method = "applyDamage", at = @At(value = "INVOKE", shift = At.Shift.BEFORE, ordinal = 0, target = "Lnet/minecraft/entity/player/PlayerEntity;getHealth()F"))
	private float damage(float amount, DamageSource source) {
		if (!world.isClient && getImmortal() && getHealth() - amount <= 0) {
			world.playSound(null, getBlockPos(), SoundEvents.BLOCK_CHORUS_FLOWER_GROW, SoundCategory.PLAYERS, 1, 1);
			setHealth(getMaxHealth());
			setWeaknessTimer(Math.min(getWeaknessTimer() + 400, 1600));
			if (getY() <= -64 && source == DamageSource.OUT_OF_WORLD && (Object) this instanceof ServerPlayerEntity) {
				ServerPlayerEntity player = (ServerPlayerEntity) (Object) this;
				ServerWorld serverWorld = world.getServer().getWorld(player.getSpawnPointDimension());
				if (serverWorld != null) {
					BlockPos spawnPos = player.getSpawnPointPosition();
					if (spawnPos == null) {
						spawnPos = serverWorld.getSpawnPos();
					}
					player.teleport(serverWorld, spawnPos.getX(), spawnPos.getY(), spawnPos.getZ(), player.yaw, player.pitch);
				}
			}
			return 0;
		}
		return amount;
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
}
