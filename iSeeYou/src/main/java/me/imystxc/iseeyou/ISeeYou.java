package me.imystxc.iseeyou;

import info.pixelmon.repack.ninja.leaping.configurate.commented.CommentedConfigurationNode;
import info.pixelmon.repack.ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import info.pixelmon.repack.ninja.leaping.configurate.loader.ConfigurationLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;

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
    private static String separator = FileSystems.getDefault().getSeparator();
    public static String primaryPath;
    public static String commandConfigPath;
    public static Path configPath;
    public static ConfigurationLoader<CommentedConfigurationNode> primaryConfigLoader;
    String path;
    File configFile;
    ConfigurationLoader<CommentedConfigurationNode> configLoader;
    private static final Logger logger = LogManager.getLogger();

    /**
     * This is the instance of your mod as created by Forge. It will never be null.
     */
    @Mod.Instance(MOD_ID)
    public static ISeeYou INSTANCE;

    public ISeeYou() {
        this.path = "config" + File.separator + "iSeeYou";
        this.configFile = new File(this.path, "messages.conf");
        this.configLoader = ((HoconConfigurationLoader.Builder) HoconConfigurationLoader.builder().setFile(this.configFile)).build();
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent e) {
        MessageConfig.getInstance().setup(this.configFile, this.configLoader);
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

    static{
        primaryPath = "config" + separator;
        commandConfigPath = "config" + separator + "iSeeYou" + separator;
        configPath = Paths.get(primaryPath, "messages.conf");
        primaryConfigLoader = ((HoconConfigurationLoader.Builder)HoconConfigurationLoader.builder().setPath(configPath)).build();
    }
}
