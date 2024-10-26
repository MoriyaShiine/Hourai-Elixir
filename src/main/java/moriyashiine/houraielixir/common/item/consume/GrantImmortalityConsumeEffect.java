/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.houraielixir.common.item.consume;

import com.mojang.serialization.MapCodec;
import moriyashiine.houraielixir.common.HouraiElixir;
import moriyashiine.houraielixir.common.component.world.ImmortalEntitiesComponent;
import moriyashiine.houraielixir.common.init.ModConsumeEffectTypes;
import moriyashiine.houraielixir.common.init.ModWorldComponents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.consume.ConsumeEffect;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.text.Text;
import net.minecraft.world.World;

public record GrantImmortalityConsumeEffect() implements ConsumeEffect {
	public static final GrantImmortalityConsumeEffect INSTANCE = new GrantImmortalityConsumeEffect();
	public static final MapCodec<GrantImmortalityConsumeEffect> CODEC = MapCodec.unit(INSTANCE);
	public static final PacketCodec<RegistryByteBuf, GrantImmortalityConsumeEffect> PACKET_CODEC = PacketCodec.unit(INSTANCE);

	@Override
	public Type<? extends ConsumeEffect> getType() {
		return ModConsumeEffectTypes.GRANT_IMMORTALITY;
	}

	@Override
	public boolean onConsume(World world, ItemStack stack, LivingEntity user) {
		if (user instanceof PlayerEntity player) {
			player.sendMessage(Text.translatable(HouraiElixir.MOD_ID + ".message." + (HouraiElixir.isImmortal(user) ? "already_immortal" : "become_immortal")), true);
		}
		if (!world.isClient) {
			ImmortalEntitiesComponent immortalEntitiesComponent = ModWorldComponents.IMMORTAL_ENTITIES.get(world.getServer().getOverworld());
			if (!immortalEntitiesComponent.getImmortalEntities().contains(user.getUuid())) {
				immortalEntitiesComponent.getImmortalEntities().add(user.getUuid());
				return true;
			}
		}
		return false;
	}
}
