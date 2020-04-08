package me.lapis.honorityplugin;

import me.lapis.honorityplugin.honority.Honority;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class HonorityEventManager implements Listener {

    ColorfulConsole colorfulConsole;
    Honority honorityManager;

    public HonorityEventManager(ColorfulConsole colorfulConsole, Honority pluginHonorityManager){
        this.colorfulConsole = colorfulConsole;
        this.honorityManager = pluginHonorityManager;
        this.colorfulConsole.console(this.colorfulConsole.debug, "I received Honority object : " + this.honorityManager.toString());
    }

    /**
     * An event occured when player has joined to the server.
     * @params: PlayerJoinEvent e
     * */
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        this.colorfulConsole.console(this.colorfulConsole.log, "A Player " + e.getPlayer().getName() + " has joined the server!");
        UUID playerUUID = e.getPlayer().getUniqueId();
        boolean result = this.honorityManager.LoadPlayerHonority(playerUUID);
        if(!result){
            this.colorfulConsole.console(this.colorfulConsole.log, "A Player " + e.getPlayer().getName() + " has joined the server!");
        }
    }

    /**
     * An event occured when player has quit to the server.
     * @params: PlayerQuitEvent e
     * */
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e){
        this.colorfulConsole.console(this.colorfulConsole.log, "A Player " + e.getPlayer().getName() + " has quit the server!");
        UUID playerUUID = e.getPlayer().getUniqueId();
        boolean result = this.honorityManager.SavePlayerHonority(playerUUID);

    }
}
