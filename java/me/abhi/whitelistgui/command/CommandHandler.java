package me.abhi.whitelistgui.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.SimplePluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 7x6
 * @since 2/05/2019
 */

public class CommandHandler {

    private CommandMap commandMap;
    private JavaPlugin javaPlugin;

    public CommandHandler(JavaPlugin javaPlugin) {
        setJavaPlugin(javaPlugin);
        try {
            if (javaPlugin.getServer().getPluginManager() instanceof SimplePluginManager) {
                SimplePluginManager simplePluginManager = (SimplePluginManager) javaPlugin.getServer().getPluginManager();

                Field field = simplePluginManager.getClass().getDeclaredField("commandMap");
                field.setAccessible(true);

                setCommandMap((CommandMap) field.get(Bukkit.getPluginManager()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void register(final Object object) {
        for (final Method method : object.getClass().getDeclaredMethods()) {
            for (Annotation annotation : method.getDeclaredAnnotations()) {
                if (annotation instanceof Command) {
                    final Command command = (Command) annotation;
                    org.bukkit.command.Command bukkitCommand = new org.bukkit.command.Command(command.name()) {
                        @Override
                        public boolean execute(CommandSender sender, String s, String[] strings) {
                            if (command.permission() != "" && !sender.hasPermission(command.permission())) {
                                sender.sendMessage(this.getPermissionMessage());
                                return true;
                            }

                            if (command.playersOnly() && !(sender instanceof Player)) {
                                sender.sendMessage(ChatColor.RED + "Players only.");
                                return true;
                            }

                            try {
                                method.invoke(object, sender, strings);
                            } catch (Exception e) {
                                System.err.println("Invalid method");
                                e.printStackTrace();
                            }
                            return true;
                        }
                    };
                    bukkitCommand.setPermissionMessage(ChatColor.RED + "You do not have the required permission to do this.");

                    if (!command.aliases()[0].equalsIgnoreCase(null)) {
                        List<String> aliases = new ArrayList();

                        for (String alias : command.aliases()) aliases.add(alias);


                        bukkitCommand.setAliases(aliases);

                        commandMap.register(getJavaPlugin().getName(), bukkitCommand);
                    }

                }
            }
        }
    }

    public CommandMap getCommandMap() {
        return commandMap;
    }

    public void setCommandMap(CommandMap commandMap) {
        this.commandMap = commandMap;
    }

    public JavaPlugin getJavaPlugin() {
        return javaPlugin;
    }

    public void setJavaPlugin(JavaPlugin javaPlugin) {
        this.javaPlugin = javaPlugin;
    }
}
