package daos;

import java.util.List;

public interface GenericDao<T> {

    List<T> getAll();

    T getByPK(int pk);

    void save(T t);

    void update(T t, String[] params);

    void delete(T t);

}

