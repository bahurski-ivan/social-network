package ru.bahurski.socialnetwork.core.model.dialog;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * users dialog entity
 */
@Entity(name = "DIALOGS")
public class Dialog implements Serializable {
    private static final long serialVersionUID = -1545836696834190932L;

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false, unique = true)
    private long dialogId;

    @Column(name = "user_id1", nullable = false)
    private long userId1;

    @Column(name = "user_id2", nullable = false)
    private long userId2;


    public long getDialogId() {
        return dialogId;
    }

    public void setDialogId(long dialogId) {
        this.dialogId = dialogId;
    }

    public long getUserId1() {
        return userId1;
    }

    public void setUserId1(long userId1) {
        this.userId1 = userId1;
    }

    public long getUserId2() {
        return userId2;
    }

    public void setUserId2(long userId2) {
        this.userId2 = userId2;
    }

    @Override
    public String toString() {
        return "Dialog{" +
                "dialogId=" + dialogId +
                ", userId1=" + userId1 +
                ", userId2=" + userId2 +
                '}';
    }
}
