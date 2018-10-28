package daos;

import exceptions.FailedToPrepareStatementException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PreparedStatementFactory {

    public static PreparedStatement getPreparedStatement(String query){
        Connection connection = ConnectionFactory.getConnection();
        try {
            return connection.prepareStatement(query);
        } catch (SQLException exception){
            exception.printStackTrace();
            throw new FailedToPrepareStatementException();
        }
    }

    public static PreparedStatement getPreparedStatementWithReturn(String query){
        Connection connection = ConnectionFactory.getConnection();
        try {
            return connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
        } catch (SQLException exception){
            exception.printStackTrace();
            throw new FailedToPrepareStatementException();
        }
    }

    public static PreparedStatement getSelectAllStatement(String table){
        String query = "SELECT * FROM " + table + ";";
        return getPreparedStatement(query);
    }

    public static PreparedStatement getSelectByIdStatement(String table, int id){
        String query = "SELECT * FROM " + table + " WHERE id = " + id + ";";
        return getPreparedStatement(query);
    }

    public static PreparedStatement getSelectByForeignKeyStatement(String table, String column, int id){
        String query = "SELECT * FROM " + table + " WHERE " + column + " = " + id + ";";
        return getPreparedStatement(query);
    }

    public static PreparedStatement getSelectByColumnStatement(String table, String column){
        String query = "SELECT * FROM " + table + " WHERE " + column + " = ?;";
        return getPreparedStatement(query);
    }

    public static PreparedStatement getInsertStatement(String table, String[] columnNames){
        StringBuilder query = new StringBuilder("INSERT INTO " + table + "(" + columnNames[0]);
        for (int i = 1; i < columnNames.length; i++) {
            query.append(",").append(columnNames[i]);
        }
        query.append(")" + " VALUES(?");
        for (int i = 1; i < columnNames.length; i++) {
            query.append(",?");
        }
        query.append(");");

        return getPreparedStatementWithReturn(query.toString());
    }

    public static PreparedStatement getUpdateStatement(String[] columnNames, String table, int id){
        StringBuilder query = new StringBuilder("UPDATE " + table);
        query.append(" SET ").append(columnNames[0]).append(" = ?");
        for (int i = 1; i < columnNames.length; i++) {
            query.append(" , ").append(columnNames[i]).append(" = ?");
        }
        query.append(" WHERE id = ").append(id).append(";");

        return getPreparedStatement(query.toString());
    }

    public static PreparedStatement getDeleteStatement(String table, int id){
        String query = "DELETE FROM " + table + " WHERE id = " + id + ";";
        return getPreparedStatement(query);
    }

}
