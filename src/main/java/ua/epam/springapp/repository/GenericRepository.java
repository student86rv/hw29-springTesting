package ua.epam.springapp.repository;

import java.util.List;

public interface GenericRepository<T, ID> {

    void add(T entity);

    T get(ID id);

    List<T> getAll();

    boolean update(T entity);

    T remove(ID id);
}
