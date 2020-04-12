package me.lapis.honorityplugin.honority;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

import me.lapis.honorityplugin.ColorfulConsole;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class Honority {
    HashMap<UUID, Short> honorityDict = new HashMap<>();
    ColorfulConsole colorfulConsole;

    public Honority(ColorfulConsole colorfulConsole){
        this.colorfulConsole = colorfulConsole;
        File honorityDataDir = new File("data/HonorityPlugin");
        if(!honorityDataDir.exists()){
            boolean result = honorityDataDir.mkdirs();
        }
    }

    /**
     * Find a honority data using uuid.
     * Load data from data/HonorityPlugin/(uuid value).honor
     *
     * File contains content below:
     * uuid : (uuid)
     * honority_value : (value)
     *
     * @return true if honority data is successfully loaded. if not, return false.
     */
    public boolean LoadPlayerHonority(UUID uuid){
        //Add uuid & honority value pair into #honorityDict(Dictionary<UUID, Short>)
        this.colorfulConsole.console(this.colorfulConsole.log, "[LoadPlayerHonority] Loading honority data of player" + uuid.toString());
        try {
            FileReader honorityFileReader = new FileReader("data/HonorityPlugin/" + uuid.toString() + ".txt");
            BufferedReader bufferedHonorityFileReader = new BufferedReader(honorityFileReader);

            String honorityFile_uuidData = bufferedHonorityFileReader.readLine();
            this.colorfulConsole.console(this.colorfulConsole.debug,"[LoadPlayerHonority] honorityFile_uuidData : " + honorityFile_uuidData);

            String honorityFile_valueData = bufferedHonorityFileReader.readLine();
            this.colorfulConsole.console(this.colorfulConsole.debug,"[LoadPlayerHonority] honorityFile_valueData : " + honorityFile_valueData);

            String strHonorityValue = honorityFile_valueData.split(":")[1];
            this.colorfulConsole.console(this.colorfulConsole.debug,"[LoadPlayerHonority] strHonorityValue : " + strHonorityValue);

            short honorityValue;
            try {
                honorityValue = Short.parseShort(strHonorityValue);
            } catch (NumberFormatException e)
            {
                this.colorfulConsole.console(this.colorfulConsole.error,"[LoadPlayerHonority] Error Occured : Cannot parse data into short!\n" + e.getMessage());
                honorityValue = (short) 0;
            }

            honorityFileReader.close();
            this.honorityDict.put(uuid, honorityValue);

        } catch (FileNotFoundException e){
            this.colorfulConsole.console(this.colorfulConsole.error,"[LoadPlayerHonority] Error Occured : Honority Data File Not Found! Creating new one...\n" + e.getMessage());
            boolean honorityCreationResult = this.CreatePlayerHonority(uuid);
            if(!honorityCreationResult){
                this.colorfulConsole.console(this.colorfulConsole.error,"[LoadPlayerHonority] Error Occured : Honority Data File Not Created! Critical Error Occured :(\n" +
                        "Please report this error to developer.\n" + e.getMessage());
            }

        } catch (IOException e) {
            this.colorfulConsole.console(this.colorfulConsole.error,"[LoadPlayerHonority] Error Occured!\n" + e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * Laod all honority datas of players in given argument.
     * @param playerCollection
     * Load data from data/HonorityPlugin/(uuid value).honor
     *
     * File contains content below:
     * uuid : (uuid)
     * honority_value : (value)
     *
     * @return true if honority data is successfully loaded. if not, return false.
     * @see Honority#LoadPlayerHonority for process of loading each file.
     */
    public boolean LoadAllHonorityInCollection(Collection<? extends Player> playerCollection){
        for(Player p: Bukkit.getServer().getOnlinePlayers()){
            this.colorfulConsole.console(this.colorfulConsole.log, "[LoadAllHonority] Found an online player " + this.colorfulConsole.white + p.getDisplayName()
                    + this.colorfulConsole.gold + " in server. Register player's honority data.");
            boolean honorityLoadResult = this.LoadPlayerHonority(p.getUniqueId());
            if(!honorityLoadResult){
                this.colorfulConsole.console(this.colorfulConsole.error, "[LoadAllHonority] Player " + p.getDisplayName()
                        + " does not have honority data file! creating new one...");
                boolean honorityCreateResult = this.CreatePlayerHonority(p.getUniqueId());
                if(honorityCreateResult){
                    this.colorfulConsole.console(this.colorfulConsole.error, "[LoadAllHonority] Player " + p.getDisplayName()
                            + "'s honority data successfully created and saved.");
                }
                else{
                    this.colorfulConsole.console(this.colorfulConsole.error, "[LoadAllHonority] Player " + p.getDisplayName()
                            + "'s honority data occured error during ");
                }
            }
        }
        this.colorfulConsole.console(this.colorfulConsole.log, "[LoadAllHonority] Loaded all players' datas!");
        return true;
    }

    /**
     * Save a player(whose uuid matches with given uuid)'s honority honority data.
     * Save data is located at data/HonorityPlugin/(uuid value).honor
     *
     * File contains content below:
     * uuid : (uuid)
     * honority_value : (value)
     *
     * @return true if honority data is successfully saved. if not, return false.
     */
    public boolean SavePlayerHonority(UUID uuid){
        this.colorfulConsole.console(this.colorfulConsole.log, "[SavePlayerHonority] Saving honority data of player" + uuid.toString());
        try{
            Writer honorityFileWriter = new FileWriter("data/HonorityPlugin/"+uuid.toString()+".txt", false);
            String save_data = "uuid:"+uuid.toString() + "\nhonority_value:" + this.honorityDict.get(uuid).toString();
            honorityFileWriter.write(save_data);
            honorityFileWriter.close();
            this.colorfulConsole.console(this.colorfulConsole.log, "[SavePlayerHonority] Successfully saved honority data of player" + uuid.toString());
            return true;
        } catch (IOException e) {
            this.colorfulConsole.console(this.colorfulConsole.error,"[SavePlayerHonority] Error Occured!\n" + e.getMessage());
            return false;
        }
    }

    /**
     * Save all honority datas of players in given argument.
     * @param playerCollection
     * Save data is located at data/HonorityPlugin/(uuid value).honor
     *
     * File contains content below:
     * uuid : (uuid)
     * honority_value : (value)
     *
     * @return true if honority data is successfully saved. if not, return false.
     * @see Honority#SavePlayerHonority for process of saving each file.
     */
    public boolean SaveAllHonorityInCollection(Collection<? extends Player> playerCollection){
        for(Player p: playerCollection){
            this.colorfulConsole.console(this.colorfulConsole.log, "[SaveAllHonority] Found an online player " + this.colorfulConsole.gold + p.getDisplayName()
                   + this.colorfulConsole.blue + " in server. Save player's honority data.");
            boolean honoritySaveResult = this.SavePlayerHonority(p.getUniqueId());
            if(!honoritySaveResult){
                this.colorfulConsole.console(this.colorfulConsole.error, "[SaveAllHonority] Player " + p.getDisplayName() + " does not have honority data file! creating new one...");
                boolean honorityCreateResult = this.CreatePlayerHonority(p.getUniqueId());
                if(honorityCreateResult){
                    this.colorfulConsole.console(this.colorfulConsole.error, "[SaveAllHonority] Player " + p.getDisplayName() + "'s honority data successfully created and saved.");
                }
                else{
                    this.colorfulConsole.console(this.colorfulConsole.error, "[SaveAllHonority] Player " + p.getDisplayName()
                            + "'s honority data occurred an error during creating new data file");
                }
            }
        }
        this.colorfulConsole.console(this.colorfulConsole.log, "[SaveAllHonority] Saved all players' datas!");
        return true;
    }

    /**
     * Create and initialize honority data of player whose uuid is matching with given uuid value.
     * Create data file at data/HonorityPlugin/(uuid value).honor
     *
     * @return true if honority data is successfully created. if not, return false.
     */
    public boolean CreatePlayerHonority(UUID uuid){
        //Add uuid & honority value pair into #honorityDict(Dictionary<UUID, Short>)
        this.honorityDict.put(uuid, (short) 0);

        //FileIO : Create data file with given uuid
        try {
            Writer honorityFileWriter = new FileWriter("data/HonorityPlugin/"+uuid.toString()+".txt", false);
            String save_data = "uuid:"+uuid.toString() + "\nhonority_value:0";
            this.honorityDict.put(uuid, (short) 0);
            honorityFileWriter.write(save_data);
            honorityFileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }

    /**
     * Returns honority value of given uuid.
     *
     * @return honority value(short) if given uuid's honority data is loaded(in Honority.honoritydict). if not, return null.
     */
    public short GetPlayerHonority(UUID uuid){
        return honorityDict.get(uuid);
    }

    /**
     * Returns honority value of given uuid.
     *
     * @return honority value(short) if given uuid's honority data is loaded(in Honority.honoritydict). if not, return null.
     */
    public short SetPlayerHonority(UUID uuid, short newValue) throws NullPointerException, ArithmeticException{
        if (newValue<=150 && newValue>=-150){
            short oldValue = this.honorityDict.replace(uuid, newValue);
            this.ShowHonorityOnPlayer(Bukkit.getPluginManager().getPlugin("HonrityPlugin"), Bukkit.getPlayer(uuid));
            return oldValue;
        } else {
          throw new ArithmeticException("New value of player's honority value is out of range!");
        }
    }


    @SuppressWarnings("unchecked")
    public void ShowHonorityOnPlayer(Plugin plugin, Player player) {
        try{
            this.colorfulConsole.console(this.colorfulConsole.log,"Applying honority status to player " + player.getName());
            Object entityPlayer = player.getClass().getMethod("getHandle").invoke(player);
            this.colorfulConsole.console(this.colorfulConsole.debug,"(Object) #entityPlayer :  " + entityPlayer.getClass().getName());
            /*
             * These methods are no longer needed, as we can just access the
             * profile using handle.getProfile. Also, because we can just use
             * the method, which will not change, we don't have to do any
             * field-name look-ups.
             */
            boolean gameProfileExists = false;
            // Some 1.7 versions had the GameProfile class in a different package
            try {
                Class.forName("net.minecraft.util.com.mojang.authlib.GameProfile");
                gameProfileExists = true;
            } catch (ClassNotFoundException ignored) {

            }
            try {
                Class.forName("com.mojang.authlib.GameProfile");
                gameProfileExists = true;
            } catch (ClassNotFoundException ignored) {

            }
            this.colorfulConsole.console(this.colorfulConsole.debug,"Does GameProfile exists? : " + this.colorfulConsole.green + gameProfileExists);

            String newName = player.getName() + this.colorfulConsole.gold + "\n명성도 : "
                    + this.colorfulConsole.white + this.GetPlayerHonority(player.getUniqueId());
            this.colorfulConsole.console(this.colorfulConsole.debug,"New name of the player : " + this.colorfulConsole.white + newName);

            // Only 1.7+ servers can run this code
            Object profile = entityPlayer.getClass().getMethod("getProfile").invoke(entityPlayer);
            this.colorfulConsole.console(this.colorfulConsole.debug, "(Object)#profile : " + profile.getClass().getName());
            Field ff = profile.getClass().getDeclaredField("name");
            this.colorfulConsole.console(this.colorfulConsole.debug, "(Field)#ff (entityPlayer's declared field 'name' ) : " + ff.getName());
            ff.setAccessible(true);
            ff.set(entityPlayer, newName);

            //Appply changed name
            player.hidePlayer(plugin, player);
            player.showPlayer(plugin, player);

        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {
            /*
             * Merged all the exceptions. Less lines
             */
            this.colorfulConsole.console(this.colorfulConsole.error, "Cause :\n" +  e.getCause().getMessage() + "\nlog :\n" + e.getMessage());
        }catch (Exception e) {
            this.colorfulConsole.console(this.colorfulConsole.error, "Cause :\n" + e.getCause().getMessage() + "\nlog :\n" + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public void ShowHonorityOnAllPlayers(Plugin plugin, Collection<? extends Player> playerCollection) {
        this.colorfulConsole.console(this.colorfulConsole.log,"Applying honority data to players` nickname..");
        for(Player player: playerCollection) {
            this.ShowHonorityOnPlayer(plugin, player);
        }
    }

    /*
     * Code frome : https://bukkit.org/threads/reflection-change-playernametag-with-24-lines-of-code-without-scoreboards.439948/ (last code)
     *
     *  Works from 1.0+.
     *
     * @param name new name of the player
     * @param player player to change the name of
     */
    @SuppressWarnings("unchecked")
    public static void changeName(String name, Player player, Plugin plugin, Collection<? extends Player> playerCollection) {
        try {
            Method getHandle = player.getClass().getMethod("getHandle");
            Object entityPlayer = getHandle.invoke(player);
            /*
             * These methods are no longer needed, as we can just access the
             * profile using handle.getProfile. Also, because we can just use
             * the method, which will not change, we don't have to do any
             * field-name look-ups.
             */
            boolean gameProfileExists = false;
            // Some 1.7 versions had the GameProfile class in a different package
            try {
                Class.forName("net.minecraft.util.com.mojang.authlib.GameProfile");
                gameProfileExists = true;
            } catch (ClassNotFoundException ignored) {

            }
            try {
                Class.forName("com.mojang.authlib.GameProfile");
                gameProfileExists = true;
            } catch (ClassNotFoundException ignored) {

            }
            if (!gameProfileExists) {
                /*
                 * Only 1.6 and lower servers will run this code.
                 *
                 * In these versions, the name wasn't stored in a GameProfile object,
                 * but as a String in the (final) name field of the EntityHuman class.
                 * Final (non-static) fields can actually be modified by using
                 * {@link java.lang.reflect.Field#setAccessible(boolean)}
                 */
                Field nameField = entityPlayer.getClass().getSuperclass().getDeclaredField("name");
                nameField.setAccessible(true);
                nameField.set(entityPlayer, name);
            } else {
                // Only 1.7+ servers will run this code
                Object profile = entityPlayer.getClass().getMethod("getProfile").invoke(entityPlayer);
                Field ff = profile.getClass().getDeclaredField("name");
                ff.setAccessible(true);
                ff.set(profile, name);
            }
            // In older versions, Bukkit.getOnlinePlayers() returned an Array instead of a Collection.
            if (Bukkit.class.getMethod("getOnlinePlayers", new Class<?>[0]).getReturnType() == Collection.class) {
                Collection<? extends Player> players = (Collection<? extends Player>) Bukkit.class.getMethod("getOnlinePlayers").invoke(null);
                for (Player p : players) {
                    p.hidePlayer(plugin, player);
                    p.showPlayer(player);
                }
            } else {
                Player[] players = ((Player[]) Bukkit.class.getMethod("getOnlinePlayers").invoke(null));
                for (Player p : players) {
                    p.hidePlayer(plugin, player);
                    p.showPlayer(plugin, player);
                }
            }
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {
            /*
             * Merged all the exceptions. Less lines
             */
            e.printStackTrace();
        }
    }
}
