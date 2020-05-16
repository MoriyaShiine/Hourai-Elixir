package moriyashiine.houraielixir.common.registry;

import moriyashiine.houraielixir.common.item.HouraiElixirItem;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class HEItems {
	@SubscribeEvent
	public static void registerItems(final RegistryEvent.Register<Item> event) {
		event.getRegistry().register(new HouraiElixirItem().setRegistryName("hourai_elixir"));
	}
}
