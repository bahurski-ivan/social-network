package ru.bahurski.socialnetwork.userservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import ru.bahurski.socialnetwork.core.model.user.User;
import ru.bahurski.socialnetwork.core.model.user.UserSearchQuery;
import ru.bahurski.socialnetwork.core.service.UserService;
import ru.bahurski.socialnetwork.userservice.repository.UserRepo;

import java.time.LocalDate;
import java.util.Optional;

/**
 * user ru.bahurski.socialnetwork.friendservice.service implementation
 */
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;
    private final PlatformTransactionManager transactionManager;

    @Autowired
    public UserServiceImpl(UserRepo userRepo, PlatformTransactionManager transactionManager) {
        this.userRepo = userRepo;
        this.transactionManager = transactionManager;
    }

    @Override
    public User create(User user) {
        final TransactionTemplate transaction = new TransactionTemplate(transactionManager);

        if (null == user.getFirstName() || null == user.getLastName())
            return null;

        user.setId(0);
        return transaction.execute(status -> userRepo.save(user));
    }

    @Override
    public User update(User user) {
        final TransactionTemplate transaction = new TransactionTemplate(transactionManager);

        if (null == user.getFirstName() || null == user.getLastName())
            return null;

        return transaction.execute(status -> {
            userRepo.updateUser(user.getId(),
                    user.getFirstName(),
                    user.getLastName(),
                    user.getBirthDate(),
                    user.getSex(),
                    user.getAvatarPhotoId());
            return userRepo.findOne(user.getId());
        });
    }

    @Override
    public Page<User> search(UserSearchQuery searchQuery, PageRequest pageRequest) {
        final TransactionTemplate transaction = new TransactionTemplate(transactionManager);
        final LocalDate birthDateMin, birthDateMax;
        boolean useDate = false;

        if (searchQuery.getAgeMin() != null) {
            LocalDate ld = LocalDate.now();
            birthDateMax = ld.minusYears(searchQuery.getAgeMin()).minusDays(1);
            useDate = true;
        } else birthDateMax = LocalDate.now().plusDays(1);

        if (searchQuery.getAgeMax() != null) {
            LocalDate ld = LocalDate.now();
            birthDateMin = ld.minusYears(searchQuery.getAgeMax() + 1).plusDays(1);
            useDate = true;
        } else birthDateMin = LocalDate.of(0, 1, 1);

        String firstName = searchQuery.getFirstName() == null ? null :
                searchQuery.getFirstName().toLowerCase().trim();

        String lastName = searchQuery.getLastName() == null ? null :
                searchQuery.getLastName().toLowerCase().trim();

        final boolean useD = useDate;

        return transaction.execute(status ->
                userRepo.findByMultipleParameters(
                        firstName,
                        lastName,
                        birthDateMin, birthDateMax,
                        searchQuery.getSex(),
                        (useD ? 1 : 0),
                        pageRequest
                )
        );
    }

    @Override
    public Optional<User> tryLogin(String login, String password) {
        final TransactionTemplate transaction = new TransactionTemplate(transactionManager);

        return transaction.execute(status ->
                userRepo.findByLoginAndPasswordMd5(login, password)
        );
    }
}
