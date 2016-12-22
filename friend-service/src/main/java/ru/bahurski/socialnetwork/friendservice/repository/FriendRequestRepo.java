package ru.bahurski.socialnetwork.friendservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.bahurski.socialnetwork.core.model.friend.FriendRequest;

/**
 * Created by Ivan on 22/12/2016.
 */
public interface FriendRequestRepo extends JpaRepository<FriendRequest, Long> {
    Page<FriendRequest> findByTargetId(long targetId, Pageable pageable);

    @Query("SELECT f FROM FRIEND_REQUESTS f WHERE " +
            "f.senderId = :user_id1 AND f.targetId = :user_id2 OR " +
            "f.senderId = :user_id2 AND f.targetId = :user_id1")
    Page<FriendRequest> checkFriendRequestExist(@Param("user_id1") long userId1, @Param("user_id2") long userId2,
                                                Pageable pageable);


}
