package game;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class Instance {
    public static GameObject create(String className, GameObject parent) throws ClassNotFoundException, NoSuchMethodException, SecurityException,
    InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException  {
        Class<?> objectClass = Class.forName("game.objectclasses." + className);
        Class<?>[] parameters = new Class[] { String.class, GameObject.class };
        Constructor<?> constructor = objectClass.getConstructor(parameters);
        Object object = constructor.newInstance(className, parent);
        return (GameObject) object;
    }
    public static GameObject create(String className) throws ClassNotFoundException, NoSuchMethodException, SecurityException,
    InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException  {
        Class<?> objectClass = Class.forName("game.objectclasses." + className);
        Class<?>[] parameters = new Class[] { String.class, GameObject.class };
        Constructor<?> constructor = objectClass.getConstructor(parameters);
        Object object = constructor.newInstance(className, null);
        return (GameObject) object;
    }
}
