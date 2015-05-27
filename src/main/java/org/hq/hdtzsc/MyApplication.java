package org.hq.hdtzsc;

import android.app.Application;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

/**
 * Description:
 * Author: WuRuiqiang(263454190@qq.com)
 * Date: 2015-05-27 14:35
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initImageLoader();
    }

    private void initImageLoader() {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.mipmap.ic_load_image)
                .showImageOnLoading(R.mipmap.ic_load_image).
                        cacheInMemory(true).cacheOnDisk(true).build();
        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(this)
                .defaultDisplayImageOptions(options)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .diskCacheSize(1000 * 1024 * 1024) // 100 Mb
                .tasksProcessingOrder(QueueProcessingType.LIFO).build();
        ImageLoader.getInstance().init(configuration);
    }
}
