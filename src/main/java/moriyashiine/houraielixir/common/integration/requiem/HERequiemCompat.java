package moriyashiine.houraielixir.common.integration.requiem;

import ladysnake.requiem.api.v1.RequiemApi;
import ladysnake.requiem.api.v1.RequiemPlugin;
import ladysnake.requiem.api.v1.event.requiem.RemnantStateChangeCallback;
import moriyashiine.houraielixir.api.accessor.HouraiAccessor;

public class HERequiemCompat {
	public static void init() {
		RequiemApi.registerPlugin(new RequiemPlugin() {
			@Override
			public void onRequiemInitialize() {
				RemnantStateChangeCallback.EVENT.register((playerEntity, remnantComponent) -> {
					if (remnantComponent.isVagrant()) {
						((HouraiAccessor) playerEntity).setWeaknessTimer(0);
					}
				});
			}
		});
	}
}
