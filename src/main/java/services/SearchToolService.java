package services;

import application.DatabaseConnection;
import com.jcraft.jsch.Session;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;


public class SearchToolService {

    private static String FORMAT = "%-12s|%-50s|%-50s|%-12s|%-13s|%-9s|%-20s|%-20s";

    public SearchToolService() {

    }

    public static void searchTool(String searchMethod, String searchArgument, String sortByMethod, String sortByArgument) throws SQLException {
        Connection conn = null;
        Session session = null;

        try {
            session = DatabaseConnection.createSession();
            int assigned_port = session.setPortForwardingL(DatabaseConnection.LPORT, "localhost", DatabaseConnection.RPORT);
            conn = DatabaseConnection.createConnection(assigned_port);

            // Do something with the database....

            Set<String> validMethods = new HashSet<>(Arrays.asList("barcode", "name", "category"));
            Set<String> validSortByMethods = new HashSet<>(Arrays.asList("name", "category"));
            Set<String> validSortByArguments = new HashSet<>(Arrays.asList("asc", "desc"));

            String orderByClause = null;
            String orderByArgumentClause = null;


            if (!validMethods.contains(searchMethod)) {
                System.out.println("invalid search method... defaulting to 'name'");
                searchMethod = "name";
            }

            if (!validSortByArguments.contains(sortByArgument)) {
                System.out.println("invalid sort by... defaulting to 'asc'");
                orderByClause = "ASC";
            } else {
                orderByClause = sortByArgument;
            }

            if (!validSortByMethods.contains(sortByMethod)) {
                System.out.println("invalid sort by argument... defaulting to to sorting by 'name'");
                orderByArgumentClause = "name";
            } else {
                orderByArgumentClause = sortByMethod;
            }

            if (searchMethod.equals("category")) {
                searchMethod = "category_name";
            }

            if (orderByArgumentClause.equals("category")) {
                orderByArgumentClause = "category_name";
            }

            String query = "select barcode, name, description, purchase_date, purchase_price, shareable, owner,  category_name from tool_app.tool\n" +
                    "left join tool_app.category_tool on barcode = category_tool.tool_barcode\n" +
                    "left join tool_app.category on category_tool.category_id = category.category_id WHERE Lower(" + searchMethod + ") LIKE ? order by " + orderByArgumentClause + " " + orderByClause + ";";



            PreparedStatement statement = conn.prepareStatement(query);

            statement.setString(1, "%" + searchArgument.toLowerCase() + "%");


            ResultSet result = statement.executeQuery();


            if (!result.next()) {
                System.out.println("No results found");
            } else {
                System.out.format(FORMAT, "barcode", "name", "description", "purchaseDate", "purchasePrice", "shareable", "owner", "category");
                System.out.println();
                do {
                    String barcode = result.getString("barcode");
                    String name = result.getString("name");
                    String description = result.getString("description");
                    String purchaseDate = result.getString("purchase_date");
                    String purchasePrice = result.getString("purchase_price");
                    String shareable = result.getString("shareable");
                    String owner = result.getString("owner");
                    String categoryName = result.getString("category_name");



                    System.out.format(FORMAT, barcode, name, description, purchaseDate, purchasePrice, shareable, owner, categoryName);
                    System.out.println();
                } while (result.next());
            }

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
