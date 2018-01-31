/**
 * 
 */
package ca.soen6441.tf.critterstd.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

import ca.soen6441.tf.critterstd.controller.GUIController;
import ca.soen6441.tf.critterstd.utility.GameEvent;
import ca.soen6441.tf.critterstd.utility.MapEvent;
import net.miginfocom.swing.MigLayout;

/**
 * @author Farzana Alam
 *
 */
@SuppressWarnings("serial")
public class MainContainerView extends JFrame {

	// Singleton class - only one instance will be allowed
	private static MainContainerView instance;

	// Layout manager for panels
	private MigLayout rowPanelLayout = new MigLayout("fillx");
	private MigLayout columnPanelLayout = new MigLayout("filly");

	// Top Panel
	private JPanel scorecardPanel = new JPanel(rowPanelLayout);
	// Bottom-Right Panel
	private JPanel controlPanel = new JPanel(columnPanelLayout);
	// Bottom-Left Panel
	private JPanel gamePlayPanel = new JPanel();

	// tower control panel
	private JPanel towerControlPanel = new JPanel(new MigLayout());
	// Local usage
	private JScrollPane aScrollPane;

	private MainContainerView() {
		super();

		this.setTitle("Critters TD by Group TF");

		createMainContainer();

		SwingUtilities.updateComponentTreeUI(this);
	} // end constructor

	/**
	 * Creates and returns only one instance of the class
	 * 
	 * @return The main frame with containers
	 */
	public static synchronized MainContainerView getMainContainer() {
		if (instance == null)
			instance = new MainContainerView();

		return instance;

	} // end getInstance

	private void createMainContainer() {
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				if (JOptionPane
						.showConfirmDialog(MainContainerView.this,
								"Are you sure to quit the game?", "Closing?",
								JOptionPane.YES_NO_OPTION,
								JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
					GUIController.getGUIController().saveScores();
					GameEvent.closeLogger();
					MapEvent.closeLogger();
					HighestScoreView.quitGame();
				}
			}
		});
		addPanelsToTheMainPane(this.getContentPane());
	} // end createMainContainer

	/**
	 * @param pane
	 */
	private void addPanelsToTheMainPane(Container pane) {

		Border border = BorderFactory.createLineBorder(Color.GRAY, 2, true);

		scorecardPanel.setBorder(border);
		controlPanel.setBorder(border);
		gamePlayPanel.setBorder(border);

		aScrollPane = new JScrollPane(scorecardPanel,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		pane.add(aScrollPane, BorderLayout.PAGE_START);
		aScrollPane = new JScrollPane(controlPanel,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		pane.add(aScrollPane, BorderLayout.LINE_END);
		aScrollPane = new JScrollPane(gamePlayPanel,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		pane.add(aScrollPane, BorderLayout.CENTER);

	} // end addPanelsToTheMainPane

	/**
	 * @return scorecard panel view
	 */
	public JPanel getScorecardPanel() {
		return scorecardPanel;
	} // end getScorecardPanel

	/**
	 * @return control panel view
	 */
	public JPanel getControlPanel() {
		return controlPanel;
	} // end getControlPanel

	/**
	 * @return game play panel
	 */
	public JPanel getGamePlayPanel() {
		return gamePlayPanel;
	} // end gamePlayPanel

	/**
	 * 
	 * @return tower control panel
	 */
	public JPanel getTowerControlPanel() {
		return towerControlPanel;
	} // end towerControlPanel

}