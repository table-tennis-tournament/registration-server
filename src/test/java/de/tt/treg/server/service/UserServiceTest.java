package de.tt.treg.server.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.tt.treg.server.controller.helper.DateHelper;
import de.tt.treg.server.domain.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/applicationContext.xml",
		"classpath:spring/hibernateTestContext.xml" })
public class UserServiceTest {

	@Autowired
	private UserService userService;

	@Test
	public void testGetUserById() {
		User user = userService.getUserById(1);
		assertEquals("arsch", user.getUserName());
		assertEquals("test", user.getPasswort());
	}

	@Test
	public void testInsertUser() {
		User testUser = getTestUser();
		userService.createNewUser(testUser);
		User userResult = userService.getUserById(testUser.getId());
		assertEquals(testUser.getId(), userResult.getId());
		assertEquals(testUser.getUserName(), userResult.getUserName());
		assertEquals(testUser.getPasswort(), userResult.getPasswort());
		assertEquals(DateHelper.getYearMonthAndDayFromDateAsString(testUser
				.getRegistrationDate()),
				DateHelper.getYearMonthAndDayFromDateAsString(userResult
						.getRegistrationDate()));
	}

	@Test
	public void testGetUserWithPasswortAndNicknamePositiv() {
		User passwortTestUser = getPasswortTestUser();
		User userResult = userService.getUserByNameAndPasswort(
				passwortTestUser.getUserName(), passwortTestUser.getPasswort());
		assertNotNull(userResult);
		assertEquals(34, userResult.getId());
		assertEquals(passwortTestUser.getUserName(), userResult.getUserName());
	}

	@Test
	public void testGetNoUserByWrongPasswort() {
		User passwortTestUser = getPasswortTestUser();
		User userResult = userService.getUserByNameAndPasswort(
				passwortTestUser.getUserName(), "");
		assertNull(userResult);
	}

	@Test
	public void testGetNoUserByWrongUserName() {
		User passwortTestUser = getPasswortTestUser();
		User userResult = userService.getUserByNameAndPasswort("",
				passwortTestUser.getPasswort());
		assertNull(userResult);
	}

	private User getTestUser() {
		return new User("hotz", "nue", new Date(System.currentTimeMillis()));
	}

	private User getPasswortTestUser() {
		return new User("Passwort", "geheim", new Date(
				System.currentTimeMillis()));
	}
}
