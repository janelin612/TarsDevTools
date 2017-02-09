package studio.intertidal.devtools.formatter;

public class Regex {
    /**
     * <p>過濾HTML tag</p>
     */
    public static final String HTML_TAG = "<[^>]*>";

    private static final String EMPTY = "";

    /**
     * 自原始字串中移除正規表達式撈到的所有結果
     *
     * @param original 原始字串
     * @param regex    正規表達式
     * @return 新的字串
     */
    public static String remove(String original, String regex) {
        if (original == null || regex == null) {
            throw new IllegalArgumentException(new NullPointerException());
        }
        return original.replaceAll(regex, EMPTY);
    }
}
