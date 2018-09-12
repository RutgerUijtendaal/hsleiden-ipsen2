package database.firebird;

import database.Database;

public class Firebird extends Database {

    private static final String dbType = "Firebird";

    private static final String url = "jdbc:firebirdsql://80.100.250.208:42002/test?encoding=UTF8";
    private static final String user = "SYSDBA";
    private static final String pass = "dreamteam_en_bas";

    public Firebird() {
        super(url, user, pass, dbType);
    }
}

