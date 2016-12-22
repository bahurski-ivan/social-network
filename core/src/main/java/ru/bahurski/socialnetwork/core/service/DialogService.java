package ru.bahurski.socialnetwork.core.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import ru.bahurski.socialnetwork.core.model.dialog.ChatMessage;
import ru.bahurski.socialnetwork.core.model.dialog.Dialog;

/**
 * Created by Ivan on 21/12/2016.
 */
public interface DialogService {
    Dialog create(Dialog dialog);

    ChatMessage addMessage(ChatMessage chatMessage);

    Page<Dialog> list(long userId, PageRequest pageRequest);

    Page<ChatMessage> listMessages(long dialogId, PageRequest pageRequest);
}
