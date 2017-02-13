package studio.intertidal.devtools;

import android.util.Log;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * 計時器
 */
public class StopWatch {
    private static final String LOG_NAME = "StopWatch";
    private static StopWatch instance;
    private Map<String, Long> startMap;

    private StopWatch() {
        this.startMap = new HashMap<>();
    }

    public static StopWatch getInstance() {
        if (instance == null) {
            instance = new StopWatch();
        }
        return instance;
    }

    public void start(String key) {
        long start = Calendar.getInstance().getTimeInMillis();
        startMap.put(key, start);
    }

    public void end(String startKey, String log) {
        long start = startMap.get(startKey);
        long duration = Calendar.getInstance().getTimeInMillis() - start;
        Log.w(LOG_NAME, "" + log + "\nDuration: " + duration + " ms");
    }
}