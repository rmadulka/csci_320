package services;

import application.DatabaseConnection;
import application.LoginSession;
import com.jcraft.jsch.Session;

import java.sql.*;
import java.util.Properties;


public class ViewCatalogService {

    private static String FORMAT = "%-12s|%-50s|%-50s|%-12s|%-12s|%-2s";

    public ViewCatalogService() {

    }

    public static void viewCatalog(String username) throws SQLException {
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

            // Do something with the database....

            String query = "select barcode, name, description, purchase_date, purchase_price, shareable from tool_app.tool where owner = (?);";

            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, username);

            ResultSet result = statement.executeQuery();

            if (!result.next()) {
                System.out.println("User owns no tools");
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
