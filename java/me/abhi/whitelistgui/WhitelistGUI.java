package me.abhi.whitelistgui;

import lombok.Getter;
import me.abhi.whitelistgui.command.CommandHandler;
import me.abhi.whitelistgui.command.impl.WhitelistGUICommand;
import me.abhi.whitelistgui.listener.PlayerListener;
import org.bukkit.plugin.java.JavaPlugin;

public class WhitelistGUI extends JavaPlugin {

    @Getter
    private static WhitelistGUI instance;

    public void onEnable() {
        instance = this;
        registerCommands();
        registerListeners();
    }

    private void registerCommands() {
        CommandHandler commandHandler = new CommandHandler(this);
        commandHandler.register(new WhitelistGUICommand());
    }

    private void registerListeners() {
        getServer().getPluginManager().registerEvents(new PlayerListener(), this);
    }
}
