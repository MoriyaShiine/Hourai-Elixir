package moriyashiine.houraielixir.misc;

import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;

public class HEDataTrackers {
	public static final TrackedData<Boolean> IMMORTAL = DataTracker.registerData(PlayerEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
	public static final TrackedData<Integer> WEAKNESS_TIMER = DataTracker.registerData(PlayerEntity.class, TrackedDataHandlerRegistry.INTEGER);
	
	public static void setImmortal(PlayerEntity player, boolean immortal) {
		player.getDataTracker().set(IMMORTAL, immortal);
	}
	
	public static boolean getImmortal(PlayerEntity playerEntity) {
		return playerEntity.getDataTracker().get(IMMORTAL);
	}
	
	public static void setWeaknessTimer(PlayerEntity player, int weaknessTimer) {
		player.getDataTracker().set(WEAKNESS_TIMER, weaknessTimer);
	}
	
	public static int getWeaknessTimer(PlayerEntity playerEntity) {
		return playerEntity.getDataTracker().get(WEAKNESS_TIMER);
	}
}