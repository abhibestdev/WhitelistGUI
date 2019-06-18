package me.abhi.whitelistgui.command.impl;

import me.abhi.whitelistgui.command.Command;
import me.abhi.whitelistgui.util.PlayerUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WhitelistGUICommand {

    @Command(name = "whitelistgui", aliases = {"wlgui"}, permission = "whitelistgui.command", playersOnly = true)
    public void whitelistGUI(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        PlayerUtil.openGUI(player, 1);
    }
}
