package me.lapis.honorityplugin;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HonorityCommandManager implements CommandExecutor {
    ColorfulConsole colorfulConsole;

    public HonorityCommandManager(ColorfulConsole colorfulConsole){
        this.colorfulConsole = colorfulConsole;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        this.colorfulConsole.console(this.colorfulConsole.debug, "[CommandManager] " + sender.getName() + "이(가) " + label + ' ' + Arrays.toString(args) + " 명령어를 사용했습니다. ");
        if(label.equals("honority")) {
            return this.honority(sender, command, label, args);
        }
        else{
            return false;
        }
    }

    private boolean honority(CommandSender sender, Command command, String label, String[] args){
        if(args.length == 0){
            String honority_desc =
                    this.colorfulConsole.white + "Command group from plugin: " + this.colorfulConsole.gold + "Honirity\n"
                    + this.colorfulConsole.white + "Commands list : \n"
                    + "/honority (username) show : show honority status of given player. [Only Player Can Use This Command]\n"
                    + "/honority (username) set (value) : set player's honority value into given value.\n"
                    + "/honority (username) reset : set player's honority value into initial value(normally, 0). this is an alias of /honority (username) set 0\n"
                    + "/honority (username) add (value) : add given value into player's honority value. you can add negative number, too. only integer can be used on value.\n"
                    + "/honority help (command) : show description about given command.\n";
            sender.sendMessage(this.colorfulConsole.white + honority_desc);
            return true;
        }
        else{
            String username = args[0];
            switch (args[1]){
                case "help":

                    return true;
                case "show":
                    /*
                    * Show player's honority status of given player name.
                    * */
                    short honority_value = 0;
                    sender.sendMessage(this.colorfulConsole.gold + username + this.colorfulConsole.white + "'s honority value is : " + honority_value);
                    return true;
                case "set":
                    /*
                     * Set player's honority status with given arguement
                     * */

                    return true;
                case "reset":
                    /*
                     * Reset player's honority status into 0(
                     * */

                    return true;
                case "add":
                    /*
                     * Set player's honority status with given arguement
                     * */

                    return true;
                default:
                    return false;
            }
        }
    }

}
