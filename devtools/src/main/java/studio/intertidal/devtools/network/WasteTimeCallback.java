package studio.intertidal.devtools.network;

import android.support.annotation.Nullable;

import okhttp3.Response;

/**
 * <p>耗時Callback</p>
 * <p>與{@link Callback}相似
 * 差異在於本方法會將回傳的Response物件於Background Thread回傳
 * 因此適合用在要建置大型JSONObject的情況</p>
 * <p>除此之外，由於會回傳完整的{@link Response}物件
 * 因此可以用來提取StatusCode,Body之外的其他所有物件</p>
 */
public interface WasteTimeCallback {
    /**
     * 對應到AsyncTask的onPreExecute()
     */
    void onRetrieverStart();

    /**
     * <p>收到Response物件時此方法會被呼叫</p>
     * 此時仍然在Network Thread，不可以操作UI
     *
     * @param response OkHttp回傳的物件，使用完畢記得執行{@link Response#close()}
     */
    void onResponseInBackground(@Nullable Response response);

    /**
     * 對應到AsyncTask的onPostExecute()
     */
    void onRetrieverFinish();

    /**
     * 當Response沒有正常回來的結果
     */
    void onRetrieverError();
}
