/*
 * All Rights Reserved (c) MoriyaShiine
 */

package moriyashiine.houraielixir.mixin;

import moriyashiine.houraielixir.common.HouraiElixir;
import moriyashiine.houraielixir.common.registry.ModEntityComponents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
	public LivingEntityMixin(EntityType<?> type, World world) {
		super(type, world);
	}

	@Inject(method = "canHaveStatusEffect", at = @At("HEAD"), cancellable = true)
	private void houraielixir$statusEffectImmunity(StatusEffectInstance effect, CallbackInfoReturnable<Boolean> cir) {
		if (!getWorld().isClient && HouraiElixir.isImmortal(this) && ModEntityComponents.HOURAI.get(this).getWeaknessTimer() == 0 && effect.getEffectType().value().getCategory() != StatusEffectCategory.BENEFICIAL) {
			cir.setReturnValue(false);
		}
	}
}
