package data.daos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface GenericDao<T> {

    List<T> getAll() throws SQLException;

    T getById(int id);

    int save(T object);

    boolean update(T object);

    boolean delete(T object);

    boolean deleteById(int id);

    T createFromResultSet(ResultSet resultSet);

    void fillPreparedStatement(PreparedStatement preparedStatement, T object);

    String getTableName();

    String[] getColumnNames();

}

