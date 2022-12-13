/*
 * All Rights Reserved (c) 2022 MoriyaShiine
 */

package moriyashiine.houraielixir.common.registry;

import moriyashiine.houraielixir.common.HouraiElixir;
import moriyashiine.houraielixir.common.item.HouraiElixirItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Rarity;

public class ModItems {
	public static final Item HOURAI_ELIXIR = new HouraiElixirItem(new FabricItemSettings().rarity(Rarity.EPIC).maxCount(1));

	public static void init() {
		Registry.register(Registries.ITEM, HouraiElixir.id("hourai_elixir"), HOURAI_ELIXIR);
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK).register(entries -> entries.addAfter(Items.HONEY_BOTTLE, HOURAI_ELIXIR));
	}
}
