package locusway.colorfulhealthbar.config;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber
public class Configs {
    public static final HealthConfig CLIENT;
    public static final ForgeConfigSpec CLIENT_SPEC;

    static {
        final Pair<HealthConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(HealthConfig::new);
        CLIENT_SPEC = specPair.getRight();
        CLIENT = specPair.getLeft();
    }




    static class HealthConfig {
        public static ForgeConfigSpec.ConfigValue<List<? extends String>> healthColorValues;
        public static ForgeConfigSpec.ConfigValue<List<? extends String>> absorptionColorValues;
        public static ForgeConfigSpec.BooleanValue showIndex;
        public static ForgeConfigSpec.DoubleValue textScale;

        HealthConfig(ForgeConfigSpec.Builder builder) {
            builder.push("general");
            healthColorValues = builder
                    .comment("Colors must be specified in #RRGGBB format")
                    .translation("text.colorfulhealthbar.config.health")
                    .defineList("health color values", Lists.newArrayList("#FF1313", "#FF7713", "#FFFF00","#13FF13","#13FFFF","#3030FF", "#7720FF"), o -> o instanceof String);
            absorptionColorValues = builder
                    .comment("Colors must be specified in #RRGGBB format")
                    .translation("text.colorfulhealthbar.config.absorption")
                    .defineList("absorption color values", Lists.newArrayList("#2020FF","#FF1313","#13FF13","#FFFF13","#7713FF","#FF7713"), o -> o instanceof String);
            showIndex = builder
                    .comment("Show number of health bars remaining")
                    .define("show bars",true);
            textScale = builder
                    .comment("Size of index number")
                    .defineInRange("index text size", .75,0,1);
            builder.pop();
        }
    }
    public static List<? extends String> healthColorValues = new ArrayList<>();
    public static List<? extends String> absorptionColorValues = new ArrayList<>();
    public static boolean showIndex = true;
    public static double textScale = .75;
    public static void bake(){
        healthColorValues = HealthConfig.healthColorValues.get();
        absorptionColorValues = HealthConfig.absorptionColorValues.get();
        showIndex = HealthConfig.showIndex.get();
        textScale = HealthConfig.textScale.get();
    }


}
