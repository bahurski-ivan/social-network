package ru.bahurski.socialnetwork.core.model.user;

/**
 * Created by Ivan on 21/12/2016.
 */
public class UserSearchQuery {
    private String firstName, lastName;
    private Sex sex;
    private Integer ageMin, ageMax;

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

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public Integer getAgeMin() {
        return ageMin;
    }

    public void setAgeMin(Integer ageMin) {
        this.ageMin = ageMin;
    }

    public Integer getAgeMax() {
        return ageMax;
    }

    public void setAgeMax(Integer ageMax) {
        this.ageMax = ageMax;
    }

    @Override
    public String toString() {
        return "UserSearchQuery{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", sex=" + sex +
                ", ageMin=" + ageMin +
                ", ageMax=" + ageMax +
                '}';
    }
}
