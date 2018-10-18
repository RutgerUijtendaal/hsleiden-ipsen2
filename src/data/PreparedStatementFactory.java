package data;

import data.exceptions.FailedToPrepareStatement;

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
            throw new FailedToPrepareStatement();
        }
    }

    public static PreparedStatement getPreparedStatementWithReturn(String query){
        Connection connection = ConnectionFactory.getConnection();
        try {
            return connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
        } catch (SQLException exception){
            exception.printStackTrace();
            throw new FailedToPrepareStatement();
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

    public static PreparedStatement getInsertStatement(String table, String[] columnNames){
        String query = "INSERT INTO " + table + "(" + columnNames[0];
        for (int i = 1; i < columnNames.length; i++) {
            query += "," + columnNames[i];
        }
        query += ")" +
                " VALUES(?";
        for (int i = 1; i < columnNames.length; i++) {
            query += ",?";
        }
        query += ");";

        return getPreparedStatementWithReturn(query);
    }

    public static PreparedStatement getUpdateStatement(String[] columnNames, String table, int id){
        String query = "UPDATE " + table;
        query += " SET " + columnNames[0] + " = ?";
        for (int i = 1; i < columnNames.length; i++) {
            query += " , " + columnNames[i] + " = ?";
        }
        query += " WHERE id = " + id + ";";

        return getPreparedStatement(query);
    }

    public static PreparedStatement getDeleteStatement(String table, int id){
        String query = "DELETE FROM " + table + " WHERE id = " + id + ";";
        return getPreparedStatement(query);
    }

}
