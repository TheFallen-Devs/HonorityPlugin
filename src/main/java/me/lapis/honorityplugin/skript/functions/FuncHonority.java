package me.lapis.honorityplugin.skript.functions;

import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.lang.function.FunctionEvent;
import ch.njol.skript.lang.function.JavaFunction;
import ch.njol.skript.lang.function.Parameter;
import ch.njol.skript.registrations.Classes;
import me.lapis.honorityplugin.honority.Honority;

public abstract class FuncHonority extends JavaFunction<Number> {

    Honority honorityManager;

    public FuncHonority(String name, Parameter[] parameters, boolean single, Honority honorityManager) {
        super(name, parameters, Classes.getExactClassInfo(Number.class), single);
        this.honorityManager = honorityManager;
    }

    public abstract Number[] execute(FunctionEvent functionEvent, Object[][] objects);
}
