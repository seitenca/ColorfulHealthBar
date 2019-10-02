package locusway.colorfulhealthbar;

import locusway.colorfulhealthbar.config.Configs;
import locusway.colorfulhealthbar.proxy.ClientProxy;
import locusway.colorfulhealthbar.proxy.CommonProxy;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(value = ColorfulHealthBar.MODID)
public class ColorfulHealthBar
{
    public static final String MODID = "colorfulhealthbar";

    public static Logger logger = LogManager.getLogger();

    public ColorfulHealthBar() {
        ModLoadingContext.get().registerConfig(net.minecraftforge.fml.config.ModConfig.Type.CLIENT, Configs.CLIENT_SPEC);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::bakeConfigs);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);

    }
    public static CommonProxy proxy = DistExecutor.runForDist(() -> ClientProxy::new, () -> CommonProxy::new);

    public void bakeConfigs(ModConfig.ModConfigEvent event)
    {
        if (event.getConfig().getSpec() == Configs.CLIENT_SPEC)
            Configs.bake();
    }

@SubscribeEvent
    public void setup(final FMLCommonSetupEvent event)
    {

        proxy.postInit(event);
    }
}
