package com.ron.utils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Ron 2019/08/01
 */
public class ClassUtil {
    /**
     * 获取target的第一个泛型参数Class
     *
     * @param target
     * @return
     */
    public static Class getGenericType(Class target) {
        if (target == null) {
            return null;
        }
        Type genType = target.getGenericSuperclass();
        if (!(genType instanceof ParameterizedType)) {
            return getGenericType(target.getSuperclass());
        }
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        return (Class) params[0];
    }


    /**
     * 构建目标对象泛型的实例
     *
     * @param target
     * @return
     */
    public static Object newInstanceOfGenericType(Object target) throws IllegalAccessException, InstantiationException {
        if (target == null) {
            return null;
        }
        Class genericType = getGenericType(target.getClass());
        if (genericType == null) {
            return null;
        }
        return genericType.newInstance();
    }

    public static void main(String[] args) throws InstantiationException, IllegalAccessException {
//        System.out.println(newInstanceOfGenericType(new SalesOrderJobService()));
//        System.out.println(newInstanceOfGenericType(new Object()));
//        System.out.println(JSON.toJSONString(getClassFieldsAndSuperClassFields(SalesOrderJobService.class)));

    }

    public static Field[] getClassFieldsAndSuperClassFields(Class clazz) {
        return getAllFields(clazz).toArray(new Field[0]);
    }

    public static List<Field> getAllFields(Class clazz) {
        List<Field> fields = new ArrayList<>();
        if (clazz == null) {
            return fields;
        }
        fields.addAll(getAllFields(clazz.getSuperclass()));
        fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
        return fields;
    }
}
