package ru.bahurski.socialnetwork.core.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import ru.bahurski.socialnetwork.core.model.user.User;
import ru.bahurski.socialnetwork.core.model.user.UserSearchQuery;

import java.util.Optional;

/**
 * Created by Ivan on 21/12/2016.
 */
public interface UserService {
    User create(User user);

    User update(User user);

    Page<User> search(UserSearchQuery searchQuery, PageRequest pageRequest);

    Optional<User> tryLogin(String login, String password);
}
