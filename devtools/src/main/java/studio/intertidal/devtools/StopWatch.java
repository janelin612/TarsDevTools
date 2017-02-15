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

    /**
     * 取得Singleton的實例
     */
    public static StopWatch getInstance() {
        if (instance == null) {
            instance = new StopWatch();
        }
        return instance;
    }

    /**
     * 寫入起始時間
     *
     * @param key 結束時用來取值的key
     */
    public void start(String key) {
        long start = Calendar.getInstance().getTimeInMillis();
        startMap.put(key, start);
    }

    /**
     * 計時結束
     * 並將結果印至LogCat上
     *
     * @param startKey 當初開始計時的時候存入的key
     * @param log      除了時間差(ms)之外要額外顯示的log訊息
     */
    public void end(String startKey, String log) {
        long duration = getDuration(startKey);
        Log.w(LOG_NAME, "" + log + "\nDuration: " + duration + " ms");
    }

    /**
     * 取得時間差
     *
     * @param startKey 當初開始計時的時候存入的key
     * @return 毫秒
     */
    public long getDuration(String startKey) {
        long start = startMap.get(startKey);
        return Calendar.getInstance().getTimeInMillis() - start;
    }
}