/*
 */
package student;

import Main.StudentController;
import java.sql.Connection;
import database.Connect_db;
import database.QueryStudent;
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

    public Announcement() {
    }

//Student list
    SimpleIntegerProperty Id;
    SimpleStringProperty Name, Role, Subject, Message, SentDate;

    public Announcement(int sId, String sName, String sRole, String sSubject, String sMessage, String sSentDate) {
        this.Id = new SimpleIntegerProperty(sId);
        this.Name = new SimpleStringProperty(sName);
        this.Role = new SimpleStringProperty(sRole);
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

    //student list
    public ObservableList<Announcement> loadAnnList() {
        ObservableList data = FXCollections.observableArrayList();
        try {
            Connection conn = Connect_db.getConnection();
            PreparedStatement stmt = conn.prepareStatement(QueryStudent.getAnn);
            stmt.setInt(1, Common.getRoomIdByEmail(StudentController.email));
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                data.add(new Announcement(rs.getInt("announcements.id"),
                                rs.getString("full_name"),
                                rs.getString("role"),
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


}
