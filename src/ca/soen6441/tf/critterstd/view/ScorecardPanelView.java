/**
 * 
 */
package ca.soen6441.tf.critterstd.view;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;
import ca.soen6441.tf.critterstd.controller.GUIController;
import ca.soen6441.tf.critterstd.controller.GamePlayController;
import ca.soen6441.tf.critterstd.model.ModelObservable;
import ca.soen6441.tf.critterstd.model.Player;

/**
 * @author Farzana Alam
 *
 */
public class ScorecardPanelView extends ViewObserver {

	// Scorecard Panel from the Main Container
	private JPanel scoreCardPanel = MainContainerView.getMainContainer()
			.getScorecardPanel();
	private Player player;
	private MainContainerView mainContainer = MainContainerView
			.getMainContainer();

	// Text area to show Bank currency remaining from the Models
	private JLabel remainingCurrenyTextArea;

	/*
	 * Text area to show on which level the critters & player are from then
	 * Models
	 */
	private JLabel currentLevelTextArea;

	// Text area to show remaining lives from the Models
	private JLabel remainingLivesTextArea;

	// Text area to show score from the Models
	private JLabel scoreTextArea;

	// Send Wave button
	private JButton sendWaveButton = new JButton("Send Wave");
	// Game save button
	private JButton gameSaveButton = new JButton("Save Game");
	// View the current game logs
	private JButton viewLogButton = new JButton("Events Logs");

	// For creating Labels
	private JPanel aPanel;
	private JLabel aLabel;
	protected JTextField aTextField;

	private static ScorecardPanelView instance = null;

	private ScorecardPanelView(ModelObservable aPlayer) {
		super();
		player = (Player) aPlayer;
		player.attach(this);
		remainingCurrenyTextArea = new JLabel(player.getCurrency() + " $");
		currentLevelTextArea = new JLabel(player.getLevel() + "");
		remainingLivesTextArea = new JLabel(player.getLife() + "");
		scoreTextArea = new JLabel(player.getScore() + "");
		addComponentsToPanel();
	} // end constructor

	public static ScorecardPanelView getScorecardPanel(ModelObservable player) {
		if (instance == null)
			return new ScorecardPanelView(player);

		return instance;
	}

	/**
	 * 
	 */
	private void addComponentsToPanel() {
		remainingCurrenyTextArea
				.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

		currentLevelTextArea
				.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

		remainingLivesTextArea
				.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		scoreTextArea
				.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

		// Setting up Application title and score area
		aPanel = new JPanel(new MigLayout("fill"));
		aLabel = new JLabel();
		aLabel.setText("Critters TD");
		aPanel.add(aLabel, "growx, wrap");

		aLabel = new JLabel("Score: ");
		aPanel.add(aLabel, "grow");
		aPanel.add(scoreTextArea, "grow");

		aPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1,
				true));
		scoreCardPanel.add(aPanel, "grow");

		JPanel tmpPanel = new JPanel(new MigLayout("fillx"));
		tmpPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1,
				true));

		// Setting up Bank & Currency remaining at the text area
		aPanel = new JPanel();
		aLabel = new JLabel();
		aLabel.setText("Bank: ");
		aPanel.add(aLabel);
		aPanel.add(remainingCurrenyTextArea);
		aPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1,
				true));
		tmpPanel.add(aPanel, "growx, wrap");

		// Setting up Levels at the text area
		aPanel = new JPanel();
		aLabel = new JLabel();
		aLabel.setText("Level (/30):");
		aPanel.add(aLabel);
		aPanel.add(currentLevelTextArea);
		aPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1,
				true));
		tmpPanel.add(aPanel, "growx, wrap");

		// Setting up Lives remaining at the text area
		aPanel = new JPanel();
		aLabel = new JLabel();
		aLabel.setText("Lives: ");
		aPanel.add(aLabel);
		aPanel.add(remainingLivesTextArea);
		aPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1,
				true));
		tmpPanel.add(aPanel, "growx");

		scoreCardPanel.add(tmpPanel, "grow");

		// Setting up view log button
		viewLogButton.setBackground(new Color(194, 252, 155));

		viewLogButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				new LogView(false);
			}
		});

		scoreCardPanel.add(viewLogButton, "grow");

		// Setting up Game Save button
		gameSaveButton.setBackground(new Color(4, 201, 192));

		gameSaveButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				String fileName = getNewFileName();

				if (fileName != null) {
					GUIController.getGUIController().saveGame(fileName);
				} // end if
			}
		});

		scoreCardPanel.add(gameSaveButton, "grow");

		// Setting up Send Wave button
		sendWaveButton.setBackground(new Color(153, 153, 255).brighter());

		sendWaveButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				GamePlayController.getGamePlayController().sendWave();
			}
		});

		scoreCardPanel.add(sendWaveButton, "grow");

	} // end addComponentsToPanel

	/*
	 * (non-Javadoc)
	 * 
	 * @see ca.soen6441.tf.critterstd.view.ViewObserver#update()
	 */
	@Override
	public void update() {
		player = (Player) player.getState();
		this.remainingCurrenyTextArea.setText(String.valueOf(player
				.getCurrency()));
		this.currentLevelTextArea.setText(String.valueOf(player.getLevel()));
		this.remainingLivesTextArea.setText(String.valueOf(player.getLife()));
		this.scoreTextArea.setText(String.valueOf(player.getScore()));
	}

	private String getNewFileName() {
		final JDialog aDialog = new JDialog(mainContainer, "File Name:", true);
		aPanel = new JPanel();
		final JTextField aTextField1 = new JTextField(10);
		aTextField1.setText("game_");
		JButton aButton = new JButton("OK");
		aDialog.getRootPane().setDefaultButton(aButton);

		aPanel.add(aTextField1);
		aButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				aTextField = new JTextField();
				aTextField.setText(aTextField1.getText());
				aDialog.dispose();
			}
		});
		aPanel.add(aButton);

		aDialog.add(aPanel);
		aDialog.pack();
		aDialog.setVisible(true);

		if (aTextField == null || aTextField.getText().equalsIgnoreCase(""))
			return null;

		return aTextField.getText();
	}
}
