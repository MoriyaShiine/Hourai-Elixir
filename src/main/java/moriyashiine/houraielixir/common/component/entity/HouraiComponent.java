/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.houraielixir.common.component.entity;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import org.ladysnake.cca.api.v3.component.tick.ServerTickingComponent;

public class HouraiComponent implements ServerTickingComponent {
	private final LivingEntity obj;
	private int weaknessTimer = 0;

	public HouraiComponent(LivingEntity obj) {
		this.obj = obj;
	}

	@Override
	public void readFromNbt(NbtCompound tag, RegistryWrapper.WrapperLookup registryLookup) {
		weaknessTimer = tag.getInt("WeaknessTimer");
	}

	@Override
	public void writeToNbt(NbtCompound tag, RegistryWrapper.WrapperLookup registryLookup) {
		tag.putInt("WeaknessTimer", weaknessTimer);
	}

	@Override
	public void serverTick() {
		if (weaknessTimer > 0) {
			obj.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, 10, 0, true, false));
			if (weaknessTimer >= 400) {
				obj.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, 10, 1, true, false));
				obj.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 10, 0, true, false));
				if (weaknessTimer >= 800) {
					obj.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, 10, 1, true, false));
					obj.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 10, 1, true, false));
					obj.addStatusEffect(new StatusEffectInstance(StatusEffects.MINING_FATIGUE, 10, 0, true, false));
					if (weaknessTimer >= 1200) {
						obj.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, 10, 2, true, false));
						obj.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 10, 2, true, false));
						obj.addStatusEffect(new StatusEffectInstance(StatusEffects.MINING_FATIGUE, 10, 1, true, false));
					}
				}
			}
			weaknessTimer--;
		}
	}

	public int getWeaknessTimer() {
		return weaknessTimer;
	}

	public void setWeaknessTimer(int weaknessTimer) {
		this.weaknessTimer = weaknessTimer;
	}
}
