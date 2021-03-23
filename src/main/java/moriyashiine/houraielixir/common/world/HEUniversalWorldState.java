package moriyashiine.houraielixir.common.world;

import moriyashiine.houraielixir.common.HouraiElixir;
import net.fabricmc.fabric.api.util.NbtType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.PersistentState;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class HEUniversalWorldState extends PersistentState {
	public final List<UUID> immortalEntities = new ArrayList<>();
	
	public HEUniversalWorldState(String key) {
		super(key);
	}
	
	@Override
	public void fromTag(CompoundTag tag) {
		ListTag immortalEntities = tag.getList("ImmortalEntities", NbtType.COMPOUND);
		for (int i = 0; i < immortalEntities.size(); i++) {
			this.immortalEntities.add(immortalEntities.getCompound(i).getUuid("UUID"));
		}
	}
	
	@Override
	public CompoundTag toTag(CompoundTag tag) {
		ListTag immortalEntities = new ListTag();
		for (UUID uuid : this.immortalEntities) {
			CompoundTag compound = new CompoundTag();
			compound.putUuid("UUID", uuid);
			immortalEntities.add(compound);
		}
		tag.put("ImmortalEntities", immortalEntities);
		return tag;
	}
	
	public static HEUniversalWorldState get(World world) {
		return ((ServerWorld) world).getServer().getOverworld().getPersistentStateManager().getOrCreate(() -> new HEUniversalWorldState(HouraiElixir.MODID + "_universal"), HouraiElixir.MODID + "_universal");
	}
}
