package ru.bahurski.socialnetwork.friendservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.bahurski.socialnetwork.core.model.friend.FriendRelation;

import java.util.Optional;

/**
 * Created by Ivan on 22/12/2016.
 */
public interface FriendRelationRepo extends JpaRepository<FriendRelation, Long> {

    @Query("SELECT f FROM FRIENDS f WHERE f.userId1 = :user_id OR f.userId2 = :user_id")
    Page<FriendRelation> findByUserId(@Param("user_id") long userId, Pageable pageable);

    Optional<FriendRelation> findByUserId1AndUserId2(long userId1, long userId2);
}
