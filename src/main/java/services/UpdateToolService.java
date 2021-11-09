package services;

import application.DatabaseConnection;
import com.jcraft.jsch.Session;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


public class UpdateToolService {

    public UpdateToolService() {

    }

    public static void updateTool(String barcode, String name, String description, String purchaseDate, String purchasePrice, String shareable, String username) throws SQLException {
        Connection conn = null;
        Session session = null;

        try {
            session = DatabaseConnection.createSession();
            int assigned_port = session.setPortForwardingL(DatabaseConnection.LPORT, "localhost", DatabaseConnection.RPORT);
            conn = DatabaseConnection.createConnection(assigned_port);

            // Do something with the database....

            String nameClause = "", descriptionClause = "", purchaseDateClause = "", purchasePriceClause =   "", shareableClause = "";
            if (name == null && description == null && purchaseDate == null && purchasePrice == null & shareable == null) {
                System.out.println("Please provide an attribute to be changed");
                return;
            }

            List<String> buildClauses = new ArrayList<>();

            if (name != null) {
                nameClause = " name = (?) ";
                buildClauses.add(nameClause);
            }
            if (description != null) {
                descriptionClause = " description = (?) ";
                buildClauses.add(descriptionClause);
            }
            if (purchaseDate != null) {
                purchaseDateClause = " purchase_date = (?) ";
                buildClauses.add(purchaseDateClause);
            }
            if (purchasePrice != null) {
                purchasePriceClause = " purchase_price = (?) ";
                buildClauses.add(purchasePriceClause);
            }
            if (shareable != null) {
                shareableClause = " shareable = (?) ";
                buildClauses.add(shareableClause);
            }


            String newBuildClauses = String.join(",", buildClauses);

            String query = "update tool\n" +
                    "set " + newBuildClauses +
                    " where barcode = ? AND owner = ?;";



            PreparedStatement statement = conn.prepareStatement(query);

            int i = 1;

            if (name != null) {
                statement.setString(i, name);
                i++;
            }
            if (description != null) {
                statement.setString(i, description);
                i++;
            }
            if (purchaseDate != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                java.sql.Date sqlPurchaseDate = new java.sql.Date(sdf.parse(purchaseDate).getTime());
                statement.setDate(i, sqlPurchaseDate);
                i++;
            }
            if (purchasePrice != null) {
                statement.setDouble(i, Double.parseDouble(purchasePrice));
                i++;
            }
            if (shareable != null) {
                statement.setBoolean(i, Boolean.parseBoolean(shareable));
                i++;
            }

            statement.setString(i, barcode);
            i++;
            statement.setString(i, username);

            int result = statement.executeUpdate();

            System.out.println("Successfully updated tool: rows affected: " + result);

        } catch (NumberFormatException nfe) {
            System.err.println("Error parsing arguments, NumberFormatException. See help for formatting");
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
