/**
 * 
 */
package ca.soen6441.tf.critterstd.model.cell;

import java.awt.Color;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;

import ca.soen6441.tf.critterstd.model.PathCellDirection;
import ca.soen6441.tf.critterstd.model.tower.TowerInterface;

/**
 * This class defines the characteristics of a Scenery Cell in the grid map.
 * 
 * @author Farzana Alam
 * 
 *
 */
public class SceneryCell extends Cell {

	private static final long serialVersionUID = 1L;

	private TowerInterface tower;

	/**
	 * Default constructor
	 */
	SceneryCell() {
		super();
		this.cellColor = Color.LIGHT_GRAY;
		ImageIcon buttonIcon = new ImageIcon(new BufferedImage(WIDTH, WIDTH,
				BufferedImage.TYPE_INT_ARGB));
		this.setBackground(cellColor);
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		this.setIcon(buttonIcon);
		cellName = "SceneryCell";

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ca.soen6441.tf.critterstd.model.cell.CellInterface#getCellName()
	 */
	/**
	 * @return cellName String
	 */
	@Override
	public String getCellName() {
		return cellName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ca.soen6441.tf.critterstd.model.cell.CellInterface#disableCell()
	 */
	/**
	 * Disable The Cell
	 */
	@Override
	public void disableCell() {
		cellColor = new Color(28, 107, 14); // dark green
		setOpaque(false);
		this.setBorder(BorderFactory.createLineBorder(new Color(26, 99, 13)));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ca.soen6441.tf.critterstd.model.cell.CellInterface#setEntryPoint()
	 */

	@Override
	/**
	 * Do Nothing As Scenery Cell Can not Be Entry Point
	 */
	public void setEntryPoint() {
		// scenery cell cannot be an entry point
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ca.soen6441.tf.critterstd.model.cell.CellInterface#setExitPoint()
	 */

	@Override
	/**
	 * Do Nothing As Scenery Cell Can not Be Exit Point
	 */
	public void setExitPoint() {
		// scenery cell cannot be an exit point
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ca.soen6441.tf.critterstd.model.cell.CellInterface#isEntryPonit()
	 */
	/**
	 * @return Boolean entryPoint
	 */
	@Override
	public boolean isEntryPonit() {
		return entryPoint;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ca.soen6441.tf.critterstd.model.cell.CellInterface#isExitPoint()
	 */
	/**
	 * @return Boolean exitPoint
	 */

	@Override
	public boolean isExitPoint() {
		return exitPoint;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ca.soen6441.tf.critterstd.model.cell.CellInterface#setEndPoint()
	 */
	/**
	 * Scenery Cell Can not be End Point
	 */
	@Override
	public void setEndPoint() {
		// scenery cell cannot be an end point
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ca.soen6441.tf.critterstd.model.cell.CellInterface#isEndPoint()
	 */
	/**
	 * return endPoint
	 */
	@Override
	public boolean isEndPoint() {
		return endPoint;
	}

	/*
	 * Set TowerInterface to the SceneryCell as Element of the Cell
	 * 
	 * (non-Javadoc)
	 * 
	 * @see
	 * ca.soen6441.tf.critterstd.model.cell.CellInterface#setElementOnTheCell
	 * (java.lang.Object)
	 */
	@Override
	public boolean setElementOnTheCell(Object element) {
		if (element == null)
			return false;

		if (!(element instanceof TowerInterface))
			return false;

		if (!cellEmpty)
			return false;

		tower = (TowerInterface) element;
		cellColor = Color.WHITE;
		setCellEmpty(false);
		return true;
	}

	/**
	 * Return Object of TowerInterface
	 */
	@Override
	public Object getElementOfTheCell() {
		return tower;
	}

	/**
	 * Make Tower Object Null for the Scenery Cell
	 */
	@Override
	public void removeElementOfTheCell() {
		if (tower == null)
			return;

		if (isCellEmpty())
			return;

		cellEmpty = true;
		tower = null;
	}

	/**
	 * @return Boolean False
	 */
	@Override
	public boolean isOpposingDirection(PathCellDirection direction) {
		// for path cell, do nothing on scenery cell
		return false;
	}

}
