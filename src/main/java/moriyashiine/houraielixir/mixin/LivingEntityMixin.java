package moriyashiine.houraielixir.mixin;

import moriyashiine.houraielixir.api.accessor.HouraiAccessor;
import moriyashiine.houraielixir.common.HouraiElixir;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@SuppressWarnings("ConstantConditions")
@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements HouraiAccessor {
	private int weaknessTimer = 0;
	
	@Shadow
	public abstract boolean addStatusEffect(StatusEffectInstance effect);
	
	public LivingEntityMixin(EntityType<?> type, World world) {
		super(type, world);
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
	
	@ModifyVariable(method = "applyDamage", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;getHealth()F"))
	private float modifyApplyDamage(float amount, DamageSource source) {
		return HouraiElixir.handleDamage((LivingEntity) (Object) this, source, amount);
	}
	
	@Inject(method = "canHaveStatusEffect", at = @At("HEAD"), cancellable = true)
	private void canHaveStatusEffect(StatusEffectInstance effect, CallbackInfoReturnable<Boolean> callbackInfo) {
		if (!world.isClient && HouraiElixir.isImmortal((LivingEntity) (Object) this) && getWeaknessTimer() == 0 && effect.getEffectType().getType() != StatusEffectType.BENEFICIAL) {
			callbackInfo.setReturnValue(false);
		}
	}
	
	@Inject(method = "readCustomDataFromNbt", at = @At("TAIL"))
	private void readCustomDataFromNbt(NbtCompound nbt, CallbackInfo callbackInfo) {
		setWeaknessTimer(nbt.getInt("WeaknessTimer"));
	}
	
	@Inject(method = "writeCustomDataToNbt", at = @At("TAIL"))
	private void writeCustomDataToNbt(NbtCompound nbt, CallbackInfo callbackInfo) {
		nbt.putInt("WeaknessTimer", getWeaknessTimer());
	}
}
