package studio.intertidal.devtools.network;

/**
 * <p>簡單Callback</p>
 * <p>配合{@link android.os.AsyncTask}的生命週期設計
 * 適用於大多數情況</p>
 */
public interface Callback {
    /**
     * 對應到AsyncTask的onPreExecute()
     */
    void onRetrieverStart();

    /**
     * 當Response有正常回來時會被呼叫
     *
     * @param statusCode 回來的status code
     * @param body       回來的response body
     */
    void onRetrieverFinish(int statusCode, String body);

    /**
     * 當Response沒有正常回來的結果
     */
    void onRetrieverError();
}
