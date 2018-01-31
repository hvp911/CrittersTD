/**
 * 
 */
package ca.soen6441.tf.critterstd.model.cell;

/**
 * This the factory from where Map gets cells as needed.
 * 
 * @author Farzana Alam
 *
 */
public class CellFactory {

	private static CellFactory instance;

	/**
	 * CellFactory Constructor
	 */
	private CellFactory() {
		super();
	} // end constructor

	/**
	 * Create Singleton for getCellFactory
	 * 
	 * @return the singleton <tt> CellFactory </tt> object.
	 */
	public static CellFactory getCellFactory() {
		if (instance == null)
			instance = new CellFactory();

		return instance;
	} // end getCellFactory

	/**
	 * @param cellType
	 *            cell name as either <tt> String </tt> of "PathCell" or
	 *            "SceneryCell".
	 * @return the desired cell as specified by <b><tt>cellType</tt></b>.
	 */
	public CellInterface getCell(String cellType) {
		cellType = cellType.toLowerCase().trim();
		switch (cellType) {

		case "pathcell":
			return new PathCell();

		case "scenerycell":
			return new SceneryCell();

		default:
			return new SceneryCell();

		} // end switch
	} // end getCell

	/**
	 * Changes a particular cell from Path to Scenery or Scenery to Path when
	 * this method is called.
	 * 
	 * @param cellType
	 *            the cell name in <tt>String</tt> with either "PathCell" or
	 *            "SceneryCell".
	 * @return the changed cell.
	 */
	public CellInterface changeCell(String cellType) {
		cellType = cellType.toLowerCase();
		switch (cellType) {
		case "pathcell":
			return getCell("scenerycell");

		case "scenerycell":
			return getCell("pathcell");

		default:
			return getCell("scenerycell");
		}
	}
}