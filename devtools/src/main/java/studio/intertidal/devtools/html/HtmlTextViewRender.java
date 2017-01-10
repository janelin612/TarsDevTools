package studio.intertidal.devtools.html;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.text.Spanned;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * 將帶有圖片的HTML文件渲染到TextView上面的工具
 */
public class HtmlTextViewRender {
    private Activity activity;

    private TextView textView;
    private String htmlString;
    private Spanned spanned;

    private int manualWidth = MANUAL_WIDTH_NOT_DEFINE;
    public static final int MANUAL_WIDTH_NOT_DEFINE = -2;


    private Runnable uiThread = new Runnable() {
        @Override
        public void run() {
            textView.setText(spanned);
        }
    };

    private Runnable networkThread = new Runnable() {
        @Override
        public void run() {
            spanned = Html.fromHtml(htmlString, new Html.ImageGetter() {
                @Override
                public Drawable getDrawable(String s) {
                    Bitmap bitmap = ImageLoader.getInstance().loadImageSync(s);
                    int height = bitmap.getHeight();
                    int width = bitmap.getWidth();
                    if (manualWidth != MANUAL_WIDTH_NOT_DEFINE) {
                        float ratio = manualWidth / ((float) width);
                        height = (int) (ratio * height);
                        width = manualWidth;
                    }
                    Drawable drawable = new BitmapDrawable(activity.getResources(), bitmap);
                    drawable.setBounds(0, 0, width, height);
                    return drawable;
                }
            }, null);
            activity.runOnUiThread(uiThread);
        }
    };

    public HtmlTextViewRender(Activity activity) {
        this.activity = activity;
    }

    /**
     * 執行渲染
     * 本方法會先以無圖片的方式渲染一次加快顯示效果
     * 然後才會正式於其他thread開始撈圖片
     * 並於撈出來之後重新繪製一次
     *
     * @param textView   預備被繪製的TextView
     * @param htmlString 帶有html元素的string
     */
    public void render(TextView textView, String htmlString) {
        this.textView = textView;
        this.htmlString = htmlString;
        textView.setText(Html.fromHtml(htmlString));
        new Thread(networkThread).start();
    }


    /**
     * 執行渲染
     * 並且自行指定圖片寬度
     * 指定後高度會自行縮放
     *
     * @param textView    預備被繪製的TextView
     * @param htmlString  帶有html元素的string
     * @param manualWidth 指定的圖片寬度
     */
    public void render(TextView textView, String htmlString, int manualWidth) {
        this.manualWidth = manualWidth;
        this.render(textView, htmlString);
    }
}
