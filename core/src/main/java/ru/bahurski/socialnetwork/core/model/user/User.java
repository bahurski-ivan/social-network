package ru.bahurski.socialnetwork.core.model.user;

import ru.bahurski.socialnetwork.core.util.converter.hibernate.LocalDateAttributeConverter;
import ru.bahurski.socialnetwork.core.util.converter.hibernate.SexAttributeConverter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity(name = "USERS")
public class User implements Serializable {
    private static final long serialVersionUID = 3931814872085163347L;

    @Id
    @GeneratedValue
    @Column(name = "id", unique = true, nullable = false)
    private long id;

    @Column(name = "first_name", length = 40, nullable = false)
    private String firstName;

    @Column(name = "last_name", length = 40, nullable = false)
    private String lastName;

    @Column(name = "birth_date")
    @Convert(converter = LocalDateAttributeConverter.class)
    private LocalDate birthDate;

    @Column(name = "photo_id")
    private Long avatarPhotoId;

    @Column(name = "login", length = 40, unique = true, nullable = false)
    private String login;

    @Column(name = "password_md5", length = 32, nullable = false)
    private String passwordMd5;

    @Column(name = "sex")
    @Convert(converter = SexAttributeConverter.class)
    private Sex sex;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Long getAvatarPhotoId() {
        return avatarPhotoId;
    }

    public void setAvatarPhotoId(Long avatarPhotoId) {
        this.avatarPhotoId = avatarPhotoId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPasswordMd5() {
        return passwordMd5;
    }

    public void setPasswordMd5(String passwordMd5) {
        this.passwordMd5 = passwordMd5;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthDate=" + birthDate +
                ", avatarPhotoId=" + avatarPhotoId +
                ", login='" + login + '\'' +
                ", passwordMd5='" + passwordMd5 + '\'' +
                ", sex=" + sex +
                '}';
    }
}
