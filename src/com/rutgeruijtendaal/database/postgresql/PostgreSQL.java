package com.rutgeruijtendaal.database.postgresql;

import com.rutgeruijtendaal.database.Database;

public class PostgreSQL extends Database {

    private static final String dbType = "PostgreSQL";

    private static final String url = "jdbc:postgresql://80.100.250.208:42000/test";
    private static final String user = "postgres";
    private static final String pass = "dreamteam_en_bas";

    public PostgreSQL() {
        super(url, user, pass, dbType);
    }
}
