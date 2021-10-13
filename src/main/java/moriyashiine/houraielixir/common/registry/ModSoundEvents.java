package moriyashiine.houraielixir.common.registry;

import moriyashiine.houraielixir.common.HouraiElixir;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModSoundEvents {
	public static final SoundEvent ENTITY_GENERIC_RESURRECT = new SoundEvent(new Identifier(HouraiElixir.MOD_ID, "entity.generic.resurrect"));
	
	public static void init() {
		Registry.register(Registry.SOUND_EVENT, new Identifier(HouraiElixir.MOD_ID, "entity.generic.resurrect"), ENTITY_GENERIC_RESURRECT);
	}
}
