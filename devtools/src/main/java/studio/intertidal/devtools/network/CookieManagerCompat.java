package studio.intertidal.devtools.network;

import android.os.Build;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.ValueCallback;
import android.webkit.WebView;

/**
 * 與Cookie處理相關的物件
 */
public class CookieManagerCompat {
    private CookieSyncManager cookieSyncManager;
    private CookieManager cookieManager;

    private CookieManagerCompat() {

    }

    public static CookieManagerCompat getInstance(WebView webView) {
        CookieManagerCompat instance = new CookieManagerCompat();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            instance.cookieSyncManager = CookieSyncManager.createInstance(webView.getContext());
        }
        instance.cookieManager = CookieManager.getInstance();
        instance.acceptCookie(webView);
        return instance;
    }

    private void acceptCookie(WebView webView) {
        cookieManager.setAcceptCookie(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            cookieManager.setAcceptThirdPartyCookies(webView, true);
        }
    }

    /**
     * 添加Cookie
     *
     * @param domain 網站Domain Name
     * @param value  Cookie本體
     */
    public void addCookie(String domain, String value) {
        cookieManager.setCookie(domain, value);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP && cookieSyncManager != null) {
            cookieSyncManager.sync();
        }
    }

    /**
     * <p>從WebView內抽取Cookie並且保存起來</p>
     * 建議於{@link android.webkit.WebViewClient#onPageFinished(WebView, String)} 執行
     *
     * @param webView onPageFinished的第一個參數
     * @param url     onPageFinished的第二個參數
     */
    public void addCookieOnWebViewFinished(WebView webView, String url) {
        String cookie = cookieManager.getCookie(url);
        this.addCookie(url, cookie);
    }

    public void onPause() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP && cookieSyncManager != null) {
            cookieSyncManager.stopSync();
        }
    }

    public void onResume() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP && cookieSyncManager != null) {
            cookieSyncManager.startSync();
        }
    }

    public void onDestroy() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP && cookieSyncManager != null) {
            cookieSyncManager.resetSync();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            cookieManager.removeAllCookies(new ValueCallback<Boolean>() {
                @Override
                public void onReceiveValue(Boolean aBoolean) {

                }
            });
        } else {
            cookieManager.removeAllCookie();
        }
    }
}
