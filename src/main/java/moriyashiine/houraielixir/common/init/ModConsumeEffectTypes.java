/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.houraielixir.common.init;

import moriyashiine.houraielixir.common.HouraiElixir;
import moriyashiine.houraielixir.common.item.consume.GrantImmortalityConsumeEffect;
import net.minecraft.item.consume.ConsumeEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModConsumeEffectTypes {
	public static final ConsumeEffect.Type<GrantImmortalityConsumeEffect> GRANT_IMMORTALITY = new ConsumeEffect.Type<>(GrantImmortalityConsumeEffect.CODEC, GrantImmortalityConsumeEffect.PACKET_CODEC);

	public static void init() {
		Registry.register(Registries.CONSUME_EFFECT_TYPE, HouraiElixir.id("grant_immortality"), GRANT_IMMORTALITY);
	}
}
