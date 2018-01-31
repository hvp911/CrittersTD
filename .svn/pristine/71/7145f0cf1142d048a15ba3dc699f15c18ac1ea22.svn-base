package ca.soen6441.tf.critterstd.test;

import javax.swing.JButton;
import javax.swing.JPanel;

import ca.soen6441.tf.critterstd.model.Map;
import ca.soen6441.tf.critterstd.model.Player;
import ca.soen6441.tf.critterstd.view.ControlPanelView;
import ca.soen6441.tf.critterstd.view.MainContainerView;
import ca.soen6441.tf.critterstd.view.MapEditorView;
import ca.soen6441.tf.critterstd.view.MapView;
import ca.soen6441.tf.critterstd.view.ScorecardPanelView;
import ca.soen6441.tf.critterstd.view.TowerControlPanelView;

/**
 * This class was created to manually test the GUI during building GUI Views
 * 
 * @author Farzana Alam
 *
 */
public class GUITester {

	private static MainContainerView container = MainContainerView
			.getMainContainer();
	private static JPanel scorecardPanel = container.getScorecardPanel();
	private static JPanel controlPanel = container.getControlPanel();
	private static JPanel gamePlayPanel = container.getGamePlayPanel();
	private static JPanel towerPanel = container.getTowerControlPanel();

	public static void main(String[] args) {
		testMainContainerGUI();
		testScorcardPanelView();
		testControlPanelView();
		testTowerPanelView();
		testMapEditorView();

	}

	private static void testMapEditorView() {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				controlPanel.removeAll();
				scorecardPanel.removeAll();
				gamePlayPanel.removeAll();
				towerPanel.removeAll();
				new MapEditorView();
				MapView.getMapView(Map.getMap());
				container.pack();
				container.setMinimumSize(container.getSize());
				container.setVisible(true);
			} // end run
		});

	}

	private static void testControlPanelView() {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				controlPanel.removeAll();
				ControlPanelView.getControlPanelView();
				container.pack();
				container.setVisible(true);
			} // end run
		});

	}

	private static void testScorcardPanelView() {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				scorecardPanel.removeAll();
				ScorecardPanelView.getScorecardPanel(Player.getPlayer());
				container.pack();
				container.setVisible(true);
			} // end run
		});

	}

	private static void testMainContainerGUI() {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				scorecardPanel.add(new JButton("Test1"));
				controlPanel.add(new JButton("Test2"));
				gamePlayPanel.add(new JButton("Test3"));
				container.pack();
				container.setVisible(true);
			} // end run
		});
	}

	private static void testTowerPanelView() {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				towerPanel.removeAll();
				new TowerControlPanelView();
				towerPanel.setVisible(true);
			} // end run
		});

	}
}