package me.imystxc.iseeyou;

import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.common.config.Configuration;

import java.lang.reflect.Method;

@net.minecraftforge.common.config.Config(
        modid = "iseeyou",
        name = "iSeeYou/config"
)
public class Config {
    @net.minecraftforge.common.config.Config.Comment({"Block List - ITEM/META"})
    public static String[] blocks = new String[]{"minecraft:coal_ore/0"};

    public Config() {
    }

    public static void reloadConfig() {
        try {
            Method getCfg = ConfigManager.class.getDeclaredMethod("getConfiguration", String.class, String.class);
            getCfg.setAccessible(true);
            Configuration cfg = (Configuration)getCfg.invoke((Object)null, "iseeyou", "iSeeYou/config");
            if (cfg == null) {
                return;
            }
            cfg.load();
            blocks = cfg.get("general", "blocks", blocks).getStringList();
        } catch (Exception var2) {
            var2.printStackTrace();
        }

    }
}
