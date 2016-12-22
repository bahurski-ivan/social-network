package ru.bahurski.socialnetwork.core.model.friend;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by Ivan on 22/12/2016.
 */
@Entity(name = "FRIENDS")
public class FriendRelation implements Serializable {
    private static final long serialVersionUID = -6963546670662296051L;

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false, unique = true)
    private long id;

    @Column(name = "user_id1", nullable = false)
    private long userId1;

    @Column(name = "user_id2", nullable = false)
    private long userId2;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
        return "FriendRelation{" +
                "id=" + id +
                ", userId1=" + userId1 +
                ", userId2=" + userId2 +
                '}';
    }
}
