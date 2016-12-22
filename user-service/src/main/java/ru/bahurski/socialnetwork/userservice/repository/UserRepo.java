package ru.bahurski.socialnetwork.userservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.bahurski.socialnetwork.core.model.user.Sex;
import ru.bahurski.socialnetwork.core.model.user.User;

import java.time.LocalDate;
import java.util.Optional;

/**
 * jpa repo for user pojo
 */
public interface UserRepo extends JpaRepository<User, Long> {
    @Query(
            "SELECT u FROM USERS u WHERE " +
                    "(:first_name is null OR lower(u.firstName) LIKE CONCAT('%', :first_name, '%')) AND " +
                    "(:last_name is null OR lower(u.lastName) LIKE CONCAT('%', :last_name, '%')) AND " +
                    "(:use_date_in_select = 0 OR (u.birthDate BETWEEN :birth_date_from AND :birth_date_to)) AND " +
                    "(:sex is null OR u.sex is not null AND u.sex = :sex)"
    )
    Page<User> findByMultipleParameters(
            @Param("first_name") String firstName,
            @Param("last_name") String lastName,
            @Param("birth_date_from") LocalDate birthDateFrom,
            @Param("birth_date_to") LocalDate birthDateTo,
            @Param("sex") Sex sex,
            @Param("use_date_in_select") int useBirthDateInSelect,
            Pageable pageable
    );

    @Query(
            "UPDATE USERS u " +
                    "SET u.firstName = :first_name, " +
                    "u.lastName = :last_name, " +
                    "u.birthDate = :birth_date, " +
                    "u.avatarPhotoId = :photo_id, " +
                    "u.sex = :sex " +
                    "WHERE u.id = :user_id")
    @Modifying
    void updateUser(@Param("user_id") long userId,
                    @Param("first_name") String firstName,
                    @Param("last_name") String lastName,
                    @Param("birth_date") LocalDate birthDate,
                    @Param("sex") Sex sex,
                    @Param("photo_id") Long photoId);

    Optional<User> findByLoginAndPasswordMd5(String login, String passwordMd5);
}
