package studio.intertidal.devtools.network;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.util.Calendar;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okio.Buffer;


public class Retriever {
    private static boolean isDebugMode = false;
    private static OkHttpClient client;

    /**
     * 設定是否要顯示Debug訊息
     *
     * @param isDebugMode 是/否?
     */
    public static void setIsDebugMode(boolean isDebugMode) {
        Retriever.isDebugMode = isDebugMode;
    }

    /**
     * 自外部將OkhttpClient導入
     * 非必要執行的方法
     *
     * @param client 已經初始化好的OkHttpClient
     */
    public static void setClient(OkHttpClient client) {
        Retriever.client = client;
    }

    private static OkHttpClient getClientInstance() {
        if (client == null) {
            client = new OkHttpClient();
        }
        return client;
    }

    /**
     * 封裝OkHttp連線的方法
     *
     * @param request  要發送的Request
     * @param callback 實作callback
     */
    public static void call(Request request, Callback callback) {
        new OkHttpAsyncTask(callback, request).execute();
    }

    private static class OkHttpAsyncTask extends AsyncTask<Object, Object, OkHttpAsyncTask.Result> {
        private static final String LOG_TAG = "Retriever";
        private Callback callback;
        private Request request;
        private long startTime;

        /**
         * @param callback callback interface
         * @param request  request object
         */
        OkHttpAsyncTask(Callback callback, Request request) {
            this.callback = callback;
            this.request = request;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (isDebugMode) {
                startTime = Calendar.getInstance().getTimeInMillis();
                printRequestDebugLog();
            }
            if (callback != null) callback.onRetrieverStart();
        }

        @Override
        protected Result doInBackground(Object... objects) {
            Response response = null;
            try {
                response = getClientInstance().newCall(request).execute();
                Result result = new Result();
                result.status = response.code();
                result.body = response.body().string();
                return result;
            } catch (IOException e) {
                return null;
            } finally {
                if (response != null) {
                    response.close();
                }
            }
        }

        @Override
        protected void onPostExecute(Result result) {
            super.onPostExecute(result);
            if (result == null) {
                if (isDebugMode) {
                    printResponseErrorLog("Response is null");
                }
                if (callback != null) callback.onRetrieverError();
            } else {
                if (isDebugMode) {
                    printResponseDebugLog(result);
                }
                if (callback != null) callback.onRetrieverFinish(result.status, result.body);
            }
        }

        /**
         * 印出發送前的Debug所需資訊
         */
        private void printRequestDebugLog() {
            Buffer buffer = new Buffer();
            Log.d(LOG_TAG, "╒════════════════════════════════════════════════════════════════════");
            Log.d(LOG_TAG, "│ URL: [" + request.method() + "] " + request.url());
            try {
                request.body().writeTo(buffer);
                Log.d(LOG_TAG, "│ RegqBody: " + buffer.readUtf8());
            } catch (IOException e) {
                Log.e(LOG_TAG, "│ Error: build request body fail :( " + e.toString());
            } catch (NullPointerException e) {
                Log.d(LOG_TAG, "│ RegqBody: empty");
            }
            Log.d(LOG_TAG, "├────────────────────────────────────────────────────────────────────");
            buffer.close();
        }

        /**
         * 印出發送失敗的錯誤訊息
         *
         * @param msg 要顯示的訊息
         */
        private void printResponseErrorLog(String msg) {
            Log.e(LOG_TAG, "│ Error: " + msg);
            Log.d(LOG_TAG, "│ Duration: " + (Calendar.getInstance().getTimeInMillis() - startTime) + "ms");
            Log.d(LOG_TAG, "└────────────────────────────────────────────────────────────────────");
        }

        /**
         * 印出發送成功的Debug訊息
         *
         * @param result Result物件
         */
        private void printResponseDebugLog(Result result) {
            Log.d(LOG_TAG, "│ Status: " + result.status);
            Log.d(LOG_TAG, "│ RespBody: " + result.body);
            Log.d(LOG_TAG, "│ Duration: " + (Calendar.getInstance().getTimeInMillis() - startTime) + "ms");
            Log.d(LOG_TAG, "└────────────────────────────────────────────────────────────────────");
        }

        class Result {
            int status;
            String body;
        }
    }
}
