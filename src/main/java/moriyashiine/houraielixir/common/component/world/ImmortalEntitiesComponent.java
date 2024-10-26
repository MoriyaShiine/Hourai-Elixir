/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.houraielixir.common.component.world;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.registry.RegistryWrapper;
import org.ladysnake.cca.api.v3.component.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class ImmortalEntitiesComponent implements Component {
	private final Set<UUID> immortalEntities = new HashSet<>();

	@Override
	public void readFromNbt(NbtCompound tag, RegistryWrapper.WrapperLookup registryLookup) {
		this.immortalEntities.clear();
		NbtList immortalEntities = tag.getList("ImmortalEntities", NbtElement.STRING_TYPE);
		for (int i = 0; i < immortalEntities.size(); i++) {
			this.immortalEntities.add(UUID.fromString(immortalEntities.getString(i)));
		}
	}

	@Override
	public void writeToNbt(NbtCompound tag, RegistryWrapper.WrapperLookup registryLookup) {
		NbtList immortalEntities = new NbtList();
		for (UUID uuid : this.immortalEntities) {
			immortalEntities.add(NbtString.of(uuid.toString()));
		}
		tag.put("ImmortalEntities", immortalEntities);
	}

	public Set<UUID> getImmortalEntities() {
		return immortalEntities;
	}
}
