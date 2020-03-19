package locusway.colorfulhealthbar.overlay;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;

//Class which handles the render event and hides the vanilla armor bar
public class OverlayEventHandler
{
    public OverlayEventHandler(HealthBarRenderer healthBarRenderer) {
        this.healthBarRenderer = healthBarRenderer;
    }

    private HealthBarRenderer healthBarRenderer;

    @SubscribeEvent(receiveCanceled = true)
    public void onRenderGameOverlayEventPre(RenderGameOverlayEvent.Pre event) {
        int scaledWidth = event.resolution.getScaledWidth();
        int scaledHeight = event.resolution.getScaledHeight();

        // Don't render the vanilla health bar
        if (event.type == RenderGameOverlayEvent.ElementType.HEALTH) {
            healthBarRenderer.renderHealthBar(scaledWidth, scaledHeight);
            event.setCanceled(true);
        }
    }
}