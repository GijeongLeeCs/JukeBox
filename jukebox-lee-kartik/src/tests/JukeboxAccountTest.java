package tests;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.Test;

import model.JukeboxAccount;

class JukeboxAccountTest {

	@Test
	void testGetters() {
		JukeboxAccount aJBA = new JukeboxAccount("Name");
	}
	
	@Test
	public void testChangeOfDateWithAFewTimes() {
		JukeboxAccount user = new JukeboxAccount("Casey", "1111");
		assertEquals(0, user.songsSelectedToday());
		user.recordOneSelection();
		assertEquals(1, user.songsSelectedToday());
		user.recordOneSelection();
		assertTrue(user.canSelect());
		user.recordOneSelection();
		assertEquals(3, user.songsSelectedToday());
		assertFalse(user.canSelect());
		user.pretendItsTomorrow(); // Uses a LocalDate instance variable
		assertEquals(0, user.songsSelectedToday());
		user.recordOneSelection();
		user.recordOneSelection();
		assertTrue(user.canSelect());
		user.recordOneSelection();
		assertEquals(3, user.songsSelectedToday());
		assertFalse(user.canSelect());
	}

}
