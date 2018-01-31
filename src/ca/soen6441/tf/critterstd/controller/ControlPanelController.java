/**
 * 
 */
package ca.soen6441.tf.critterstd.controller;

import ca.soen6441.tf.critterstd.model.TowerHelper;
import ca.soen6441.tf.critterstd.model.strategy.ShootingStrategy;
import ca.soen6441.tf.critterstd.model.tower.TowerInterface;
import ca.soen6441.tf.critterstd.view.TowerControlPanelView;

/**
 * This interface provide a control panel for Tower properties.
 * 
 *
 * @author Vishal
 *
 */
public class ControlPanelController {

	private static ControlPanelController instance;

	private TowerControlPanelView towerControlPanel = new TowerControlPanelView();

	private transient TowerInterface tower;

	private TowerHelper towerHelper = TowerHelper.getTowerHelper();

	private int rowIndex;
	private int colIndex;

	/**
	 * Default constructor
	 */
	private ControlPanelController() {
	}// end constructor

	/**
	 * Create Instance using Singleton Design Pattern
	 * 
	 * @return instance Instance of ControlPanelController class
	 * 
	 */
	public static ControlPanelController getTowerActionController() {
		if (instance == null)
			instance = new ControlPanelController();

		return instance;
	}

	/**
	 * Set the value of tower to display description on tower control panel.
	 * 
	 * @param command
	 *            value of tower type
	 */
	public void setTowerValueForDescription(String command) {
		tower = towerHelper.getTower(command);
		tower.setShootingStrategy(ShootingStrategy.SHOOT_CLOSEST);
		setTowerDescription(tower);
		GamePlayController.getGamePlayController().setTower(tower);

	}

	/**
	 * Call Methods for set Panel for Tower, Validating panel and Set Tower
	 * Details in Text Area.
	 * 
	 * @param tower
	 *            Instance of tower
	 */
	public void setTowerDescription(TowerInterface tower) {

		towerControlPanel.removePanel();
		towerControlPanel.setTowerValue(tower);
		towerControlPanel.setTowerDetailsTextArea();
		towerControlPanel.addComponentsToPanel(true);
		towerControlPanel.validatePanel();
	}// end set tower visible

	/**
	 * setTowerOperationDetails Set the tower on a map and their description
	 * 
	 * @param tower
	 *            Instance of tower
	 */
	public void setTowerOperationDetails(TowerInterface tower) {

		towerControlPanel.removePanel();
		towerControlPanel.setRowIndex(rowIndex);
		towerControlPanel.setColIndex(colIndex);
		towerControlPanel.setTowerValue(tower);
		towerControlPanel.setTowerOperationTextArea();
		towerControlPanel.addComponentsToPanel(false);
		towerControlPanel.validatePanel();
	} // end set tower detail

	/**
	 * @param rowIndex
	 *            the Row Value using parameter rowIndex
	 */
	public void setRowIndex(int rowIndex) {
		this.rowIndex = rowIndex;
	}

	/**
	 * @param colIndex
	 *            The Column Value using parameter colIndex
	 */
	public void setColIndex(int colIndex) {
		this.colIndex = colIndex;
	}

}