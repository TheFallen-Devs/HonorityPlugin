package me.lapis.honorityplugin.honority;

import java.io.*;
import java.util.*;

import me.lapis.honorityplugin.ColorfulConsole;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

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
     */
    public boolean LoadAllHonorityInCollection(Collection<? extends Player> playerCollection){
        for(Player p: Bukkit.getServer().getOnlinePlayers()){
            this.colorfulConsole.console(this.colorfulConsole.log, "[LoadAllHonority] Found an online player " + p.getDisplayName() + " in server. Register player's honority data.");
            boolean honorityLoadResult = this.LoadPlayerHonority(p.getUniqueId());
            if(!honorityLoadResult){
                this.colorfulConsole.console(this.colorfulConsole.error, "[LoadAllHonority] Player " + p.getDisplayName() + " does not have honority data file! creating new one...");
                boolean honorityCreateResult = this.CreatePlayerHonority(p.getUniqueId());
                if(honorityCreateResult){
                    this.colorfulConsole.console(this.colorfulConsole.error, "[LoadAllHonority] Player " + p.getDisplayName() + "'s honority data successfully created and saved.");
                }
                else{
                    this.colorfulConsole.console(this.colorfulConsole.error, "[LoadAllHonority] Player " + p.getDisplayName() + "'s honority data occured error during ");
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
     * Save all honority datas which Honority object contains when this method called.
     * Save data is located at data/HonorityPlugin/(uuid value).honor
     *
     * File contains content below:
     * uuid : (uuid)
     * honority_value : (value)
     *
     * @return true if honority data is successfully saved. if not, return false.
     */
    @Deprecated
    public boolean SaveAllLoadedHonority(){
        this.colorfulConsole.console(this.colorfulConsole.debug, "[SaveAllHonority] Saving all honority datas...");
        ArrayList<Boolean> resultArrayList = new ArrayList<>();
        for(UUID uuid : this.honorityDict.keySet()){
            boolean result = this.SavePlayerHonority(uuid);
            resultArrayList.add(result);
            if(!result){
                this.colorfulConsole.console(this.colorfulConsole.debug, "[SaveAllHonority] Found an error during saving honority data of player " + uuid.toString());
            }
        }
        if(resultArrayList.contains(false)){
            this.colorfulConsole.console(this.colorfulConsole.debug, "[SaveAllHonority] There was an error during honority data save.");
            return false;
        }else{
            this.colorfulConsole.console(this.colorfulConsole.debug, "[SaveAllHonority] Successfully saved all honority data.");
            return true;
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
            return oldValue;
        } else {
          throw new ArithmeticException("New value of player's honority value is out of range!");
        }
    }
}
