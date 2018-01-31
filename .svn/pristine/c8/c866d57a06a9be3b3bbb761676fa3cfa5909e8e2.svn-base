/**
 * 
 */
package ca.soen6441.tf.critterstd.controller;

import java.io.File;
import java.util.ArrayList;

import javax.swing.JPanel;

import ca.soen6441.tf.critterstd.model.Map;
import ca.soen6441.tf.critterstd.model.Player;
import ca.soen6441.tf.critterstd.model.cell.CellInterface;
import ca.soen6441.tf.critterstd.utility.GameEvent;
import ca.soen6441.tf.critterstd.utility.GameUtility;
import ca.soen6441.tf.critterstd.utility.MapEvent;
import ca.soen6441.tf.critterstd.view.HighestScoreView;
import ca.soen6441.tf.critterstd.view.MainContainerView;
import ca.soen6441.tf.critterstd.view.MapEditorView;
import ca.soen6441.tf.critterstd.view.MapView;

/**
 * @author Farzana Alam
 *
 */
public class GUIController {
	private static MainContainerView container = MainContainerView
			.getMainContainer();
	private static JPanel scorecardPanel = container.getScorecardPanel();
	private static JPanel controlPanel = container.getControlPanel();
	private static JPanel gamePlayPanel = container.getGamePlayPanel();
	private static GamePlayController gameController;

	private static GUIController instance;

	private static String pathToResources;

	private String gameControllerFileName = "game_controller";
	private String mapViewFileName = "map_view";

	static {
		pathToResources = new File(GUIController.class.getProtectionDomain()
				.getCodeSource().getLocation().getPath()).getParent()
				+ File.separator + "resources" + File.separator;
		pathToResources = pathToResources.replaceAll("%20", " ");
	}

	/**
	 * Default constructor
	 */
	private GUIController() {
		super();
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				controlPanel.removeAll();
				scorecardPanel.removeAll();
				gamePlayPanel.removeAll();
				new MapEditorView();
				MapView.getMapView(Map.getMap());
				container.pack();
				container.setMinimumSize(container.getSize());
				container.setVisible(true);
			} // end run
		});
	}

	public static void main(String[] args) {
		new GUIController();

		// LOG: Event
		new GameEvent("Game GUI loaded!!");
	}

	/**
	 * When Start Button is pressed
	 */
	void startGame() {

		new HighestScoreView(Map.getMap().getMapName());
		MapView.getMapView(Map.getMap()).setMapEditorView(false);

		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				controlPanel.removeAll();
				scorecardPanel.removeAll();
				gamePlayPanel.removeAll();
				gameController = GamePlayController.getGamePlayController();
				container.pack();
				container.setMinimumSize(container.getSize());
				container.setVisible(true);
			} // end run
		});

		// LOG: Event
		new GameEvent("Game Started.");
		new MapEvent("Game started on: " + Map.getMap().getMapName(), Map
				.getMap().getMapName());
	}

	/**
	 * Create Singleton Instance for getGUIController
	 * 
	 * @return instance
	 */
	public static GUIController getGUIController() {
		if (instance == null)
			instance = new GUIController();
		return instance;
	}

	/**
	 * @return file path
	 */
	public static String getPathToResources() {
		return pathToResources;
	}

	/**
	 * Save current game
	 * 
	 * @param filePath
	 * 
	 */
	public void saveGame(String filePath) {
		final String path = filePath;

		if (!GameUtility.setDirectory(filePath, false)) {
			System.err.println("File operation error in saving game!!");
			return;
		}

		new Thread(new Runnable() {

			@Override
			public void run() {
				gameController.pauseGame(true);
				// save map
				MapEditorController.getMapEditorController().saveMap(
						Map.getMap().validateMap(), path);
				// save game controller
				new GameUtility().saveGameObject(gameController,
						gameControllerFileName);
				// save map view
				new GameUtility().saveGameObject(
						MapView.getMapView(Map.getMap()), mapViewFileName);
				gameController.pauseGame(false);

				// LOG: Event
				new GameEvent("Game saved.");
			}
		}).start();

	}

	/**
	 * Load the saved games
	 * 
	 * @param filePath
	 */
	public void loadGame(String filePath) {

		filePath = filePath.replace(".game", "");
		// load map
		MapEditorController.getMapEditorController().loadMap(filePath + ".map");

		if (!GameUtility.setDirectory(filePath, true)) {
			System.err.println("File operation error in loading game!!");
			return;
		}

		for (ArrayList<CellInterface> tmpCells : Map.getMap().getValidPaths()) {
			for (CellInterface aCell : tmpCells) {
				aCell.setCellEmpty(true);
			}
		}

		// start game
		startGame();

		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				// load game controller
				GamePlayController tmpController = (GamePlayController) new GameUtility()
						.loadAGameObject(gameControllerFileName);

				gameController.loadASavedController(tmpController);

				// load map view
				MapView tmpView = (MapView) new GameUtility()
						.loadAGameObject(mapViewFileName);

				MapView.getMapView(Map.getMap()).loadASavedMapView(tmpView);
				// LOG: Event
				new GameEvent("Saved game loaded.");

			} // end run
		});

	}

	/**
	 * Save the score of player.
	 */
	public void saveScores() {
		int life = Player.getPlayer().getLife();
		int level = Player.getPlayer().getLevel();
		double currency = Player.getPlayer().getCurrency();
		int score = Player.getPlayer().getScore();

		// LOG: Event
		new MapEvent("Game quitting. Scores saved. Player Life: " + life
				+ "; Level: " + level + "; Currency: " + currency + "; Score: "
				+ score + ".", Map.getMap().getMapName(), score);

		// LOG: Event
		new GameEvent("Game quitting. Scores saved. Player Life: " + life
				+ "; Level: " + level + "; Currency: " + currency + "; Score: "
				+ score + ".");

		new HighestScoreView(Map.getMap().getMapName());
	}
}
