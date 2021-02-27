package moriyashiine.houraielixir.mixin;

import com.mojang.authlib.GameProfile;
import moriyashiine.houraielixir.api.accessor.HouraiAccessor;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin extends PlayerEntity {
	public ServerPlayerEntityMixin(World world, BlockPos pos, float yaw, GameProfile profile) {
		super(world, pos, yaw, profile);
	}
	
	@Inject(method = "copyFrom", at = @At("TAIL"))
	private void copyFrom(ServerPlayerEntity oldPlayer, boolean alive, CallbackInfo callbackInfo) {
		((HouraiAccessor) this).setImmortal(((HouraiAccessor) oldPlayer).getImmortal());
		((HouraiAccessor) this).setWeaknessTimer(((HouraiAccessor) oldPlayer).getWeaknessTimer());
	}
}
