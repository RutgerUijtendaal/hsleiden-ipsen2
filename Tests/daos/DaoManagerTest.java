package daos;

import org.junit.jupiter.api.Test;

import java.sql.*;

class DaoManagerTest {

    private final String [] columNames = {"col1","col2","col3"};
//    private final String [] columNames = {"col1"};
    private final String tableName = "testTable";
    private final int id = 1;

    @Test
    void getPreparedStatement() {
    }

    @Test
    void closeTransaction() {
    }

    @Test
    void getSelectAllStatement() {
        PreparedStatement statement = DaoManager.getSelectAllStatement(tableName);
        System.out.println(statement);
    }

    @Test
    void getSelectByIdStatement() {
        PreparedStatement statement = DaoManager.getSelectByIdStatement(tableName, id);
        System.out.println(statement);
    }

    @Test
    void getInsertStatement() throws SQLException {
        PreparedStatement statement = DaoManager.getInsertStatement(tableName, columNames);
        System.out.println(statement);
    }

    @Test
    void getUpdateStatement() throws SQLException {
        PreparedStatement statement = DaoManager.getUpdateStatement(columNames, tableName, id);
        System.out.println(statement);
    }

    @Test
    void getDeleteStatement() {
        PreparedStatement statement = DaoManager.getDeleteStatement(tableName, id);
        System.out.println(statement);
    }

}