package application;

import com.jcraft.jsch.*;

import java.sql.*;
import java.util.Properties;

public class DatabaseConnection {

    public static int LPORT = 5432;
    public static int RPORT = 5432;
    private static String RHOST = "starbug.cs.rit.edu";
    private static String DRIVER = "org.postgresql.Driver";

    private static String DB_NAME = "p320_21"; //change to your database name
    private static String USERNAME = "jec5704"; //change to your username
    private static String PASSWORD = "Nianjecu2301-"; //change to your password

    public static Session createSession () throws JSchException {

        java.util.Properties config = new java.util.Properties();
        config.put("StrictHostKeyChecking", "no");
        JSch jsch = new JSch();
        Session session = jsch.getSession(USERNAME, RHOST, 22);
        session.setPassword(PASSWORD);
        session.setConfig(config);
        session.setConfig("PreferredAuthentications","publickey,keyboard-interactive,password");
        session.connect();
        System.out.println("Connected");
        return session;
    }

    public static Connection createConnection (int assigned_port) throws ClassNotFoundException, SQLException {
        // Assigned port could be different from 5432 but rarely happens
        String url = "jdbc:postgresql://localhost:"+ assigned_port + "/" + DB_NAME;

        System.out.println("database Url: " + url);
        Properties props = new Properties();
        props.put("user", USERNAME);
        props.put("password", PASSWORD);

        Class.forName(DRIVER);
        Connection conn = DriverManager.getConnection(url, props);
        System.out.println("Database connection established");
        return conn;
    }
}
