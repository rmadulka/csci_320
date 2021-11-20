package services;

import application.DatabaseConnection;
import com.jcraft.jsch.Session;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ViewTopTenLent {

    private static String FORMAT = "%-5s|%-20s|%-12s|%-50s|%-50s|%-12s|%-12s|%-2s";

    public ViewTopTenLent() {

    }

    public static void viewTopTenLent(String username) throws SQLException {
        Connection conn = null;
        Session session = null;

        try {
            session = DatabaseConnection.createSession();
            int assigned_port = session.setPortForwardingL(DatabaseConnection.LPORT, "localhost", DatabaseConnection.RPORT);
            conn = DatabaseConnection.createConnection(assigned_port);

            // Do something with the database....
            String query = "select row_number() over (order by total_days_lent/(total_days::decimal) DESC) as ranking,\n" +
                    "       round((total_days_lent/(total_days::decimal)) * 100, 2) as percentage_lent,\n" +
                    "       tool_barcode, name, description, purchase_date, purchase_price, shareable\n" +
                    "from\n" +
                    "    (select tool_barcode,\n" +
                    "       current_date::DATE - purchase_date::DATE as total_days,\n" +
                    "           SUM((CASE\n" +
                    "            WHEN date_returned IS NULL THEN current_date\n" +
                    "            WHEN date_returned IS NOT NULL THEN date_returned END)::DATE - request.date_responded::DATE) as total_days_lent\n" +
                    "    from request\n" +
                    "    join tool t on t.barcode = request.tool_barcode\n" +
                    "    where owner = (?) AND status in ('Returned', 'Accepted')\n" +
                    "    group by tool_barcode, total_days) as inner_table\n" +
                    "join tool t2 on inner_table.tool_barcode = t2.barcode\n" +
                    "order by percentage_lent desc\n" +
                    "limit 10;";

            PreparedStatement statement = conn.prepareStatement(query);

            statement.setString(1, username);

            ResultSet result = statement.executeQuery();

            if (!result.next()) {
                System.out.println("No available tools");
            } else {
                System.out.format(FORMAT, "rank", "percentage borrowed", "barcode", "name", "description", "purchaseDate", "purchasePrice", "shareable");
                System.out.println();
                do {
                    String rank = result.getString("ranking");
                    String percentage = result.getString("percentage_lent");
                    String barcode = result.getString("tool_barcode");
                    String name = result.getString("name");
                    String description = result.getString("description");
                    String purchaseDate = result.getString("purchase_date");
                    String purchasePrice = result.getString("purchase_price");
                    String shareable = result.getString("shareable");



                    System.out.format(FORMAT, rank, percentage, barcode, name, description, purchaseDate, purchasePrice, shareable);
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
