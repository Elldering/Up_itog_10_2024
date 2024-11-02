package org.example.up_itog_10_2024.DAO;

import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
@Component
public class FieldHelper {

    public List<Object> getFieldValues(Object obj) {
        List<Object> values = new ArrayList<>();
        for (String fieldName : getFieldNames(obj.getClass())) {
            values.add(getFieldValue(obj, fieldName));
        }
        return values;
    }

    public Object getFieldValue(Object obj, String fieldName) {
        try {
            Field field = obj.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(obj);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }
    public void get_last_id(Object obj, String fieldName, Object value) {

    }


    public static List<String> getFieldNames(Class<?> clazz) {
        List<String> fieldNames = new ArrayList<>();
        for (Field field : clazz.getDeclaredFields()) {
            fieldNames.add(field.getName());
        }
        return fieldNames;
    }

}
