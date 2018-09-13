package tests;

import util.ResultWriter;
import database.Database;

import java.util.concurrent.TimeUnit;

public class DatabaseTests {

    private final int cycles = 5;

    private Database db;
    private ResultWriter rw;

    public DatabaseTests(Database db) {
        this.db = db;
        rw = new ResultWriter(db.dbType);
    }

    public void runTests() {
        rw.write("[+] Starting Tests for " + db.dbType);

        insertSpeedTest();
        readSpeedTest();
        updateSpeedTest();
        deleteSpeedTest();

        rw.closeWriter();
    }

    private void timeAndReport(long startTime, long endTime, String action) {

        long duration = (endTime - startTime);
        rw.write("[" + action.charAt(0) + "] Milisecond duration for " + Integer.toString(cycles) + " cycles of " + action + ": " + Long.toString(TimeUnit.MILLISECONDS.convert(duration, TimeUnit.NANOSECONDS)) + " ms");
        rw.write("[" + action.charAt(0) + "] Milisecond duration per " + action + ": " + Long.toString(TimeUnit.MILLISECONDS.convert(duration, TimeUnit.NANOSECONDS)/cycles) + " ms");
    }

    private void insertSpeedTest() {
        String sql = "INSERT INTO test VALUES ('bas')";

        long startTime = System.nanoTime();
        for (int i = 0; i < cycles; i++) {
            db.execute(sql);
        }
        long endTime = System.nanoTime();

        timeAndReport(startTime, endTime, "CREATE");
    }

    private void updateSpeedTest() {
        String sql = "UPDATE test SET first = 'updated' WHERE first = 'bas'";

        long startTime = System.nanoTime();
        for (int i = 0; i < cycles; i++) {
            db.execute(sql);
        }
        long endTime = System.nanoTime();

        timeAndReport(startTime, endTime, "UPDATE");
    }

    private void readSpeedTest() {
        String sql = "SELECT * FROM test";

        long startTime = System.nanoTime();
        for (int i = 0; i < cycles; i++) {
            db.execute(sql);
        }
        long endTime = System.nanoTime();

        timeAndReport(startTime, endTime, "READ");
    }

    private void deleteSpeedTest() {
        String sql = "DELETE FROM test WHERE first = 'updated'";

        long startTime = System.nanoTime();
        for (int i = 0; i < cycles; i++) {
            db.execute(sql);
        }
        long endTime = System.nanoTime();

        timeAndReport(startTime, endTime, "DELETE");
    }

}
