/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.houraielixir.common.item;

import moriyashiine.houraielixir.common.HouraiElixir;
import moriyashiine.houraielixir.common.registry.ModWorldComponents;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.Items;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

import java.util.List;

public class HouraiElixirItem extends Item {
	public HouraiElixirItem(Settings settings) {
		super(settings);
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		return ItemUsage.consumeHeldItem(world, user, hand);
	}

	@Override
	public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
		boolean decrement = false;
		if (user instanceof ServerPlayerEntity player) {
			decrement = !player.isCreative();
			Criteria.CONSUME_ITEM.trigger(player, stack);
			player.incrementStat(Stats.USED.getOrCreateStat(this));
			player.sendMessage(Text.translatable(HouraiElixir.MOD_ID + ".message." + (HouraiElixir.isImmortal(user) ? "already_immortal" : "become_immortal")), true);
			ModWorldComponents.IMMORTAL_ENTITIES.maybeGet(world.getServer().getOverworld()).ifPresent(immortalEntitiesComponent -> {
				if (!immortalEntitiesComponent.getImmortalEntities().contains(user.getUuid())) {
					immortalEntitiesComponent.getImmortalEntities().add(user.getUuid());
				}
			});
		}
		if (decrement) {
			stack.decrement(1);
		}
		if (stack.isEmpty()) {
			return new ItemStack(Items.GLASS_BOTTLE);
		}
		if (decrement) {
			PlayerEntity player = (PlayerEntity) user;
			if (!player.isCreative()) {
				ItemStack glassBottle = new ItemStack(Items.GLASS_BOTTLE);
				if (!player.getInventory().insertStack(glassBottle)) {
					player.dropItem(glassBottle, false);
				}
			}
		}
		return stack;
	}

	@Override
	public UseAction getUseAction(ItemStack stack) {
		return UseAction.DRINK;
	}

	@Override
	public int getMaxUseTime(ItemStack stack, LivingEntity user) {
		return 16;
	}

	@Override
	public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
		tooltip.add(Text.translatable(HouraiElixir.MOD_ID + ".tooltip.hourai_elixir").formatted(Formatting.GRAY));
	}
}
