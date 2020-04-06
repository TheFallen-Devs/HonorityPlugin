package me.lapis.honorityplugin.honority;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.Async;

import me.lapis.honorityplugin.ColorfulConsole;

public class Honority {
    HashMap<UUID, Short> honorityDict = new HashMap<UUID, Short>();
    short honority = 0;
    ColorfulConsole colorfulConsole;

    public Honority(ColorfulConsole colorfulConsole){
        this.colorfulConsole = colorfulConsole;
    }

    /**
     * Find a honority data using uuid.
     * Load data from data/HonorityPlugin/(uuid value).honor
     *
     * @return true if honority data is successfully loaded. if not, return false.
     */
    public boolean LoadPlayerHonority(UUID uuid){


        //Add uuid & honority value pair into #honorityDict(Dictionary<UUID, Short>)
        this.honorityDict.put(uuid, (short) 0);

        return true;
    }

    /**
     * Save a player(whose uuid matches with given uuid)'s honority honority data.
     * Save data at data/HonorityPlugin/(uuid value).honor
     *
     * @return true if honority data is successfully saved. if not, return false.
     */
    public boolean SavePlayerHonority(UUID uuid){
        this.colorfulConsole.console(this.colorfulConsole.debug, "Saving data of player" + uuid.toString());
        try{
            FileOutputStream fileOutputStream = new FileOutputStream("data/HonorityPlugin/"+uuid+"txt", false);
        }catch (FileNotFoundException e){
            this.colorfulConsole.console(this.colorfulConsole.error, "File not found!");
            this.colorfulConsole.console(this.colorfulConsole.error, e.getMessage());
        }
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

        return true;
    }

    public short getPlayerHonority(UUID uuid){
        return honorityDict.get(uuid);
    }
}
