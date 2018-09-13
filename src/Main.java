import database.Database;
import database.mariadb.MariaDB;
import database.firebird.Firebird;
import database.postgresql.PostgreSQL;
import tests.DatabaseTests;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        MariaDB mariaDB = new MariaDB();
        PostgreSQL postgreSQL = new PostgreSQL();
        Firebird firebird = new Firebird();

        ArrayList<Database> databases = new ArrayList<>();
        databases.add(mariaDB);
        databases.add(postgreSQL);
        databases.add(firebird);

        for(Database db : databases) {
            db.connect();

            DatabaseTests dbt = new DatabaseTests(db);
            dbt.runTests();

            db.close();

        }
    }
}
