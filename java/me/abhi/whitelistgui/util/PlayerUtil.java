package me.abhi.whitelistgui.util;

import me.abhi.whitelistgui.WhitelistGUI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class PlayerUtil {

    public static void openGUI(Player player, int page) {
        boolean nextPage = false;
        Inventory inventory = WhitelistGUI.getInstance().getServer().createInventory(null, 54, "Whitelisted Players | Page " + page);
        for (int i = (page - 1) * 26 + (page - 1); i <= (page * 27) - 1; i++) {
            if (WhitelistGUI.getInstance().getServer().getWhitelistedPlayers().size() < i + 1) {
                break;
            }
            OfflinePlayer offlinePlayer = (OfflinePlayer) WhitelistGUI.getInstance().getServer().getWhitelistedPlayers().toArray()[i];
            inventory.setItem((page == 1 ? i : i - (27 * (page - 1))), new ItemBuilder(Material.SKULL_ITEM).setName(ChatColor.YELLOW + offlinePlayer.getName()).setLore(ChatColor.RED + "Shift + Left Click to unwhitelist " + offlinePlayer.getName() + ".").toItemStack());
            if ((page == 1 ? i : i - (27 * (page - 1))) >= 26) {
                nextPage = true;
            }
        }
        if (nextPage) {
            inventory.setItem(53, new ItemBuilder(Material.ARROW).setName(ChatColor.GREEN + "Turn to page " + (page + 1)).toItemStack());
        }
        inventory.setItem(49, new ItemBuilder(Material.ANVIL).setName(ChatColor.GRAY + "Toggle Whitelist").setLore(Bukkit.hasWhitelist() ? ChatColor.GREEN + "Currently Enabled" : ChatColor.RED + "Currently Disabled").toItemStack());
        if (page > 1) {
            inventory.setItem(45, new ItemBuilder(Material.ARROW).setName(ChatColor.GREEN + "Turn to page " + (page - 1)).toItemStack());
        }
        player.openInventory(inventory);
    }

}
