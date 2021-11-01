package services;

import application.DatabaseConnection;
import application.LoginSession;
import com.jcraft.jsch.Session;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Properties;


public class AddToolService {

    public AddToolService() {

    }

    public static void addTool(String barcode, String name, String description, String purchaseDate, String purchasePrice, String shareable, String username) throws SQLException {
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

            String query = "INSERT INTO tool_app.tool(barcode, name, description, purchase_date, purchase_price, shareable, owner) VALUES (?, ?, ?, ?, ?, ?, ?);";

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            java.sql.Date sqlPurchaseDate = new java.sql.Date(sdf.parse(purchaseDate).getTime());

            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, barcode);
            statement.setString(2, name);
            statement.setString(3, description);
            statement.setDate(4, sqlPurchaseDate);
            statement.setDouble(5, Double.parseDouble(purchasePrice));
            statement.setBoolean(6, Boolean.parseBoolean(shareable));
            statement.setString(7, username);

            boolean result = statement.execute();

            System.out.println("Successfully added tool");

        } catch (NumberFormatException nfe) {
            System.err.println("Error parsing arguments, NumberFormatException. See help for formatting");
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
