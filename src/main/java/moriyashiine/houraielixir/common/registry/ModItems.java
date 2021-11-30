package moriyashiine.houraielixir.common.registry;

import moriyashiine.houraielixir.common.HouraiElixir;
import moriyashiine.houraielixir.common.item.HouraiElixirItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;

public class ModItems {
	public static final Item HOURAI_ELIXIR = new HouraiElixirItem(new FabricItemSettings().group(ItemGroup.MISC).rarity(Rarity.EPIC).maxCount(1));
	
	public static void init() {
		Registry.register(Registry.ITEM, new Identifier(HouraiElixir.MOD_ID, "hourai_elixir"), HOURAI_ELIXIR);
	}
}
