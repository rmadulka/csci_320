package services;

import application.DatabaseConnection;
import com.jcraft.jsch.Session;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class ViewIncomingRequestService {

    private static String FORMAT = "%-10s|%-20s|%-15s|%-15s|%-15s|%-20s|%-20s|%-20s|%-12s|%-50s";

    public ViewIncomingRequestService() {

    }

    public static void viewIncomingRequests(String username) throws SQLException {
        Connection conn = null;
        Session session = null;

        try {
            session = DatabaseConnection.createSession();
            int assigned_port = session.setPortForwardingL(DatabaseConnection.LPORT, "localhost", DatabaseConnection.RPORT);
            conn = DatabaseConnection.createConnection(assigned_port);

            // Do something with the database....

            String query = "select * from request\n" +
                    "join tool on request.tool_barcode = tool.barcode\n" +
                    "where owner = ? order by request_id;";

            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, username);

            ResultSet result = statement.executeQuery();

            if (!result.next()) {
                System.out.println("User has no requests");
            } else {
                System.out.format(FORMAT, "request_id", "request_from", "status", "date_required", "duration", "date_responded", "date_needed_return", "date_returned", "barcode", "name");
                System.out.println();
                do {
                    String requestId = result.getString("request_id");

                    String requestFrom = result.getString("username");
                    String status = result.getString("status");
                    String dateRequired = result.getString("date_required");
                    String duration = result.getString("duration");
                    String dateResponded = result.getString("date_responded");
                    String dateNeededReturn = result.getString("date_needed_return");
                    String dateReturned = result.getString("date_returned");
                    String barcode = result.getString("barcode");
                    String name = result.getString("name");



                    System.out.format(FORMAT, requestId, requestFrom, status, dateRequired, duration, dateResponded, dateNeededReturn, dateReturned, barcode, name);
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
