/*
 */
package Main;

import student.*;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

/**
 *
 * @author tuan
 */
public class StudentDetailController {
//nodes

    //buttons
    @FXML
    Label from, to, sentDate, subject, replier, replyTime;
    @FXML
    TextArea message, replyMessage;
    @FXML
    Button confirmBtn;

//build
    //build data
    public void buildAnn(int id) {
        DetailLoader d = new DetailLoader();
        d.detailAnnouncement(id);

        from.setText(d.getFrom());
        to.setText(d.getTo());
        sentDate.setText(d.getSentDate());
        subject.setText(d.getSubject());
        message.setText(d.getMessage());
    }

    public void buildFeedback(int id) {
        DetailLoader d = new DetailLoader();
        d.detailFeedback(id);

        subject.setText( d.getSubject());
        message.setText(d.getMessage());
        sentDate.setText("sent: " + d.getSentDate());

    }
    public void buildFeedbackReply(int id) {
        DetailLoader d = new DetailLoader();
 
        d.detailFeedbackReply(id);
        replier.setText("From: " + d.getReplier());
        replyMessage.setText(d.getReplyMessage());
        replyTime.setText("replied: " + d.getReplyTime());
    }

//methods
    //close 
    public void closeWindow(ActionEvent event) throws IOException {
        Stage sta = (Stage) confirmBtn.getScene().getWindow();
        sta.close();
    }
}
