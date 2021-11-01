package services;

import application.DatabaseConnection;
import com.jcraft.jsch.Session;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Properties;

public class DenyRequestService {
    public DenyRequestService(){


    }
    public static void denyRequest(String username, String incominguser, String barcode) throws SQLException {
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

            String query = "select * from tool_app.request\n" +
                    "join tool_app.tool on request.tool_barcode = tool.barcode\n" +
                    "where owner = ?;";

            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, username);

            ResultSet result = statement.executeQuery();

            if(!result.next()){
                System.out.println("User has no requests");
            }
            else{
                String query2 = "update tool_app.request\n "  + "set status = ?, date_responded = current_date" + " where username = ? and tool_barcode = ?;";

                PreparedStatement statement2 = conn.prepareStatement(query2);

                statement2.setString(1, "Denied");

                statement2.setString(2, incominguser);

                statement2.setString(3, barcode);

                boolean result2 = statement2.execute();

                System.out.println("Request Denied");

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
