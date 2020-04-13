package me.lapis.honorityplugin.skript.expressions;

import ch.njol.skript.doc.*;
import ch.njol.skript.expressions.base.PropertyExpression;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

//Skript
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

//For @Nullable Annotaion
import org.jetbrains.annotations.Nullable;

//For Interact with Honority system
import me.lapis.honorityplugin.honority.Honority;

import java.util.List;

@Name("Get Honority Value")
@Description("Number of ")
@Examples({"set arrows stuck of player to 10",
        "add 10 to arrows stuck of player",
        "remove 10 from player's stuck arrows",
        "delete arrows stuck of player"})
@RequiredPlugins("Paper 1.15.2+")
@Since("1.0")
public class ExprGetHonority extends SimplePropertyExpression<Player, Integer> {

    static Honority honorityManager;
    // you can call this expression like "honority of {_player}" or "{_player}'s honority"
    static {
        PropertyExpression.register(ExprGetHonority.class, Integer.class, "honority", "players");
        try{
            ExprGetHonority.honorityManager = (Honority) Bukkit.getPluginManager().getPlugin("HonorityPlugin").getClass().getMethod("getHonorityManager", null).invoke(null);
        }catch (Exception e){
            Bukkit.getLogger().severe("Can't get Honority Manager from plugin! The Skript-API expressions will not work properly!");
        }
    }

    private Expression<?> expr;

    /**
     * Method which is called when expression is called in Skript
     * @param exprs : Array of expression args.
     * @param matchedPattern : a int type variable that returns between 0-X X being the amount of strings your syntax supports.
     * @param isDelayed : a Kleenean(three-status boolean from Skript) type variable to tell if it's as the event happens or a tick after the event has happened.
     *                  This is for telling if the user has waited a second or not. Some times methods need to have no delay in them.
     * @param parser : parser is a system to help with advanced syntax.
     * */
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parser) {

        return false;
    }

    @Override
    protected String getPropertyName() {
        return "honority";
    }

    /**
     * What type does this expression returns?
     * (This must be Generic Type of SimpleExpression which this class extends
     * */
    @Override
    public Class<? extends Integer> getReturnType() {
        return Integer.class;
    }

    @Override
    public Integer convert(final Player player) {
        return (int) honorityManager.GetPlayerHonority(player.getUniqueId());
    }

    /**
     *  toString() is a method to help debug errors that may arise from users reporting issues or anything in between.
     *  So we need something that will be helpful for us to understand what is going on.
     * */
    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "This expression is from Honority Plugin, and its purpose is to get the honority value(number) from honoriy plugin. Sample expression is like this :\n" +
                "[the] honority of %player%\n" +
                "%player%[`s] honority\n" +
                "returns honority value of player, whose type is Number";
    }
}
