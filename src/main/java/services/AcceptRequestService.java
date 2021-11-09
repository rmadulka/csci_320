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
    public static void acceptRequest(String username,String requestID, String date_needed_returned) throws SQLException {
        Connection conn = null;
        Session session = null;

        try {
            session = DatabaseConnection.createSession();
            int assigned_port = session.setPortForwardingL(DatabaseConnection.LPORT, "localhost", DatabaseConnection.RPORT);
            conn = DatabaseConnection.createConnection(assigned_port);

            String query = "select * from tool_app.request\n" +
                    "join tool_app.tool on request.tool_barcode = tool.barcode\n" +
                    "where owner = ? AND status = 'Pending' AND tool.shareable = TRUE;";

            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, username);

            ResultSet result = statement.executeQuery();

            if(!result.next()){
                System.out.println("User has no requests or tool is not shareable");
            }
            else{
                String query2 = "update request\n "  + "set status = ?, date_responded = current_date,  date_needed_return = ?" + " where request_id = ? ;";

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                java.sql.Date sql_date_needed_returned = new java.sql.Date(sdf.parse(date_needed_returned).getTime());

                PreparedStatement statement2 = conn.prepareStatement(query2);

                statement2.setString(1, "Accepted");

                statement2.setDate(2, sql_date_needed_returned);

                statement2.setInt(3, Integer.parseInt(requestID));

                boolean result2 = statement2.execute();

                String query3 = "update tool set shareable = false from request where request.tool_barcode = tool.barcode AND request.request_id = (?);";
                PreparedStatement statement3 = conn.prepareStatement(query3);
                statement3.setInt(1, Integer.parseInt(requestID));
                boolean result3 = statement3.execute();

                System.out.println("Request Accepted");

            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
            if (session != null && session.isConnected()) {
                session.disconnect();
            }
        }
    }

}
