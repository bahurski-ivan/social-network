package ru.bahurski.socialnetwork.core.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.bahurski.socialnetwork.core.model.friend.FriendRelation;
import ru.bahurski.socialnetwork.core.model.friend.FriendRequest;

import java.util.Optional;

/**
 * friend ru.bahurski.socialnetwork.friendservice.service interface
 */
public interface FriendService {
    /**
     * lists all incoming fiends requests to the userId
     */
    Page<FriendRequest> listFriendRequests(long userId, Pageable pageable);

    /**
     * lists friends ids of given user
     *
     * @param userId   target user id
     * @param pageable pagination settings
     * @return page of user's friends ids
     */
    Page<Long> listFriends(long userId, Pageable pageable);

    /**
     * tries to make friend request
     *
     * @return empty if cannot create relation
     */
    Optional<FriendRequest> createFriendRequest(FriendRequest request);

    /**
     * will try to remove friend request
     *
     * @return true if successfully
     */
    boolean removeFriendRequest(FriendRequest request);

    /**
     * will try to make a friendRelation form FriendRequest
     *
     * @return empty if relation already exist OR if friend request not exist
     * <p>
     * on success will automatically remove FriendRequest
     */
    Optional<FriendRelation> acceptFriendRequest(FriendRequest request);

    /**
     * will try to delete the friend relation
     *
     * @return true if successfully
     */
    boolean deleteFriendRelation(FriendRelation relation);
}
