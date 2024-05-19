package game;

import java.util.*;

public enum Element {
    FIRE, WATER, WIND, LIGHTNING;

    private static final Map<Element, List<String>> attacks = new HashMap<>();

    static {
        attacks.put(FIRE, Arrays.asList("Fireball", "Flame Thrower", "Inferno"));
        attacks.put(WATER, Arrays.asList("Water Blast", "Tsunami", "Whirlpool"));
        attacks.put(WIND, Arrays.asList("Tornado", "Gale Force", "Hurricane"));
        attacks.put(LIGHTNING, Arrays.asList("Thunderbolt", "Electro Ball", "Lightning Strike"));
    }

    public List<String> getAttacks() {
        return attacks.get(this);
    }
}