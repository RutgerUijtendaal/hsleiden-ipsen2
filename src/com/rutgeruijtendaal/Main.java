package com.rutgeruijtendaal;

import com.rutgeruijtendaal.database.Database;
import com.rutgeruijtendaal.database.mariadb.MariaDB;
import com.rutgeruijtendaal.database.postgresql.PostgreSQL;
import com.rutgeruijtendaal.tests.DatabaseTests;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        MariaDB mariaDB = new MariaDB();
        PostgreSQL postgreSQL = new PostgreSQL();

        ArrayList<Database> databases = new ArrayList<>();
        databases.add(mariaDB);
        databases.add(postgreSQL);

        for(Database db : databases) {
            db.connect();

            DatabaseTests dbt = new DatabaseTests(db);
            dbt.runTests();

            db.close();

        }
    }

}
