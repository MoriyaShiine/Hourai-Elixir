package moriyashiine.houraielixir.mixin;

import moriyashiine.houraielixir.api.accessor.HouraiAccessor;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffectType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
	@Inject(method = "canHaveStatusEffect", at = @At("HEAD"), cancellable = true)
	private void canHaveStatusEffect(StatusEffectInstance effect, CallbackInfoReturnable<Boolean> callbackInfo) {
		if (this instanceof HouraiAccessor && ((HouraiAccessor) this).getImmortal() && ((HouraiAccessor) this).getWeaknessTimer() == 0 && ((StatusEffectAccessor) effect.getEffectType()).he_getType() != StatusEffectType.BENEFICIAL) {
			callbackInfo.setReturnValue(false);
		}
	}
}
