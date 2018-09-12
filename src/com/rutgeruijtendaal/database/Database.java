package com.rutgeruijtendaal.database;

import java.sql.*;

public class Database {

    public String dbType;

    private String url;
    private String user;
    private String pass;
    private Connection conn;

    public Database(String url, String user, String pass, String dbType) {
        this.url = url;
        this.user = user;
        this.pass = pass;
        this.dbType = dbType;
    }


    public void connect() {
        conn = null;
        try {
            conn = DriverManager.getConnection(url, user, pass);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            conn.close();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public void execute(String sql) {
        Statement stmt = null;

        try {
            stmt = conn.createStatement();
            stmt.execute(sql);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch(SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
