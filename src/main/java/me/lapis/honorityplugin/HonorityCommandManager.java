package me.lapis.honorityplugin;

import me.lapis.honorityplugin.honority.Honority;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class HonorityCommandManager implements CommandExecutor {
    ColorfulConsole colorfulConsole;
    Honority honorityManager;

    public HonorityCommandManager(ColorfulConsole colorfulConsole, Honority pluginHonorityManager){
        this.colorfulConsole = colorfulConsole;
        this.honorityManager = pluginHonorityManager;
        this.colorfulConsole.console(this.colorfulConsole.debug, "I received Honority object : " + this.honorityManager.toString());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        this.colorfulConsole.console(this.colorfulConsole.debug, "[CommandManager] /" + label + ' ' + Arrays.toString(args) + " command has been executed!");
        if(label.equals("honority")) {
            return this.honority(sender, command, label, args);
        }
        else{
            return false;
        }
    }

    private boolean honority(CommandSender sender, Command command, String label, String[] args){
        this.colorfulConsole.console(this.colorfulConsole.debug, "[CommandManager] args: " + this.colorfulConsole.gold + Arrays.toString(args)
                + this.colorfulConsole.blue + ", args length : " + this.colorfulConsole.gold + args.length);
        switch(args.length){
            //only "/honority"
            case 0:
                /*
                 * Show all subcommands' descriptions
                 */
                this.colorfulConsole.console(this.colorfulConsole.debug,"`/honority` base command invoked!");
                return this.help_info(sender, "base");

            // "/honority (subcommand)
            case 1:
                if (args[0].equals("help")){
                    this.colorfulConsole.console(this.colorfulConsole.debug,"`/honority help` help command with no args invoked!");
                    return this.help_info(sender, "base");
                } else {
                    this.colorfulConsole.console(this.colorfulConsole.error,"Unknown Command!");
                    return false;
                }
            // "/honority (subcommand) arg1
            case 2:
                switch (args[0]){
                    case "help":
                        /*
                         * Show subcommand's description
                         */
                        this.colorfulConsole.console(this.colorfulConsole.debug,"`/honority help " + args[1] + "` help command invoked!");
                        return this.help_info(sender, args[1]);

                    case "show":
                        /*
                         * Show player's honority status of given player name.
                         */
                        try{
                            Player targetPlayer = Bukkit.getPlayer(args[1]);
                            short targetHonorityValue = this.honorityManager.GetPlayerHonority(targetPlayer.getUniqueId());
                            sender.sendMessage(this.colorfulConsole.gold + targetPlayer.getName()
                                    + this.colorfulConsole.white + "'s honority value is : " + targetHonorityValue);

                        }catch (NullPointerException e){
                            this.colorfulConsole.console(this.colorfulConsole.error,"Error Occured : #player Object is Pointing Null!\n" + e.getMessage());
                            sender.sendMessage(this.colorfulConsole.gold + args[1] + this.colorfulConsole.white + " is an unknown player!");
                            return false;
                        }
                        return true;

                    case "reset":
                        /*
                         * Reset player's honority status into 0(
                         */
                        try{
                            Player targetPlayer = Bukkit.getPlayer(args[1]);
                            short oldValue = this.honorityManager.SetPlayerHonority(targetPlayer.getUniqueId(), (short) 0);
                            sender.sendMessage(this.colorfulConsole.gold + targetPlayer.getName()
                                    + this.colorfulConsole.white + "'s honority value has been resetted!");

                        }catch (NullPointerException e){
                            this.colorfulConsole.console(this.colorfulConsole.error,"Error Occured : #player Object or target player's honority data is Null!\n" + e.getMessage());
                            sender.sendMessage(this.colorfulConsole.gold + args[1] + this.colorfulConsole.white + " is an unknown player!");
                            return false;
                        }
                        return true;
                }
            // "/honority (subcommand) arg1 arg2
            case 3:
                switch (args[0]){
                    case "set":
                        /*
                         * Set player's honority status with given arguement
                         */
                        try{
                            Player targetPlayer = Bukkit.getPlayer(args[1]);
                            if(this.isShort(args[2])){
                                short oldValue = this.honorityManager.SetPlayerHonority(targetPlayer.getUniqueId(), Short.parseShort(args[2]));
                                sender.sendMessage(this.colorfulConsole.gold + targetPlayer.getName()
                                        + this.colorfulConsole.white + "'s honority value has been changed from "+ oldValue + " to " + args[2] + "!");
                            }else{
                                sender.sendMessage(this.colorfulConsole.error + "New honority value must be number, and it shold be in range of -150 ~ 150");
                                return false;
                            }

                        }catch (NullPointerException e){
                            this.colorfulConsole.console(this.colorfulConsole.error,"Error Occured : #player Object or target player's honority data is Null!\n" + e.getMessage());
                            sender.sendMessage(this.colorfulConsole.gold + args[1] + this.colorfulConsole.white + " is an unknown player!");
                            return false;
                        }catch (ArithmeticException e){
                            this.colorfulConsole.console(this.colorfulConsole.error,"Error Occured : Given value is out of range(-150~150)\n" + e.getMessage());
                            sender.sendMessage(this.colorfulConsole.white + "Given value "
                                    + this.colorfulConsole.gold + args[1]
                                    + this.colorfulConsole.white + " is out of acceotable range!");
                            return false;
                        }
                        return true;

                    case "add":
                        /*
                         * Add given value to given player's honority value
                         */
                        try{
                            Player targetPlayer = Bukkit.getPlayer(args[1]);
                            if(this.isShort(args[2])){
                                short oldValue = this.honorityManager.GetPlayerHonority(targetPlayer.getUniqueId());
                                short newValue = (short) (oldValue + Short.parseShort(args[2]));
                                this.honorityManager.SetPlayerHonority(targetPlayer.getUniqueId(), newValue);
                                sender.sendMessage(this.colorfulConsole.gold + targetPlayer.getName()
                                        + this.colorfulConsole.white + "'s honority value has been changed from "+ oldValue + " to " + newValue + "!");
                            }else{
                                sender.sendMessage(this.colorfulConsole.error + "New honority value must be number, and it shold be in range of -150 ~ 150");
                                return false;
                            }

                        }catch (NullPointerException e){
                            this.colorfulConsole.console(this.colorfulConsole.error,"Error Occured : #player Object or target player's honority data is Null!\n" + e.getMessage());
                            sender.sendMessage(this.colorfulConsole.gold + args[1] + this.colorfulConsole.white + " is an unknown player!");
                            return false;
                        }catch (ArithmeticException e){
                            this.colorfulConsole.console(this.colorfulConsole.error,"Error Occured : Given value is out of range(-150~150)\n" + e.getMessage());
                            sender.sendMessage(this.colorfulConsole.white + "Given value "
                                    + this.colorfulConsole.gold + args[1]
                                    + this.colorfulConsole.white + " is out of acceotable range!");
                            return false;
                        }
                        return true;
                }
            default:
                this.colorfulConsole.console(this.colorfulConsole.error,"Unknown Command!");
                return false;

        }
    }

    private boolean help_info(CommandSender sender, String subcommand){
        String show_desc = "/honority show (playername) : Show honority status of given player. [Only Player Can Use This Command]";
        String set_desc = "/honority set (playername) (value) : Set player's honority value into given value. Only Number can be used on value. Negative number is acceptable.";
        String reset_desc = "/honority reset (playername) : Set player's honority value into initial value(normally, 0). This is an alias of /honority (playername) set 0";
        String add_desc = "/honority add (playername) (value) : Add given value into player's honority value. Only Number can be used on value. Negative number is acceptable.";
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
                this.colorfulConsole.console(this.colorfulConsole.error,"Error Occured : " + subcommand + " is an invalid subcommand!");
                return false;

        }
    }

    private boolean isShort(String s)
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

    @Deprecated
    private boolean deprecated_honority(CommandSender sender, Command command, String label, String[] args){
        if(args.length == 0){
            this.colorfulConsole.console(this.colorfulConsole.debug,"`/honority` base command invoked!");
            return this.help_info(sender, "base");
        }
        else{
            if(args[0].equals("help")){
                /*
                 * Show subcommand's description
                 */
                this.colorfulConsole.console(this.colorfulConsole.debug,"`/honority help` help base command invoked!");
                return this.help_info(sender, args[1]);
            }else{
                String playername = args[0];
                Player player = Bukkit.getPlayer(playername);

                try{
                    player.getName();
                } catch (NullPointerException e){
                    this.colorfulConsole.console(this.colorfulConsole.error,"Error Occured : #player Object is Pointing Null!\n" + e.getMessage());
                    sender.sendMessage(this.colorfulConsole.gold + playername + this.colorfulConsole.white + " is an unknown player!");
                    return false;
                }
                switch (args[1]){
                    case "show":

                        this.colorfulConsole.console(this.colorfulConsole.debug,"`/honority show` show command invoked!");
                        short honority_value = this.honorityManager.GetPlayerHonority(player.getUniqueId());
                        sender.sendMessage(this.colorfulConsole.gold + playername + this.colorfulConsole.white + "'s honority value is : " + honority_value);

                        return true;
                    case "set":

                        this.colorfulConsole.console(this.colorfulConsole.debug,"`/honority set` set command invoked!");

                        if (args[2]!= null){
                            if(this.isShort(args[2]))
                            {
                                short value = Short.parseShort(args[2]);
                                this.honorityManager.SetPlayerHonority(player.getUniqueId(), value);
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

                        this.colorfulConsole.console(this.colorfulConsole.debug,"`/honority reset` reset command invoked!");
                        sender.sendMessage(this.colorfulConsole.gold + playername + this.colorfulConsole.white + "'s honority value is resetted.");

                        return true;
                    case "add":

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
    }

}
