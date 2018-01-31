/**
 * 
 */
package ca.soen6441.tf.critterstd.utility;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import ca.soen6441.tf.critterstd.controller.GUIController;

/**
 * @author Farzana Alam
 *
 */
public class MapEvent {

	// global map events
	private static final ArrayList<MapEvent> MAP_EVENTS = new ArrayList<MapEvent>();

	private static final Object LOCK_EVENTS = new Object();

	private static final String PATH_TO_LOG_FILES = GUIController
			.getPathToResources() + "logs" + File.separator;

	private static final File LOG_FILE = new File(PATH_TO_LOG_FILES + "map.log");

	private static PrintWriter logger;
	private static final Object LOCK_WRITER = new Object();

	// Delimiter
	private static final String DL = " ## ";

	private final Date DATE;
	private Timestamp ts;

	private static final HashSet<String> MAPS = new HashSet<String>(15);
	private static final Object LOCK_MAPS = new Object();
	// event tags to filter events as desired
	private final String MAP;
	private String eventDetails;

	private Integer score = 0;

	static {
		if (LOG_FILE.exists()) {
			loadSavedEvents();
		}

		try {
			logger = new PrintWriter(new BufferedWriter(new FileWriter(
					LOG_FILE, true)));
		} catch (IOException e) {
			System.err.println("Cannot write logs to a file..");
			e.printStackTrace();
		}
	}

	/**
	 * @param eventDetails
	 * @param mapName
	 */
	public MapEvent(String eventDetails, String mapName, int... scores) {
		super();
		this.DATE = new Date();
		this.ts = new Timestamp(DATE.getTime());
		this.eventDetails = eventDetails.trim();
		this.MAP = mapName.trim().toLowerCase();
		for (int score : scores)
			this.score = score;
		synchronized (LOCK_MAPS) {
			MAPS.add(mapName);
		}

		logEvent();

		synchronized (LOCK_EVENTS) {
			MAP_EVENTS.add(this);
		}
	}

	private MapEvent(String timeStamp, String eventDetails, String mapName,
			String... scores) {
		super();
		this.DATE = new Date();
		DateFormat df = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss.SSS");
		try {
			Date date1 = df.parse(timeStamp);
			long t = date1.getTime();
			this.ts = new Timestamp(t);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		for (String score : scores)
			this.score = new Integer(score);
		this.eventDetails = eventDetails.trim();
		this.MAP = mapName.trim().toLowerCase();
		synchronized (LOCK_MAPS) {
			MAPS.add(mapName);
		}

		synchronized (LOCK_EVENTS) {
			MAP_EVENTS.add(this);
		}
	}

	private void logEvent() {
		String event;
		if (score > 0) {
			event = ts + DL + eventDetails + DL + MAP + DL + score;
		} else {
			event = ts + DL + eventDetails + DL + MAP;
		}

		System.out.println(ts + " > " + eventDetails);

		synchronized (LOCK_WRITER) {
			logger.println(event);
		}

	}

	private static void loadSavedEvents() {
		BufferedReader reader = null;
		String line;

		try {
			reader = new BufferedReader(new FileReader(LOG_FILE));
			while ((line = reader.readLine()) != null) {
				String[] fields = line.split(DL);
				if (fields.length > 3) {
					new MapEvent(fields[0], fields[1], fields[2], fields[3]);
				} else if (fields.length > 2) {
					new MapEvent(fields[0], fields[1], fields[2]);
				}
			}
		} catch (IOException e) {
			System.err.println("File not accessible. "
					+ "Previous logs were not found.");
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * @return list
	 */
	public static List<String> getMaps() {
		List<String> list = new ArrayList<String>(MAPS);
		Collections.sort(list);
		return list;
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
	 * @param mapName
	 * @return array
	 */
	public static String[][] getFilteredEvents(String mapName) {

		// filtered map events for specific event types
		ArrayList<String[]> filteredEvents = new ArrayList<String[]>();

		Iterator<MapEvent> eventIterator;
		synchronized (LOCK_EVENTS) {
			eventIterator = MAP_EVENTS.iterator();
		}

		while (eventIterator.hasNext()) {
			MapEvent event = eventIterator.next();
			if (!event.MAP.equalsIgnoreCase(mapName.trim()))
				continue;
			filteredEvents.add(new String[] { event.ts.toString(),
					event.eventDetails });
		}

		String[][] list = new String[filteredEvents.size()][];
		for (int i = 0; i < filteredEvents.size(); i++) {
			list[i] = filteredEvents.get(i);
		}
		return list;
	}

	/**
	 * @param mapName
	 * @return list
	 */
	public static String[][] getHigestScores(String mapName) {

		// filtered map events for specific event types
		ArrayList<String[]> filteredScores = new ArrayList<String[]>();
		List<MapEvent> filteredEvents = new ArrayList<MapEvent>();

		Iterator<MapEvent> eventIterator;
		synchronized (LOCK_EVENTS) {
			eventIterator = MAP_EVENTS.iterator();
		}

		while (eventIterator.hasNext()) {
			MapEvent event = eventIterator.next();
			if ((event.score < 1)
					|| !event.MAP.equalsIgnoreCase(mapName.trim()))
				continue;

			if (filteredEvents.size() < 5) {
				filteredEvents.add(event);
				continue;
			}

			for (int i = 0; i < filteredEvents.size(); i++) {
				if (event.score <= filteredEvents.get(i).score)
					continue;

				filteredEvents.set(i, event);
				break;
			}
		}

		Collections.sort(filteredEvents, new Comparator<MapEvent>() {

			@Override
			public int compare(MapEvent e1, MapEvent e2) {
				return e2.score.compareTo(e1.score);
			}
		});

		for (MapEvent event : filteredEvents) {
			filteredScores.add(new String[] { event.ts.toString(),
					event.score.toString() });
		}

		String[][] list = new String[filteredEvents.size()][];
		for (int i = 0; i < filteredEvents.size(); i++) {
			list[i] = filteredScores.get(i);
		}
		return list;

	}
}
