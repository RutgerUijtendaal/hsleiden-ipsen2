import java.sql.*;

public class Main {
    public static void main(String[] args) {

        final String url = "jdbc:postgresql://80.100.250.208:42000/test";
        final String user = "postgres";
        final String pass = "dreamteam_en_bas";

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return;
        }

        Connection conn = null;

        try {
            conn = DriverManager.getConnection(url, user, pass);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (conn != null) {
            System.out.println("connected");
        }

        try {

            Statement stmt = conn.createStatement();
            String sql;

            sql = "INSERT INTO test VALUES ('bas')";

            for (int i = 0; i < 1000; i++) {
                stmt.execute(sql);
            }

            sql = "TRUNCATE TABLE test";
            stmt.execute(sql);

            /*
               while (rs.next()) {
               String nr = rs.getString("patient_nr"); 
               System.out.println(nr);
               }
               */

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

