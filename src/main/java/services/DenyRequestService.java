package services;

import application.DatabaseConnection;
import com.jcraft.jsch.Session;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

public class DenyRequestService {
    public DenyRequestService(){


    }
    public static void denyRequest(String username, String requestID) throws SQLException {
        Connection conn = null;
        Session session = null;

        try {
            session = DatabaseConnection.createSession();
            int assigned_port = session.setPortForwardingL(DatabaseConnection.LPORT, "localhost", DatabaseConnection.RPORT);
            conn = DatabaseConnection.createConnection(assigned_port);

            String query = "select * from tool_app.request\n" +
                    "join tool_app.tool on request.tool_barcode = tool.barcode\n" +
                    "where owner = ? AND status = 'Pending';";

            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, username);

            ResultSet result = statement.executeQuery();

            if(!result.next()){
                System.out.println("User has no requests");
            }
            else{
                String query2 = "update tool_app.request\n "  + "set status = ?, date_responded = current_date" + " where request_id = (?);";

                PreparedStatement statement2 = conn.prepareStatement(query2);

                statement2.setString(1, "Denied");

                statement2.setInt(2, Integer.parseInt(requestID));

                boolean result2 = statement2.execute();

                System.out.println("Request Denied");

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
