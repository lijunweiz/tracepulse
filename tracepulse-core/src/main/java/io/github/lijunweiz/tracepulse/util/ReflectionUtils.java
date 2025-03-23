package io.github.lijunweiz.tracepulse.util;

import java.lang.reflect.Field;
import java.util.Objects;

/**
 * @author lijunwei
 */
public class ReflectionUtils {

    public static Object getDeclaredField(String fieldName, Object target) {
        if (Objects.isNull(target)) {
            return null;
        }
        try {
            Field field = target.getClass().getDeclaredField(fieldName);
            if (!field.canAccess(target)) {
                field.setAccessible(true);
            }
            return field.get(target);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            // ignore
        }
        return null;
    }

}
