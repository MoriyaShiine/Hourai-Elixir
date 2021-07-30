package moriyashiine.houraielixir.common.item;

import moriyashiine.houraielixir.common.HouraiElixir;
import moriyashiine.houraielixir.common.world.HEUniversalWorldState;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
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
		return Items.POTION.use(world, user, hand);
	}
	
	@Override
	public UseAction getUseAction(ItemStack stack) {
		return Items.POTION.getUseAction(stack);
	}
	
	@Override
	public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
		if (!world.isClient) {
			if (user instanceof PlayerEntity player) {
				player.sendMessage(new TranslatableText(HouraiElixir.MODID + ".message." + (HouraiElixir.isImmortal(user) ? "already_immortal" : "become_immortal")), true);
			}
			HEUniversalWorldState worldState = HEUniversalWorldState.get(world);
			if (!worldState.immortalEntities.contains(user.getUuid())) {
				worldState.immortalEntities.add(user.getUuid());
				worldState.markDirty();
			}
		}
		return Items.POTION.finishUsing(stack, world, user);
	}
	
	@Override
	public int getMaxUseTime(ItemStack stack) {
		return 16;
	}
	
	@Override
	@Environment(EnvType.CLIENT)
	public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
		tooltip.add(new TranslatableText(HouraiElixir.MODID + ".tooltip.hourai_elixir").formatted(Formatting.GRAY));
	}
}
