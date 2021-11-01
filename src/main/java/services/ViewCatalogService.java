package services;

import application.DatabaseConnection;
import application.LoginSession;
import com.jcraft.jsch.Session;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class ViewCatalogService {

    private static String FORMAT = "%-12s|%-50s|%-50s|%-12s|%-15s|%-12s|%-20s";

    public ViewCatalogService() {

    }

    public static void viewCatalog(String username) throws SQLException {
        Connection conn = null;
        Session session = null;

        try {
            session = DatabaseConnection.createSession();
            int assigned_port = session.setPortForwardingL(DatabaseConnection.LPORT, "localhost", DatabaseConnection.RPORT);
            conn = DatabaseConnection.createConnection(assigned_port);

            // Do something with the database....

            String query = "select barcode, name, description, purchase_date, purchase_price, shareable, category_name\n" +
                    "from tool_app.tool\n" +
                    "left join tool_app.category_tool ct on tool.barcode = ct.tool_barcode\n" +
                    "left join tool_app.category c on ct.category_id = c.category_id\n" +
                    "where owner = (?);";

            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, username);

            ResultSet result = statement.executeQuery();

            if (!result.next()) {
                System.out.println("User owns no tools");
            } else {
                System.out.format(FORMAT, "barcode", "name", "description", "purchaseDate", "purchasePrice", "shareable", "category");
                System.out.println();
                do {
                    String barcode = result.getString("barcode");
                    String name = result.getString("name");
                    String description = result.getString("description");
                    String purchaseDate = result.getString("purchase_date");
                    String purchasePrice = result.getString("purchase_price");
                    String shareable = result.getString("shareable");
                    String category = result.getString("category_name");



                    System.out.format(FORMAT, barcode, name, description, purchaseDate, purchasePrice, shareable, category);
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
