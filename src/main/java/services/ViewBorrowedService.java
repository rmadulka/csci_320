package services;

import application.DatabaseConnection;
import com.jcraft.jsch.Session;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ViewBorrowedService {

    private static String FORMAT = "%-12s|%-50s|%-50s|%-12s|%-12s|%-2s";

    public ViewBorrowedService() {

    }

    public static void viewBorrowed() throws SQLException {
        Connection conn = null;
        Session session = null;

        try {
            session = DatabaseConnection.createSession();
            int assigned_port = session.setPortForwardingL(DatabaseConnection.LPORT, "localhost", DatabaseConnection.RPORT);
            conn = DatabaseConnection.createConnection(assigned_port);
            System.out.println("Port Forwarded");

            // Do something with the database....
            String query = "select barcode, name, description, purchase_date, purchase_price, shareable from (select * from tool_app.tool order by name asc) as x where shareable = true";

            PreparedStatement statement = conn.prepareStatement(query);

            ResultSet result = statement.executeQuery();

            if (!result.next()) {
                System.out.println("No available tools");
            } else {
                System.out.format(FORMAT, "barcode", "name", "description", "purchaseDate", "purchasePrice", "shareable");
                System.out.println();
                do {
                    String barcode = result.getString("barcode");
                    String name = result.getString("name");
                    String description = result.getString("description");
                    String purchaseDate = result.getString("purchase_date");
                    String purchasePrice = result.getString("purchase_price");
                    String shareable = result.getString("shareable");



                    System.out.format(FORMAT, barcode, name, description, purchaseDate, purchasePrice, shareable);
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