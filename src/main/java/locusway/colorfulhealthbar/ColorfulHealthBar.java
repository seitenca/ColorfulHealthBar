package locusway.colorfulhealthbar;

import locusway.colorfulhealthbar.overlay.HealthBarRenderer;
import locusway.colorfulhealthbar.overlay.OverlayEventHandler;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import org.apache.logging.log4j.Logger;

@Mod(modid = ColorfulHealthBar.MODID, name = ColorfulHealthBar.MODNAME, version = ColorfulHealthBar.MODVERSION, useMetadata = true, clientSideOnly = true)
@Mod.EventBusSubscriber(value = Side.CLIENT)
public class ColorfulHealthBar
{

    public static final String MODID = "colorfulhealthbar";
    public static final String MODNAME = "Colorful Health Bar";
    public static final String MODVERSION = "@VERSION@";

    public static Logger logger;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();

        if(ModConfig.healthColorValues.length == 0)
        {
           logger.error("Config error! No health colors specified in config");
			logger.error("Health bar will not display correctly.");
        }

    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
      /*  if (Loader.isModLoaded("mantle")) {
            logger.info("Unregistering Mantle health renderer.");
            Field f = EventBus::class.java.getDeclaredField("listeners");
            f.setAccessible(true);
            val listeners = f.get(MinecraftForge.EVENT_BUS) as ConcurrentHashMap<*, *>
            val handler = listeners.keys.firstOrNull { it.javaClass.canonicalName == "slimeknights.mantle.client.ExtraHeartRenderHandler" }
            if (handler == null) LOGGER.warn("Unable to unregister Mantle health renderer!")
            else MinecraftForge.EVENT_BUS.unregister(handler) */
        //Register Armor Renderer for events
        healthBarRenderer = new HealthBarRenderer(Minecraft.getMinecraft());
        OverlayEventHandler overlay = new OverlayEventHandler(healthBarRenderer);
        MinecraftForge.EVENT_BUS.register(overlay);
}

    private static HealthBarRenderer healthBarRenderer;

    @SubscribeEvent
    public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event)
    {
        ConfigManager.sync(ColorfulHealthBar.MODID, Config.Type.INSTANCE);

        //Ensure changes are applied by forcing recalculation.
        healthBarRenderer.forceUpdate();
    }
}
