package ru.bahurski.socialnetwork.dialogservice.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import ru.bahurski.socialnetwork.core.model.dialog.ChatMessage;
import ru.bahurski.socialnetwork.core.model.dialog.Dialog;
import ru.bahurski.socialnetwork.core.service.DialogService;

import java.util.Random;

/**
 * Created by Ivan on 21/12/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestAppConfig.class, loader = AnnotationConfigContextLoader.class)
public class DialogServiceImplTest {
    @Autowired
    private DialogService dialogService;

    @Test
    public void testDialogsCreation() throws Exception {
        Dialog dialog = new Dialog();
        Random random = new Random();
        int totalDialogs = 0;

        for (int i = 0; i < 1000; ++i) {
            dialog.setUserId1(20);
            dialog.setUserId2(random.nextInt(100));
            Dialog newD = dialogService.create(dialog);

            if (newD != null)
                ++totalDialogs;
        }

        int totalPages;
        int currPage = 0;
        int dCounter = 0;

        do {
            PageRequest pageRequest = new PageRequest(currPage, 100);
            Page<Dialog> result = dialogService.list(20, pageRequest);
            totalPages = result.getTotalPages();

            for (Dialog d : result) {
                ++dCounter;
                Assert.assertFalse(d.getUserId1() >= d.getUserId2());   //ids >= violated
            }

        } while (currPage++ < totalPages);

        Assert.assertFalse(dCounter != totalDialogs);                   //counters not equal
    }

    @Test
    public void checkSelfDialog() throws Exception {
        Dialog dialog = new Dialog();

        dialog.setUserId1(1);
        dialog.setUserId2(1);

        Assert.assertNull(dialogService.create(dialog));
    }

    @Test
    public void testMessageCreation() throws Exception {
        Dialog d = new Dialog();
        d.setUserId1(1);
        d.setUserId2(2);
        d = dialogService.create(d);

        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setSenderId(1);
        chatMessage.setText("Hello");
        chatMessage.setDialogId(d.getDialogId());
        dialogService.addMessage(chatMessage);

        PageRequest pageRequest = new PageRequest(0, 2);
        Page<ChatMessage> messagePage = dialogService.listMessages(d.getDialogId(), pageRequest);

        Assert.assertTrue(messagePage.getContent().size() != 0);

        ChatMessage mm = messagePage.getContent().get(0);

        Assert.assertEquals(mm.getDialogId(), chatMessage.getDialogId());
        Assert.assertEquals(mm.getSenderId(), chatMessage.getSenderId());
        Assert.assertEquals(mm.getText(), chatMessage.getText());
    }

//    @Test
//    public void testCascadeDeletion() throws Exception {
//        Dialog d = new Dialog();
//        d.setUserId1(110);
//        d.setUserId2(111);
//        d = dialogService.create(d);
//
//        ChatMessage chatMessage = new ChatMessage();
//        chatMessage.setSenderId(110);
//        chatMessage.setText("Hello");
//        chatMessage.setDialogId(d.getDialogId());
//
//        chatMessage = dialogService.addMessage(chatMessage);
//
//        ((DialogServiceImpl)dialogService).delete(d.getDialogId());
//
//        Page<Dialog> dialogPage = dialogService.list(110, new PageRequest(0, 100));
//
//        Assert.assertTrue(dialogPage.getTotalElements() == 0);
//
//        Page<ChatMessage> page = dialogService.listMessages(d.getDialogId(), new PageRequest(0, 100));
//
//        Assert.assertTrue(page.getTotalPages() == 0);
//    }
}