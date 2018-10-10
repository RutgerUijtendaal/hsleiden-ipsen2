package daos;

import java.util.List;

public interface GenericDao<T> {

    List<T> getAll();

    void save(T t);

    void update(T t, String[] params);

    void delete(T t);

}

