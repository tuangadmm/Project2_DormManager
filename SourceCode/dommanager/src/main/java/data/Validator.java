/*
 */
package data;

import database.Connect_db;
import database.Query;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.validator.routines.EmailValidator;

/**
 *
 * @author tuan
 */
public class Validator {

//validation check
    //check empty string
    public boolean isEmpty(String s){
        return s.length() < 1;
    }

    //name 
    public boolean nameCheck(String name) {
        if (name.length() < 1) {
            return false;
        } else {
            for (int i = 0; i < name.length(); i++) {
                if (Character.isSpaceChar(name.charAt(i))) {
                    continue;
                } else if (!Character.isAlphabetic(name.charAt(i))) {
                    return false;
                }
            }

        }
        return true;
    }

    //phone
    public boolean phoneCheck(String phone) {
        if (phone.length() > 11 || phone.length() < 10 || phone.charAt(0) != '0') {
            return false;
        } else {
            for (int i = 1; i < phone.length(); i++) {
                if (!Character.isDigit((phone.charAt(i)))) {
                    return false;
                }
            }

        }
        return true;

    }

    //email
    public boolean emailcheck(String email) {
        return EmailValidator.getInstance(true).isValid(email);
    }

    //role
    public boolean roleCheck(String role) {
        List<String> arr = new ArrayList<String>(2);
        arr.add("Admin");
        arr.add("Staff");

        if (!arr.contains(role)) {
            return false;
        }
        return true;
    }

    //money
    public boolean moneyCheck(String money) {
        try {
            if (Integer.parseInt(money) > 0) {
                return true;
            }
        } catch (NumberFormatException exx) {
            return false;
        }
        return false;
    }

    //number
    public boolean numbercheck(String number) {
        try {
            if (Integer.parseInt(number) >= 0) {
                return true;
            }
        } catch (NumberFormatException ne) {
            return false;
        }
        return false;
    }
    
    //password
    public boolean passwordCheck(String password, int id){
        String p = "select password from users where id = '" + id + "'";
        String current;

        try {
            Connection conn = Connect_db.getConnection();
            //ResultSet
            ResultSet prs = conn.createStatement().executeQuery(p);
            prs.next();
            current = prs.getString("password");
            if (!current.equals(password)) {
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Error loading data: " + e.getMessage());
        }
        return true;
    }

//availability check
    //email
    public boolean emailAvailable(String email) {
        try {
            Connection conn = Connect_db.getConnection();
            ResultSet ers = conn.createStatement().executeQuery(Query.getEmail(email));
            ers.next();
            if (ers.getInt("numRow") == 1) {
                return false;
            }
        } catch (SQLException ae) {
        }
        return true;
    }
    
    //phone
    public boolean phoneAvailable(String phone) {
        try {
            Connection conn = Connect_db.getConnection();
            ResultSet prs = conn.createStatement().executeQuery(Query.getPhone(phone));
            prs.next();
            if (prs.getInt("numRow") == 1) {
                return false;
            }
        } catch (SQLException ae) {
        }
        return true;
    }

    //username
    public boolean usernameAvailable(String username) {
        String ua = "select count(*) as numRow from users where username = '" + username + "'";
        try {
            Connection conn = Connect_db.getConnection();
            ResultSet urs = conn.createStatement().executeQuery(ua);
            urs.next();
            if (urs.getInt("numRow") != 0) {
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Error loading data: " + e.getMessage());
        }
        return true;
    }

}
