/**
 * 
 */
package ca.soen6441.tf.critterstd.view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import ca.soen6441.tf.critterstd.controller.ControlPanelController;
import ca.soen6441.tf.critterstd.controller.GamePlayController;
import ca.soen6441.tf.critterstd.controller.TowerPanelController;
import ca.soen6441.tf.critterstd.model.ModelObservable;
import ca.soen6441.tf.critterstd.model.Player;
import ca.soen6441.tf.critterstd.model.TowerHelper;
import ca.soen6441.tf.critterstd.model.strategy.ShootingStrategy;
import ca.soen6441.tf.critterstd.model.tower.TowerInterface;
import ca.soen6441.tf.critterstd.utility.GameEvent;

/**
 * This class is separation for tower's operation from control panel view. It
 * displays towers, related properties and allows operation for sell, upgrade
 * etc.
 *
 * @author Vishal
 *
 */
public class TowerControlPanelView extends ViewObserver {

	// Control Panel from the Main Container
	private JPanel towerPanel = MainContainerView.getMainContainer()
			.getTowerControlPanel();

	// Instance of TowerControlPanelView
	private static TowerControlPanelView instance;
	private static Player player = Player.getPlayer();
	private transient static TowerInterface tower;
	// Local usage
	private JLabel aLabel;
	private JScrollPane aScrollPane;
	private JButton aButton;
	// Tower details text area
	private JTextArea towerDetailsTextArea = new JTextArea();

	// Tower details text area
	private JTextArea towerOperationTextArea = new JTextArea();
	private boolean towerDetailPanelVisible = true;

	private TowerHelper towerHelper = TowerHelper.getTowerHelper();

	int rowIndex;
	int colIndex;

	/**
	 * Default constructor
	 */
	public TowerControlPanelView() {
		super();
		towerHelper.attach(this);
		addComponentsToPanel(towerDetailPanelVisible);
	} // end constructor

	/**
	 * @param observableTower
	 *            subject to observe
	 * @return this tower control panle view
	 */
	public synchronized static TowerControlPanelView getInstance(
			ModelObservable observableTower) {
		model = observableTower;
		tower = (TowerInterface) model;

		if (instance == null)
			instance = new TowerControlPanelView();

		return instance;

	} // end getInstance

	/**
	 * This method adds control to the panels.
	 * 
	 * @param towerDetailPanelVisible
	 *            flag
	 */
	public void addComponentsToPanel(boolean towerDetailPanelVisible) {

		if (towerDetailPanelVisible) {
			towerDetailPanel();
		} else {
			towerOperationPanel();
		}
	} // end addComponentsToPanel

	/**
	 * Tower Detail Panel for Each Tower Placed On the Map and Tower Buttons
	 */
	private void towerDetailPanel() {
		// 1. To display towers Images.
		aLabel = new JLabel();
		if (tower == null)
			aLabel.setText("");
		else
			aLabel.setText(tower.getTowerName());

		aLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
		towerPanel.add(aLabel, "growx, wrap");

		// 2. Text area to display tower's property.
		towerDetailsTextArea.setEditable(false);
		towerDetailsTextArea.setLineWrap(true);
		towerDetailsTextArea.setWrapStyleWord(true);
		aScrollPane = new JScrollPane(towerDetailsTextArea,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		towerPanel.add(aScrollPane, "growx, spanx, wrap,width 250");

	}// end add tower details

	/**
	 * Tower Operation Panel for Tower Operations on The Tower on the Map
	 */
	private void towerOperationPanel() {

		// 1. Display image and name of tower.
		aLabel = new JLabel();
		if (tower == null)
			aLabel.setText("");
		else
			aLabel.setText(tower.getTowerName());

		aLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
		towerPanel.add(aLabel, "growx, span");

		// 2. Show tower's properties.
		towerOperationTextArea.setEditable(false);
		towerOperationTextArea.setLineWrap(true);
		towerOperationTextArea.setWrapStyleWord(true);
		aScrollPane = new JScrollPane(towerOperationTextArea,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		towerPanel.add(aScrollPane, "growx, span, width 250");

		// 3. Upgrade tower button.
		aButton = new JButton();
		aButton.setText("Upgrade :" + tower.getBuyingCost() * 0.5);
		// Disable the button if currency is not enough.
		if (player.getCurrency() < tower.getBuyingCost() * 0.5
				|| tower.getLevel() >= 10) {
			aButton.setEnabled(false);
		}

		aLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
		aButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Action listner for upgrade tower button.
				if (!tower.isSold()) {
					TowerPanelController.getTowerActionController()
							.upgradeTower(tower, player);
					ControlPanelController.getTowerActionController()
							.setTowerOperationDetails(tower);

				}
			}
		});
		towerPanel.add(aButton, "growx, wrap, span");

