package moriyashiine.houraielixir.common.registry;

import moriyashiine.houraielixir.common.HouraiElixir;
import moriyashiine.houraielixir.common.item.HouraiElixirItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;

public class ModItems {
	public static final Item HOURAI_ELIXIR = new HouraiElixirItem(new Item.Settings().group(ItemGroup.MISC).rarity(Rarity.EPIC).maxCount(1));
	
	public static void init() {
		Registry.register(Registry.ITEM, new Identifier(HouraiElixir.MOD_ID, "hourai_elixir"), HOURAI_ELIXIR);
	}
}
