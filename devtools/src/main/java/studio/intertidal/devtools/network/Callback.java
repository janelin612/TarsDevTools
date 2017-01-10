package studio.intertidal.devtools.network;

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
