package daos;

import exceptions.PrepareStatementException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PreparedStatementFactory {

    public static PreparedStatement createPreparedStatement(String query){
        Connection connection = ConnectionFactory.getConnection();
        try {
            return connection.prepareStatement(query);
        } catch (SQLException exception){
            throw new PrepareStatementException();
        }
    }

    public static PreparedStatement createPreparedStatementWithReturn(String query){
        Connection connection = ConnectionFactory.getConnection();
        try {
            return connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
        } catch (SQLException exception){
            throw new PrepareStatementException();
        }
    }

    public static PreparedStatement createSelectAllStatement(String table){
        String query = "SELECT * FROM " + table + ";";
        return createPreparedStatement(query);
    }

    public static PreparedStatement createSelectByIdStatement(String table, int id){
        String query = "SELECT * FROM " + table + " WHERE id = " + id + ";";
        return createPreparedStatement(query);
    }

    public static PreparedStatement createSelectByForeignKeyStatement(String table, String column, int id){
        String query = "SELECT * FROM " + table + " WHERE " + column + " = " + id + ";";
        return createPreparedStatement(query);
    }

    public static PreparedStatement createSelectByColumnStatement(String table, String column){
        String query = "SELECT * FROM " + table + " WHERE " + column + " = ?;";
        return createPreparedStatement(query);
    }

    public static PreparedStatement createInsertStatement(String table, String[] columnNames){
        StringBuilder query = new StringBuilder("INSERT INTO " + table + "(" + columnNames[0]);
        for (int i = 1; i < columnNames.length; i++) {
            query.append(",").append(columnNames[i]);
        }
        query.append(")" + " VALUES(?");
        for (int i = 1; i < columnNames.length; i++) {
            query.append(",?");
        }
        query.append(");");

        return createPreparedStatementWithReturn(query.toString());
    }

    public static PreparedStatement getUpdateStatement(String[] columnNames, String table, int id){
        StringBuilder query = new StringBuilder("UPDATE " + table);
        query.append(" SET ").append(columnNames[0]).append(" = ?");
        for (int i = 1; i < columnNames.length; i++) {
            query.append(" , ").append(columnNames[i]).append(" = ?");
        }
        query.append(" WHERE id = ").append(id).append(";");

        return createPreparedStatement(query.toString());
    }

    public static PreparedStatement getDeleteStatement(String table, int id){
        String query = "DELETE FROM " + table + " WHERE id = " + id + ";";
        return createPreparedStatement(query);
    }

}
