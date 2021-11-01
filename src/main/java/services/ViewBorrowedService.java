package services;

import application.DatabaseConnection;
import com.jcraft.jsch.Session;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

public class ViewBorrowedService {

    private static String FORMAT = "%-12s|%-50s|%-50s|%-12s|%-12s|%-15s";

    public ViewBorrowedService() {

    }

    public static void viewBorrowed(String usernameArg) throws SQLException {

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
            String query = "select * from (select barcode, name, description, username, owner, date_responded, date_needed_return, " +
                    "status from (select * from tool_app.tool tool inner join tool_app.request request on request.tool_barcode = " +
                    "tool.barcode) as x where status = 'Accepted' and username = (?)) as y order by date_responded asc";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, usernameArg);
            ResultSet result = statement.executeQuery();

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDateTime now = LocalDateTime.now();
            String currDate = dtf.format(now);      //todays date, for determining if a tool is overdue

            if (!result.next()) {
                System.out.println("No borrowed tools");
            } else {
                System.out.format(FORMAT, "barcode", "name", "description", "owner", "lend date", "return by date");
                System.out.println();
                do {
                    String barcode = result.getString("barcode");
                    String name = result.getString("name");
                    String description = result.getString("description");
                    String username = result.getString("owner");
                    String date_responded = result.getString("date_responded");
                    String date_needed_return = result.getString("date_needed_return");



                    System.out.format(FORMAT, barcode, name, description, username, date_responded, date_needed_return);
                    if(overdue(currDate, date_needed_return)){
                        System.out.print("   --OVERDUE--  ");
                    }
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

    public static boolean overdue(String now, String dueDate){
        if(Integer.parseInt(now.substring(0,4)) == Integer.parseInt(dueDate.substring(0,4))){
            if(Integer.parseInt(now.substring(5,7)) == Integer.parseInt(dueDate.substring(5,7))){
                return Integer.parseInt(now.substring(8)) > Integer.parseInt(dueDate.substring(8));
            } else return Integer.parseInt(now.substring(5, 7)) > Integer.parseInt(dueDate.substring(5, 7));
        } else return Integer.parseInt(now.substring(0, 4)) > Integer.parseInt(dueDate.substring(0, 4));
    }
}