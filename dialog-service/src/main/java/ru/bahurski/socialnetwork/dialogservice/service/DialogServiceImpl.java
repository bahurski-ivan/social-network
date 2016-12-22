package ru.bahurski.socialnetwork.dialogservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import ru.bahurski.socialnetwork.core.model.dialog.ChatMessage;
import ru.bahurski.socialnetwork.core.model.dialog.Dialog;
import ru.bahurski.socialnetwork.core.service.DialogService;
import ru.bahurski.socialnetwork.dialogservice.repository.ChatMessageRepo;
import ru.bahurski.socialnetwork.dialogservice.repository.DialogRepo;

import java.time.LocalDateTime;

/**
 * Created by Ivan on 21/12/2016.
 */
public class DialogServiceImpl implements DialogService {
    private final ChatMessageRepo chatMessageRepo;
    private final DialogRepo dialogRepo;
    private final PlatformTransactionManager transactionManager;

    @Autowired
    public DialogServiceImpl(ChatMessageRepo chatMessageRepo, DialogRepo dialogRepo, PlatformTransactionManager transactionManager) {
        this.chatMessageRepo = chatMessageRepo;
        this.dialogRepo = dialogRepo;
        this.transactionManager = transactionManager;
    }

    @Override
    public Page<Dialog> list(long userId, PageRequest pageRequest) {
        final TransactionTemplate transaction = new TransactionTemplate(transactionManager);
        return transaction.execute((status) -> dialogRepo.findByUserId(userId, pageRequest));
    }

    @Override
    public Dialog create(Dialog dialog) {
        final TransactionTemplate transaction = new TransactionTemplate(transactionManager);

        long userId1 = dialog.getUserId1();
        long userId2 = dialog.getUserId2();

        if (userId1 == userId2)
            return null;
        else if (userId1 > userId2) {
            dialog.setUserId1(userId2);
            dialog.setUserId2(userId1);
        }

        dialog.setDialogId(0);

        return transaction.execute((status) -> {
            Dialog result = null;
            Dialog existent = dialogRepo.findByUserId1AndUserId2(dialog.getUserId1(), dialog.getUserId2());

            if (existent == null)
                result = dialogRepo.save(dialog);

            return result;
        });
    }

    @Override
    public ChatMessage addMessage(ChatMessage chatMessage) {
        final TransactionTemplate transaction = new TransactionTemplate(transactionManager);
        final long dialogId = chatMessage.getDialogId();

        return transaction.execute((status) -> {
            ChatMessage result = null;
            Dialog dialog = dialogRepo.findOne(dialogId);

            if (dialog != null && (chatMessage.getSenderId() == dialog.getUserId1() ||
                    chatMessage.getSenderId() == dialog.getUserId2())) {

                chatMessage.setTimeSent(LocalDateTime.now());
                result = chatMessageRepo.save(chatMessage);
            }
            return result;
        });
    }

    @Override
    public Page<ChatMessage> listMessages(long dialogId, PageRequest pageRequest) {
        final TransactionTemplate transaction = new TransactionTemplate(transactionManager);
        return transaction.execute((status) -> chatMessageRepo.findByDialogId(dialogId, pageRequest));
    }

    public void delete(long dialogId) {
        final TransactionTemplate transaction = new TransactionTemplate(transactionManager);

        transaction.execute(status -> {
            dialogRepo.delete(dialogId);
            return null;
        });
    }
}
