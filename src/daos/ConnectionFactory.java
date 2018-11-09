package daos;

import exceptions.OpenDatabaseConnectionException;
import org.postgresql.Driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Bas de Bruyn
 */
class ConnectionFactory {
    private static final String URL = "jdbc:postgresql://80.100.250.208:25002/dubio";
    private static final String USER = "postgres";
    private static final String PASS = "dreamteam_en_bas";

    public static Connection getConnection() {
        try {
            DriverManager.registerDriver(new Driver());
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (SQLException exception) {
            throw new OpenDatabaseConnectionException();
        }
    }
}
