package org.example.up_itog_10_2024.DAO;

import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.lang.reflect.Field;
import java.util.List;

@Component
public abstract class GenericDAO<T extends Serializable> {

    private Class<T> modelClass;
    // Метод для получения списка полей модели
    public List<String> getFieldNames(Class<T> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        List<String> fieldNames = new ArrayList<>();
        for (Field field : fields) {
            fieldNames.add(field.getName());
        }
        return fieldNames;
    }

    public Class<T> getEntityClass() {
        return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public int get_new_id() {
        return (items.size() + 1);
    }

    public Object getFieldValue(T obj, String fieldName) {
        try {
            Field field = obj.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(obj);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }
    public int getLastId() {
        return items.isEmpty() ? 1 : items.size() + 1;
    }

    protected List<T> items = new ArrayList<>();

    public List<T> index() {
        return items;
    }

    public T show(int id) {
        return items.get(id);
    }

    public void save(T item) {
        items.add(item);
    }

    public void update(int id, T updatedItem) {
        items.set(id, updatedItem);
    }

    public void delete(int id) {
        items.remove(id);
    }
}

