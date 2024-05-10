package am.deep;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.*;

@SuppressWarnings("unchecked")
public class DeepCopyUtils {

    public static <T> T deepCopy(T original) {
        if (original == null) {
            return null;
        }
        Map<Object, Object> seen = new IdentityHashMap<>();
        return copyRecursive(original, seen);
    }

    @SuppressWarnings("unchecked")

    private static <T> T copyRecursive(T original, Map<Object,Object> seen) {
        if (original == null) {
            return null;
        }

        Class<?> clazz = original.getClass();
        T cachedCopy = (T) seen.get(original);
        if (cachedCopy != null) {
            return cachedCopy;
        }

        T copy = null;
        try {
            if (clazz.isArray()) {
                int length = Array.getLength(original);
                copy = (T) Array.newInstance(clazz.getComponentType(), length);
                seen.put(original, copy);
                for (int i = 0; i < length; i++) {
                        Array.set(copy, i, copyRecursive(Array.get(original, i), seen));
                    }
                } else if (Collection.class.isAssignableFrom(clazz)) {
                    return (T) copyCollection((Collection<?>) original, seen);
                } else {
                    copy = (T) clazz.getDeclaredConstructor().newInstance();
                    seen.put(original, copy);
                    for (Field field : clazz.getDeclaredFields()) {
                        if (!Modifier.isStatic(field.getModifiers()) && !field.isSynthetic()) {
                            field.setAccessible(true);
                            Object fieldValue = field.get(original);
                            Object copiedValue = (field.getType().isPrimitive() || fieldValue instanceof String || fieldValue.getClass().isEnum()) ?
                                    fieldValue : copyRecursive(fieldValue, seen);
                            field.set(copy, copiedValue);
                        }
                    }
                }
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                throw new CopyException(e);
            }

            return copy;
        }

    private static Collection<Object> copyCollection(Collection<?> original, Map<Object, Object> seen) {
        Collection<Object> copy;
        if (original instanceof List) {
            copy = new ArrayList<>(original.size());
        } else if (original instanceof Set) {
            copy = new HashSet<>(Math.max((int) (original.size() / 0.75f) + 1, 16));
        } else {
            throw new IllegalArgumentException("Unsupported collection type: " + original.getClass());
        }
        seen.put(original, copy);
        original.forEach(item -> copy.add(copyRecursive(item, seen)));
        return copy;
    }
}
