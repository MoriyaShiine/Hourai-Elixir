package moriyashiine.houraielixir.mixin;

import moriyashiine.houraielixir.common.HouraiElixir;
import moriyashiine.houraielixir.common.component.entity.HouraiComponent;
import moriyashiine.houraielixir.common.registry.ModComponents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
	public LivingEntityMixin(EntityType<?> type, World world) {
		super(type, world);
	}
	
	@ModifyVariable(method = "applyDamage", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;getHealth()F"), ordinal = 0)
	private float houraiDamage(float amount, DamageSource source) {
		return HouraiElixir.handleDamage((LivingEntity) (Object) this, source, amount);
	}
	
	@Inject(method = "canHaveStatusEffect", at = @At("HEAD"), cancellable = true)
	private void houraiStatusImmunity(StatusEffectInstance effect, CallbackInfoReturnable<Boolean> cir) {
		if (!world.isClient && HouraiComponent.isImmortal((LivingEntity) (Object) this) && ModComponents.HOURAI_COMPONENT.get((LivingEntity) (Object) this).getWeaknessTimer() == 0 && effect.getEffectType().getCategory() != StatusEffectCategory.BENEFICIAL) {
			cir.setReturnValue(false);
		}
	}
}
