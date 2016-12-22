package ru.bahurski.socialnetwork.friendservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import ru.bahurski.socialnetwork.core.model.friend.FriendRelation;
import ru.bahurski.socialnetwork.core.model.friend.FriendRequest;
import ru.bahurski.socialnetwork.core.service.FriendService;
import ru.bahurski.socialnetwork.friendservice.repository.FriendRelationRepo;
import ru.bahurski.socialnetwork.friendservice.repository.FriendRequestRepo;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * friend ru.bahurski.socialnetwork.friendservice.service implementation
 */
public class FriendServiceImpl implements FriendService {
    private final FriendRelationRepo friendRelationRepo;
    private final FriendRequestRepo friendRequestRepo;
    private final PlatformTransactionManager transactionManager;

    @Autowired
    public FriendServiceImpl(FriendRelationRepo friendRelationRepo,
                             FriendRequestRepo friendRequestRepo,
                             PlatformTransactionManager transactionManager) {
        this.friendRelationRepo = friendRelationRepo;
        this.friendRequestRepo = friendRequestRepo;
        this.transactionManager = transactionManager;
    }

    @Override
    public Page<FriendRequest> listFriendRequests(long userId, Pageable pageable) {
        final TransactionTemplate transaction = new TransactionTemplate(transactionManager);

        return transaction.execute(context ->
                friendRequestRepo.findByTargetId(userId, pageable)
        );
    }

    @Override
    public Page<Long> listFriends(long userId, Pageable pageable) {
        final TransactionTemplate transaction = new TransactionTemplate(transactionManager);

        return transaction.execute(context -> {
            Page<FriendRelation> friends = friendRelationRepo.findByUserId(userId, pageable);

            List<Long> ids = friends.getContent().stream().map(fr ->
                    fr.getUserId1() == userId ? fr.getUserId2() : fr.getUserId1()
            ).collect(Collectors.toList());

            return new PageImpl<>
                    (ids, pageable, friends.getTotalElements());
        });
    }

    @Override
    public Optional<FriendRequest> createFriendRequest(FriendRequest request) {
        final TransactionTemplate transaction = new TransactionTemplate(transactionManager);

        if (request.getSenderId() == request.getTargetId())
            return Optional.empty();

        return transaction.execute(context -> {
            Page<FriendRequest> friendRequests = friendRequestRepo.checkFriendRequestExist(request.getSenderId(),
                    request.getTargetId(), new PageRequest(0, 1));

            // in this case friendRequest (user1 <-> user2) already exists
            if (friendRequests.getTotalElements() != 0)
                return Optional.empty();

            long maxId, minId;

            if (request.getSenderId() > request.getTargetId()) {
                maxId = request.getSenderId();
                minId = request.getTargetId();
            } else {
                maxId = request.getTargetId();
                minId = request.getSenderId();
            }

            Optional<FriendRelation> relation = friendRelationRepo.findByUserId1AndUserId2(minId, maxId);

            // here users are already friends!
            if (relation.isPresent())
                return Optional.empty();

            request.setRequestId(0);
            return Optional.ofNullable(friendRequestRepo.save(request));
        });
    }

    @Override
    public boolean removeFriendRequest(FriendRequest request) {
        final TransactionTemplate transaction = new TransactionTemplate(transactionManager);

        return transaction.execute(status -> {
            if (friendRequestRepo.exists(request.getRequestId())) {
                friendRequestRepo.delete(request.getRequestId());
                return true;
            }
            return false;
        });
    }

    @Override
    public Optional<FriendRelation> acceptFriendRequest(FriendRequest request) {
        final TransactionTemplate transaction = new TransactionTemplate(transactionManager);

        if (request.getSenderId() == request.getTargetId())
            return Optional.empty();

        return transaction.execute(context -> {
            Page<FriendRequest> friendRequests = friendRequestRepo.checkFriendRequestExist(request.getSenderId(),
                    request.getTargetId(), new PageRequest(0, 1));

            if (friendRequests.getTotalElements() == 0)
                return Optional.empty();

            // in this case friendRequest (user1 <-> user2) exists

            long maxId, minId;

            if (request.getSenderId() > request.getTargetId()) {
                maxId = request.getSenderId();
                minId = request.getTargetId();
            } else {
                maxId = request.getTargetId();
                minId = request.getSenderId();
            }

            FriendRelation friendRelation = new FriendRelation();

            friendRelation.setUserId1(minId);
            friendRelation.setUserId2(maxId);

            // remove request
            friendRequestRepo.delete(friendRequests.getContent().get(0));
            // and create friendship
            return Optional.of(friendRelationRepo.save(friendRelation));
        });
    }

    @Override
    public boolean deleteFriendRelation(FriendRelation relation) {
        final TransactionTemplate transaction = new TransactionTemplate(transactionManager);

        return transaction.execute(status -> {
            if (friendRelationRepo.exists(relation.getId())) {
                friendRelationRepo.delete(relation.getId());
                return true;
            }
            return false;
        });
    }
}
