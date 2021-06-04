package me.imystxc.iseeyou;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.server.permission.DefaultPermissionHandler;
import net.minecraftforge.server.permission.DefaultPermissionLevel;
import net.minecraftforge.server.permission.PermissionAPI;
import sun.net.ftp.FtpDirEntry;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class Command extends CommandBase {
    @Override
    public String getName() {
        return "icu";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/icu";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {


        if (args.length > 0 && args.length < 3) {
            EntityPlayerMP player = (EntityPlayerMP)sender;
            if (args[0].equals("reload")) {
                if (sender.canUseCommand(0, "iseeyou.command.reload")) {
                    Config.reloadConfig();
                    sender.sendMessage(new TextComponentString(BreakBlockMessage.regex("&7[&c&lICU&7] &3config has been reloaded.")));
                    return;
                } else {
                    sender.sendMessage(new TextComponentString(BreakBlockMessage.regex("&cYou don't have permission to reload the config.")));
                    return;
                }
            }

            if (args.length == 1) {
                switch (args[0]) {
                    case "on":
                        sender.getServer().getCommandManager().executeCommand(server, "lp user " + player.getName() + " perm set iseeyou.log.base true");
                        sender.sendMessage(new TextComponentString(BreakBlockMessage.regex("&7[&c&lICU&7] &3You have enabled ICU")));
                        break;
                    case "off":
                        sender.getServer().getCommandManager().executeCommand(server, "lp user " + player.getName() + " perm set iseeyou.log.base false");
                        sender.sendMessage(new TextComponentString(BreakBlockMessage.regex("&7[&c&lICU&7] &3You have disabled ICU")));
                        break;
                }
            }
        }
        else sender.sendMessage(new TextComponentString(BreakBlockMessage.regex("&7[&c&lICU&7] &3Use &b/icu &7<&aon&7/&coff&7>")));
    }

    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        List<String> possibleArgs = new ArrayList<>();
        if (sender instanceof EntityPlayerMP) {
            EntityPlayerMP player = (EntityPlayerMP) sender;

            if (player.canUseCommand(0, "iseeyou.command.reload") && args.length == 1) {
                possibleArgs.add("reload");
            }

            if (player.canUseCommand(0, "iseeyou.command.toggle") && args.length == 1) {
                possibleArgs.add("on");
                possibleArgs.add("off");
            }
        }
        return getListOfStringsMatchingLastWord(args, possibleArgs);
    }
}