/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.houraielixir.common.item;

import moriyashiine.houraielixir.common.HouraiElixir;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.List;

public class HouraiElixirItem extends Item {
	public HouraiElixirItem(Settings settings) {
		super(settings);
	}

	@Override
	public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
		tooltip.add(Text.translatable(HouraiElixir.MOD_ID + ".tooltip.hourai_elixir").formatted(Formatting.GRAY));
	}
}
