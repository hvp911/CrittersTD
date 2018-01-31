/**
 * 
 */
package ca.soen6441.tf.critterstd.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import org.apache.commons.io.FileUtils;

/**
 * @author Farzana Alam
 *
 */
public class GameUtility {

	private FileOutputStream fOS;
	private ObjectOutputStream objOS;
	private FileInputStream fIS;
	private ObjectInputStream objIS;
	private static String pathToGames = System.getProperty("user.dir")
			+ File.separator + "resources" + File.separator + "games"
			+ File.separator;
	private static String path;

	/**
	 * Utility method to save the game objects
	 * 
	 * @param fileName
	 *            file name as string
	 * @return <tt>true</tt> if the saving of an object operation was
	 *         successful.
	 */
	public boolean saveGameObject(Serializable gameObject, String fileName) {

		try {
			fOS = new FileOutputStream(path + fileName);
			objOS = new ObjectOutputStream(fOS);
			objOS.writeObject(gameObject);
			objOS.flush();
			objOS.close();
			fOS.flush();
			fOS.close();
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
	 * Utility method to load game objects from file.
	 * 
	 * @param fileName
	 *            map name as string
	 * @return the game object from the stored <tt>Game</tt>.
	 */
	public Serializable loadAGameObject(String fileName) {

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

		if (!(obj instanceof Serializable))
			return null;

		Serializable gameObject = (Serializable) obj;
		return gameObject;

	}

	/**
	 * @param fileName
	 */
	public static synchronized boolean setDirectory(String fileName,
			boolean loadingGame) {

		path = pathToGames + fileName + File.separator;

		if (loadingGame) {
			return true;
		}

		File dir = new File(path);

		if (dir.exists() && dir.isDirectory()) {
			try {
				FileUtils.deleteDirectory(dir);
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		}
		dir.mkdirs();

		FileOutputStream gameFile;
		try {

			if (!new File(pathToGames + fileName + ".game").exists()) {
				gameFile = new FileOutputStream(pathToGames + fileName
						+ ".game");
				gameFile.flush();
				gameFile.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} finally {
			gameFile = null;
			System.gc();
		}

		return true;

	}
}
