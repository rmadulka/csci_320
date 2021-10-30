package services;

import application.DatabaseConnection;
import com.jcraft.jsch.Session;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ViewLentService {

    private static String FORMAT = "%-12s|%-50s|%-50s|%-12s|%-12s";

    public ViewLentService() {

    }

    public static void viewLent() throws SQLException {
        Connection conn = null;
        Session session = null;

        try {
            session = DatabaseConnection.createSession();
            int assigned_port = session.setPortForwardingL(DatabaseConnection.LPORT, "localhost", DatabaseConnection.RPORT);
            conn = DatabaseConnection.createConnection(assigned_port);
            System.out.println("Port Forwarded");

            // Do something with the database....
            String query = "select * from (select barcode, name, description, username, date_responded, status from " +
                    "(select * from tool_app.tool tool inner join tool_app.request request on request.tool_barcode = " +
                    "tool.barcode) as x where status = 'Accepted') as y order by date_responded asc";
            PreparedStatement statement = conn.prepareStatement(query);

            ResultSet result = statement.executeQuery();

            if (!result.next()) {
                System.out.println("No lent tools");
            } else {
                System.out.format(FORMAT, "barcode", "name", "description", "username", "date_responded");
                System.out.println();
                do {
                    String barcode = result.getString("barcode");
                    String name = result.getString("name");
                    String description = result.getString("description");
                    String username = result.getString("username");
                    String date_responded = result.getString("date_responded");



                    System.out.format(FORMAT, barcode, name, description, username, date_responded);
                    System.out.println();
                } while (result.next());
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
