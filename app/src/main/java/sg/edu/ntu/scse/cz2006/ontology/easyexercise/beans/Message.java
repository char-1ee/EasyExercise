package sg.edu.ntu.scse.cz2006.ontology.easyexercise.beans;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Message implements Serializable {

    private String messageText;
    private String messageUsername;
    private String messageAvatarUrl;
    private String messageTime;

    public Message(String messageText, String messageUser, String messageAvatarUrl) {
        this.messageText = messageText;
        this.messageUsername = messageUser;
        this.messageAvatarUrl = messageAvatarUrl;

        // Initialize to current time
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        messageTime = sdf.format(new Date());
    }

    public Message(){

    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getMessageUsername() {
        return messageUsername;
    }

    public void setMessageUsername(String messageUsername) {
        this.messageUsername = messageUsername;
    }

    public String getMessageAvatarUrl() {
        return messageAvatarUrl;
    }

    public void setMessageAvatarUrl(String messageAvatarUrl) {
        this.messageAvatarUrl = messageAvatarUrl;
    }

    public String getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(String messageTime) {
        this.messageTime = messageTime;
    }
}
