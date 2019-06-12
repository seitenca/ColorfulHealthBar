package locusway.colorfulhealthbar;

import net.minecraftforge.common.config.Config;

@Config(modid = ColorfulHealthBar.MODID)
public class ModConfig {

    @Config.Name("Health icon colors")
    @Config.Comment("Colors must be specified in #RRGGBB format")
    public static String[] healthColorValues = new String[]{"#FF1313", "#EE8100", "#E5CE00","#00DA00","#0C9DF1","#B486FF", "#EC8AFB","#FBD78B","#03EFEC","#B7E7FD","#EDEDED"};

    @Config.Name("Absorption icon colors")
    @Config.Comment("Colors must be specified in #RRGGBB format")
    public static String[] absorptionColorValues = new String[]{"#2020FF","#FF1313","#13FF13","#FFFF13","#7713FF","#FF7713"};

    @Config.Name("Display number of health bars?")
    public static boolean showIndex = true;

  @Config.Name("Display number of absorption bars?")
  public static boolean showAbsorptionIndex = true;

  @Config.Name("Text scale")
  @Config.RangeDouble(min = 0,max = 1)
  public static double textScale = .75;
}
