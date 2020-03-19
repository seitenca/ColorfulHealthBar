package locusway.colorfulhealthbar.proxy;

import locusway.colorfulhealthbar.overlay.HealthBarRenderer;
import locusway.colorfulhealthbar.overlay.OverlayEventHandler;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;

public class ClientProxy extends CommonProxy{

    @Override
    public void registerEvents() {
        HealthBarRenderer healthBarRenderer = new HealthBarRenderer(Minecraft.getMinecraft());
        OverlayEventHandler overlay = new OverlayEventHandler(healthBarRenderer);
        MinecraftForge.EVENT_BUS.register(overlay);
    }
}
