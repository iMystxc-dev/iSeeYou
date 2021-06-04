package me.imystxc.iseeyou;

import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerList;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.client.config.GuiUtils;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.ServerLaunchWrapper;
import net.minecraftforge.fml.server.FMLServerHandler;
import net.minecraftforge.server.permission.PermissionAPI;
import org.apache.logging.log4j.core.jmx.Server;

import java.util.Collection;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BreakBlockMessage {

    public static String regex(String line) {
        String regex = "&(?=[0123456789abcdefklmnor])";
        Pattern pattern = Pattern.compile(regex, 2);
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            line = line.replaceAll(regex, "§");
        }

        return line;
    }


    @SubscribeEvent
    public void sendMessage(BlockEvent.BreakEvent event) {

        for (int i = 0; i < Config.blocks.length; ++i) {

            String itemInfo = Config.blocks[i];
            String[] splitInfo = itemInfo.split("/");
            String itemID = splitInfo[0];
            PlayerList list = FMLServerHandler.instance().getServer().getPlayerList();
            MinecraftServer server = FMLServerHandler.instance().getServer();

            for (EntityPlayerMP player : server.getPlayerList().getPlayers()) {
                if(player.canUseCommand(0, "iseeyou.log.base")){
                    if (itemID.equals(event.getState().getBlock().getDefaultState().toString())) {
                        player.sendMessage(new TextComponentString(regex(TextFormatting.DARK_AQUA + event.getPlayer().getName() + " &7- [" + TextFormatting.AQUA + event.getState().getBlock().getLocalizedName() + "&7] " + event.getPos().getX() + " &b/ &7" + event.getPos().getY() + " &b/ &7" + event.getPos().getZ())));
                    }
                }
            }
        }
    }
}
