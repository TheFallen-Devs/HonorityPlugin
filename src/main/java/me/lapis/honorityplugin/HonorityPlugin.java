package me.lapis.honorityplugin;

//Java imports
import java.io.IOException;

//Bukkit imports
import me.lapis.honorityplugin.skript.functions.FuncSetPlayerHonority;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

//Skript imports
import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import ch.njol.skript.lang.function.Functions;

//Honority imports
import me.lapis.honorityplugin.honority.Honority;
import me.lapis.honorityplugin.skript.functions.FuncGetPlayerHonority;

public final class HonorityPlugin extends JavaPlugin {

    //variables for colored console
    private PluginDescriptionFile pluginDescription = this.getDescription();                            //get Plugin's description file
    private ColorfulConsole colorfulConsole = new ColorfulConsole(this.pluginDescription);              //get ConsoleSender & send colorized logs using it

    //variables for JavaPlugin
    HonorityEventManager eventManager;
    HonorityCommandManager cmdManager;

    //variables for Honority feature
    static Honority honorityManager;

    //variables for Skript Addon feature
    private static HonorityPlugin instance;
    private static SkriptAddon addonInstance;

    public HonorityPlugin() {
        if (instance == null) {
            instance = this;
        } else {
            throw new IllegalStateException();
        }
    }

    @Override
    public void onEnable() {
        // Plugin startup logic

        // Prints plugin information
        this.colorfulConsole.console(colorfulConsole.log,"Loading plugin informations...");
        this.colorfulConsole.console(colorfulConsole.info, "====================================================");
        this.colorfulConsole.console(colorfulConsole.info, "Plugin Version : " + this.pluginDescription.getVersion());
        this.colorfulConsole.console(colorfulConsole.info, "Plugin API(Bukkit) Version : " + this.pluginDescription.getAPIVersion());
        this.colorfulConsole.console(colorfulConsole.info, "Plugin Authors : " + this.pluginDescription.getAuthors());
        this.colorfulConsole.console(colorfulConsole.info, "Plugin Website : " + this.pluginDescription.getWebsite());
        this.colorfulConsole.console(colorfulConsole.info, "====================================================");
        this.colorfulConsole.console(colorfulConsole.log,"Successfully loaded plugin information!");

        /*
        * [ Check Object ]
        * Check if objects are same. <Debug>
        */
        //Initialize Honority Object
        honorityManager = new Honority(this.colorfulConsole);
        //this.colorfulConsole.console(this.colorfulConsole.debug, "I have Honority object : " + this.honorityManager.toString());

        /* [ Register Event Manager class ]
         * Register Event Manager class of this plugin(me.lapis.honorityplugin.HonorityEventManager.java).
         */

        //Initialize plugin's EventManager class
        this.colorfulConsole.console(colorfulConsole.log, "[EventRegistration] Initializing EventManager...");
        this.eventManager = new HonorityEventManager(this.colorfulConsole, honorityManager);
        this.colorfulConsole.console(colorfulConsole.log, "[EventRegistration] Initialized!");

        //Register EventManager class into plugin
        this.colorfulConsole.console(colorfulConsole.log, "[EventRegistration] Registering EventManager class into plugin...");
        Bukkit.getPluginManager().registerEvents(this.eventManager, this);
        this.colorfulConsole.console(colorfulConsole.log, "[EventRegistration] Successfully registerd EventManager class!");

        /* [ Register command manager class ]
         * Register command manager class of this plugin(me.lapis.honorityplugin.HonorityCommandManager.java).
         */

        //Initialize plugin's command manager class
        this.colorfulConsole.console(colorfulConsole.log,  "[CommandRegistration] Initializing CommandManager...");
        this.cmdManager = new HonorityCommandManager(this.colorfulConsole, honorityManager);
        this.colorfulConsole.console(colorfulConsole.log,  "[CommandRegistration] Initialized!");

        //Register commandManager into commands
        this.colorfulConsole.console(colorfulConsole.log,  "[CommandRegistration] Registering commands...");
        for(String key: this.pluginDescription.getCommands().keySet()){
            this.colorfulConsole.console(colorfulConsole.log, "[CommandRegistration] Registering `" + key + "` command...");
            try{
                getCommand(key).setExecutor(this.cmdManager);
            }catch (NullPointerException e){
                this.colorfulConsole.console(colorfulConsole.error, "[CommandRegistration] An Exception occured during registering command, " + key);
                this.colorfulConsole.console(colorfulConsole.error, "[CommandRegistration] A command `" + key + "` seems invalid command! ");
                this.colorfulConsole.console(colorfulConsole.error, e.getMessage());
            }catch (Exception e){
                this.colorfulConsole.console(colorfulConsole.error, "[CommandRegistration] An Exception occured during registering command, " + key);
                this.colorfulConsole.console(colorfulConsole.error, e.getMessage());
            }
            this.colorfulConsole.console(colorfulConsole.log, "[CommandRegistration] Done!");
        }
        this.colorfulConsole.console(colorfulConsole.log,  "[CommandRegistration] Successfully registerd commands!");

        /* [ Load Honority Datas ]
         *  Load Honority Datas of currently online players
         */
        honorityManager.LoadAllHonorityInCollection(Bukkit.getOnlinePlayers());
        //honorityManager.ShowHonorityOnAllPlayers(this, Bukkit.getOnlinePlayers());


        if (Bukkit.getPluginManager().getPlugin("Skript") != null) {
            // put all code related to Skript here : Skript addon functions
            this.colorfulConsole.console(colorfulConsole.info,"Found a plugin, Skript. Loading classes from me.lapis.honorityplugin.skript");
            try {
                //this.colorfulConsole.console(colorfulConsole.info,"Try to load the `skript.expressions` subpackage");
                //addonInstance.loadClasses("me.lapis.honorityplugin", "skript.expressions");

                this.colorfulConsole.console(this.colorfulConsole.log, "Try to register Skript addon functions");
                SkriptFunctionsRegister();
            } catch (Exception e){
                this.colorfulConsole.console(this.colorfulConsole.log, "An error occured! :\nCause :\n" + e.getCause() + "\nError Message :\n" + e.getMessage());
            }
        }

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

        /* [ Save Honority Datas ]
         *  Save Honority Datas of currently online players
         */
        honorityManager.SaveAllHonorityInCollection(Bukkit.getOnlinePlayers());

    }

    /*
    * Skript Addon codes
    * Reference : https://github.com/btk5h/reqn/blob/master/src/main/java/com/btk5h/reqn/Reqn.java
    */

    public static SkriptAddon getAddonInstance() {
        if (addonInstance == null) {
            addonInstance = Skript.registerAddon(getInstance());
        }
        return addonInstance;
    }

    public static HonorityPlugin getInstance() {
        if (instance == null) {
            throw new IllegalStateException();
        }
        return instance;
    }

    public static Honority getHonorityManager() {
        if (honorityManager == null) {
            throw new IllegalStateException("Honority Manager has not been initialized!");
        }
        return honorityManager;
    }

    void SkriptFunctionsRegister(){
        this.colorfulConsole.console(this.colorfulConsole.log, "Registering Skript addon functions");

        this.colorfulConsole.console(this.colorfulConsole.log, "Registering function : getPlayerHonority(uuid)");
        Functions.registerFunction(new FuncGetPlayerHonority(honorityManager));


        this.colorfulConsole.console(this.colorfulConsole.log, "Registering function : setPlayerHonority(uuid, number)");
        Functions.registerFunction(new FuncSetPlayerHonority(honorityManager));

    }
}
