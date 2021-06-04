package me.imystxc.iseeyou;

import java.io.File;
import java.io.IOException;
import info.pixelmon.repack.ninja.leaping.configurate.commented.CommentedConfigurationNode;
import info.pixelmon.repack.ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import info.pixelmon.repack.ninja.leaping.configurate.loader.ConfigurationLoader;
import info.pixelmon.repack.ninja.leaping.configurate.objectmapping.ObjectMappingException;

public class MessageConfig {
    private static MessageConfig instance = new MessageConfig();
    private CommentedConfigurationNode config;
    String path;
    private File configFile;
    private ConfigurationLoader<CommentedConfigurationNode> configLoader;

    public MessageConfig() {
        this.path = "config" + File.separator + "iSeeYou";
        this.configFile = new File(this.path, "messages.conf");
        this.configLoader = ((HoconConfigurationLoader.Builder)HoconConfigurationLoader.builder().setFile(this.configFile)).build();
    }

    public static MessageConfig getInstance() {
        return instance;
    }

    public void configCreate() throws ObjectMappingException, IOException {
        try {
            if (!this.configFile.getParentFile().exists()) {
                this.configFile.getParentFile().mkdir();
            }

            this.configFile.createNewFile();
            this.configLoad();
            CommentedConfigurationNode config = this.config.getNode("iSeeYou");
            config.getNode("messages", "notification").setComment("Available Placeholders: %players% | %block% | %x_pos% | %y_pos% | %z_pos%");
            config.getNode(new Object[]{"messages","notification"}).setValue("&3%player% &7- &7[&b%block%&7] &7%x_pos% &b/ &7%y_pos% &b/ &7%z_pos%");
            this.configSave();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setup(File configFile, ConfigurationLoader<CommentedConfigurationNode> configLoader) {
        this.configLoader = configLoader;
        this.configFile = configFile;
        if (!configFile.exists()) {
            try {
                this.configCreate();
            } catch (IOException | ObjectMappingException var4) {
                var4.printStackTrace();
            }
        } else {
            this.configLoad();
        }

    }

    public CommentedConfigurationNode getConfig() {
        return this.config;
    }

    public void configLoad() {
        if (!this.configFile.exists()) {
            try {
                this.configCreate();
            } catch (IOException | ObjectMappingException e) {
                e.printStackTrace();
            }
        } else {
            try {
                this.config = (CommentedConfigurationNode)this.configLoader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void configSave() {
        try {
            this.configLoader.save(this.config);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
