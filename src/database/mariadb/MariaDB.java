package database.mariadb;

import database.Database;

public class MariaDB extends Database {

    private static final String dbType = "MariaDB";

    private static final String url = "jdbc:mariadb://80.100.250.208:42001/test";
    private static final String user = "root";
    private static final String pass = "dreamteam_en_bas";

    public MariaDB() {
        super(url, user, pass, dbType);
    }
}

