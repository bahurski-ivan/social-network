package ru.bahurski.socialnetwork.core.model.friend;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by Ivan on 22/12/2016.
 */
@Entity(name = "FRIEND_REQUESTS")
public class FriendRequest implements Serializable {
    private static final long serialVersionUID = -2417459741497560915L;

    @Id
    @GeneratedValue
    @Column(name = "request_id", nullable = false, unique = true)
    private long requestId;

    /**
     * user id who sent request to become friend
     */
    @Column(name = "sender_id", nullable = false)
    private long senderId;

    /**
     * user id who being requested to be friend
     */
    @Column(name = "target_id", nullable = false)
    private long targetId;


    public long getRequestId() {
        return requestId;
    }

    public void setRequestId(long requestId) {
        this.requestId = requestId;
    }

    public long getSenderId() {
        return senderId;
    }

    public void setSenderId(long senderId) {
        this.senderId = senderId;
    }

    public long getTargetId() {
        return targetId;
    }

    public void setTargetId(long targetId) {
        this.targetId = targetId;
    }

    @Override
    public String toString() {
        return "FriendRequest{" +
                "requestId=" + requestId +
                ", senderId=" + senderId +
                ", targetId=" + targetId +
                '}';
    }
}
