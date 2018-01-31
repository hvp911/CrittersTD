/**
 * 
 */
package ca.soen6441.tf.critterstd.model.cell;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;

import ca.soen6441.tf.critterstd.model.PathCellDirection;

/**
 * This class defines the characteristics of a Path Cell in the grid map.
 * 
 * @author Farzana Alam
 *
 */
public class PathCell extends Cell {

	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor
	 */
	PathCell() {
		super();
		this.cellColor = Color.BLACK;
		this.setBackground(cellColor);
		this.setPreferredSize(new Dimension(WIDTH, WIDTH));
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		cellName = "PathCell";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ca.soen6441.tf.critterstd.model.cell.CellInterface#getCellName()
	 */
	@Override
	/**
	 * @return cellName
	 */
	public String getCellName() {
		return cellName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ca.soen6441.tf.critterstd.model.cell.CellInterface#disableCell()
	 */
	@Override
	public void disableCell() {
		this.setEnabled(false);
		this.setVisible(false);
		if (entryPoint || exitPoint)
			return;
		// dark grey
		this.cellColor = new Color(94, 93, 99);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ca.soen6441.tf.critterstd.model.cell.CellInterface#setEntryPoint()
	 */
	/**
	 * SetBackground color for entry point
	 */
	public void setEntryPoint() {
		this.entryPoint = true;
		this.cellColor = new Color(122, 138, 99); // dark GREEN
		this.setBackground(cellColor);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ca.soen6441.tf.critterstd.model.cell.CellInterface#setExitPoint()
	 */
	@Override
	/**
	 * SetBackground color for exit point
	 */
	public void setExitPoint() {
		this.exitPoint = true;
		this.cellColor = new Color(242, 68, 10); // dark RED
		this.setBackground(cellColor);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ca.soen6441.tf.critterstd.model.cell.CellInterface#isEntryPonit()
	 */
	/**
	 * @return entrypoint value
	 */
	public boolean isEntryPonit() {
		return entryPoint;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ca.soen6441.tf.critterstd.model.cell.CellInterface#isExitPoint()
	 */
	/**
	 * @return exitpoint value
	 */
	public boolean isExitPoint() {
		return exitPoint;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ca.soen6441.tf.critterstd.model.cell.CellInterface#setEndPoint()
	 */
	/**
	 * setendpoint as true value
	 */
	public void setEndPoint() {
		this.endPoint = true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ca.soen6441.tf.critterstd.model.cell.CellInterface#isEndPoint()
	 */
	/**
	 * @return endPoint value
	 */
	@Override
	public boolean isEndPoint() {
		return endPoint;
	}

	/**
	 * @param element Object
	 * @return true
	 */
	@Override
	public synchronized boolean setElementOnTheCell(Object element) {
		// if (!(element instanceof Critter))
		// return false;

		// do nothing

		return true;

	}

	/**
	 * @return null Object
	 */
	@Override
	public Object getElementOfTheCell() {
		// return critter
		return null;
	}

	/**
	 * Do Nothing
	 */
	@Override
	public void removeElementOfTheCell() {
		// do nothing

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ca.soen6441.tf.critterstd.model.cell.CellInterface#setPathCellDirection
	 * (ca.soen6441.tf.critterstd.model.PathCellDirection)
	 */
	/**
	 * @return Boolean value
	 */
	@Override
	public boolean isOpposingDirection(PathCellDirection direction) {

		if (!directions.contains(direction))
			directions.add(direction);

		/*
		 * same cell will not be allowed for critters to roam in opposing
		 * directions
		 */
		if ((directions.contains(PathCellDirection.RIGHT) && directions
				.contains(PathCellDirection.LEFT))
				|| (directions.contains(PathCellDirection.UP) && directions
						.contains(PathCellDirection.DOWN))) {
			directions.remove(direction);
			return true;
		}

		return false;
	}

}
