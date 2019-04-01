package locusway.colorfulhealthbar.proxy;

import locusway.colorfulhealthbar.ColorfulHealthBar;
import locusway.colorfulhealthbar.EventConfigChanged;
import locusway.colorfulhealthbar.overlay.HealthBarRenderer;
import locusway.colorfulhealthbar.overlay.OverlayEventHandler;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(Side.CLIENT)
public class ClientProxy extends CommonProxy
{
    private static HealthBarRenderer healthBarRenderer;

    @Override
    public void postInit(FMLPostInitializationEvent event)
    {
        super.postInit(event);

        //Register Armor Renderer for events
        healthBarRenderer = new HealthBarRenderer(Minecraft.getMinecraft());
        OverlayEventHandler overlay = new OverlayEventHandler(healthBarRenderer);
        MinecraftForge.EVENT_BUS.register(overlay);

        //Register event for configuration change
        EventConfigChanged eventConfigChanged = new EventConfigChanged();
        MinecraftForge.EVENT_BUS.register(eventConfigChanged);
    }

    @Override
    public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event)
    {
        ConfigManager.sync(ColorfulHealthBar.MODID, Config.Type.INSTANCE);

        //Ensure changes are applied by forcing recalculation.
        healthBarRenderer.forceUpdate();
    }
}
