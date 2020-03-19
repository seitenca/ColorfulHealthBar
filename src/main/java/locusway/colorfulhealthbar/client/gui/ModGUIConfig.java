package locusway.colorfulhealthbar.client.gui;

import cpw.mods.fml.client.config.GuiConfig;
import locusway.colorfulhealthbar.ColorfulHealthBar;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;

import static locusway.colorfulhealthbar.ModConfig.getConfig;

public class ModGUIConfig extends GuiConfig {

    public ModGUIConfig(GuiScreen guiScreen){
        super(guiScreen,
                new ConfigElement(getConfig().getCategory(Configuration.CATEGORY_GENERAL)).getChildElements(),
                ColorfulHealthBar.MODID,
                false,
                false,
                GuiConfig.getAbridgedConfigPath(getConfig().toString()));
    }

}