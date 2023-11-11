/*
 */
package data;

import database.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author tuan
 */
public class UpdateAnnFedbkLoader {
    //fields
    int id;
    String from, to, sentDate, subject, message;
    String replier, replyMessage, replyTime;
    //constructor
    public UpdateAnnFedbkLoader() {
    }

    public UpdateAnnFedbkLoader(int id, String from, String to, String sentDate, String subject, String message, String replier, String replyMessage, String replyTime) {
        this.id = id;
        this.from = from;
        this.to = to;
        this.sentDate = sentDate;
        this.subject = subject;
        this.message = message;
        this.replier = replier;
        this.replyMessage = replyMessage;
        this.replyTime = replyTime;
    }
    
    //getter and setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getSentDate() {
        return sentDate;
    }

    public void setSentDate(String sentDate) {
        this.sentDate = sentDate;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getReplier() {
        return replier;
    }

    public void setReplier(String replier) {
        this.replier = replier;
    }

    public String getReplyMessage() {
        return replyMessage;
    }

    public void setReplyMessage(String replyMessage) {
        this.replyMessage = replyMessage;
    }

    public String getReplyTime() {
        return replyTime;
    }

    public void setReplyTime(String replyTime) {
        this.replyTime = replyTime;
    }
    

//build data
    public void detailAnnouncement(int annId) {
        try {
            Connection conn = Connect_db.getConnection();
            PreparedStatement stmt = conn.prepareStatement(Query.getAnnDetail);
            stmt.setInt(1, annId);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            
            setFrom(rs.getString("full_name"));
            setTo(rs.getString("name"));
            setSentDate(rs.getString("sent_date"));
            setSubject(rs.getString("subject"));
            setMessage(rs.getString("message"));
            
        } catch (SQLException ex) {
            System.out.println("Error loading announcement detail: " + ex.getMessage());
        }
    }

    //sent feedback
    public void detailFeedback(int feedbackId) {
        try {
            Connection conn = Connect_db.getConnection();
            
            PreparedStatement stmt = conn.prepareStatement(Query.getFeedbackDetail);
            stmt.setInt(1, feedbackId);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            setId(rs.getInt("feedback.id"));
            setTo(rs.getString("students.id"));
            setFrom("From: " + rs.getString("students.full_name") + "; Room: " + rs.getString("rooms.name"));
            setSubject(rs.getString("feedback.subject"));
            setMessage(rs.getString("feedback.message"));
            setSentDate(rs.getString("feedback.sent_date"));
            
        } catch (SQLException ex) {
            System.out.println("Error loading feedback detail: " + ex.getMessage());
        }
    }
    
    //replied feedback
    public void detailFeedbackReply(int feedbackId) {
        try {
            Connection conn = Connect_db.getConnection();
            
            PreparedStatement stmt = conn.prepareStatement(Query.getFeedbackReply);
            stmt.setInt(1, feedbackId);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            setReplyMessage(rs.getString("message"));
            
            
        } catch (SQLException ex) {
            setReplyMessage("Not yet answered");
            System.out.println("Error loading feedback detail: " + ex.getMessage());
        }
    }
    
//methods
    //answer feedback
    public boolean answerFeedback(int feedbackId, String message, int reciever){
        try {
            Connection conn = Connect_db.getConnection();
            
            PreparedStatement stmt = conn.prepareStatement(Query.answerFeedback);
            stmt.setInt(1, feedbackId);
            stmt.setInt(2, Common.getManagerId());
            stmt.setInt(3, reciever);
            stmt.setString(4, message);
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            System.out.println("Error answering feedback: " + ex.getMessage());
            return false;
        }
    }

    
}
