/*
 */
package data;

import java.sql.Connection;
import database.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author tuan
 */
public class Announcement {

    //fields
    String nameErr, emailErr, phoneErr;

    public Announcement() {
    }

//Student list
    SimpleIntegerProperty Id;
    SimpleStringProperty Name, Role, To, Subject, Message, SentDate;

    public Announcement(int sId, String sName, String sRole, String sTo, String sSubject, String sMessage, String sSentDate) {
        this.Id = new SimpleIntegerProperty(sId);
        this.Name = new SimpleStringProperty(sName);
        this.Role = new SimpleStringProperty(sRole);
        this.To = new SimpleStringProperty(sTo);
        this.Subject = new SimpleStringProperty(sSubject);
        this.Message = new SimpleStringProperty(sMessage);
        this.SentDate = new SimpleStringProperty(sSentDate);
    }

    //getter and setter
    public int getId() {
        return Id.get();
    }

    public String getName() {
        return Name.get();
    }

    public String getRole() {
        return Role.get();
    }

    public String getTo() {
        return To.get();
    }

    public String getSubject() {
        return Subject.get();
    }

    public String getMessage() {
        return Message.get();
    }

    public String getSentDate() {
        return SentDate.get();
    }

    public void setsId(int sId) {
        this.Id = new SimpleIntegerProperty(sId);
    }

    public void setsName(String sName) {
        this.Name = new SimpleStringProperty(sName);
    }

    public void setsRole(String sRole) {
        this.Role = new SimpleStringProperty(sRole);
    }

    public void setsTo(String sTo) {
        this.To = new SimpleStringProperty(sTo);
    }

    public void setsSubject(String sSubject) {
        this.Subject = new SimpleStringProperty(sSubject);
    }

    public void setsMessage(String sMessage) {
        this.Message = new SimpleStringProperty(sMessage);
    }

    public void setsSentDate(String sSentDate) {
        this.SentDate = new SimpleStringProperty(sSentDate);
    }

    //methods
    //announcement list
    public ObservableList<Announcement> loadAnnList() {
        ObservableList data = FXCollections.observableArrayList();
        try {
            Connection conn = Connect_db.getConnection();
            PreparedStatement stmt = conn.prepareStatement(Query.getAnnList);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                data.add(new Announcement(rs.getInt("announcements.id"),
                        rs.getString("full_name"),
                        rs.getString("role"),
                        rs.getString("rooms.name"),
                        rs.getString("subject"),
                        rs.getString("message"),
                        rs.getString("sent_date")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error loading announcement list: " + e.getMessage());
        }
        return data;
    }

    //add announcement
    public boolean addAnn(int sender, int roomId, String subject, String message) {
        try {
            Connection conn = Connect_db.getConnection();
            PreparedStatement stmt = conn.prepareStatement(Query.addAnnouncement);
            stmt.setInt(1, sender);
            stmt.setInt(2, roomId);
            stmt.setString(3, subject);
            stmt.setString(4, message);
            stmt.execute();
            return true;
          
        } catch (SQLException e) {
            System.out.println("Error loading announcement list: " + e.getMessage());
            return false;
        }
    }

}
