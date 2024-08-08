package ralf2oo2.extrapistons.property;

import net.modificationstation.stationapi.api.util.StringIdentifiable;

public enum PistonType implements StringIdentifiable {
    DEFAULT("normal"),
    STICKY("sticky");

    private final String name;

    private PistonType(String name){
        this.name = name;
    }

    @Override
    public String asString() {
        return this.name;
    }
}
