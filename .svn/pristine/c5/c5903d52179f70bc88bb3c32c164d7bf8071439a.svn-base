/**
 * 
 */
package ca.soen6441.tf.critterstd.view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import ca.soen6441.tf.critterstd.controller.ControlPanelController;
import ca.soen6441.tf.critterstd.model.Player;
import ca.soen6441.tf.critterstd.model.tower.TowerFactory;
import ca.soen6441.tf.critterstd.model.tower.TowerInterface;

/**
 * Shows the control panel view of the game play panel
 * 
 * @author Farzana Alam
 *
 */
public class ControlPanelView extends ViewObserver {

	// Control Panel from the Main Container
	private JPanel controlPanel = MainContainerView.getMainContainer()
			.getControlPanel();

	private String towerOneImageFileName = "Tower1.png";
	private String towerTwoImageFileName = "Tower2.png";
	private String towerThreeImageFileName = "Tower3.png";

	// Local usage
	private JLabel aLabel;
	private JScrollPane aScrollPane;

	// Tower buttons
	private JButton towerOneButton = new JButton();
	private JButton towerTwoButton = new JButton();
	private JButton towerThreeButton = new JButton();

	// Critter details text area
	private JTextArea currentCritterDetailsTextArea = new JTextArea(
			"Current critter details not found");
	private JTextArea nextCritterDetailsTextArea = new JTextArea(
			"Waiting for First Critter Wave");

	// get instance of tower control panel
	private JPanel towerControlPanel = MainContainerView.getMainContainer()
			.getTowerControlPanel();
	private static Player player = Player.getPlayer();
	// Instance of ControlPanelView
	private static ControlPanelView instance;

	// Instance Of Towers 
	private TowerInterface tower1 = TowerFactory.getTowerFactory().getTower("tower1");
	private TowerInterface tower2 = TowerFactory.getTowerFactory().getTower("tower2");
	private TowerInterface tower3 = TowerFactory.getTowerFactory().getTower("tower3");
	/**
	 * default constructor
	 */
	private ControlPanelView() {
		super();
		player.attach(this);
		addComponentsToPanel();
	} // end constructor

	/**
	 * @return singleton object
	 */
	public static ControlPanelView getControlPanelView() {
		if (instance == null)
			instance = new ControlPanelView();

		return instance;

	} // end getInstance

	private void addComponentsToPanel() {

		// setting Tower label
		aLabel = new JLabel("Towers", SwingConstants.CENTER);
		aLabel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
		controlPanel.add(aLabel, "growx, wrap, span");

		// Preparing Tower buttons
		String pathToTheImages = System.getProperty("user.dir")
				+ File.separator + "resources" + File.separator + "Images"
				+ File.separator;

		Icon towerOneIcon = new ImageIcon(pathToTheImages
				+ towerOneImageFileName);
		Icon towerTwoIcon = new ImageIcon(pathToTheImages
				+ towerTwoImageFileName);
		Icon towerThreeIcon = new ImageIcon(pathToTheImages
				+ towerThreeImageFileName);

		towerOneButton.setIcon(towerOneIcon);
		towerTwoButton.setIcon(towerTwoIcon);
		towerThreeButton.setIcon(towerThreeIcon);

		towerOneButton.setBackground(Color.WHITE);
		towerTwoButton.setBackground(Color.WHITE);
		towerThreeButton.setBackground(Color.WHITE);

		towerOneButton.setActionCommand("tower1");
		towerTwoButton.setActionCommand("tower2");
		towerThreeButton.setActionCommand("tower3");

		if (player.getCurrency() < tower1.getBuyingCost()) {
			towerOneButton.setEnabled(false);
			towerTwoButton.setEnabled(false);
			towerThreeButton.setEnabled(false);
		} else if (player.getCurrency() < tower2.getBuyingCost()) {
			towerOneButton.setEnabled(true);
			towerTwoButton.setEnabled(false);
			towerThreeButton.setEnabled(false);
		} else if (player.getCurrency() < tower3.getBuyingCost()) {
			towerOneButton.setEnabled(true);
			towerTwoButton.setEnabled(true);
			towerThreeButton.setEnabled(false);
		} else {
			towerOneButton.setEnabled(true);
			towerTwoButton.setEnabled(true);
			towerThreeButton.setEnabled(true);
		}

		towerOneButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ControlPanelController.getTowerActionController()
						.setTowerValueForDescription("tower1");
			}
		});

		towerTwoButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ControlPanelController.getTowerActionController()
						.setTowerValueForDescription("tower2");
			}
		});

		towerThreeButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ControlPanelController.getTowerActionController()
						.setTowerValueForDescription("tower3");
			}
		});

		// Adding tower buttons to the panel
		controlPanel.add(towerOneButton, "center");
		controlPanel.add(towerTwoButton, "center");
		controlPanel.add(towerThreeButton, "wrap, center");

		// adding labels at the bottom of the buttons
		aLabel = new JLabel("Tower 1 ( "+Double.toString(tower1.getBuyingCost())+" )", SwingConstants.CENTER);
		aLabel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
		controlPanel.add(aLabel, "growx");

		aLabel = new JLabel("Tower 2 ( "+Double.toString(tower2.getBuyingCost())+" )", SwingConstants.CENTER);
		aLabel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
		controlPanel.add(aLabel, "growx");

		aLabel = new JLabel("Tower 3 ( "+Double.toString(tower3.getBuyingCost())+" )", SwingConstants.CENTER);
		aLabel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
		controlPanel.add(aLabel, "growx, wrap");

		// adding a seperate tower control panel
		Border border = BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1,
				true);
		towerControlPanel.setBorder(border);
		controlPanel.add(towerControlPanel, "growx, span");

		// adding critters details components
		aLabel = new JLabel("Critter Details", SwingConstants.CENTER);
		aLabel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
		controlPanel.add(aLabel, "growx, span");

		// Current critter details
		aLabel = new JLabel("Current: ");
		aLabel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
		controlPanel.add(aLabel, "growx");

		currentCritterDetailsTextArea.setEditable(false);
		currentCritterDetailsTextArea.setLineWrap(true);
		currentCritterDetailsTextArea.setWrapStyleWord(true);
		aScrollPane = new JScrollPane(currentCritterDetailsTextArea,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		controlPanel.add(aScrollPane, "grow, span, wrap");

		// Next critter details
		aLabel = new JLabel("Next: ");
		aLabel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
		controlPanel.add(aLabel, "growx");

		nextCritterDetailsTextArea.setEditable(false);
		nextCritterDetailsTextArea.setLineWrap(true);
		nextCritterDetailsTextArea.setWrapStyleWord(true);
		aScrollPane = new JScrollPane(nextCritterDetailsTextArea,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		controlPanel.add(aScrollPane, "grow, span, wrap");
	}// end addComponentsToPanel

	/**
	 * @param currentCritterDetails
	 *            description of critters
	 * @param nextCritterDetails
	 *            description of critters
	 */
	public void addCritterDetails(String currentCritterDetails,
			String nextCritterDetails) {
		currentCritterDetailsTextArea.setText(currentCritterDetails);
		currentCritterDetailsTextArea.repaint();
		nextCritterDetailsTextArea.setText(nextCritterDetails);
		nextCritterDetailsTextArea.repaint();

	}

	@Override
	public void update() {
		controlPanel.removeAll();
		addComponentsToPanel();
		controlPanel.revalidate();
		controlPanel.repaint();
	}

}
