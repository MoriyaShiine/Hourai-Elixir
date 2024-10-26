/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.houraielixir.common.init;

import moriyashiine.houraielixir.common.HouraiElixir;
import moriyashiine.houraielixir.common.item.HouraiElixirItem;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Rarity;

import java.util.function.Function;

public class ModItems {
	public static final Item HOURAI_ELIXIR = register("hourai_elixir", HouraiElixirItem::new, new Item.Settings()
			.rarity(Rarity.EPIC)
			.recipeRemainder(Items.GLASS_BOTTLE)
			.useRemainder(Items.GLASS_BOTTLE)
			.component(DataComponentTypes.CONSUMABLE, ModConsumableComponents.HOURAI_ELIXIR)
			.maxCount(1));

	private static Item register(String name, Function<Item.Settings, Item> factory, Item.Settings settings) {
		return Items.register(RegistryKey.of(RegistryKeys.ITEM, HouraiElixir.id(name)), factory, settings);
	}

	public static void init() {
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK).register(entries -> entries.addAfter(Items.HONEY_BOTTLE, HOURAI_ELIXIR));
	}
}
