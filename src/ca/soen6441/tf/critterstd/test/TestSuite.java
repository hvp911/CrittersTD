package ca.soen6441.tf.critterstd.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Test suite for running all the unit tests for map features
 * 
 * @author Farazana
 *
 */
@RunWith(Suite.class)
@SuiteClasses({ MapValidationTest.class, MapOperationsTest.class,
		MapSaveUtilityTest.class, TestTowerOperationOnMap.class,
		TowerTestCase.class, PlayerOperationTest.class,
		CritterOperationTest.class, TestTowerStrategy.class,
		CellOperationTest.class })
public class TestSuite {

}