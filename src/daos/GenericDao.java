package daos;

import models.DatabaseObject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface GenericDao<T> {

    List<DatabaseObject<T>> getAll() throws SQLException;

    DatabaseObject<T> getById(int id);

    int save(DatabaseObject<T> t);

    boolean update(DatabaseObject<T> t);

    boolean delete(DatabaseObject<T> t);

    DatabaseObject createFromResultSet(ResultSet resultSet) throws SQLException;

    void fillPreparedStatement(PreparedStatement preparedStatement, DatabaseObject<T> databaseObject) throws SQLException;

    String getTableName();

    String[] getColumnames();
}

