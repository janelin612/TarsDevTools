package studio.intertidal.devtools.recyclerview;

import android.app.Activity;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;


/**
 * 簡單分隔線
 */
public class SimpleItemDecoration extends RecyclerView.ItemDecoration {
    private final int[] attr = {android.R.attr.listDivider};
    private Drawable divider;

    public SimpleItemDecoration(Activity activity) {
        TypedArray typedArray = activity.obtainStyledAttributes(attr);
        divider = typedArray.getDrawable(0);
        typedArray.recycle();
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();

        int length = parent.getChildCount();
        for (int i = 0; i < length; i++) {
            View childView = parent.getChildAt(i);
            try {
                int top = childView.getBottom();
                int bottom = top + divider.getIntrinsicHeight();
                divider.setBounds(left, top, right, bottom);
                divider.draw(c);
            } catch (NullPointerException e) {
                Log.w(getClass().getSimpleName(), e.toString());
            }
        }
    }
}
