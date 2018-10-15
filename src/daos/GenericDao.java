package daos;

import java.sql.SQLException;
import java.util.List;

public interface GenericDao<T> {

    List<T> getAll() throws SQLException;

    T getById(int id);

    void save(T t);

    void update(T t);

    void delete(T t);

}

