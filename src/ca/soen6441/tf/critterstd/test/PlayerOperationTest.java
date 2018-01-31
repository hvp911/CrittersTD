/**
 * 
 */
package ca.soen6441.tf.critterstd.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ca.soen6441.tf.critterstd.model.Player;

/**
 * @author Farzana Alam
 *
 */
public class PlayerOperationTest {

	Player aPlayer;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		aPlayer = Player.getPlayer();
		aPlayer.setCurrency(1000);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		aPlayer = null;
	}

	/**
	 * Test method for
	 * {@link ca.soen6441.tf.critterstd.model.Player#increaseLevel()}.
	 */
	@Test
	public void testIncreaseLevel() {
		aPlayer.increaseLevel();
		assertEquals(1, aPlayer.getLevel());
	}

	/**
	 * Test method for
	 * {@link ca.soen6441.tf.critterstd.model.Player#getCurrency()}.
	 */
	@Test
	public void testGetCurrency() {
		assertEquals(1000, aPlayer.getCurrency(), 0);
	}

	/**
	 * Test method for
	 * {@link ca.soen6441.tf.critterstd.model.Player#setCurrency(double)}.
	 */
	@Test
	public void testSetCurrency() {
		aPlayer.setCurrency(500);
		assertEquals(500, aPlayer.getCurrency(), 0);
	}

	/**
	 * Test method for {@link ca.soen6441.tf.critterstd.model.Player#getLevel()}
	 * .
	 */
	@Test
	public void testGetLevel() {
		assertEquals(0, aPlayer.getLevel());
	}

	/**
	 * Test method for {@link ca.soen6441.tf.critterstd.model.Player#getLife()}.
	 */
	@Test
	public void testGetLife() {
		assertEquals(99, aPlayer.getLife());
	}

	/**
	 * Test method for
	 * {@link ca.soen6441.tf.critterstd.model.Player#reduceLife(int)}.
	 */
	@Test
	public void testReduceLife() {
		aPlayer.reduceLife(1);
		assertEquals(99, aPlayer.getLife());
	}

	/**
	 * 
	 */
	@Test
	public void testIncreasePoints() {
		int expected = aPlayer.getScore() + 15;
		aPlayer.increasePoints(15);
		assertEquals(expected, aPlayer.getScore());
	}

	@Test
	public void testGetScore() {
		int expected = aPlayer.getScore() + 15;
		aPlayer.increasePoints(15);
		assertEquals(expected, aPlayer.getScore());
	}

}
