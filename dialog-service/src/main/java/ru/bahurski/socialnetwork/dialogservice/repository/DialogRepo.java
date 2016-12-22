package ru.bahurski.socialnetwork.dialogservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.bahurski.socialnetwork.core.model.dialog.Dialog;

/**
 * Created by Ivan on 21/12/2016.
 */
public interface DialogRepo extends JpaRepository<Dialog, Long> {
    Dialog findByUserId1AndUserId2(long userId1, long userId2);

    @Query("SELECT d FROM DIALOGS d WHERE d.userId1 = (:user_id) OR d.userId2 = (:user_id)")
    Page<Dialog> findByUserId(@Param("user_id") long userId, Pageable pageable);
}
