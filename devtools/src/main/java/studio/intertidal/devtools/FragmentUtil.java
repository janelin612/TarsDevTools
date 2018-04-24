package studio.intertidal.devtools;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.inputmethod.InputMethodManager;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class FragmentUtil {

    /**
     * 取回目前畫面上的Fragment
     *
     * @param manager Activity持有的FragmentManager
     * @return 若出錯會回傳null
     * @deprecated 似乎有問題 待debug
     */
    @Deprecated
    public static Fragment getTopFragment(FragmentManager manager) {
        if (manager == null) return null;

        try {
            int pointer = manager.getBackStackEntryCount();
            return manager.getFragments().get(pointer);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 封裝{@link android.support.v4.app.FragmentTransaction#replace(int, Fragment)}
     *
     * @param manager  Activity持有的FragmentManager
     * @param id       FrameLayout的ID
     * @param fragment 要插入的Fragment
     */
    public static void quickReplace(FragmentManager manager, int id, Fragment fragment) {
        manager.beginTransaction()
                .replace(id, fragment)
                .addToBackStack(null)
                .commit();
    }

    /**
     * 從Fragment關閉鍵盤
     *
     * @param fragment 目前開啟的Fragment
     */
    public static void closeKeyboard(Fragment fragment) {
        closeKeyboard(fragment.getActivity());
    }

    /**
     * 從Fragment關閉鍵盤
     *
     * @param activity Activity
     */
    public static void closeKeyboard(Activity activity) {
        try {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 退回到剩下最原始的第一個Fragment
     *
     * @param manager Activity持有的FragmentManager
     */
    public static void popBackToTop(FragmentManager manager) {
        int count = manager.getBackStackEntryCount();
        for (int i = 0; i < count; ++i) {
            clearFragmentAtSameBackStack(manager);
            manager.popBackStackImmediate();
        }
    }

    /**
     * 處理同一個BackStack階段同時存在多個Fragment於畫面上的問題
     *
     * @param manager
     */
    private static void clearFragmentAtSameBackStack(FragmentManager manager) {
        int count = manager.getFragments().size();
        if (count > 1) {
            FragmentTransaction transaction = manager.beginTransaction();
            for (int i = 0; i < count; i++) {
                transaction.remove(manager.getFragments().get(i));
            }
            transaction.commit();
        }
    }
}
