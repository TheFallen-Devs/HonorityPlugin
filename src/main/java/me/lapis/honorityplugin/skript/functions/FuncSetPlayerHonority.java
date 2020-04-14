package me.lapis.honorityplugin.skript.functions;

import ch.njol.skript.lang.function.FunctionEvent;
import ch.njol.skript.lang.function.JavaFunction;
import ch.njol.skript.lang.function.Parameter;
import ch.njol.skript.registrations.Classes;
import me.lapis.honorityplugin.honority.Honority;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class FuncSetPlayerHonority extends JavaFunction<Boolean> {
    Honority funcHonorityManager;

    public FuncSetPlayerHonority(Honority honorityManager) {
        super("setPlayerHonority", new Parameter[]{
                new Parameter<>("player_uuid", Classes.getExactClassInfo(UUID.class), true, null),
                new Parameter<Number>("new_value", Classes.getExactClassInfo(Number.class), true, null)
        }, Classes.getExactClassInfo(Boolean.class), true);

        this.funcHonorityManager = honorityManager;
        Bukkit.getLogger().info("[HonorityPlugin][Skript][FuncSetPlayerHonority] loading function : setPlayerHonority(playerUUID: uuid, newValue: number)");
    }

    @NotNull
    @Override
    public Boolean[] execute(FunctionEvent functionEvent, Object[][] objects) {
        Bukkit.getLogger().info("[HonorityPlugin][Skript][setPlayerHonority] objects : " + objects.toString());
        UUID player_uuid = (UUID) objects[0][0];

        short old_value = this.funcHonorityManager.GetPlayerHonority(player_uuid);

        short new_value = (short) objects[1][0];
        return new Boolean[]{this.funcHonorityManager.SetPlayerHonority(player_uuid, new_value) != old_value};
    }
}