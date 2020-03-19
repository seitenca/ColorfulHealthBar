package locusway.colorfulhealthbar;

import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import locusway.colorfulhealthbar.overlay.HealthBarRenderer;
import net.minecraftforge.common.config.Configuration;

import java.io.File;

public class ModConfig {

    public static Configuration config;
    private static HealthBarRenderer healthBarRenderer;
    public static String[] healthColorValues = new String[]{"#FF1313", "#EE8100", "#E5CE00","#00DA00","#0C9DF1","#B486FF", "#EC8AFB","#FBD78B","#03EFEC","#B7E7FD","#EDEDED"};
    public static String[] absorptionColorValues = new String[]{"#2020FF","#FF1313","#13FF13","#FFFF13","#7713FF","#FF7713"};
    public static boolean showIndex = true;
    public static boolean showAbsorptionIndex = true;
    public static double textScale = 0.75F;

    public static void init(String configDir) {
        if(config == null){
            File path = new File(configDir + "/" + ColorfulHealthBar.MODID + ".cfg");
            config = new Configuration(path);
            loadConfiguration();
        }
    }

    private static void loadConfiguration() {
        healthColorValues=config.getStringList("Health Icon Colors", Configuration.CATEGORY_GENERAL, new String[]{"#FF1313", "#EE8100", "#E5CE00","#00DA00","#0C9DF1","#B486FF", "#EC8AFB","#FBD78B","#03EFEC","#B7E7FD","#EDEDED"} , "Colors must be specified in #RRGGBB format");
        absorptionColorValues=config.getStringList("Absorption Icon Colors", Configuration.CATEGORY_GENERAL, new String[]{"#2020FF","#FF1313","#13FF13","#FFFF13","#7713FF","#FF7713"} , "Colors must be specified in #RRGGBB format");
        showIndex=config.getBoolean("Display number of health bars?", Configuration.CATEGORY_GENERAL, true, "Display number of health bars" );
        showAbsorptionIndex=config.getBoolean("Display number of absorption bars?", Configuration.CATEGORY_GENERAL, true,"Display number of absorption bars");
        textScale=config.getFloat("Text scale", Configuration.CATEGORY_GENERAL, 0.75F, 0.0F, 5.0F, "Text scale" );
        textScale=config.get(Configuration.CATEGORY_GENERAL, "Text scale", textScale, "Text scale [default: 0.75]").getDouble();

        if(config.hasChanged())
            config.save();
    }

    @SubscribeEvent
    public void onConfigurationChangeEvent (ConfigChangedEvent.OnConfigChangedEvent event){
        if(event.modID.equalsIgnoreCase(ColorfulHealthBar.MODID)){
            loadConfiguration();
            healthBarRenderer.forceUpdate();
        }
    }

    public static Configuration getConfig(){
        return config;
    }

}
