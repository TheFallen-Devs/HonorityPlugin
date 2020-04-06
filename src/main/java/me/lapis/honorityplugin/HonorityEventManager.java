package me.lapis.honorityplugin;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class HonorityEventManager implements Listener {

    ColorfulConsole colorfulConsole;

    public HonorityEventManager(ColorfulConsole colorfulConsole){
        this.colorfulConsole = colorfulConsole;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        /**
         * Change Player Join Message.
         * @params: PlayerJoinEvent e
         * */
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e){

    }
}
