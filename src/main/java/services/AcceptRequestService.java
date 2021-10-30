package services;

import application.DatabaseConnection;
import com.jcraft.jsch.Session;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

public class AcceptRequestService {
    public AcceptRequestService(){


    }
    public static void acceptRequest(String username,String incominguser, String barcode, String date_needed_returned) throws SQLException {
        Connection conn = null;
        Session session = null;

        try {
            session = DatabaseConnection.createSession();
            int assigned_port = session.setPortForwardingL(DatabaseConnection.LPORT, "localhost", DatabaseConnection.RPORT);
            conn = DatabaseConnection.createConnection(assigned_port);
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
                String query2 = "update tool_app.request\n "  + "set status = ?, date_responded = current_date,  date_needed_return = ?" + " where username = ? and tool_barcode = ?;";

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                java.sql.Date sql_date_needed_returned = new java.sql.Date(sdf.parse(date_needed_returned).getTime());

                PreparedStatement statement2 = conn.prepareStatement(query2);

                statement2.setString(1, "Accepted");

                statement2.setDate(2, sql_date_needed_returned);

                statement2.setString(3, incominguser);

                statement2.setString(4, barcode);

                boolean result2 = statement2.execute();

                System.out.println("Request Accepted");

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
