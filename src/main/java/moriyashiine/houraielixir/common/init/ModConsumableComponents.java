/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.houraielixir.common.init;

import moriyashiine.houraielixir.common.item.consume.GrantImmortalityConsumeEffect;
import net.minecraft.component.type.ConsumableComponent;
import net.minecraft.component.type.ConsumableComponents;

public class ModConsumableComponents {
	public static final ConsumableComponent HOURAI_ELIXIR = ConsumableComponents.drink()
			.consumeSeconds(0.75F)
			.consumeEffect(GrantImmortalityConsumeEffect.INSTANCE)
			.build();
}