		// 4. Sell tower button.
		aButton = new JButton();
		aButton.setText("Sell :" + tower.getCurrentValue() * 0.75);
		aLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
		aButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Action listner for sell tower button.
				if (!tower.isSold()) {
					TowerPanelController.getTowerActionController().sellTower(
							rowIndex, colIndex, tower, player);
					tower.setSold(true);
					removePanel();
				}
			}
		});
		towerPanel.add(aButton, "growx, wrap, span");

		// 5. Radio button to select the strategy for shooting.
		ButtonGroup strategyButtons = new ButtonGroup();
		JRadioButton closestCritterButton = new JRadioButton("Closest Critter");
		JRadioButton weakestCritterButton = new JRadioButton("Weakest Critter");
		JRadioButton strongestCritterButton = new JRadioButton(
				"Strongest Critter");
		JRadioButton exitingCritterButton = new JRadioButton("Exiting Critter");

		switch (tower.getShootingStrategy()) {
		case SHOOT_EXITING:
			exitingCritterButton.setSelected(true);
			break;
		case SHOOT_STRONGEST:
			strongestCritterButton.setSelected(true);
			break;
		case SHOOT_WEAKEST:
			weakestCritterButton.setSelected(true);
			break;
		default:
			closestCritterButton.setSelected(true);
			break;

		}

		strategyButtons.add(closestCritterButton);
		strategyButtons.add(weakestCritterButton);
		strategyButtons.add(strongestCritterButton);
		strategyButtons.add(exitingCritterButton);

		closestCritterButton.addActionListener(new ActionListener() {
			// Action listener to select and apply new strategy.
			@Override
			public void actionPerformed(ActionEvent arg0) {
				tower.setShootingStrategy(ShootingStrategy.SHOOT_CLOSEST);

				// LOG: Event
				new GameEvent("Tower number: " + tower.getSerial()
						+ " is set with strategy:"
						+ tower.getShootingStrategy() + ".", "towers", "tower"
						+ tower.getSerial(), "wave"
						+ GamePlayController.getGamePlayController()
								.getWaveNumber());
			}
		});
		weakestCritterButton.addActionListener(new ActionListener() {
			// Action listener to select and apply new strategy.
			@Override
			public void actionPerformed(ActionEvent arg0) {
				tower.setShootingStrategy(ShootingStrategy.SHOOT_WEAKEST);

				// LOG: Event
				new GameEvent("Tower number: " + tower.getSerial()
						+ " is set with strategy:"
						+ tower.getShootingStrategy() + ".", "towers", "tower"
						+ tower.getSerial(), "wave"
						+ GamePlayController.getGamePlayController()
								.getWaveNumber());
			}
		});
		strongestCritterButton.addActionListener(new ActionListener() {
			// Action listener to select and apply new strategy.
			@Override
			public void actionPerformed(ActionEvent arg0) {
				tower.setShootingStrategy(ShootingStrategy.SHOOT_STRONGEST);

				// LOG: Event
				new GameEvent("Tower number: " + tower.getSerial()
						+ " is set with strategy:"
						+ tower.getShootingStrategy() + ".", "towers", "tower"
						+ tower.getSerial(), "wave"
						+ GamePlayController.getGamePlayController()
								.getWaveNumber());
			}
		});
		exitingCritterButton.addActionListener(new ActionListener() {
			// Action listener to select and apply new strategy.
			@Override
			public void actionPerformed(ActionEvent arg0) {
				tower.setShootingStrategy(ShootingStrategy.SHOOT_EXITING);

				// LOG: Event
				new GameEvent("Tower number: " + tower.getSerial()
						+ " is set with strategy:"
						+ tower.getShootingStrategy() + ".", "towers", "tower"
						+ tower.getSerial(), "wave"
						+ GamePlayController.getGamePlayController()
								.getWaveNumber());
			}
		});

		towerPanel.add(closestCritterButton);
		towerPanel.add(weakestCritterButton);
		towerPanel.add(strongestCritterButton);
		towerPanel.add(exitingCritterButton);

	}

	/**
	 * We will pass the object of tower here. so we can bind the value for
	 * property.Currently, I am just setting the static values.
	 */
	public void setTowerDetailsTextArea() {

		String towerDescription = "Tower             : " + tower.getTowerName()
				+ "\n" + "Description     : " + tower.getDescription() + "\n"
				+ "Cost            : " + tower.getBuyingCost() + "\n"
				+ "Damage Capacity : " + tower.getDamageCapability() + "\n"
				+ "Range           : " + tower.getRange() + "\n"
				+ "Special Shot    : " + tower.getSpecialFire().toString();

		towerDetailsTextArea.setText(towerDescription);
		// towerDetailsTextArea.setText("");
	}

	/**
	 * Set the details for tower to display.
	 */
	public void setTowerOperationTextArea() {
		towerOperationTextArea.setText("Tower Serial : " + tower.getSerial()
				+ "\n" + "Current Characteristics :" + " Damage : "
				+ tower.getDamageCapability() + "\n");
		towerOperationTextArea.append("Range : " + tower.getRange() + "\n");
		towerOperationTextArea.append("Level : " + tower.getLevel() + "\n");
		towerOperationTextArea.append("\n");
		towerOperationTextArea.append("After Upgrade \n");
		towerOperationTextArea.append("Damage : "
				+ (tower.getDamageCapability() + 0.45 * tower
						.getDamageCapability()) + "\n");
		towerOperationTextArea.append("Range : "
				+ (tower.getRange() + tower.getRange() * 0.5) + "\n");
		towerOperationTextArea.append("Level : " + (tower.getLevel() + 1));
	}

	/**
	 * Remove the panel from controlPanel view.
	 */
	public void removePanel() {
		towerPanel.removeAll();
	}

	/**
	 * Set the value of tower
	 * 
	 * @param value
	 *            tower object
	 */
	public void setTowerValue(TowerInterface value) {
		tower = value;
	}

	/**
	 * Validate thw tower to update this panel.
	 */
	public void validatePanel() {
		towerPanel.revalidate();
		towerPanel.repaint();
	}

	/**
	 * @param rowIndex
	 *            row index
	 */
	public void setRowIndex(int rowIndex) {
		this.rowIndex = rowIndex;
	}

	/**
	 * @param colIndex
	 *            column index
	 */
	public void setColIndex(int colIndex) {
		this.colIndex = colIndex;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ca.soen6441.tf.critterstd.view.ViewObserver#update()
	 */
	@Override
	public void update() {
		removePanel();
		// set tower value
		towerHelper = (TowerHelper) TowerHelper.getTowerHelper().getState();
		setTowerValue(towerHelper.getTower());
		towerOperationPanel();
		setTowerOperationTextArea();
		validatePanel();
	}

}
