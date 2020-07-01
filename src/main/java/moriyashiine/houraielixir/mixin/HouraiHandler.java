package moriyashiine.houraielixir.mixin;

import moriyashiine.houraielixir.misc.HEDataTrackers;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@SuppressWarnings("ALL")
@Mixin(LivingEntity.class)
public class HouraiHandler {
	@Inject(method = "tick", at = @At("HEAD"))
	private void tick(CallbackInfo callbackInfo) {
		Object obj = this;
		if (obj instanceof PlayerEntity) {
			PlayerEntity thisObj = (PlayerEntity) obj;
			if (!thisObj.world.isClient) {
				int weaknessTimer = HEDataTrackers.getWeaknessTimer(thisObj);
				if (weaknessTimer > 0) {
					HEDataTrackers.setWeaknessTimer(thisObj, --weaknessTimer);
					thisObj.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, 10, 0, true, false));
					if (weaknessTimer >= 400) {
						thisObj.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, 10, 1, true, false));
						thisObj.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 10, 0, true, false));
						if (weaknessTimer >= 800) {
							thisObj.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, 10, 1, true, false));
							thisObj.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 10, 1, true, false));
							thisObj.addStatusEffect(new StatusEffectInstance(StatusEffects.MINING_FATIGUE, 10, 0, true, false));
							if (weaknessTimer >= 1200) {
								thisObj.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, 10, 2, true, false));
								thisObj.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 10, 2, true, false));
								thisObj.addStatusEffect(new StatusEffectInstance(StatusEffects.MINING_FATIGUE, 10, 1, true, false));
							}
						}
					}
				}
			}
		}
	}
	
	@Inject(method = "canHaveStatusEffect", at = @At("HEAD"), cancellable = true)
	private void canHaveStatusEffect(StatusEffectInstance effect, CallbackInfoReturnable<Boolean> callbackInfo) {
		Object obj = this;
		if (obj instanceof PlayerEntity) {
			PlayerEntity thisObj = (PlayerEntity) obj;
			if (HEDataTrackers.getImmortal(thisObj) && HEDataTrackers.getWeaknessTimer(thisObj) == 0 && !effect.getEffectType().isBeneficial()) {
				callbackInfo.setReturnValue(false);
			}
		}
	}
	
	@Inject(method = "damage", at = @At("HEAD"), cancellable = true)
	private void cancelDeath(DamageSource source, float amount, CallbackInfoReturnable<Boolean> callbackInfo) {
		Object obj = this;
		if (obj instanceof PlayerEntity) {
			PlayerEntity thisObj = (PlayerEntity) obj;
			World world = thisObj.world;
			if (!world.isClient) {
				if (HEDataTrackers.getImmortal(thisObj) && thisObj.getHealth() - amount <= 0) {
					world.playSound(null, thisObj.getBlockPos(), SoundEvents.BLOCK_CHORUS_FLOWER_GROW, SoundCategory.PLAYERS, 1, 1);
					thisObj.setHealth(thisObj.getMaxHealth());
					thisObj.damage(DamageSource.OUT_OF_WORLD, 0.05f);
					HEDataTrackers.setWeaknessTimer(thisObj, Math.min(HEDataTrackers.getWeaknessTimer(thisObj) + 400, 1600));
					if (thisObj.getY() <= -64 && source == DamageSource.OUT_OF_WORLD && thisObj instanceof ServerPlayerEntity) {
						ServerPlayerEntity player = (ServerPlayerEntity) thisObj;
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
	}
}