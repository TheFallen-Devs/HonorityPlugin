package me.lapis.honorityplugin;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import me.lapis.honorityplugin.honority.Honority;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.Map;

public final class HonorityPlugin extends JavaPlugin {

    private static HonorityPlugin instance;
    private static SkriptAddon addonInstance;

    private PluginDescriptionFile pluginDescription = this.getDescription();                            //get Plugin's description file
    private ColorfulConsole colorfulConsole = new ColorfulConsole(this.pluginDescription);              //get ConsoleSender & send colorized logs using it

    HonorityCommandManager cmdManager = new HonorityCommandManager(this.colorfulConsole);       //Plugin's command manager class
    HonorityEventManager eventManager = new HonorityEventManager(this.colorfulConsole);         //Plugin's event manager class
    Honority honorityManager = new Honority(this.colorfulConsole);                                                  //Honority manager class

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
        if (Bukkit.getPluginManager().getPlugin("Skript") != null) {
            // put all code related to Skript here : Skript addon function
            try {
                getAddonInstance().loadClasses("me.lapis.honorityplugin", "skript");
                this.colorfulConsole.console(colorfulConsole.info,"플러그인의 정보 파일을 불러옵니다...");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        /* [ 플러그인 정보 출력 ]
         * 이 플러그인의 정보를 pluginDescription 을 통해 출력합니다.
         */

        this.colorfulConsole.console(colorfulConsole.info,"플러그인의 정보 파일을 불러옵니다...");
        this.colorfulConsole.console(colorfulConsole.info, "====================================================");
        this.colorfulConsole.console(colorfulConsole.info, "Plugin Version : " + this.pluginDescription.getVersion());
        this.colorfulConsole.console(colorfulConsole.info, "Plugin API(Bukkit) Version : " + this.pluginDescription.getAPIVersion());
        this.colorfulConsole.console(colorfulConsole.info, "Plugin Authors : " + this.pluginDescription.getAuthors());
        this.colorfulConsole.console(colorfulConsole.info, "Plugin Website : " + this.pluginDescription.getWebsite());
        this.colorfulConsole.console(colorfulConsole.info, "====================================================");
        this.colorfulConsole.console(colorfulConsole.info,"플러그인의 정보 파일을 불러왔습니다!");

        /* [ 플러그인 이벤트 수신자 등록 ]
         * 이 플러그인의 이벤트 수신자 클래스인 me.lapis.firstplugin.EventManager 클래스를 버킷의 이벤트 수신자에 추가합니다.
         * */

        this.colorfulConsole.console(colorfulConsole.info, "[EventRegistration] 플러그인의 이벤트 수신 메소드를 등록하는 중입니다...");
        Bukkit.getPluginManager().registerEvents(this.eventManager, this);
        this.colorfulConsole.console(colorfulConsole.info, "[EventRegistration] 플러그인의 이벤트 수신 메소드를 등록했습니다!");

        /* [ 플러그인 커맨드 관리자 등록 ]
         * 이 플러그인의 커맨드 관리자 클래스인 me.lapis.firstplugin.CommandManager 클래스를 이 플러그인의 명령어들의 수신자에 추가합니다.
         * */

        this.colorfulConsole.console(colorfulConsole.debug,  "[CommandRegistration] Initializing HonorityCommandManager class...");

        Map<String, Map<String, Object>> commandsMap = this.pluginDescription.getCommands();
        this.colorfulConsole.console(colorfulConsole.debug,  "[CommandRegistration] Collecting commands Map keys");
        for(String key: commandsMap.keySet()){
            this.colorfulConsole.console(colorfulConsole.debug, "[CommandRegistration] key : " + key);
            getCommand(key).setExecutor(this.cmdManager);
        }

        for(Player p: Bukkit.getServer().getOnlinePlayers()){
            this.colorfulConsole.console(this.colorfulConsole.debug, "Found an online player " + p.getDisplayName() + " in server. Register player's honority data.");
            boolean honorityLoadResult = this.honorityManager.LoadPlayerHonority(p.getUniqueId());
            if(!honorityLoadResult){
                this.colorfulConsole.console(this.colorfulConsole.error, "Player " + p.getDisplayName() + " does not have honority data file! creating new one...");
                boolean honorityCreateResult = this.honorityManager.CreatePlayerHonority(p.getUniqueId());
                if(honorityCreateResult){
                    this.colorfulConsole.console(this.colorfulConsole.error, "Player " + p.getDisplayName() + "'s honority data successfully created and saved.");
                }
                else{
                    this.colorfulConsole.console(this.colorfulConsole.error, "Player " + p.getDisplayName() + "'s honority data occured error during ");
                }
            }
        }

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        for(Player p: Bukkit.getServer().getOnlinePlayers()){
            this.colorfulConsole.console(this.colorfulConsole.debug, "Found an online player " + p.getDisplayName() + " in server. Save player's honority data.");
            boolean honoritySaveResult = this.honorityManager.SavePlayerHonority(p.getUniqueId());
            if(!honoritySaveResult){
                this.honorityManager.CreatePlayerHonority(p.getUniqueId());
            }
        }
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
}
