package ua.epam.springapp.service;

import java.util.List;

public interface GenericService<T, ID> {

    void add(T entity);

    T get(ID id);

    List<T> getAll();

    boolean update(ID id, T entity);

    T remove(ID id);
}
