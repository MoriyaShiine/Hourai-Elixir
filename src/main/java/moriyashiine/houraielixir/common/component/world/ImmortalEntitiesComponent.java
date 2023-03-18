/*
 * All Rights Reserved (c) MoriyaShiine
 */

package moriyashiine.houraielixir.common.component.world;

import dev.onyxstudios.cca.api.v3.component.Component;
import net.fabricmc.fabric.api.util.NbtType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ImmortalEntitiesComponent implements Component {
	private final List<UUID> immortalEntities = new ArrayList<>();

	@Override
	public void readFromNbt(NbtCompound tag) {
		NbtList immortalEntities = tag.getList("ImmortalEntities", NbtType.STRING);
		for (int i = 0; i < immortalEntities.size(); i++) {
			this.immortalEntities.add(UUID.fromString(immortalEntities.getString(i)));
		}
	}

	@Override
	public void writeToNbt(NbtCompound tag) {
		NbtList immortalEntities = new NbtList();
		for (UUID uuid : this.immortalEntities) {
			immortalEntities.add(NbtString.of(uuid.toString()));
		}
		tag.put("ImmortalEntities", immortalEntities);
	}

	public List<UUID> getImmortalEntities() {
		return immortalEntities;
	}
}
