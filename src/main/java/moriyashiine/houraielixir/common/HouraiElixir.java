package moriyashiine.houraielixir.common;

import moriyashiine.houraielixir.common.item.HouraiElixirItem;
import net.fabricmc.api.ModInitializer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;

public class HouraiElixir implements ModInitializer {
	public static final String MODID = "houraielixir";
	
	public static final Item HOURAI_ELIXIR = new HouraiElixirItem(new Item.Settings().group(ItemGroup.MISC).rarity(Rarity.EPIC).maxCount(1));
	
	@Override
	public void onInitialize() {
		Registry.register(Registry.ITEM, new Identifier(MODID, "hourai_elixir"), HOURAI_ELIXIR);
	}
}
