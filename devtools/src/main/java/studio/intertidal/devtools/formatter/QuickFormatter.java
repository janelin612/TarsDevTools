package studio.intertidal.devtools.formatter;

import android.support.annotation.Nullable;

import java.text.DecimalFormat;

/**
 * 快速文字格式化工具
 */
public class QuickFormatter {
    private static DecimalFormat moneyFormatter;

    /**
     * 對金額格式化 標記千分位
     *
     * @param dollar 要被標記的金額
     * @return 每三位數一個逗號
     */
    public static String formatMoney(int dollar) {
        if (moneyFormatter == null) {
            moneyFormatter = new DecimalFormat("#,###");
        }

        return moneyFormatter.format(dollar);
    }

    /**
     * 對金額格式化 標記千分位
     *
     * @param prefix 前置字串 可為null
     * @param dollar 金額
     * @param suffix 後置字串 可為null
     * @return 每三位數一個逗號 並於數字前後加上傳入的字元
     */
    public static StringBuilder formatMoney(@Nullable String prefix, int dollar, @Nullable String suffix) {
        StringBuilder builder = new StringBuilder();
        if (prefix != null) {
            builder.append(prefix);
        }
        builder.append(formatMoney(dollar));
        if (suffix != null) {
            builder.append(suffix);
        }

        return builder;
    }
}
