/**
 * 
 */
package ca.soen6441.tf.critterstd.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;

import ca.soen6441.tf.critterstd.controller.GamePlayController;
import ca.soen6441.tf.critterstd.controller.MapEditorController;
import ca.soen6441.tf.critterstd.model.Map;
import ca.soen6441.tf.critterstd.model.ModelObservable;

/**
 * Contains the map view
 * 
 * @author fa_alam
 *
 */
public class MapView extends ViewObserver implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static Map map;
	private static MapView instance;

	private transient int numOfRows;
	private transient int numOfColumns;

	private transient boolean mapEditorView = true;

	private transient JPanel gamePlayPanel;
	private transient JPanel mapPanel;
	private transient GridLayout gridLayout;

	private transient int elementWidth;

	// for path
	private ArrayList<Integer> pathCellXs = new ArrayList<Integer>();
	private ArrayList<Integer> pathCellYs = new ArrayList<Integer>();
	private ArrayList<Color> pathCellColors = new ArrayList<Color>();

	// for critter and fire
	private transient boolean isElement = false;
	private ArrayList<Element> critters = new ArrayList<MapView.Element>();
	private transient ArrayList<Element> fires = new ArrayList<MapView.Element>();

	private final transient Object lock = new Object();
	private transient boolean gameOver = false;
	private ArrayList<Element> towerRanges = new ArrayList<MapView.Element>();
	private transient ArrayList<Element> splashes = new ArrayList<MapView.Element>();
	private transient Object lockSplashes = new Object();

	@SuppressWarnings("serial")
	private MapView() {
		super();
		// adding the Subject
		map.attach(this);
		this.numOfRows = map.getDEFAULT_NUM_ROWS();
		this.numOfColumns = map.getDEFAULT_NUM_COLUMNS();
		mapPanel = new JPanel() {

			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				paintGraphics(g);

			}
		};

		this.gamePlayPanel = MainContainerView.getMainContainer()
				.getGamePlayPanel();
		createMapView();
	}

	/**
	 * @param observableMap
	 *            subject
	 * @return singleton object
	 */
	public static MapView getMapView(ModelObservable observableMap) {
		model = observableMap;
		map = (Map) model;

		if (instance == null)
			instance = new MapView();

		return instance;

	}

	private void createMapView() {

		gridLayout = new GridLayout(numOfRows, numOfColumns);
		mapPanel.setLayout(gridLayout);

		mapPanel.removeAll();

		for (int row = 0; row < numOfRows; row++) {
			for (int col = 0; col < numOfColumns; col++) {

				JButton aButton = (JButton) map.getCells().get(row).get(col);
				for (ActionListener l : aButton.getActionListeners())
					aButton.removeActionListener(l);
				setListener(aButton);

				mapPanel.add(aButton);

			} // end inner loop

		}// end outer loop

		gamePlayPanel.removeAll();
		gamePlayPanel.add(mapPanel);
		gamePlayPanel.validate();
		gamePlayPanel.repaint();

	}

	/**
	 * Sets the listener to the cells
	 * 
	 * @param jButton
	 */
	private void setListener(JButton jButton) {

		final JButton aCellButton = jButton;

		jButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (mapEditorView) {
					MapEditorController.getMapEditorController().changeCell(
							aCellButton);
					return;
				}

				GamePlayController.getGamePlayController().updateCellProperty(
						aCellButton);
			}
		});

	}

	/**
	 * @param elementSerial
	 *            the serial number of the object to add
	 * @param x
	 *            y coordinate
	 * @param y
	 *            y coordinate
	 * @param elementColor
	 *            color to paint
	 * @param isCritter
	 *            flag to show if it is critter or fire object
	 */
	public void addElements(int elementSerial, int x, int y,
			Color elementColor, boolean isCritter) {

		Element element = new Element(x, y, elementColor);

		// for critters
		if (isCritter) {

			if (elementSerial == critters.size()) {
				critters.add(element);
			} else if (elementSerial < critters.size()) {
				critters.set(elementSerial, element);
			} else {
				for (int i = critters.size(); i < elementSerial; i++) {
					critters.add(null);
				}
				critters.add(element);
			}
		} else {
			// for fire
			if (elementSerial == fires.size()) {
				fires.add(element);
			} else if (elementSerial < fires.size()) {
				fires.set(elementSerial, element);
			} else {
				for (int i = fires.size(); i < elementSerial; i++) {
					fires.add(null);
				}
				fires.add(element);
			}
		} // end else

		synchronized (lock) {
			this.isElement = true;
		}

		mapPanel.repaint();
	}

	/*
	 * 
	 * (non-Javadoc)
	 * 
	 * @see ca.soen6441.tf.critterstd.view.ViewObserver#update()
	 */
	@Override
	public void update() {
		map = (Map) map.getState();
		this.numOfRows = map.getNumOfRows();
		this.numOfColumns = map.getNumOfColumns();
		createMapView();
	}

	/**
	 * @param mapEditorView
	 *            flag to show specific view
	 */
	public void setMapEditorView(boolean mapEditorView) {
		this.mapEditorView = mapEditorView;
	}

	/**
	 * This will re animate the paths
	 * 
	 * @param x
	 *            x-coordinate
	 * @param y
	 *            y-coordinate
	 * @param width
	 *            width of the shape
	 * @param cellColor
	 *            color of the shape
	 */
	public void setCells(int x, int y, int width, Color cellColor) {

		pathCellXs.add(x);
		pathCellYs.add(y);
		pathCellColors.add(cellColor);
		elementWidth = width;

		mapPanel.repaint();

	}

	/**
	 * This will add path cells, critters and fires in the map view.
	 * 
	 * @param g
	 *            map's view
	 */
	protected void paintGraphics(Graphics g) {
		// adding cell graphics when start game button is pressed
		for (int i = 0; i < pathCellColors.size(); i++) {

			g.setColor(pathCellColors.get(i));
			g.fillRect(pathCellXs.get(i), pathCellYs.get(i), elementWidth,
					elementWidth);
		} // end for

		if (gameOver)
			return;

		if (towerRanges.size() > 0) {
			for (Element towerRange : towerRanges) {

				if (towerRange == null)
					continue;

				g.setColor(new Color(16, 222, 2, 10));
				g.fillOval(towerRange.getX(), towerRange.getY(),
						towerRange.getWidth(), towerRange.getWidth());

				g.setColor(new Color(16, 222, 2, 30));
				g.fillOval(towerRange.getX(), towerRange.getY(),
						towerRange.getWidth(), towerRange.getWidth());
			}
		}

		Graphics2D g2D = (Graphics2D) g.create();

		// animating critters in time
		if (isElement) {
			synchronized (lock) {
				isElement = false;
			}
			g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);

			for (Element e : critters) {

				if (e == null) {
					continue;
				}

				g2D.setColor(e.getColor());
				g2D.fillOval(e.getX(), e.getY(), elementWidth, elementWidth);
			} // end for

			if (fires != null) {
				for (Element e : fires) {

					if (e == null) {
						continue;
					}

					g2D.setColor(e.getColor());
					g2D.fillOval(e.getX(), e.getY(), 10, 10);
				}
			}

		} // end if

		if (!splashes.isEmpty()) {
			for (Element e : splashes) {
				if (e == null)
					continue;

				g2D.setColor(e.getColor());
				g2D.fillOval(e.getX(), e.getY(), 5, 5);

			}

		}

		g2D.dispose();
	}

	/**
	 * @author Farzana Alam
	 *
	 */
	private class Element implements Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private int x;
		private int y;
		private int width;
		private Color color;

		/**
		 * @param x1
		 * @param y1
		 * @param width
		 * @param y2
		 */
		public Element(int x, int y, int width) {
			super();
			this.x = x;
			this.y = y;
			this.width = width;
		}

		/**
		 * Element Constructor for Critters and Fire Effect
		 * 
		 * @param x
		 * @param y
		 * @param color
		 */
		private Element(int x, int y, Color color) {
			super();
			this.x = x;
			this.y = y;
			this.color = color;
		}

		private Color getColor() {
			return color;
		}

		private int getX() {
			return x;
		}

		private int getY() {
			return y;
		}

		public int getWidth() {
			return width;
		}

	}

	/**
	 * @param serial
	 *            the serial number of the object to remove
	 * @param isCritter
	 *            flag to show if a critter object or a fire object
	 */
	public void removeElement(int serial, boolean isCritter) {

		try {
			if (isCritter) {
				if (critters.size() < serial || critters.size() == 0)
					return;
				critters.set(serial, null);
			} else if (fires != null) {
				if (fires.size() <= serial)
					return;
				fires.set(serial, null);
			}
			synchronized (lock) {
				this.isElement = true;
			}
		} catch (Exception e) {
			System.out.println("Need to Check Here");
		}
		mapPanel.repaint();
	}

	/**
	 * 
	 */
	public void stopMap() {
		synchronized (lock) {
			this.gameOver = true;
		}

		mapPanel.repaint();
	}

	/**
	 * @param serial
	 *            the serial number of the object to paint
	 * @param startX
	 *            x coordinate
	 * @param startY
	 *            y coordinate
	 * @param width
	 *            width of the shape
	 */
	public void addTowerRanges(int serial, int startX, int startY, int width) {

		Element element = new Element(startX, startY, width);

		if (serial == towerRanges.size()) {
			towerRanges.add(element);
		} else if (serial < towerRanges.size()) {
			towerRanges.set(serial, element);
		} else {
			for (int i = 0; i < serial; i++) {
				towerRanges.add(null);
			}
			towerRanges.add(element);
		}

		mapPanel.repaint();
	}

	/**
	 * @param serial
	 *            the serial number of the object to remove
	 */
	public void removeTowerRange(int serial) {
		if (serial >= towerRanges.size())
			return;

		towerRanges.remove(serial);

		mapPanel.repaint();
	}

	/**
	 * @param serial
	 *            the serial number of the object to remove
	 */
	public void removeSplash(int serial) {
		if (serial >= splashes.size())
			return;
		synchronized (lockSplashes) {
			splashes.set(serial, null);
		}
	}

	/**
	 * @param serial
	 *            the serial number of the object to paint
	 * @param x
	 *            x coordinate
	 * @param y
	 *            y coordinate
	 * @param color
	 *            color to paint
	 */
	public void paintSplash(int serial, int x, int y, Color color) {

		Element element = new Element(x, y, color);

		if (serial == splashes.size()) {
			splashes.add(element);
		} else if (serial < splashes.size()) {
			splashes.set(serial, element);
		} else {
			for (int i = 0; i < serial; i++) {
				splashes.add(null);
			}
			splashes.add(element);
		}

		synchronized (lock) {
			this.isElement = true;
		}

		mapPanel.repaint();

	}

	/**
	 * @param tmpView
	 */
	public void loadASavedMapView(MapView tmpView) {
		pathCellXs = tmpView.pathCellXs;
		pathCellYs = tmpView.pathCellYs;
		pathCellColors = tmpView.pathCellColors;
		isElement = tmpView.isElement;
		towerRanges = tmpView.towerRanges;
	}

}
