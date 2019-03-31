package locusway.colorfulhealthbar;

import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

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
