package data.daos;

import java.sql.SQLException;
import java.util.List;

public interface DatabaseViewDao<T>{
    List<T> getAll() throws SQLException;

    T getById(int id);
}
