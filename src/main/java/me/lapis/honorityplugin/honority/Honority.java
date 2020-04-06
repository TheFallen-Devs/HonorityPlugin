package me.lapis.honorityplugin.honority;

import me.lapis.honorityplugin.ColorfulConsole;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Async;

import java.util.Dictionary;
import java.util.UUID;

public class Honority {
    Dictionary<UUID, Short> honorityDict;
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
        // 잠시 문서보고 공부하러감~

        return true;
    }

    public short getPlayerHonority(UUID uuid){
        return honorityDict.get(uuid);
    }
}
