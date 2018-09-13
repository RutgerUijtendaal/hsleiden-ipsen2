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
        rw.write("[+] Starting Tests for " + db.dbType + ":");

        long totalDuration;
        totalDuration = insertSpeedTest();
        totalDuration += readSpeedTest();
        totalDuration +=updateSpeedTest();
        totalDuration += deleteSpeedTest();
        rw.write("[+] Total Duration: " + TimeUnit.MILLISECONDS.convert(totalDuration, TimeUnit.NANOSECONDS) + "ms");
        rw.closeWriter();
    }

    private long timeAndReport(long startTime, long endTime, String action) {

        long duration = (endTime - startTime);
        rw.write("  [" + action.charAt(0) + "] Milisecond duration for " + Integer.toString(cycles) + " cycles of " + action + ": " + Long.toString(TimeUnit.MILLISECONDS.convert(duration, TimeUnit.NANOSECONDS)) + " ms");
        rw.write("  [" + action.charAt(0) + "] Milisecond duration per " + action + ": " + Long.toString(TimeUnit.MILLISECONDS.convert(duration, TimeUnit.NANOSECONDS)/cycles) + " ms");
        return duration;
    }

    private long insertSpeedTest() {
        String sql = "INSERT INTO test VALUES ('bas')";

        long startTime = System.nanoTime();
        for (int i = 0; i < cycles; i++) {
            db.execute(sql);
        }
        long endTime = System.nanoTime();

        return timeAndReport(startTime, endTime, "CREATE");
    }

    private long updateSpeedTest() {
        String sql = "UPDATE test SET first = 'updated' WHERE first = 'bas'";

        long startTime = System.nanoTime();
        for (int i = 0; i < cycles; i++) {
            db.execute(sql);
        }
        long endTime = System.nanoTime();

        return timeAndReport(startTime, endTime, "UPDATE");
    }

    private long readSpeedTest() {
        String sql = "SELECT * FROM test";

        long startTime = System.nanoTime();
        for (int i = 0; i < cycles; i++) {
            db.execute(sql);
        }
        long endTime = System.nanoTime();

        return timeAndReport(startTime, endTime, "READ");
    }

    private long deleteSpeedTest() {
        String sql = "DELETE FROM test WHERE first = 'updated'";

        long startTime = System.nanoTime();
        for (int i = 0; i < cycles; i++) {
            db.execute(sql);
        }
        long endTime = System.nanoTime();

        return timeAndReport(startTime, endTime, "DELETE");
    }

}
