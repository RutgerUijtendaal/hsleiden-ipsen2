package daos;

import java.sql.SQLException;
import java.util.List;

public interface GenericDao<T> {

    List<T> getAll() throws SQLException;

    T getByPK(int pk);

    void save(T t);

    void update(T t, String[] params);

    void delete(T t);

}

