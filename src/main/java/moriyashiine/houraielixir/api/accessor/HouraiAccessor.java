package moriyashiine.houraielixir.api.accessor;

import net.minecraft.entity.Entity;

import java.util.Optional;

public interface HouraiAccessor {
	static Optional<HouraiAccessor> of(Entity entity) {
		if (entity instanceof HouraiAccessor) {
			return Optional.of((HouraiAccessor) entity);
		}
		return Optional.empty();
	}
	
	boolean getImmortal();
	
	void setImmortal(boolean immortal);
	
	int getWeaknessTimer();
	
	void setWeaknessTimer(int weaknessTimer);
}
