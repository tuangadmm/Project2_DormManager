package database;

import java.sql.*;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;

public class Connect_db {

    private static int initError = 0;

    //create database if not exist
    private static boolean initDb() {
        String initUrl = "jdbc:mysql://" + Database.HOSTNAME + ":" + Database.PORT;
        String checkDb = "select count(*) as num from information_schema.schemata where schema_name = 'dormmanager'";

        try {
            Connection initConn = DriverManager.getConnection(initUrl, Database.USERNAME, Database.PASSWORD);
            ResultSet rs = initConn.createStatement().executeQuery(checkDb);
            rs.next();
            //if database is exist, return false
            if (rs.getInt("num") == 1) {
                return false;
            } else { //if database not exist, create
                try {
                    String initDb = "create database dormmanager";
                    initConn.createStatement().execute(initDb);
                    Init.initDbInfo();
                    
                    return true;
                } catch (SQLException e) {
                    initError = 1;
                    return false;
                }
            }

        } catch (SQLException e) {
            initError = 1;
        }
        return false;
    }

    public static Connection getConnection() {
        Connection con = null;
        String connectionUrl = "jdbc:mysql://" + Database.HOSTNAME
                + ":" + Database.PORT + "/"
                + Database.DBNAME;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            System.err.println("MySQL Connector/J is missing");
            return con;
        }

        //check database
        //if database is not exist, create one and ask user restart app
        if (initDb() == true) {
            Dialog initPrompt = new Dialog();
            ButtonType btn = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
            initPrompt.setTitle("Information");
            initPrompt.setContentText("Application has been updated\nRestart to apply change");
            initPrompt.getDialogPane().getButtonTypes().add(btn);
            initPrompt.showAndWait();
            System.exit(0);
        } //if database is not exist and failed to create new one, ask user to reconfigure database/Database.java
        else if (initDb() == false && initError == 1) {
            Dialog initPrompt = new Dialog();
            ButtonType btn = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
            initPrompt.setTitle("Error");
            initPrompt.setContentText("Update applicaiton failed\nPlease reconfigure information under 'database/Database.java' except 'DBNAME' and try again");
            initPrompt.getDialogPane().getButtonTypes().add(btn);
            initPrompt.showAndWait();
            System.exit(0);
        } //if database is exist and faied to connect, ash user to reconfigure database/Database.java
        else {
            try {
                con = DriverManager.getConnection(connectionUrl, Database.USERNAME, Database.PASSWORD);
            } catch (SQLException ex) {
                Dialog initPrompt = new Dialog();
                ButtonType btn = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                initPrompt.setTitle("Error");
                initPrompt.setContentText("Connect to database failed\nPlease reconfigure information under 'database/Database.java' except 'DBNAME' and try again\n"
                        + "Error : " + ex.getLocalizedMessage());
                initPrompt.getDialogPane().getButtonTypes().add(btn);
                initPrompt.showAndWait();
                System.exit(0);
            }
        }

        return con;
    }

    public static void closeConnection(Connection con) {
        try {
            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
            System.out.println("Close connection failed");
        }
    }

    public static void closeResultSet(ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            System.out.println("Close ResultSet failed");
        }
    }

    public static void closePreparedStatement(PreparedStatement prepare) {
        try {
            if (prepare != null) {
                prepare.close();
            }
        } catch (SQLException e) {
            System.out.println("Close PreparedStatement failed");
        }
    }

}
