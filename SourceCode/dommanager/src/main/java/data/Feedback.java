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
public class Feedback {

    public Feedback() {
    }

//Student list
    SimpleIntegerProperty Id;
    SimpleStringProperty Sender, Room, Subject, Message, SentDate;

    public Feedback(int sId, String sSubject, String sSender, String sRoom, String sMessage, String sSentDate) {
        this.Id = new SimpleIntegerProperty(sId);
        this.Subject = new SimpleStringProperty(sSubject);
        this.Sender = new SimpleStringProperty(sSender);
        this.Room = new SimpleStringProperty(sRoom);
        this.Message = new SimpleStringProperty(sMessage);
        this.SentDate = new SimpleStringProperty(sSentDate);
    }

    //getter and setter
    public int getId() {
        return Id.get();
    }

    public String getSubject() {
        return Subject.get();
    }

    public String getSender() {
        return Sender.get();
    }

    public String getRoom() {
        return Room.get();
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

    public void setsSubject(String sSubject) {
        this.Subject = new SimpleStringProperty(sSubject);
    }
    public void setsMessage(String sMessage) {
        this.Message = new SimpleStringProperty(sMessage);
    }

    public void setsSender(String sSender) {
        this.Sender = new SimpleStringProperty(sSender);
    }

    public void setsRoom(String sRoom) {
        this.Room = new SimpleStringProperty(sRoom);
    }

    public void setsSentDate(String sSentDate) {
        this.SentDate = new SimpleStringProperty(sSentDate);
    }


    //methods
    //feedback list
    public ObservableList<Feedback> loadFeedbackList() {
        ObservableList data = FXCollections.observableArrayList();
        try {
            Connection conn = Connect_db.getConnection();
            PreparedStatement stmt = conn.prepareStatement(Query.getFeedbackList);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                data.add(new Feedback(rs.getInt("feedback.id"),
                                rs.getString("subject"),
                                rs.getString("sender"),
                                rs.getString("rooms.name"),
                                rs.getString("message"),
                                rs.getString("sent_date")
                        ));
            }
        } catch (SQLException e) {
            System.out.println("Error loading data: " + e.getMessage());
        }
        return data;
    }
    
}
