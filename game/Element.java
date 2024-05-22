package game;

import java.util.*;

public enum Element {
    FIRE, WATER;

    private static final Map<Element, List<String>> attacks = new HashMap<>();

    static {
        attacks.put(FIRE, Arrays.asList("fireball", "inferno"));
        attacks.put(WATER, Arrays.asList("waterball", "tsunami"));
    }

    public List<String> getAttacks() {
        return attacks.get(this);
    }
}