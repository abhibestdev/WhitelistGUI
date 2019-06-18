package me.abhi.whitelistgui.listener;

import me.abhi.whitelistgui.util.PlayerUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class PlayerListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (!event.getInventory().getName().startsWith("Whitelisted Players | Page ")) {
            return;
        }
        event.setCancelled(true);
        int page = Integer.parseInt(event.getInventory().getName().replace("Whitelisted Players | Page ", ""));
        int slot = event.getSlot();
        if (slot < 45 && event.getCurrentItem().getType() == Material.SKULL_ITEM && event.isLeftClick() && event.isShiftClick() && player.hasPermission("whitelistgui.unwhitelist")) {
            String username = ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName());
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "whitelist remove " + username);
            PlayerUtil.openGUI(player, page);
            player.sendMessage(ChatColor.GOLD + "You have unwhitelisted the player " + ChatColor.WHITE + username + ChatColor.GOLD + ".");
        }
        if (slot == 53) {
            PlayerUtil.openGUI(player, page + 1);
        }
        if (slot == 49 && player.hasPermission("whitelistgui.toggle")) {
            Bukkit.setWhitelist(Bukkit.hasWhitelist() ? false : true);
            player.sendMessage(ChatColor.GOLD + "Whitelist has been " + ChatColor.WHITE + (Bukkit.hasWhitelist() ? "enabled" : "disabled") + ChatColor.GOLD + ".");
            PlayerUtil.openGUI(player, page);
        }
        if (slot == 45) {
            if (page <= 1) {
                return;
            }
            PlayerUtil.openGUI(player, page - 1);
        }
    }
}
