package locusway.colorfulhealthbar;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;

public class EventConfigChanged
{
    @SubscribeEvent
    public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event)
    {
        //Only process events for this mod
        if (event.getModID().equals(ColorfulHealthBar.MODID))
        {
            ColorfulHealthBar.proxy.onConfigChanged(event);
        }
    }
}
