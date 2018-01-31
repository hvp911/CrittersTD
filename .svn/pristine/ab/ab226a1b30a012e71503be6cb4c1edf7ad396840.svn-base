package ca.soen6441.tf.critterstd.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ca.soen6441.tf.critterstd.model.Map;
import ca.soen6441.tf.critterstd.model.Player;
import ca.soen6441.tf.critterstd.model.TowerHelper;
import ca.soen6441.tf.critterstd.model.cell.CellInterface;
import ca.soen6441.tf.critterstd.model.strategy.ShootingStrategy;
import ca.soen6441.tf.critterstd.model.tower.FireEffect;
import ca.soen6441.tf.critterstd.model.tower.Tower;
import ca.soen6441.tf.critterstd.model.tower.TowerFactory;

/**
 * Test cases for tower's property and related operations
 * 
 * @author v_dhaduk
 *
 */
public class TowerTestCase {

	private Tower tower1;
	private Tower tower2;
	private CellInterface aSceneryCell;
	private TowerFactory towerFactory = TowerFactory.getTowerFactory();
	private TowerHelper towerHelper = TowerHelper.getTowerHelper();
	private Player player = Player.getPlayer();

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		tower1 = towerFactory.getTower("tower1");
		tower2 = towerFactory.getTower("tower1");
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		tower1 = null;
	}

	@Test
	public void testTowerUpgrade() {

		tower2.upgradeTower();

		assertTrue((tower1.getDamageCapability() + (tower1
				.getDamageCapability() * 0.36)) == tower2.getDamageCapability());

		assertTrue((int) (tower1.getRange() + (tower1.getRange() * 0.04)) == tower2
				.getRange());

	}

	@Test
	public void testTowerSell() {
		tower2 = towerFactory.getTower("tower1");
		towerHelper.sellTower(tower2, player);

		assertTrue((tower2.getRefundValue() + player.getCurrency() == (tower1
				.getBuyingCost() * 0.75) + player.getCurrency()));
	}

	@Test
	public void testTowerPlaceOnCell() {
		aSceneryCell = Map.getMap().getCells().get(0).get(0);
		aSceneryCell.setElementOnTheCell(tower1);
		tower1.setX(aSceneryCell.getX() + 13);
		tower1.setY(aSceneryCell.getY() + 13);

		assertEquals(13, tower1.getX());
		assertEquals(13, tower1.getY());

	}

	@Test
	public void testGetSpecialFire() {
		assertEquals(FireEffect.BURN, tower1.getSpecialFire());
	}

	@Test
	public void testSetShootingStrategy() {
		tower1.setShootingStrategy(ShootingStrategy.SHOOT_EXITING);
		assertEquals(ShootingStrategy.SHOOT_EXITING,
				tower1.getShootingStrategy());
	}

	@Test
	public void testGetShootingStrategy() {
		tower1.setShootingStrategy(ShootingStrategy.SHOOT_EXITING);
		assertEquals(ShootingStrategy.SHOOT_EXITING,
				tower1.getShootingStrategy());

	}

	@Test
	public void testGetFireLength() {
		assertEquals(8, tower1.getFireLength());
	}

	@Test
	public void testSetSold() {
		tower1.setSold(true);
		assertTrue(tower1.isSold());
	}

	@Test
	public void testIsSold() {
		tower1.setSold(true);
		assertTrue(tower1.isSold());
	}

	@Test
	public void testGetCurrentValue() {
		tower1.upgradeTower();
		assertTrue(tower1.getCurrentValue() == (tower2.getCurrentValue() + tower2
				.getBuyingCost() * 0.4));
	}

	@Test
	public void testGetRefundValue() {
		assertTrue(tower1.getRefundValue() == tower1.getBuyingCost() * 0.75);
	}

	@Test
	public void testIsShooting() {
		tower1.setShooting(true);
		assertTrue(tower1.isShooting());
	}

	@Test
	public void testIsFiringSpecial() {
		tower1.setShooting(true);
		assertTrue(!tower1.isFiringSpecial());
	}
}
