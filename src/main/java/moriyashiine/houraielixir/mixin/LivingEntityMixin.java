package moriyashiine.houraielixir.mixin;

import moriyashiine.houraielixir.api.component.HouraiComponent;
import moriyashiine.houraielixir.common.HouraiElixir;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@SuppressWarnings("ConstantConditions")
@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
	public LivingEntityMixin(EntityType<?> type, World world) {
		super(type, world);
	}
	
	@ModifyVariable(method = "applyDamage", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;getHealth()F"))
	private float modifyApplyDamage(float amount, DamageSource source) {
		return HouraiElixir.handleDamage((LivingEntity) (Object) this, source, amount);
	}
	
	@Inject(method = "canHaveStatusEffect", at = @At("HEAD"), cancellable = true)
	private void canHaveStatusEffect(StatusEffectInstance effect, CallbackInfoReturnable<Boolean> callbackInfo) {
		if (!world.isClient && HouraiElixir.isImmortal((LivingEntity) (Object) this) && HouraiComponent.get((LivingEntity) (Object) this).getWeaknessTimer() == 0 && effect.getEffectType().getType() != StatusEffectType.BENEFICIAL) {
			callbackInfo.setReturnValue(false);
		}
	}
}
