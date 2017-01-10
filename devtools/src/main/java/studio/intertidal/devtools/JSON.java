package studio.intertidal.devtools;


import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Set;

/**
 * <p>自行封裝的JSON解析器</p>
 * 主要是為了逃避惱人的{@link JSONException}問題
 */
public class JSON {

    /**
     * 取出字串
     *
     * @param father       要取值的JSONObject
     * @param key          要取值的key
     * @param defaultValue 遇到問題時塞入的預設值 可為null
     * @return 成功時回傳取到的值 失敗回傳defaultValue
     */
    public static String getString(JSONObject father, String key, @Nullable String defaultValue) {
        String output;
        try {
            output = father.getString(key);
        } catch (JSONException e) {
            output = defaultValue;
        }
        return output;
    }

    /**
     * 取出整數
     *
     * @param father       要取值的JSONObject
     * @param key          要取值的key
     * @param defaultValue 遇到問題時塞入的預設值
     * @return 成功時回傳取到的值 失敗回傳defaultValue傳入的東西
     */
    public static int getInt(JSONObject father, String key, int defaultValue) {
        try {
            return father.getInt(key);
        } catch (JSONException e) {
            return defaultValue;
        }
    }

    /**
     * 取出長整數
     *
     * @param father       要取值的JSONObject
     * @param key          要取值的key
     * @param defaultValue 遇到問題時塞入的預設值
     * @return 成功時回傳取到的值 失敗回傳defaultValue傳入的東西
     */
    public static long getLong(JSONObject father, String key, long defaultValue) {
        try {
            return father.getLong(key);
        } catch (JSONException e) {
            return defaultValue;
        }
    }

    /**
     * 取出Double
     *
     * @param father       要取值的JSONObject
     * @param key          要取值的key
     * @param defaultValue 遇到問題時塞入的預設值
     * @return 成功時回傳取到的值 失敗回傳defaultValue傳入的東西
     */
    public static double getDouble(JSONObject father, String key, double defaultValue) {
        try {
            return father.getDouble(key);
        } catch (JSONException e) {
            return defaultValue;
        }
    }

    /**
     * 取出布林值
     *
     * @param father       要取值的JSONObject
     * @param key          要取值的key
     * @param defaultValue 遇到問題時塞入的預設值
     * @return 成功時回傳取到的值 失敗回傳defaultValue傳入的東西
     */
    public static boolean getBoolean(JSONObject father, String key, boolean defaultValue) {
        try {
            return father.getBoolean(key);
        } catch (JSONException e) {
            return defaultValue;
        }
    }

    /**
     * <p>從一個JSONArray裡面找到第一個包含指定key-value的物件並回傳</p>
     * 例如有個陣列[{"name":"a"},{"name":"b"}]<br/>
     * 那呼叫findSpecifiedInArray(array,"name","b")<br/>
     * 會回傳第二個物件
     *
     * @param array 要尋找的陣列
     * @param key   要尋找的欄位
     * @param value 要尋找的值
     * @return 成功回傳該物件 失敗回傳null
     */
    @Nullable
    public static JSONObject findSpecifiedInArray(JSONArray array, String key, Object value) {
        int length = array.length();
        for (int i = 0; i < length; i++) {
            try {
                JSONObject object = array.getJSONObject(i);
                if (object.get(key).equals(value)) {
                    return object;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * <p>從一個JSONArray裡面找到第一個包含指定key-value的物件並回傳</p>
     * 其判斷邏輯是包含({@link String#contains(CharSequence)}) <br/>
     * 而非相等({@link String#equals(Object)})
     * <br/><br/>
     * 例如有個陣列[{"name":"asdfg"},{"name":"zxcvb"}] <br/>
     * 呼叫findSpecifiedInArray(array,"name","b") <br/>
     * 會回傳第二個物件
     *
     * @param array 要尋找的陣列
     * @param key   要尋找的欄位
     * @param value 要尋找的值
     * @return 成功回傳該物件 失敗回傳null
     */
    @Nullable
    public static JSONObject findSimilarInArray(JSONArray array, String key, String value) {
        int length = array.length();
        for (int i = 0; i < length; i++) {
            try {
                JSONObject object = array.getJSONObject(i);
                if (object.getString(key).contains(value)) {
                    return object;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 封裝{@link JSONException}的JSONObject產生器
     */
    public static class ObjectBuilder {
        private static final String LOG_TAG = "JSON.ObjectBuilder";
        private JSONObject output;
        private ArrayMap<String, Object> map;

        /**
         * 建構子
         *
         * @param json 預先傳入的json物件
         */
        public ObjectBuilder(String json) {
            map = new ArrayMap<>();
            try {
                output = new JSONObject(json);
            } catch (JSONException e) {
                Log.e(LOG_TAG, e.toString());
                output = new JSONObject();
            }
        }

        /**
         * 建構子
         */
        public ObjectBuilder() {
            output = new JSONObject();
            map = new ArrayMap<>();
        }

        /**
         * 壓入物件
         *
         * @param key   key值
         * @param value 要押入的物件
         */
        public ObjectBuilder put(String key, Object value) {
            map.put(key, value);
            return this;
        }

        /**
         * 壓入字串
         *
         * @param key   key值
         * @param value 要押入的字串
         */
        public ObjectBuilder put(String key, String value) {
            map.put(key, value);
            return this;
        }

        /**
         * 建構物件
         *
         * @return 建立好的JSONObject
         */
        public JSONObject build() {
            Set<String> keys = map.keySet();
            try {
                for (String key : keys) {
                    output.put(key, map.get(key));
                }
            } catch (JSONException e) {
                Log.e(LOG_TAG, e.toString());
            } finally {
                map.clear();
            }
            return output;
        }
    }

}
