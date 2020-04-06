package me.lapis.honorityplugin;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class HonorityCommandManager implements CommandExecutor {
    ColorfulConsole colorfulConsole;

    public HonorityCommandManager(ColorfulConsole colorfulConsole){
        this.colorfulConsole = colorfulConsole;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        this.colorfulConsole.console(this.colorfulConsole.debug, "[CommandManager] " + '[' + sender.getClass().getCanonicalName() + ']' + sender.getName() + "이(가) " + label + ' ' + Arrays.toString(args) + " 명령어를 사용했습니다. ");
        if(label.equals("honority")) {
            return this.honority(sender, command, label, args);
        }
        else{
            return false;
        }
    }

    private boolean honority(CommandSender sender, Command command, String label, String[] args){
        if(args.length == 0){
            this.colorfulConsole.console(this.colorfulConsole.debug,"`/honority` base command invoked!");
            return this.help_info(sender, "base");
        }
        else{
            String playername = args[0];
            Player player = Bukkit.getPlayer(playername);
            switch (args[1]){
                case "help":
                    /*
                     * Show subcommand's description
                     */

                    this.colorfulConsole.console(this.colorfulConsole.debug,"`/honority help` help base command invoked!");
                    return this.help_info(sender, args[2]);
                case "show":
                    /*
                    * Show player's honority status of given player name.
                    */

                    this.colorfulConsole.console(this.colorfulConsole.debug,"`/honority show` show command invoked!");

                    short honority_value = 0;
                    sender.sendMessage(this.colorfulConsole.gold + playername + this.colorfulConsole.white + "'s honority value is : " + honority_value);
                    return true;
                case "set":
                    /*
                     * Set player's honority status with given arguement
                     */

                    this.colorfulConsole.console(this.colorfulConsole.debug,"`/honority set` set command invoked!");

                    if (args[2]!= null){
                        if(this.isShort(args[2]))
                        {
                            short value = Short.parseShort(args[2]);
                            sender.sendMessage(this.colorfulConsole.gold + playername + this.colorfulConsole.white + "'s honority value is set to: " + value);
                            return true;
                        }
                        else{
                            sender.sendMessage(this.colorfulConsole.error + "New honority value must be number, and it shold be in range of -2^15 ~ +2^15-1");
                            return false;
                        }
                    }
                    else{
                        sender.sendMessage(this.colorfulConsole.error + "You should give an argument of number to set new honority value!");
                        return false;
                    }
                case "reset":
                    /*
                     * Reset player's honority status into 0(
                     */

                    this.colorfulConsole.console(this.colorfulConsole.debug,"`/honority reset` reset command invoked!");
                    sender.sendMessage(this.colorfulConsole.gold + playername + this.colorfulConsole.white + "'s honority value is resetted.");

                    return true;
                case "add":
                    /*
                     * Set player's honority status with given arguement
                     */

                    this.colorfulConsole.console(this.colorfulConsole.debug,"`/honority add` add command invoked!");

                    if(this.isShort(args[2])){
                        short value = Short.parseShort(args[2]);
                        sender.sendMessage(this.colorfulConsole.white + "Successfully added" + value + " to " + this.colorfulConsole.gold + playername + this.colorfulConsole.white + "'s honority value.");
                        return true;
                    }
                    else{
                        sender.sendMessage(this.colorfulConsole.error + "New honority value must be number, and it shold be in range of -2^15 ~ +2^15-1 .");
                        return false;
                    }
                default:
                    this.colorfulConsole.console(this.colorfulConsole.error,"Unknown subcommand has beed invoked!");
                    sender.sendMessage(this.colorfulConsole.error + "Unknown subcommand has beed invoked!");
                    return false;
            }
        }
    }

    private boolean help_info(CommandSender sender, String subcommand){
        String show_desc = "/honority (playername) show : Show honority status of given player. [Only Player Can Use This Command]";
        String set_desc = "/honority (playername) set (value) : Set player's honority value into given value. Only Number can be used on value. Negative number is acceptable.";
        String reset_desc = "/honority (playername) reset : Set player's honority value into initial value(normally, 0). This is an alias of /honority (playername) set 0";
        String add_desc = "/honority (playername) add (value) : Add given value into player's honority value. Only Number can be used on value. Negative number is acceptable.";
        String help_desc = "/honority help (command) : Show description about given command.";

        String honority_desc = this.colorfulConsole.white + "Command group from plugin: "
                                + this.colorfulConsole.gold + "Honirity\n"
                                + this.colorfulConsole.white + "Commands list : \n"
                                + show_desc + '\n'
                                + set_desc + '\n'
                                + reset_desc + '\n'
                                + add_desc + '\n'
                                + help_desc;
        if(subcommand.equals("")){
            subcommand = "base";
        }
        switch (subcommand){
            case "base":
                sender.sendMessage(this.colorfulConsole.white + honority_desc);
                return true;
            case "show":
                sender.sendMessage(this.colorfulConsole.white + show_desc);
                return true;
            case "set":
                sender.sendMessage(this.colorfulConsole.white + set_desc);
                return true;
            case "reset":
                sender.sendMessage(this.colorfulConsole.white + reset_desc);
                return true;
            case "add":
                sender.sendMessage(this.colorfulConsole.white + add_desc);
                return true;
            case "help":
                sender.sendMessage(this.colorfulConsole.white + help_desc);
                return true;
            default:
                sender.sendMessage(this.colorfulConsole.error + "Invalid subcommand!");
                return false;

        }
    }

    boolean isShort(String s)
    {
        try
        {
            short parsedShort = Short.parseShort(s);
            return true;
        }
        catch (NumberFormatException ex)
        {
            return false;
        }
    }

}
