package moriyashiine.houraielixir.common.component.entity;

import dev.onyxstudios.cca.api.v3.component.tick.ServerTickingComponent;
import moriyashiine.houraielixir.common.world.ModUniversalWorldState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.nbt.NbtCompound;

public class HouraiComponent implements ServerTickingComponent {
	private final LivingEntity obj;
	private int weaknessTimer = 0;
	
	public HouraiComponent(LivingEntity obj) {
		this.obj = obj;
	}
	
	@Override
	public void readFromNbt(NbtCompound tag) {
		setWeaknessTimer(tag.getInt("WeaknessTimer"));
	}
	
	@Override
	public void writeToNbt(NbtCompound tag) {
		tag.putInt("WeaknessTimer", getWeaknessTimer());
	}
	
	@Override
	public void serverTick() {
		int weaknessTimer = getWeaknessTimer();
		if (weaknessTimer > 0) {
			setWeaknessTimer(--weaknessTimer);
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
		}
	}
	
	public int getWeaknessTimer() {
		return weaknessTimer;
	}
	
	public void setWeaknessTimer(int weaknessTimer) {
		this.weaknessTimer = weaknessTimer;
	}
	
	public static boolean isImmortal(LivingEntity entity) {
		return ModUniversalWorldState.get(entity.world).immortalEntities.contains(entity.getUuid());
	}
}
