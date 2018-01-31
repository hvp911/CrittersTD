/**
 * 
 */
package ca.soen6441.tf.critterstd.view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.NumberFormat;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileFilter;
import javax.swing.text.NumberFormatter;

import ca.soen6441.tf.critterstd.controller.GUIController;
import ca.soen6441.tf.critterstd.controller.MapEditorController;

/**
 * This view is for the map editor container
 * 
 * @author Farzana Alam
 *
 */
public class MapEditorView {

	private MainContainerView mainContainer = MainContainerView
			.getMainContainer();
	private JPanel scorecardPanel = mainContainer.getScorecardPanel();
	private JPanel controlPanel = mainContainer.getControlPanel();

	private JFormattedTextField widthInputTextField;
	private JFormattedTextField heightInputTextField;

	private JButton startGameButton = new JButton("Start Game");
	private JButton newMapButton = new JButton("New Map");
	private JButton saveMapButton = new JButton("Save Map");
	private JButton loadMapButton = new JButton("Load Map");
	private JButton loadSavedGameButton = new JButton("Load Saved Game");
	private JButton mapLogButton = new JButton("Show Map Logs");

	// Map storage location
	private String pathToMaps = GUIController.getPathToResources() + "maps"
			+ File.separator;
	// Game storage location
	private String pathToGames = GUIController.getPathToResources() + "games"
			+ File.separator;

	// Local usage
	private JLabel aLabel;
	private JPanel aPanel;
	private JTextField aTextField;

	public MapEditorView() {
		super();
		addComponentsToPanel();
	}

	private void addComponentsToPanel() {

		// setting up top panel
		scorecardPanel.add(new JLabel("Map Editor", SwingConstants.CENTER),
				"grow");

		// setting up map log button
		mapLogButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new LogView(true);
			}
		});

		scorecardPanel.add(mapLogButton, "grow");

		// setting up control panel
		aLabel = new JLabel("Control Panel", SwingConstants.CENTER);
		aLabel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 3));
		controlPanel.add(aLabel, "grow, wrap, span");

		// adding scenery cell details
		aPanel = new JPanel();
		aPanel.setSize(150, 150);
		aPanel.setBackground(Color.LIGHT_GRAY);
		aPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
		controlPanel.add(aPanel);

		aLabel = new JLabel("Scenery Cell", SwingConstants.CENTER);
		controlPanel.add(aLabel, "growx, wrap, span");

		// adding path cell details
		aPanel = new JPanel();
		aPanel.setSize(150, 150);
		aPanel.setBackground(Color.BLACK);
		aPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		controlPanel.add(aPanel);

		aLabel = new JLabel("Path Cell", SwingConstants.CENTER);
		controlPanel.add(aLabel, "growx, wrap, span");

		// adding map controls
		aLabel = new JLabel("Enter Map Size", SwingConstants.CENTER);
		aLabel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2));
		controlPanel.add(aLabel, "growx, wrap, span");

		// Only integers will be allowed
		NumberFormat format = NumberFormat.getInstance();
		NumberFormatter formatter = new NumberFormatter(format);
		formatter.setValueClass(Integer.class);
		formatter.setMinimum(1);
		formatter.setMaximum(Integer.MAX_VALUE);
		formatter.setAllowsInvalid(false);

		// Adding text fields
		aLabel = new JLabel("# of Cells along height", SwingConstants.CENTER);
		controlPanel.add(aLabel, "growx, wrap, span");

		// adding height input field
		heightInputTextField = new JFormattedTextField(formatter);
		heightInputTextField.setText("10");

		heightInputTextField.setToolTipText("int only");
		heightInputTextField.setHorizontalAlignment(JFormattedTextField.RIGHT);
		controlPanel.add(heightInputTextField, "growx, wrap, span");

		// adding width input field
		aLabel = new JLabel("# of Cells along width", SwingConstants.CENTER);
		controlPanel.add(aLabel, "growx, wrap, span");

		widthInputTextField = new JFormattedTextField(formatter);
		widthInputTextField.setText("15");

		widthInputTextField.setToolTipText("int only");
		widthInputTextField.setHorizontalAlignment(JFormattedTextField.RIGHT);
		controlPanel.add(widthInputTextField, "growx, wrap, span");

		// setting up buttons

		// Start Game button
		startGameButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (MapEditorController.getMapEditorController().startGame()) {
					return;
				}

				MapEditorController.getMapEditorController().showErrorMessage(
						mainContainer);
			}
		});

		controlPanel.add(startGameButton, "growx, wrap, span");

		// New Map button
		newMapButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				MapEditorController.getMapEditorController().updateMapView(
						heightInputTextField.getText(),
						widthInputTextField.getText());
			}
		});

		controlPanel.add(newMapButton, "growx, wrap, span");

		// Save Map Button
		saveMapButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String fileName = "";

				if (!MapEditorController.getMapEditorController().isValidMap()) {
					MapEditorController.getMapEditorController()
							.showErrorMessage(mainContainer);
					return;
				}

				fileName = getNewFileName();

				if (fileName != null) {
					MapEditorController.getMapEditorController().saveMap(true,
							fileName);
				} // end if

			} // actionPerformed

		});

		controlPanel.add(saveMapButton, "growx, wrap, span");

		// Load Map Button
		loadMapButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String fileName = getFileName(pathToMaps, true);
				if (fileName != null)
					MapEditorController.getMapEditorController().loadMap(
							fileName);

			}
		});

		controlPanel.add(loadMapButton, "growx, wrap, span");

		// Load Map Button
		loadSavedGameButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String fileName = getFileName(pathToGames, false);
				if (fileName != null)
					GUIController.getGUIController().loadGame(fileName);

			}
		});

		controlPanel.add(loadSavedGameButton, "growx, wrap, span");

	} // end addComponentsToPanel

	/**
	 * When Load Map button is pressed.
	 * 
	 * @return fileName Name of file for map
	 */
	private String getFileName(String path, boolean isMap) {
		String fileName = new String();
		final boolean isAMap = isMap;

		JFrame aFrame = new JFrame();
		JFileChooser aFileChooser = new JFileChooser();
		aFileChooser.setDialogTitle("Choose a File");
		aFileChooser.setApproveButtonText("Load");
		aFileChooser.setCurrentDirectory(new File(path));

		FileFilter ff = new FileFilter() {

			@Override
			public String getDescription() {
				if (isAMap) {
					return "*.map";
				}
				return "*.game";
			}

			@Override
			public boolean accept(File f) {
				Pattern fileExtension;
				if (isAMap) {
					fileExtension = Pattern.compile(".+?\\.map");
				} else {
					fileExtension = Pattern.compile(".+?\\.game");
				}
				return fileExtension.matcher(f.getName()).matches();
			}
		};
		aFileChooser.setFileFilter(ff);
		aFileChooser.addChoosableFileFilter(ff);
		aFileChooser.setAcceptAllFileFilterUsed(false);
		aFileChooser.setVisible(true);
		int result = aFileChooser.showOpenDialog(aFrame);

		if (result == JFileChooser.APPROVE_OPTION) {
			File selectedFile = aFileChooser.getSelectedFile();
			fileName = selectedFile.getName();

		} else if (result == JFileChooser.CANCEL_OPTION) {
			return null;
		}

		return fileName;
	}

	/**
	 * To get the map name from the user when they want to save the map. After
	 * Save Button is pressed.
	 *  
	 * @return filename
	 */
	private String getNewFileName() {
		final JDialog aDialog = new JDialog(mainContainer, "File Name:", true);
		aPanel = new JPanel();
		final JTextField aTextField1 = new JTextField(10);
		aTextField1.setText("map");
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
