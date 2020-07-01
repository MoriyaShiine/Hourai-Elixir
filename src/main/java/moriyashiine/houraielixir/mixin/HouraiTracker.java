package moriyashiine.houraielixir.mixin;

import moriyashiine.houraielixir.misc.HEDataTrackers;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@SuppressWarnings("ConstantConditions")
@Mixin(PlayerEntity.class)
public class HouraiTracker {
	@Inject(method = "readCustomDataFromTag", at = @At("TAIL"))
	public void readCustomDataFromTag(CompoundTag tag, CallbackInfo callbackInfo) {
		Object obj = this;
		if (obj instanceof PlayerEntity) {
			PlayerEntity thisObj = (PlayerEntity) obj;
			HEDataTrackers.setImmortal(thisObj, tag.getBoolean("immortal"));
			HEDataTrackers.setWeaknessTimer(thisObj, tag.getInt("weaknessTimer"));
		}
	}
	
	@Inject(method = "writeCustomDataToTag", at = @At("TAIL"))
	public void writeCustomDataToTag(CompoundTag tag, CallbackInfo callbackInfo) {
		Object obj = this;
		if (obj instanceof PlayerEntity) {
			PlayerEntity thisObj = (PlayerEntity) obj;
			tag.putBoolean("immortal", HEDataTrackers.getImmortal(thisObj));
			tag.putInt("weaknessTimer", HEDataTrackers.getWeaknessTimer(thisObj));
		}
	}
	
	@Inject(method = "initDataTracker", at = @At("TAIL"))
	public void initDataTracker(CallbackInfo callbackInfo) {
		Object obj = this;
		if (obj instanceof PlayerEntity) {
			PlayerEntity thisObj = (PlayerEntity) obj;
			thisObj.getDataTracker().startTracking(HEDataTrackers.IMMORTAL, false);
			thisObj.getDataTracker().startTracking(HEDataTrackers.WEAKNESS_TIMER, 0);
		}
	}
	
	@Mixin(ServerPlayerEntity.class)
	private static class Server {
		@Inject(method = "copyFrom", at = @At("TAIL"))
		public void copyFrom(ServerPlayerEntity oldPlayer, boolean alive, CallbackInfo callbackInfo) {
			Object obj = this;
			if (obj instanceof ServerPlayerEntity) {
				ServerPlayerEntity thisObj = (ServerPlayerEntity) obj;
				HEDataTrackers.setImmortal(thisObj, HEDataTrackers.getImmortal(oldPlayer));
				HEDataTrackers.setWeaknessTimer(thisObj, HEDataTrackers.getWeaknessTimer(oldPlayer));
			}
		}
	}
}