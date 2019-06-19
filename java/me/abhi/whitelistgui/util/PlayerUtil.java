package me.abhi.whitelistgui.util;

import me.abhi.whitelistgui.WhitelistGUI;
import org.bukkit.*;
import org.bukkit.block.Skull;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;

public class PlayerUtil {

    public static void openGUI(Player player, int page) {
        boolean nextPage = false;
        Inventory inventory = WhitelistGUI.getInstance().getServer().createInventory(null, 54, "Whitelisted Players | Page " + page);
        for (int i = (page - 1) * 26 + (page - 1); i <= (page * 27) - 1; i++) {
            if (WhitelistGUI.getInstance().getServer().getWhitelistedPlayers().size() < i + 1) {
                break;
            }
            OfflinePlayer offlinePlayer = (OfflinePlayer) WhitelistGUI.getInstance().getServer().getWhitelistedPlayers().toArray()[i];
            List<String> lore = new ArrayList();
            lore.add(ChatColor.RED + "Shift + Left Click to unwhitelist " + offlinePlayer.getName() + ".");
            ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
            SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
            skullMeta.setOwner(offlinePlayer.getName());
            skullMeta.setDisplayName(ChatColor.YELLOW + offlinePlayer.getName());
            skullMeta.setLore(lore);
            skull.setItemMeta(skullMeta);
            inventory.setItem((page == 1 ? i : i - (27 * (page - 1))), skull);
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
