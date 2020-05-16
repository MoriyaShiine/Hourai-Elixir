package moriyashiine.houraielixir.common.item;

import moriyashiine.houraielixir.HouraiElixir;
import moriyashiine.houraielixir.common.capability.HouraiCapability;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class HouraiElixirItem extends Item {
	public HouraiElixirItem() {
		super(new Properties().group(ItemGroup.MISC).rarity(Rarity.EPIC).maxStackSize(1));
	}
	
	@Override
	@Nonnull
	public ActionResult<ItemStack> onItemRightClick(@Nonnull World world, @Nonnull PlayerEntity player, @Nonnull Hand hand) {
		return Items.POTION.onItemRightClick(world, player, hand);
	}
	
	@Override
	@Nonnull
	public UseAction getUseAction(@Nonnull ItemStack stack) {
		return Items.POTION.getUseAction(stack);
	}
	
	@Override
	@Nonnull
	public ItemStack onItemUseFinish(@Nonnull ItemStack stack, @Nonnull World world, @Nonnull LivingEntity entity) {
		AtomicReference<ItemStack> fin = new AtomicReference<>(super.onItemUseFinish(stack, world, entity));
		if (entity instanceof PlayerEntity) {
			PlayerEntity player = (PlayerEntity) entity;
			entity.getCapability(HouraiCapability.CAP).ifPresent(houraiCap -> {
				String message = "message." + HouraiElixir.MODID + (houraiCap.immortal ? ".already_immortal" : ".become_immortal");
				if (!world.isRemote) player.sendStatusMessage(new TranslationTextComponent(message), true);
				houraiCap.immortal = true;
				if (!player.isCreative()) fin.set(new ItemStack(Items.GLASS_BOTTLE));
			});
		}
		return fin.get();
	}
	
	@Override
	public int getUseDuration(@Nonnull ItemStack stack) {
		return Items.POTION.getUseDuration(stack);
	}
	
	@Override
	public void addInformation(@Nonnull ItemStack stack, @Nullable World world, List<ITextComponent> tooltips, @Nonnull ITooltipFlag flags) {
		tooltips.add(new TranslationTextComponent("tooltip." + HouraiElixir.MODID + ".hourai_elixir").applyTextStyle(TextFormatting.GRAY));
	}
}
