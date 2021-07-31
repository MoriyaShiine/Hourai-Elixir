package moriyashiine.houraielixir.api.component;

import dev.onyxstudios.cca.api.v3.component.ComponentV3;
import dev.onyxstudios.cca.api.v3.component.tick.ServerTickingComponent;
import moriyashiine.houraielixir.common.registry.HEComponents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.nbt.NbtCompound;

import java.util.Optional;

public class HouraiComponent implements ComponentV3, ServerTickingComponent {
	private final LivingEntity obj;
	private int weaknessTimer = 0;
	
	public HouraiComponent(LivingEntity obj) {
		this.obj = obj;
	}
	
	public int getWeaknessTimer() {
		return weaknessTimer;
	}
	
	public void setWeaknessTimer(int weaknessTimer) {
		this.weaknessTimer = weaknessTimer;
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
	
	public static HouraiComponent get(LivingEntity obj) {
		return HEComponents.HOURAI_COMPONENT.get(obj);
	}
	
	public static Optional<HouraiComponent> maybeGet(LivingEntity obj) {
		return HEComponents.HOURAI_COMPONENT.maybeGet(obj);
	}
}
