/*
 * All Rights Reserved (c) 2022 MoriyaShiine
 */

package moriyashiine.houraielixir.mixin;

import moriyashiine.houraielixir.common.registry.ModEntityComponents;
import moriyashiine.houraielixir.common.util.HouraiElixirUtil;
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

	@ModifyVariable(method = "applyDamage", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;getHealth()F"), argsOnly = true)
	private float houraielixir$preventDeath(float amount, DamageSource source) {
		return HouraiElixirUtil.handleDamage(LivingEntity.class.cast(this), source, amount);
	}

	@Inject(method = "canHaveStatusEffect", at = @At("HEAD"), cancellable = true)
	private void houraielixir$statusEffectImmunity(StatusEffectInstance effect, CallbackInfoReturnable<Boolean> cir) {
		if (!world.isClient && HouraiElixirUtil.isImmortal(LivingEntity.class.cast(this)) && getComponent(ModEntityComponents.HOURAI).getWeaknessTimer() == 0 && effect.getEffectType().getCategory() != StatusEffectCategory.BENEFICIAL) {
			cir.setReturnValue(false);
		}
	}
}
