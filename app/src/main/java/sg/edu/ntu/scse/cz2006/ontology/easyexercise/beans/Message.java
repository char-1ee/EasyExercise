package sg.edu.ntu.scse.cz2006.ontology.easyexercise.beans;

import android.annotation.SuppressLint;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Message sent by user
 *
 * @Author Zhou Yuxuan
 */
public class Message implements Serializable {
    private final String messageText;
    private final String messageUsername;
    private final String messageAvatarUrl;
    private final String messageTime;

    public Message(String messageText, String messageUser, String messageAvatarUrl) {
        this.messageText = messageText;
        this.messageUsername = messageUser;
        this.messageAvatarUrl = messageAvatarUrl;

        // Initialize to current time
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        messageTime = sdf.format(new Date());
    }

    public String getMessageText() {
        return messageText;
    }

    public String getMessageUsername() {
        return messageUsername;
    }

    public String getMessageAvatarUrl() {
        return messageAvatarUrl;
    }

    public String getMessageTime() {
        return messageTime;
    }
}
