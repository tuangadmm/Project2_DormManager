/*
 */
package data;

import database.Connect_db;
import database.Query;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 *
 * @author tuan
 */
public class ResetPassword {

    //fields
    String un, em, ph, err, newPw;

    //getter
    public String getUn() {
        return un;
    }

    public String getEm() {
        return em;
    }

    public String getPh() {
        return ph;
    }

    public String getErr() {
        return err;
    }

    public String getNewPw() {
        return newPw;
    }

    //setter
    public void setUn(String un) {
        this.un = un;
    }

    public void setEm(String em) {
        this.em = em;
    }

    public void setPh(String ph) {
        this.ph = ph;
    }

    public void setErr(String err) {
        this.err = err;
    }

    public void setNewPw(String newPw) {
        this.newPw = newPw;
    }

    //method
    //reset password
    public boolean resetPassword(TextField u, TextField e, TextField p) {
        String username = u.getText();
        String email = e.getText();
        String phone = p.getText();

        //check
        try {
            Connection conn = Connect_db.getConnection();
            PreparedStatement stmt = conn.prepareStatement(Query.infoCheck);
            stmt.setString(1, username);
            stmt.setString(2, email);
            stmt.setString(3, phone);

            ResultSet urs = stmt.executeQuery();

            urs.next();
            if (urs.getInt("numRow") == 1) {
                String newP = passwordGenerator();
                setNewPw(newP);
                //update 
                try {
                    stmt = conn.prepareStatement(Query.resetPassword);
                    stmt.setString(1, newP);
                    stmt.setString(2, username);
                    stmt.execute();

                } catch (SQLException ee) {
                    System.out.println("Error updating data: " + ee.getMessage());
                }
                return true;
            }else{
                setErr("Your information doesn't match our record, please try again");
            }

        } catch (SQLException ex12) {
            System.out.println("Error updating data: " + ex12.getMessage());
        }
        return false;

    }

    //generate new passwors
    private String passwordGenerator() {
        String pattern = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String newPass = "";
        Random r = new Random();
        for (int i = 0; i < 10; i++) {
            newPass += pattern.charAt(r.nextInt(pattern.length()));
        }
        return newPass;

    }
}
