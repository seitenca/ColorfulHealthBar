package locusway.colorfulhealthbar;

import locusway.colorfulhealthbar.proxy.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = ColorfulHealthBar.MODID, name = ColorfulHealthBar.MODNAME, version = ColorfulHealthBar.MODVERSION, useMetadata = true, clientSideOnly = true)
public class ColorfulHealthBar
{

    public static final String MODID = "colorfulhealthbar";
    public static final String MODNAME = "Colorful Health Bar";
    public static final String MODVERSION = "@VERSION@";

    @SidedProxy(clientSide = "locusway.colorfulhealthbar.proxy.ClientProxy", serverSide = "locusway.colorfulhealthbar.proxy.ServerProxy")
    public static CommonProxy proxy;

    @Mod.Instance
    public static ColorfulHealthBar instance;

  //  public static Logger logger;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
   //     logger = event.getModLog();

        if(ModConfig.healthColorValues.length == 0)
        {
      //      logger.error("Config error! No health colors specified in config");
	//		logger.error("Health bar will not display correctly.");
        }

    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
      /*  if (Loader.isModLoaded("mantle")) {
            logger.info("Unregistering Mantle health renderer.");
            Field f = EventBus::class.java.getDeclaredField("listeners");
            f.setAccessible(true);
            val listeners = f.get(MinecraftForge.EVENT_BUS) as ConcurrentHashMap<*, *>
            val handler = listeners.keys.firstOrNull { it.javaClass.canonicalName == "slimeknights.mantle.client.ExtraHeartRenderHandler" }
            if (handler == null) LOGGER.warn("Unable to unregister Mantle health renderer!")
            else MinecraftForge.EVENT_BUS.unregister(handler) */
        proxy.postInit(event);
    }
}
