package locusway.colorfulhealthbar;

import net.minecraftforge.common.config.Config;

@Config(modid = ColorfulHealthBar.MODID)
public class ModConfig {

    @Config.Name("Health icon colors")
    @Config.Comment("Colors must be specified in #RRGGBB format")
    public static String[] healthColorValues = new String[]{"#FF1313", "#FF7713", "#FFFF00","#13FF13","#13FFFF","#3030FF", "#7720FF"};

    @Config.Name("Absorption icon colors")
    @Config.Comment("Colors must be specified in #RRGGBB format")
    public static String[] absorptionColorValues = new String[]{"#2020FF","#FF1313","#13FF13","#FFFF13","#7713FF","#FF7713"};
}
