import com.jcraft.jsch.*;

import java.sql.*;
import java.util.Properties;

public class DatabaseConnection {

    private static int LPORT = 5432;
    private static int RPORT = 5432;
    private static String RHOST = "starbug.cs.rit.edu";
    private static String DRIVER = "org.postgresql.Driver";

    private static String DB_NAME = "p320_21";
    private static String USERNAME = "";
    private static String PASSWORD = "";

    public static ResultSet runSQL(String sqlStatement) throws SQLException {

        int lport = LPORT;
        String rhost = RHOST;
        int rport = RPORT;
        String user = USERNAME; //change to your username
        String password = PASSWORD; //change to your password
        String databaseName = DB_NAME; //change to your database name

        String driverName = DRIVER;
        Connection conn = null;
        Session session = null;
        try {
            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            JSch jsch = new JSch();
            session = jsch.getSession(user, rhost, 22);
            session.setPassword(password);
            session.setConfig(config);
            session.setConfig("PreferredAuthentications","publickey,keyboard-interactive,password");
            session.connect();
            System.out.println("Connected");
            int assigned_port = session.setPortForwardingL(lport, "localhost", rport);
            System.out.println("Port Forwarded");

            // Assigned port could be different from 5432 but rarely happens
            String url = "jdbc:postgresql://localhost:"+ assigned_port + "/" + databaseName;

            System.out.println("database Url: " + url);
            Properties props = new Properties();
            props.put("user", user);
            props.put("password", password);

            Class.forName(driverName);
            conn = DriverManager.getConnection(url, props);
            System.out.println("Database connection established");

            // Do something with the database....

            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlStatement);

            return resultSet;

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
        return null;
    }

}
