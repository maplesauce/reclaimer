package net.maplesauce.reclaimer;

import net.milkbowl.vault.permission.Permission;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class DonatorReclaim extends JavaPlugin implements Listener {

    private static Permission permissions;

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        saveDefaultConfig();
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (player.hasPlayedBefore()) return;
        String group = permissions.getPrimaryGroup(player);

        if (getConfig().contains(group)) {
            for (String string : getConfig().getStringList(group)) {
                getServer().dispatchCommand(getServer().getConsoleSender(), string.replace("{name}", player.getName()));
            }
        }
    }

    private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> registeredServiceProvider =
                getServer().getServicesManager().getRegistration(Permission.class);
        permissions = registeredServiceProvider.getProvider();
        return permissions != null;
    }
}
