package ru.bahurski.socialnetwork.userservice.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import ru.bahurski.socialnetwork.core.model.user.Sex;
import ru.bahurski.socialnetwork.core.model.user.User;
import ru.bahurski.socialnetwork.core.model.user.UserSearchQuery;
import ru.bahurski.socialnetwork.core.service.UserService;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

/**
 * Created by Ivan on 22/12/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestAppConfig.class, loader = AnnotationConfigContextLoader.class)
public class UserServiceImplTest {

    @Autowired
    UserService userService;

    @Test
    public void create() throws Exception {
        User user = new User();

        user.setFirstName("Ivan");
        user.setLastName("Bahurski");
        user.setLogin("login");
        user.setPasswordMd5("123123123123123123");

        User result = userService.create(user);

        Assert.assertEquals(user.getFirstName(), result.getFirstName());
        Assert.assertEquals(user.getLastName(), result.getLastName());
        Assert.assertNull(user.getBirthDate());
        Assert.assertNotEquals(user.getId(), 0);
        Assert.assertEquals(user.getLogin(), user.getLogin());
        Assert.assertEquals(user.getPasswordMd5(), user.getPasswordMd5());
        Assert.assertNull(user.getSex());
    }

    @Test
    public void update() throws Exception {
        User user = new User();
        user.setFirstName("Ivan");
        user.setLastName("Bahurski");
        user.setLogin("new_login");
        user.setPasswordMd5("123123123123123123");

        User result = userService.create(user);

        user.setLastName("Bakurski");
        user.setBirthDate(LocalDate.of(1995, 5, 17));
        user.setSex(Sex.MALE);

        User u = userService.update(user);

        Assert.assertNotNull(u);
        Assert.assertEquals(u.getLastName(), user.getLastName());
        Assert.assertEquals(u.getBirthDate(), user.getBirthDate());
        Assert.assertEquals(u.getSex(), user.getSex());
    }

    @Test
    public void searchTestFirstNameExist() throws Exception {
        User user = new User();
        user.setFirstName("Ivan");
        user.setLastName("Bahurski");
        user.setLogin("new_login3");
        user.setPasswordMd5("123123123123123123");
        User result = userService.create(user);

        UserSearchQuery query = new UserSearchQuery();

        query.setFirstName("Ivan");

        Page<User> page = userService.search(query, new PageRequest(0, 100));

        Assert.assertNotEquals(page.getTotalElements(), 0);
        Assert.assertTrue(page.getContent().stream().anyMatch(u -> user.getId() == u.getId()));
        Assert.assertTrue(page.getContent().stream().allMatch(u -> query.getFirstName().equals(u.getFirstName())));
    }

    @Test
    public void searchTestPartialFirstNameExist() throws Exception {
        User user = new User();
        user.setFirstName("Ivan");
        user.setLastName("Bahurski");
        user.setLogin("new_login4");
        user.setPasswordMd5("123123123123123123");
        User result = userService.create(user);

        UserSearchQuery query = new UserSearchQuery();

        query.setFirstName("Iva");

        Page<User> page = userService.search(query, new PageRequest(0, 100));

        Assert.assertNotEquals(page.getTotalElements(), 0);
        Assert.assertTrue(page.getContent().stream().anyMatch(u -> user.getId() == u.getId()));
        Assert.assertTrue(page.getContent().stream().allMatch(u -> u.getFirstName().startsWith(query.getFirstName())));
    }

    @Test
    public void searchTestSex() throws Exception {
        User user = new User();
        user.setFirstName("Ivan");
        user.setLastName("Bahurski");
        user.setLogin("new_login5");
        user.setPasswordMd5("123123123123123123");
        user.setSex(Sex.MALE);
        User result = userService.create(user);

        UserSearchQuery query = new UserSearchQuery();

        query.setSex(Sex.MALE);

        Page<User> page = userService.search(query, new PageRequest(0, 100));

        Assert.assertNotEquals(page.getTotalElements(), 0);
        Assert.assertTrue(page.getContent().stream().anyMatch(u -> user.getId() == u.getId()));
        Assert.assertTrue(page.getContent().stream().allMatch(u -> u.getSex() == query.getSex()));
    }

    @Test
    public void searchTestFull() throws Exception {
        User user = new User();
        user.setFirstName("Ivan");
        user.setLastName("Bahurski");
        user.setLogin("qweqweqweqweqwe--");
        user.setPasswordMd5("123123123123123123");
        user.setSex(Sex.MALE);
        user.setBirthDate(LocalDate.of(1995, 5, 17));

        User result = userService.create(user);

        User otherUser = new User();
        otherUser.setFirstName("user2firstName");
        otherUser.setLastName("user2lastName");
        otherUser.setLogin("45456456345");
        otherUser.setPasswordMd5("123123123123123123");
        otherUser.setBirthDate(LocalDate.now());
        otherUser.setSex(Sex.FEMALE);

        User newUser = userService.create(otherUser);

        UserSearchQuery query = new UserSearchQuery();

        query.setFirstName("Ivan");
        query.setLastName("Bahurski");
        query.setSex(Sex.MALE);
        query.setAgeMax(21);
        query.setAgeMin(21);

        Page<User> page = userService.search(query, new PageRequest(0, 100));

        Assert.assertNotEquals(page.getTotalElements(), 0);

        Optional<User> rrr = page.getContent().stream().filter(u -> u.getId() == user.getId())
                .findFirst();

        Assert.assertTrue(rrr.isPresent());

        User rr = rrr.get();

        Assert.assertEquals(rr.getId(), user.getId());
        Assert.assertEquals(rr.getFirstName(), user.getFirstName());
        Assert.assertEquals(rr.getLastName(), user.getLastName());
        Assert.assertEquals(rr.getBirthDate(), user.getBirthDate());
        Assert.assertEquals(rr.getSex(), user.getSex());
        Assert.assertEquals(rr.getLogin(), user.getLogin());
        Assert.assertEquals(rr.getPasswordMd5(), user.getPasswordMd5());
    }

    @Test
    public void searchByYear() throws Exception {
        User user = new User();
        user.setFirstName("Ivan");
        user.setLastName("Bahurski");
        user.setLogin("www");
        user.setPasswordMd5("123123123123123123");
        user.setSex(Sex.MALE);
        user.setBirthDate(LocalDate.of(1995, 5, 17));
        User result = userService.create(user);

        UserSearchQuery query = new UserSearchQuery();

        query.setAgeMin(21);

        Page<User> page = userService.search(query, new PageRequest(0, 100));

        Assert.assertNotEquals(page.getTotalElements(), 0);
        Assert.assertTrue(page.getContent().stream().anyMatch(u -> u.getId() == user.getId()));
        Assert.assertTrue(page.getContent().stream().allMatch(u -> {
            LocalDate fromDateTime = u.getBirthDate();
            LocalDate toDateTime = LocalDate.now();

            LocalDate tempDateTime = LocalDate.from(fromDateTime);

            long years = tempDateTime.until(toDateTime, ChronoUnit.YEARS);
            tempDateTime = tempDateTime.plusYears(years);

            long months = tempDateTime.until(toDateTime, ChronoUnit.MONTHS);
            tempDateTime = tempDateTime.plusMonths(months);

            long days = tempDateTime.until(toDateTime, ChronoUnit.DAYS);
            tempDateTime = tempDateTime.plusDays(days);

            return years >= 21;
        }));
    }
}