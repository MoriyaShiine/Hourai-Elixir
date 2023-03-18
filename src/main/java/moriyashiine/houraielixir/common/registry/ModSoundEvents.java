/*
 * All Rights Reserved (c) MoriyaShiine
 */

package moriyashiine.houraielixir.common.registry;

import moriyashiine.houraielixir.common.HouraiElixir;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;

public class ModSoundEvents {
	public static final SoundEvent ENTITY_GENERIC_RESURRECT = SoundEvent.of(HouraiElixir.id("entity.generic.resurrect"));

	public static void init() {
		Registry.register(Registries.SOUND_EVENT, ENTITY_GENERIC_RESURRECT.getId(), ENTITY_GENERIC_RESURRECT);
	}
}
