package tests;

import util.ResultWriter;
import database.Database;

import java.util.concurrent.TimeUnit;

public class DatabaseTests {

    private final int cycles = 10;

    private Database db;
    private ResultWriter rw;

    public DatabaseTests(Database db) {
        this.db = db;
        rw = new ResultWriter(db.dbType);
    }

    public void runTests() {
        rw.write("Starting Tests for " + db.dbType);

        insertSpeedTest();

        rw.closeWriter();
    }

    private void insertSpeedTest() {
        String sql = "INSERT INTO test VALUES ('bas')";

        long startTime = System.nanoTime();
        for (int i = 0; i < cycles; i++) {
            db.execute(sql);
        }
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);

        rw.write("Milisecond duration for " + Integer.toString(cycles) + " cycles of inserts: " + Long.toString(TimeUnit.MILLISECONDS.convert(duration, TimeUnit.NANOSECONDS)) + " ms");
        rw.write("Milisecond duration per insert: " + Long.toString(TimeUnit.MILLISECONDS.convert(duration, TimeUnit.NANOSECONDS)/cycles) + " ms");
    }

}
