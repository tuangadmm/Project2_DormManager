/*
 */
package data;

import Main.SceneController;
import database.Connect_db;
import database.Query;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.scene.control.TextField;
import java.sql.PreparedStatement;
import javafx.scene.control.PasswordField;

/**
 *
 * @author tuan
 */
public class Setting {

    //info
    String sUsername, sJoinedDate, sFullname, sEmail, sPhone, sAddress, sRole;
    //error
    String nameErr, emailErr, phoneErr;
    //change password
    String cPPassword, cPNewPassword, cPConfirmPasword, cPError, cFPError;

    //getter
    public String getsUsername() {
        return sUsername;
    }

    public String getsJoinedDate() {
        return sJoinedDate;
    }

    public String getsFullname() {
        return sFullname;
    }

    public String getsEmail() {
        return sEmail;
    }

    public String getsPhone() {
        return sPhone;
    }

    public String getsAddress() {
        return sAddress;
    }

    public String getsRole() {
        return sRole;
    }

    //error
    public String getNameErr() {
        return nameErr;
    }

    public String getEmailErr() {
        return emailErr;
    }

    public String getPhoneErr() {
        return phoneErr;
    }

    //change password
    public String getcPPassword() {
        return cPPassword;
    }

    public String getcPNewPassword() {
        return cPNewPassword;
    }

    public String getcPConfirmPasword() {
        return cPConfirmPasword;
    }

    public String getcPError() {
        return cPError;
    }

    public String getcFPError() {
        return cFPError;
    }

    //setter
    public void setsUsername(String sUsername) {
        this.sUsername = sUsername;
    }

    public void setsJoinedDate(String sJoinedDate) {
        this.sJoinedDate = sJoinedDate;
    }

    public void setsFullname(String sFullname) {
        this.sFullname = sFullname;
    }

    public void setsEmail(String sEmail) {
        this.sEmail = sEmail;
    }

    public void setsPhone(String sPhone) {
        this.sPhone = sPhone;
    }

    public void setsAddress(String sAddress) {
        this.sAddress = sAddress;
    }

    public void setsRole(String sRole) {
        this.sRole = sRole;
    }

    //error
    public void setNameErr(String nameErr) {
        this.nameErr = nameErr;
    }

    public void setEmailErr(String emailErr) {
        this.emailErr = emailErr;
    }

    public void setPhoneErr(String phoneErr) {
        this.phoneErr = phoneErr;
    }

    //change password
    public void setcPPassword(String cPPassword) {
        this.cPPassword = cPPassword;
    }

    public void setcPNewPassword(String cPNewPassword) {
        this.cPNewPassword = cPNewPassword;
    }

    public void setcPConfirmPasword(String cPConfirmPasword) {
        this.cPConfirmPasword = cPConfirmPasword;
    }

    public void setcPError(String cPError) {
        this.cPError = cPError;
    }

    public void setcFPError(String cFPError) {
        this.cFPError = cFPError;
    }

    //methods
    //load data
    public void loadData(String username) {
        try {
            Connection conn = Connect_db.getConnection();
            //ResultSet
            ResultSet a = conn.createStatement().executeQuery(Query.getManager(username));

            //add data
            while (a.next()) {
                //Iterate Row
                setsRole(a.getString("role"));
                setsUsername(a.getString("username"));
                setsJoinedDate(a.getString("joined_date"));
                setsFullname(a.getString("full_name"));
                setsEmail(a.getString("email"));
                setsPhone(a.getString("phone"));
                setsAddress(a.getString("address"));
            }
        } catch (SQLException e) {
            System.out.println("Error loading data: " + e.getMessage());
        }
    }

    //update manager
    public boolean updateManager(String username, TextField fullname, TextField email, TextField phone, TextField address) {
        String n = fullname.getText();
        String e = email.getText();
        String p = phone.getText();
        String a = address.getText();
        int isok = 1;

        Validator v = new Validator();

        if (v.nameCheck(n) == false) {
            isok = 0;
            setNameErr("invalid name");
        }
        if (v.emailcheck(e) == false) {
            isok = 0;
            setEmailErr("Invalid email format");
        }
        if (v.phoneCheck(p) == false) {
            isok = 0;
            setPhoneErr("Invalid phone number");
        }

        if (isok == 1) {
            Basic b = new Basic();
            b.loadManager(SceneController.username);

            try {
                Connection conn = Connect_db.getConnection();
                PreparedStatement stmt = conn.prepareStatement(Query.updateManager);
                stmt.setString(1, n);
                stmt.setString(2, e);
                stmt.setString(3, p);
                stmt.setString(4, a);
                stmt.setInt(5, b.getId());
                stmt.executeUpdate();
                return true;

            } catch (SQLException ex) {
                System.out.println("Error updating data: " + ex.getMessage());
            }
        }
        return false;

    }

    //delete manager
    public boolean deleteManager(String username) {
        try {
            Connection conn = Connect_db.getConnection();
            conn.createStatement().execute(Query.deleteManager(username));
            return true;
        } catch (SQLException ex1) {
            System.out.println("Error updating data: " + ex1.getMessage());
        }
        return false;
    }

    //change password
    public boolean changePassword(String username, PasswordField pw, PasswordField newPw, PasswordField confirmPw) {
        String p = pw.getText();
        String nPw = newPw.getText();
        String cPw = confirmPw.getText();
        int isok = 1;

        Basic b = new Basic();
        Validator v = new Validator();
        b.loadManager(username);

        int id = b.getId();
        
        if(v.passwordCheck(p, id) == false){
            isok = 0;
            setcFPError("invalid current password");
        }

        if (!nPw.equals(cPw)) {
            isok = 0;
            setcFPError("password does not matched");
        }
        if (nPw.length() < 5 || cPw.length() < 5) {
            isok = 0;
            setcFPError("password must include at least 5 characters");
        }
        //update
        if (isok == 1) {
            try {
                Connection conn = Connect_db.getConnection();
                conn.createStatement().execute(Query.updatePassword(nPw, id));
                return true;
            } catch (SQLException ex1) {
                System.out.println("Error updateing data: " + ex1.getMessage());
            }

        }
        return false;

    }
}
