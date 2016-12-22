package ru.bahurski.socialnetwork.core.model.dialog;

import ru.bahurski.socialnetwork.core.util.converter.hibernate.LocalDateTimeAttributeConverter;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Created by Ivan on 21/12/2016.
 */
@Entity(name = "CHAT_MESSAGES")
public class ChatMessage implements Serializable {
    private static final long serialVersionUID = 4939160210125253696L;

    @Id
    @Column(name = "dialog_id", nullable = false)
    private long dialogId;

    @Column(name = "sender_id", nullable = false)
    private long senderId;

    @Column(name = "time_sent", nullable = false)
    @Convert(converter = LocalDateTimeAttributeConverter.class)
    private LocalDateTime timeSent;

    @Column(name = "text", length = 1000, nullable = false)
    private String text;


    public long getDialogId() {
        return dialogId;
    }

    public void setDialogId(long dialogId) {
        this.dialogId = dialogId;
    }

    public long getSenderId() {
        return senderId;
    }

    public void setSenderId(long senderId) {
        this.senderId = senderId;
    }

    public LocalDateTime getTimeSent() {
        return timeSent;
    }

    public void setTimeSent(LocalDateTime timeSent) {
        this.timeSent = timeSent;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "ChatMessage{" +
                "dialogId=" + dialogId +
                ", senderId=" + senderId +
                ", timeSent=" + timeSent +
                ", text='" + text + '\'' +
                '}';
    }
}
