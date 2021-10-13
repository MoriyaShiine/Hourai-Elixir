package moriyashiine.houraielixir.common.world;

import moriyashiine.houraielixir.common.HouraiElixir;
import net.fabricmc.fabric.api.util.NbtType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.world.PersistentState;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ModUniversalWorldState extends PersistentState {
	public final List<UUID> immortalEntities = new ArrayList<>();
	
	public static ModUniversalWorldState readNbt(NbtCompound tag) {
		ModUniversalWorldState worldState = new ModUniversalWorldState();
		NbtList immortalEntities = tag.getList("ImmortalEntities", NbtType.COMPOUND);
		for (int i = 0; i < immortalEntities.size(); i++) {
			worldState.immortalEntities.add(immortalEntities.getCompound(i).getUuid("UUID"));
		}
		return worldState;
	}
	
	@Override
	public NbtCompound writeNbt(NbtCompound nbt) {
		NbtList immortalEntities = new NbtList();
		for (UUID uuid : this.immortalEntities) {
			NbtCompound compound = new NbtCompound();
			compound.putUuid("UUID", uuid);
			immortalEntities.add(compound);
		}
		nbt.put("ImmortalEntities", immortalEntities);
		return nbt;
	}
	
	@SuppressWarnings("ConstantConditions")
	public static ModUniversalWorldState get(World world) {
		return world.getServer().getOverworld().getPersistentStateManager().getOrCreate(ModUniversalWorldState::readNbt, ModUniversalWorldState::new, HouraiElixir.MOD_ID + "_universal");
	}
}
