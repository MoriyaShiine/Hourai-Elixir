package moriyashiine.houraielixir;

import moriyashiine.houraielixir.common.capability.HouraiCapability;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

/** File created by mason on 4/18/20 **/
@Mod(HouraiElixir.MODID)
public class HouraiElixir {
    public static final String MODID = "houraielixir";
    
    public HouraiElixir() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
    }
    
    private void setup(FMLCommonSetupEvent event) {
        HouraiCapability.setup();
    }
}