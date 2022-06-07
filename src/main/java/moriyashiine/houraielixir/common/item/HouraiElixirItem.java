/*
 * All Rights Reserved (c) 2022 MoriyaShiine
 */

package moriyashiine.houraielixir.common.item;

import moriyashiine.houraielixir.common.HouraiElixir;
import moriyashiine.houraielixir.common.component.world.ImmortalEntitiesComponent;
import moriyashiine.houraielixir.common.registry.ModWorldComponents;
import moriyashiine.houraielixir.common.util.HouraiElixirUtil;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.Items;
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
	public UseAction getUseAction(ItemStack stack) {
		return UseAction.DRINK;
	}

	@Override
	public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
		if (!world.isClient) {
			if (user instanceof PlayerEntity player) {
				player.sendMessage(Text.translatable(HouraiElixir.MOD_ID + ".message." + (HouraiElixirUtil.isImmortal(user) ? "already_immortal" : "become_immortal")), true);
			}
			ImmortalEntitiesComponent immortalEntitiesComponent = world.getServer().getOverworld().getComponent(ModWorldComponents.IMMORTAL_ENTITIES);
			if (!immortalEntitiesComponent.getImmortalEntities().contains(user.getUuid())) {
				immortalEntitiesComponent.getImmortalEntities().add(user.getUuid());
			}
		}
		return Items.POTION.finishUsing(stack, world, user);
	}

	@Override
	public int getMaxUseTime(ItemStack stack) {
		return 16;
	}

	@Environment(EnvType.CLIENT)
	@Override
	public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
		tooltip.add(Text.translatable(HouraiElixir.MOD_ID + ".tooltip.hourai_elixir").formatted(Formatting.GRAY));
	}
}
