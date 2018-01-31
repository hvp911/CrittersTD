/**
 * 
 */
package ca.soen6441.tf.critterstd.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import net.miginfocom.swing.MigLayout;
import ca.soen6441.tf.critterstd.utility.GameEvent;
import ca.soen6441.tf.critterstd.utility.MapEvent;
import ca.soen6441.tf.critterstd.utility.WrapLayout;

/**
 * @author Farzana Alam
 *
 */
@SuppressWarnings("serial")
public class LogView extends JFrame {

	private JPanel buttonsPanel = new JPanel(new WrapLayout());
	private JPanel tablePanel = new JPanel(new MigLayout("fill"));

	private final String[] colNames = { "Time-Stamp", "Event" };

	private ButtonGroup filterButtons;
	private JTable eventsLog;
	private JScrollPane scrollPane;

	private String lastFilter = "game";

	private boolean mapLog = false;

	/**
	 * 
	 */
	public LogView(boolean mapLog) {
		this.mapLog = mapLog;
		if (mapLog)
			lastFilter = "map1";
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				LogView.this.initialize();
			}
		});

	}

	private void initialize() {
		setFilterRadioButtons(true);
		add(buttonsPanel, BorderLayout.PAGE_START);
		add(tablePanel, BorderLayout.CENTER);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setMinimumSize(new Dimension(500, 500));
		setTitle("Events for: ");
		pack();
		setVisible(true);
	}

	private void setFilterRadioButtons(boolean firstView) {
		filterButtons = new ButtonGroup();
		buttonsPanel.removeAll();
		Iterator<String> iterartor;
		if (mapLog) {
			iterartor = MapEvent.getMaps().iterator();
		} else {
			iterartor = GameEvent.getGameTags().iterator();
		}
		while (iterartor.hasNext()) {
			String filter = iterartor.next();
			final JRadioButton aButton = new JRadioButton(filter);
			if (firstView && filter.equalsIgnoreCase(lastFilter)) {
				aButton.setSelected(true);
				createEvenLog(filter);
			} else if (lastFilter.equalsIgnoreCase(filter)) {
				aButton.setSelected(true);
			}

			filterButtons.add(aButton);
			buttonsPanel.add(aButton);

			aButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					LogView.this.createEvenLog(aButton.getText());
					lastFilter = aButton.getText();
					setFilterRadioButtons(false);
				}
			});
		}

		buttonsPanel.revalidate();
		buttonsPanel.repaint();
	}

	private void createEvenLog(String filter) {

		setTitle("Events for: " + filter);

		String[][] rows;
		if (mapLog) {
			rows = MapEvent.getFilteredEvents(filter);
		} else {
			rows = GameEvent.getFilteredEvents(filter);
		}

		eventsLog = new JTable(rows, colNames);

		scrollPane = new JScrollPane(eventsLog);
		tablePanel.removeAll();
		tablePanel.add(scrollPane, "grow");
		tablePanel.revalidate();
		tablePanel.repaint();

	}
}
