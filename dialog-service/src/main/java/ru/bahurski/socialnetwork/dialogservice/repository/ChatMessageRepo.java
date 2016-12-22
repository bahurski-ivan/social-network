package ru.bahurski.socialnetwork.dialogservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.bahurski.socialnetwork.core.model.dialog.ChatMessage;

/**
 * Created by Ivan on 21/12/2016.
 */
public interface ChatMessageRepo extends JpaRepository<ChatMessage, Long> {
    Page<ChatMessage> findByDialogId(long dialogId, Pageable pageable);
}
