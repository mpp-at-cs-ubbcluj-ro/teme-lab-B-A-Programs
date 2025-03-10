package MPP.Repository;

import MPP.Domain.Entity;

import java.util.Collection;

public interface Repository<T extends Entity<ID>, ID> {
    T add(T var1);

    void delete(ID var1);

    void update(T var1, ID var2);

    T getById(ID var1);

    Collection<T> getAll();
}
