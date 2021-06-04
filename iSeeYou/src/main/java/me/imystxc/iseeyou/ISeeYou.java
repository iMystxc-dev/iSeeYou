package me.imystxc.iseeyou;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(
        modid = ISeeYou.MOD_ID,
        name = ISeeYou.MOD_NAME,
        version = ISeeYou.VERSION,
        acceptableRemoteVersions = "*",
        acceptedMinecraftVersions = "[1.12.2]"
)
public class ISeeYou {

    public static final String MOD_ID = "iseeyou";
    public static final String MOD_NAME = "ISeeYou";
    public static final String VERSION = "1.0";
    private static final Logger logger = LogManager.getLogger();

    /**
     * This is the instance of your mod as created by Forge. It will never be null.
     */
    @Mod.Instance(MOD_ID)
    public static ISeeYou INSTANCE;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent e) {

    }


    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new BreakBlockMessage());
    }

    @Mod.EventHandler
    public void postinit(FMLPostInitializationEvent event) {

    }

    @Mod.EventHandler
    public void serverStarting(FMLServerStartingEvent event) {
        MinecraftForge.EVENT_BUS.register(this);
        event.registerServerCommand(new Command());
        logger.info("iSeeYou Has Successfully Loaded");
    }
}
