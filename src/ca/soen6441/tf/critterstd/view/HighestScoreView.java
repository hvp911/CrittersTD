/**
 * 
 */
package ca.soen6441.tf.critterstd.view;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import net.miginfocom.swing.MigLayout;
import ca.soen6441.tf.critterstd.utility.MapEvent;

/**
 * @author Farzana Alam
 *
 */
@SuppressWarnings("serial")
public class HighestScoreView extends JDialog {

	private static boolean gameClosing = false;

	private Container pane;

	private JTable scoreTable;
	private JPanel tablePanel = new JPanel(new MigLayout("fill"));
	private final String[] colNames = { "Time-Stamp", "Score" };

	private JButton okButton = new JButton("OK");

	/**
	 * @param mapName
	 */
	public HighestScoreView(String mapName) {
		super(MainContainerView.getMainContainer());
		setTitle("Highest Scores on " + mapName);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setMinimumSize(new Dimension(500, 500));

		this.pane = this.getContentPane();
		this.pane.setLayout(new MigLayout("fill"));
		this.pane.add(tablePanel, "growx, wrap");
		this.pane.add(okButton, "center");

		this.okButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				HighestScoreView.this.dispose();
				if (gameClosing)
					System.exit(0);
			}
		});

		createScoreTable(mapName);

		pack();
		setVisible(true);
	}

	private void createScoreTable(String filter) {
		String[][] rows;

		rows = MapEvent.getHigestScores(filter);

		scoreTable = new JTable(rows, colNames);

		tablePanel.add(new JScrollPane(scoreTable), "growy");
	}

	/**
	 * 
	 */
	public static void quitGame() {
		gameClosing = true;
	}

}
