package me.lapis.honorityplugin;

import me.lapis.honorityplugin.honority.Honority;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class HonorityEventManager implements Listener {

    ColorfulConsole colorfulConsole;
    Honority honorityManager;

    public HonorityEventManager(ColorfulConsole colorfulConsole, Honority pluginHonorityManager){
        this.colorfulConsole = colorfulConsole;
        this.honorityManager = pluginHonorityManager;
        //this.colorfulConsole.console(this.colorfulConsole.debug, "I received Honority object : " + this.honorityManager.toString());
    }

    /**
     * An event occured when player has joined to the server.
     * @params: PlayerJoinEvent e
     * */
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        this.colorfulConsole.console(this.colorfulConsole.log, "A Player " + e.getPlayer().getName() + " has joined the server!");
        Player player = e.getPlayer();
        boolean honorityLoadResult = this.honorityManager.LoadPlayerHonority(player.getUniqueId());
        if(!honorityLoadResult){
            this.colorfulConsole.console(this.colorfulConsole.log, "Cannot load " + e.getPlayer().getName() + "`s honority data!");
        }

        //this.honorityManager.ShowHonorityOnPlayer(Bukkit.getPluginManager().getPlugin("HonorityPlugin"), player);
    
        this.colorfulConsole.console(this.colorfulConsole.debug,"[LoadPlayerHonority] Change player's name from " + player.getName());
        short playerHonorityValue = this.honorityManager.GetPlayerHonority(player.getUniqueId());
        String newName =  "[ " + this.colorfulConsole.gold + "명성도 : " + this.colorfulConsole.white + playerHonorityValue + "] " + player.getName();
        this.colorfulConsole.console(this.colorfulConsole.debug,"[LoadPlayerHonority] Player's new display name : " + this.colorfulConsole.white + newName);
        player.setDisplayName(newName);
    }

    /**
     * An event occured when player has quit to the server.
     * @params: PlayerQuitEvent e
     * */
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e){
        this.colorfulConsole.console(this.colorfulConsole.log, "A Player " + e.getPlayer().getName() + " has quit the server!");
        UUID playerUUID = e.getPlayer().getUniqueId();
        boolean honoritySaveResult = this.honorityManager.SavePlayerHonority(playerUUID);
        if(honoritySaveResult)
            this.colorfulConsole.console(this.colorfulConsole.log, "Successfully saved " + e.getPlayer().getName() + "`s honority data!");
        else
            this.colorfulConsole.console(this.colorfulConsole.log, "Failed to save " + e.getPlayer().getName() + "`s honority data...");

    }
}
