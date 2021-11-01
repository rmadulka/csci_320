package services;

import application.DatabaseConnection;
import com.jcraft.jsch.Session;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class ViewOutgoingRequestService {

    private static String FORMAT = "%-10s|%-15s|%-15s|%-15s|%-20s|%-20s|%-20s|%-12s|%-50s|%-20s";

    public ViewOutgoingRequestService() {

    }

    public static void viewOutgoingRequests(String username) throws SQLException {
        Connection conn = null;
        Session session = null;

        try {
            session = DatabaseConnection.createSession();
            int assigned_port = session.setPortForwardingL(DatabaseConnection.LPORT, "localhost", DatabaseConnection.RPORT);
            conn = DatabaseConnection.createConnection(assigned_port);

            // Do something with the database....

            String query = "select * from tool_app.request\n" +
                    "join tool_app.tool on request.tool_barcode = tool.barcode\n" +
                    "where username = ? order by request_id";

            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, username);

            ResultSet result = statement.executeQuery();

            if (!result.next()) {
                System.out.println("User has made no requests");
            } else {
                System.out.format(FORMAT, "request_id", "status", "date_required", "duration", "date_responded", "date_needed_return", "date_returned", "tool_barcode", "name", "owner");
                System.out.println();
                do {
                    String requestId = result.getString("request_id");
                    String status = result.getString("status");
                    String dateRequired = result.getString("date_required");
                    String duration = result.getString("duration");
                    String dateResponded = result.getString("date_responded");
                    String dateNeededReturn = result.getString("date_needed_return");
                    String dateReturned = result.getString("date_returned");
                    String barcode = result.getString("barcode");
                    String name = result.getString("name");
                    String owner = result.getString("owner");



                    System.out.format(FORMAT, requestId, status, dateRequired, duration, dateResponded, dateNeededReturn, dateReturned, barcode, name, owner);
                    System.out.println();
                } while (result.next());
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
