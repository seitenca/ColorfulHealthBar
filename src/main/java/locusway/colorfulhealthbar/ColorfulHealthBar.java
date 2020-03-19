package locusway.colorfulhealthbar;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import locusway.colorfulhealthbar.proxy.CommonProxy;

@Mod(modid = ColorfulHealthBar.MODID, name = ColorfulHealthBar.MODNAME, version = ColorfulHealthBar.MODVERSION, useMetadata = true, guiFactory = ColorfulHealthBar.GUI_FACTORY_CLASS)
public class ColorfulHealthBar
{

    public static final String MODID = "colorfulhealthbar";
    public static final String MODNAME = "Colorful Health Bar";
    public static final String MODVERSION = "1.7.10-1.0.0-beta1";
    public static final String GUI_FACTORY_CLASS = "locusway.colorfulhealthbar.client.gui.GuiFactory";

    public static org.apache.logging.log4j.Logger logger;

    @SidedProxy(modId=MODID, clientSide = "locusway.colorfulhealthbar.proxy.ClientProxy", serverSide = "locusway.colorfulhealthbar.proxy.CommonProxy")
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();
        proxy.registerEvents();
        String configDir = event.getModConfigurationDirectory().toString();
        ModConfig.init(configDir);
        FMLCommonHandler.instance().bus().register(new ModConfig());

        if(ModConfig.healthColorValues.length == 0) {
           logger.error("Config error! No health colors specified in config");
           logger.error("Health bar will not display correctly.");
        }
    }
}
