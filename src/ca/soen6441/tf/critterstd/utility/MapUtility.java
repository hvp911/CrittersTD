/**
 * This will help save and load Map objects
 */
package ca.soen6441.tf.critterstd.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import ca.soen6441.tf.critterstd.model.Map;

/**
 * This utility class helps store a {@link Map} object and then can restore
 * {@link Map} object from a stored one.
 * 
 * @author Farzana Alam
 *
 */
/**
 * @author Farzana Alam
 *
 */
public class MapUtility {

	private Map aMap;
	private FileOutputStream fOS;
	private ObjectOutputStream objOS;
	private FileInputStream fIS;
	private ObjectInputStream objIS;
	private String path = System.getProperty("user.dir") + File.separator
			+ "resources" + File.separator + "maps" + File.separator;

	/**
	 * default constructor
	 */
	public MapUtility() {
		super();
	}

	private void setAMap(Map aMap) {
		this.aMap = aMap;
	}

	/**
	 * Utility method to save the map
	 * 
	 * @param fileName
	 *            map name as string
	 * @return <tt>true</tt> if the saving map operation was successful.
	 */
	public boolean saveMap(String fileName) {

		setAMap(Map.getMap());
		File f = new File(path + fileName + ".map");
		boolean oldMap = f.exists();

		if (fileName.startsWith("game"))
			oldMap = false;

		try {
			fOS = new FileOutputStream(f);
			objOS = new ObjectOutputStream(fOS);
			objOS.writeObject(aMap);
			objOS.flush();
			objOS.close();
			fOS.flush();
			fOS.close();

			// LOG: Event
			if (oldMap) {
				new MapEvent("Map was edited: " + Map.getMap().getMapName(),
						Map.getMap().getMapName());
			} else {
				new MapEvent("Map created as: " + Map.getMap().getMapName(),
						Map.getMap().getMapName());
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} finally {
			fOS = null;
			objOS = null;
			System.gc();
		}

		return true;

	}

	/**
	 * Utility method to load map from file.
	 * 
	 * @param fileName
	 *            map name as string
	 * @return the {@link Map} object from the stored <tt>Map</tt> object.
	 */
	public Map loadAMap(String fileName) {

		Object obj;

		try {
			fIS = new FileInputStream(path + fileName);
			objIS = new ObjectInputStream(fIS);
			obj = objIS.readObject();
			objIS.close();
			fIS.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		} finally {
			fIS = null;
			objIS = null;
			System.gc();
		}

		if (!(obj instanceof Map))
			return null;

		aMap = (Map) obj;
		return aMap;

	}
}
