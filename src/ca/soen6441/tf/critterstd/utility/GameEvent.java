/**
 * 
 */
package ca.soen6441.tf.critterstd.utility;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.FileUtils;

import ca.soen6441.tf.critterstd.controller.GUIController;

/**
 * @author Farzana Alam
 *
 */
public class GameEvent {

	// global game events
	private static final ArrayList<GameEvent> GAME_EVENTS = new ArrayList<GameEvent>();

	private static final Object LOCK_GE_LIST = new Object();

	private static final String PATH_TO_LOG_FILES = GUIController
			.getPathToResources() + "logs" + File.separator;
	private static final File LOG_FILE = new File(PATH_TO_LOG_FILES
			+ "game.log");
	private static PrintWriter logger;
	private static final Object LOCK_WRITER = new Object();

	// Delimiter
	private static final String DL = " ## ";
	// Delimiter for tags
	private static final String DL_TAGS = " ++ ";

	private final Date DATE = new Date();
	private Timestamp ts;

	private static final HashSet<String> GAME_TAGS = new HashSet<String>(15);
	private static final Object LOCK_TAGS_LIST = new Object();
	// event tags to filter events as desired
	private final ArrayList<String> TAGS = new ArrayList<String>();
	private String eventDetails;

	static {
		if (LOG_FILE.exists()) {
			try {
				FileUtils.copyFile(LOG_FILE, new File(PATH_TO_LOG_FILES
						+ "lastgame.log"));
				FileUtils.forceDelete(LOG_FILE);
				logger = new PrintWriter(LOG_FILE);
			} catch (IOException e) {
				System.err.println("Previous log file could not be deleted."
						+ "Appending to that file instead..");
				e.printStackTrace();
			}
		} else {
			try {
				logger = new PrintWriter(LOG_FILE);
			} catch (FileNotFoundException e) {
				System.err.println("Cannot write logs to a file..");
				e.printStackTrace();
			}
		} // end else

		GAME_TAGS.add("game");
	}

	/**
	 * @param eventDetails
	 * @param tags
	 */
	public GameEvent(String eventDetails, String... tags) {
		super();
		this.ts = new Timestamp(DATE.getTime());
		this.eventDetails = eventDetails.trim();
		for (String tag : tags) {
			tag = tag.trim().toLowerCase();
			this.TAGS.add(tag);
			synchronized (LOCK_TAGS_LIST) {
				GAME_TAGS.add(tag);
			}
		}

		logEvent();

		synchronized (LOCK_GE_LIST) {
			GAME_EVENTS.add(this);
		}
	}

	/**
	 * Buffering all events to write out to a file
	 */
	private void logEvent() {
		String event = ts + DL + eventDetails + DL;

		if (TAGS.size() > 0) {
			for (int i = 0; i < TAGS.size(); i++) {
				event += TAGS.get(i);
				if (i == (TAGS.size() - 1)) {
					break;
				}
				event += DL_TAGS;
			}
		} else {
			event += "game";
		}

		System.out.println(ts + " > " + eventDetails);

		synchronized (LOCK_WRITER) {
			logger.println(event);
		}

	}

	/**
	 * Force close the writer when system is exiting.
	 */
	public static void closeLogger() {
		synchronized (LOCK_WRITER) {
			logger.flush();
			logger.close();
		}
	}

	/**
	 * @return list
	 */
	public static List<String> getGameTags() {
		List<String> list = new ArrayList<String>(GAME_TAGS);
		Collections.sort(list);
		return list;
	}

	/**
	 * @param filter
	 * @return array of string
	 */
	public static String[][] getFilteredEvents(String filter) {

		// filtered game events for specific event types
		ArrayList<String[]> filteredEvents = new ArrayList<String[]>();

		Iterator<GameEvent> eventIterator;
		synchronized (LOCK_GE_LIST) {
			eventIterator = GAME_EVENTS.iterator();
		}

		while (eventIterator.hasNext()) {
			GameEvent event = eventIterator.next();
			if (!filter.equalsIgnoreCase("game")
					&& !event.TAGS.contains(filter.trim().toLowerCase())) {
				continue;
			}
			filteredEvents.add(new String[] { event.ts.toString(),
					event.eventDetails });
		}

		String[][] list = new String[filteredEvents.size()][];
		for (int i = 0; i < filteredEvents.size(); i++) {
			list[i] = filteredEvents.get(i);
		}
		return list;
	}
}
