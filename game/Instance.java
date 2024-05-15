package game;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class Instance {
    Object object;

    public Instance(String className, GameObject parent)
            throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException,
            IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Class<?> objectClass = Class.forName(className);
        Class<?>[] parameters = null;
        Constructor<?> constructor = objectClass.getConstructor(parameters);
        Object object = constructor.newInstance();
        object.getClass().getMethod("setParent", GameObject.class).invoke(object, parent);
        this.object = object;
    }

    public Instance(String ClassName) throws ClassNotFoundException, NoSuchMethodException, SecurityException,
            InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Class<?> objectClass = Class.forName(ClassName);
        Class<?>[] parameters = null;
        Constructor<?> constructor = objectClass.getConstructor(parameters);
        Object object = constructor.newInstance();
        this.object = object;
    }
}
