package me.lapis.honorityplugin.skript.functions;

import ch.njol.skript.lang.function.FunctionEvent;
import ch.njol.skript.lang.function.JavaFunction;
import ch.njol.skript.lang.function.Parameter;
import ch.njol.skript.registrations.Classes;
import me.lapis.honorityplugin.honority.Honority;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class FuncGetPlayerHonority extends JavaFunction<Number> {
    Honority funcHonorityManager;

    public FuncGetPlayerHonority(Honority honorityManager) {
        super("getPlayerHonority", new Parameter[]{
                new Parameter<>("player_uuid", Classes.getExactClassInfo(UUID.class), true, null)
        }, Classes.getExactClassInfo(Number.class), true);

        this.funcHonorityManager = honorityManager;
        Bukkit.getLogger().info("[HonorityPlugin][Skript][FuncGetPlayerHonority] loading function : getPlayerHonority(playerUUID: uuid)");
    }

    @NotNull
    @Override
    public Number[] execute(FunctionEvent functionEvent, Object[][] objects) {
        Bukkit.getLogger().info("[HonorityPlugin][Skript][getPlayerHonority] objects : " + objects.toString());
        UUID player_uuid = (UUID) objects[0][0];
        return new Number[]{(Number) this.funcHonorityManager.GetPlayerHonority(player_uuid)};
    }
}