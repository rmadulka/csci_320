package services;

import application.DatabaseConnection;
import application.LoginSession;
import com.jcraft.jsch.Session;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Properties;


public class RequestService {

    public RequestService(){


    }
    public static void request(String username, String barcode, String date_required, String duration) throws SQLException {

        Connection conn = null;
        Session session = null;

        try {
//            session = DatabaseConnection.createSession();
//            int assigned_port = session.setPortForwardingL(DatabaseConnection.LPORT, "localhost", DatabaseConnection.RPORT);
//            conn = DatabaseConnection.createConnection(assigned_port);

            String url = "jdbc:postgresql://localhost:"+ "5432" + "/" + "postgres";
            System.out.println("database Url: " + url);
            Properties props = new Properties();
            props.put("user", "postgres");
            props.put("password", "password");
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(url, props);
            System.out.println("Database connection established");
            System.out.println("Port Forwarded");


            //check to see if item being requested is shareable
            String checkquery = "SELECT * FROM tool_app.\"tool\" WHERE tool_app.\"tool\".barcode = (?) AND tool_app.\"tool\".shareable = (?);";

            PreparedStatement checkstatement = conn.prepareStatement(checkquery);

            checkstatement.setString(1, barcode);

            checkstatement.setBoolean(2, true);

            ResultSet checkresult = checkstatement.executeQuery();

            if (!checkresult.next()) {

                System.err.println("Item being requested is not shareable or does not exist");

            } else {

                String query = "INSERT INTO tool_app.request(username, tool_barcode, status, date_required, duration) VALUES (?, ?, ?, ?, ?);";

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                java.sql.Date sql_date_required = new java.sql.Date(sdf.parse(date_required).getTime());

                PreparedStatement statement = conn.prepareStatement(query);

                statement.setString(1, username);
                statement.setString(2, barcode);
                statement.setString(3, "Pending");
                statement.setDate(4, sql_date_required);
                statement.setInt(5, Integer.parseInt(duration));

                boolean result = statement.execute();

                System.out.println("Request sent");

            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null && !conn.isClosed()) {
                System.out.println("Closing Database Connection");
                conn.close();
            }
            if (session != null && session.isConnected()) {
                System.out.println("Closing SSH Connection");
                session.disconnect();
            }
        }


    }
}
