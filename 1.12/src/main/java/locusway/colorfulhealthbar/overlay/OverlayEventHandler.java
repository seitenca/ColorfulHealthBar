package locusway.colorfulhealthbar.overlay;

import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/*
    Class which handles the render event and hides the vanilla armor bar
 */
public class OverlayEventHandler
{
    public OverlayEventHandler(HealthBarRenderer healthBarRenderer)
    {
        this.healthBarRenderer = healthBarRenderer;
    }

    private HealthBarRenderer healthBarRenderer;

    @SubscribeEvent(receiveCanceled = true)
    public void onRenderGameOverlayEventPre(RenderGameOverlayEvent.Pre event)
    {
        int scaledWidth = event.getResolution().getScaledWidth();
        int scaledHeight = event.getResolution().getScaledHeight();

        switch (event.getType())
        {

            case HEALTH:
                {
                    healthBarRenderer.renderHealthBar(scaledWidth, scaledHeight);
                    /* Don't render the vanilla health bar */
                    event.setCanceled(true);
                }
                break;

            default:
                break;
        }
    }
}