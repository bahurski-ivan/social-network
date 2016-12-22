package ru.bahurski.socialnetwork.friendservice.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import ru.bahurski.socialnetwork.core.model.friend.FriendRelation;
import ru.bahurski.socialnetwork.core.model.friend.FriendRequest;
import ru.bahurski.socialnetwork.core.service.FriendService;

import java.util.Optional;

/**
 * Created by Ivan on 21/12/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestAppConfig.class, loader = AnnotationConfigContextLoader.class)
public class FriendServiceImplTest {
    @Autowired
    private FriendService friendService;

    @Test
    public void testMakeFriendRequest() throws Exception {
        FriendRequest friendRequest = new FriendRequest();

        friendRequest.setSenderId(0);
        friendRequest.setTargetId(1);

        Optional<FriendRequest> result = friendService.createFriendRequest(friendRequest);

        Assert.assertTrue(result.isPresent());

        FriendRequest request = result.get();

        Assert.assertNotEquals(request.getRequestId(), 0);
        Assert.assertEquals(request.getSenderId(), friendRequest.getSenderId());
        Assert.assertEquals(request.getTargetId(), friendRequest.getTargetId());
    }

    @Test
    public void testAcceptFriendRequest() throws Exception {
        FriendRequest friendRequest = new FriendRequest();

        friendRequest.setSenderId(4);
        friendRequest.setTargetId(2);

        Optional<FriendRequest> result = friendService.createFriendRequest(friendRequest);

        Assert.assertTrue(result.isPresent());
        FriendRequest request = result.get();

        Optional<FriendRelation> frl = friendService.acceptFriendRequest(request);

        Assert.assertTrue(frl.isPresent());
        FriendRelation fr = frl.get();

        Assert.assertTrue(fr.getUserId1() < fr.getUserId2() && fr.getUserId1() != fr.getUserId2());

        Assert.assertFalse(friendService.removeFriendRequest(request));
    }

    @Test
    public void testCheckNoInviteWithoutRequest() throws Exception {
        FriendRequest friendRequest = new FriendRequest();

        friendRequest.setSenderId(11);
        friendRequest.setTargetId(12);

        Assert.assertFalse(friendService.acceptFriendRequest(friendRequest).isPresent());
    }

    @Test
    public void addDeleteFriend() throws Exception {
        FriendRequest friendRequest = new FriendRequest();

        friendRequest.setSenderId(11);
        friendRequest.setTargetId(12);

        friendService.createFriendRequest(friendRequest);
        Optional<FriendRelation> rel = friendService.acceptFriendRequest(friendRequest);

        Assert.assertTrue(rel.isPresent());

        FriendRelation fr = rel.get();

        Assert.assertEquals(fr.getUserId1(), 11);
        Assert.assertEquals(fr.getUserId2(), 12);

        Assert.assertTrue(friendService.deleteFriendRelation(fr));
        Page<Long> frp = friendService.listFriends(fr.getUserId1(), new PageRequest(0, 100));

        Assert.assertFalse(frp.getContent().stream().anyMatch(rr -> rr == fr.getUserId2()));
    }
}