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
public class Feedback {

    public Feedback() {
    }

//Student list
    SimpleIntegerProperty Id;
    SimpleStringProperty Subject, Message, SentDate, Status;

    public Feedback(int sId, String sSubject, String sMessage, String sSentDate, String sStatus) {
        this.Id = new SimpleIntegerProperty(sId);
        this.Subject = new SimpleStringProperty(sSubject);
        this.Message = new SimpleStringProperty(sMessage);
        this.SentDate = new SimpleStringProperty(sSentDate);
        this.Status = new SimpleStringProperty(sStatus);
    }

    //getter and setter
    public int getId() {
        return Id.get();
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

    public String getStatus() {
        return Status.get();
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
    public void setsStatus(String sStatus) {
        this.Status = new SimpleStringProperty(sStatus);
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
            PreparedStatement stmt = conn.prepareStatement(QueryStudent.getFeedback);
            stmt.setInt(1, Common.getStudentIdByEmail(StudentController.email));
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                data.add(new Feedback(rs.getInt("feedback.id"),
                                rs.getString("subject"),
                                rs.getString("message"),
                                rs.getString("sent_date"),
                                rs.getString("status")
                        ));
            }
        } catch (SQLException e) {
            System.out.println("Error loading data: " + e.getMessage());
        }
        return data;
    }
    
    //add feedback
    public boolean addFeedback(String s, String m){
        try{
            Connection conn = Connect_db.getConnection();
            PreparedStatement stmt = conn.prepareStatement(QueryStudent.addFeedback);
            stmt.setInt(1, Common.getStudentIdByEmail(StudentController.email));
            stmt.setString(2, s);
            stmt.setString(3, m);
            
            stmt.execute();
            return true;
        }catch(SQLException e){
            System.out.println("Error adding feedback: " + e.getLocalizedMessage());
            return false;
        }
    }

}
