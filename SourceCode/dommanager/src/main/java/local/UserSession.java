/*
 */
package local;

import database.Connect_db;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

/**
 *
 * @author tuan
 */
public class UserSession {

    public static int loginType;

    public UserSession() {
    }

    File user = new File("src\\main\\java\\local\\user.txt");

    //save user
    public void saveUser(String username) {
        try {
            File myObj = user;
            if (myObj.createNewFile()) {
                writeUser(username);
            } else {
                writeUser(username);
            }
        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    //write user
    private void writeUser(String username) {
        try {
            FileWriter writeUser = new FileWriter("src\\main\\java\\local\\user.txt");
            writeUser.write(username);
            writeUser.close();
        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    //clear user
    public void clearUser() {
        try {
            FileWriter writeUser = new FileWriter("src\\main\\java\\local\\user.txt");
            writeUser.write("");
            writeUser.close();
        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    //read file
    public String readUser() {
        try {
            Scanner myReader = new Scanner(user);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                return data;
            }
            myReader.close();
        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
        return "";
    }

    //check login
    public boolean isLoggedIn() {
        return !readUser().equals("");
    }

    //check valid data auto login
    public boolean userValid(String username) {
        try {
            Connection conn = Connect_db.getConnection();
            //sql
            String c = "select count(*) as num_row from users where username = '" + username + "'";
            String s = "select count(*) as num_row from students where email = '" + username + "'";
            //ResultSet
            ResultSet v = conn.createStatement().executeQuery(c);
            ResultSet sv = conn.createStatement().executeQuery(s);
            v.next();
            sv.next();
            //check login
            if (v.getInt("num_row") == 1) {
                loginType = 0;
                return true;
            }
            if (sv.getInt("num_row") == 1) {
                loginType = 1;
                return true;
            }
            return false;

        } catch (SQLException e) {
            System.out.println("Error loading data: " + e.getMessage());
        }
        return false;
    }

}
