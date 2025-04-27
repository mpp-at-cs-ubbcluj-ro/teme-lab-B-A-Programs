package repository;

import domain.Identifiable;

import java.io.Serializable;
import java.util.List;

public interface Repository<T extends Identifiable<ID>, ID extends Serializable> {
    T add(T var1);

    void delete(ID var1);

    void update(T var1, ID var2);

    T getById(ID var1);

    List<T> getAll();
}
