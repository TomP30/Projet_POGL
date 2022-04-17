package model;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum Type {
    Stone(0),
    Wind(1),
    Fire(2),
    Wave(3);

    public final int value;
    private static final Map<Integer, Type> ENUM_MAP;

    Type(int value) {
        this.value = value;
    }

    static {
        Map<Integer, Type> map = new ConcurrentHashMap<Integer, Type>();
        for (Type instance : Type.values())
            map.put(instance.value, instance);
        ENUM_MAP = Collections.unmodifiableMap(map);
    }

    public static Type from(int value) {
        return ENUM_MAP.get(value);
    }
}
