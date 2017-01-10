package studio.intertidal.devtools;

import android.content.Context;
import android.graphics.Bitmap;

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class ImageLoaderUtil {

    public static ImageLoaderConfiguration getDefaultConfig(Context context, int cacheSize) {
        DisplayImageOptions myOptions = buildDefaultOptions();
        return getDefaultConfigBuilder(context)
                .diskCacheSize(1024 * 1024 * cacheSize)
                .defaultDisplayImageOptions(myOptions)
                .build();
    }

    private static DisplayImageOptions buildDefaultOptions() {
        return new DisplayImageOptions.Builder()
                .bitmapConfig(Bitmap.Config.RGB_565) //for less memory use
                .resetViewBeforeLoading(true) //for recyclerViewHolder.ImageView
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();
    }

    private static ImageLoaderConfiguration.Builder getDefaultConfigBuilder(Context context) {
        return new ImageLoaderConfiguration.Builder(context)
                .memoryCache(new WeakMemoryCache()) //for better gc
                .threadPoolSize(4); // for less memory use
    }
}
