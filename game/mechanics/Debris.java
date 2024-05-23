package game.mechanics;

import java.util.*;

import game.GameObject;
import game.objectclasses.*;

public class Debris {
    public static HashMap<GameObject, Double> setDeletion = new HashMap<GameObject, Double>();
    public static boolean iterating = false;

    public Debris() {

    }

    public static synchronized void addDebris(GameObject part, double time) {
        setDeletion.put(part, time);
    }

    public static synchronized void removeDebris(GameObject part) {
        if (!iterating) {
            setDeletion.remove(part);
        }
    }

    public static synchronized void updateDebris() {
        Iterator it = setDeletion.entrySet().iterator();
        iterating = true;
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            GameObject part = (GameObject) pair.getKey();
            double time = (double) pair.getValue();
            if (time <= 0) {
                part.setParent(null);
                it.remove();
            } else {
                setDeletion.put(part, time - 1 / 60.0);
            }
        }
        iterating = false;
    }
}