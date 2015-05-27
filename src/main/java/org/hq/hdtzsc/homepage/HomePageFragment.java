package org.hq.hdtzsc.homepage;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.ViewPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import org.hq.hdtzsc.R;
import org.hq.hdtzsc.widget.SingleImageFragment;

import java.lang.ref.WeakReference;
import java.util.Timer;

/**
 * Description:
 * Author: WuRuiqiang
 * CreateDate: 2015/5/25-2:04
 * UpdateUser:
 * UpdateDate:
 * UpdateRemark:
 * Version: [v1.0]
 */
public class HomePageFragment extends Fragment {

    /**
     * 广告栏导航圆点
     */
    private SmartTabLayout stlAdTab;
    /**
     * 广告栏
     */
    private ViewPager vpAd;
    /**
     * 广告图片Url地址
     */
    private String[] adUrls = new String[]{"http://pic.58pic.com/58pic/16/76/08/17M58PICu9V_1024.jpg",
            "http://pic.58pic.com/58pic/16/76/08/17M58PICu9V_1024.jpg",
            "http://pic.58pic.com/58pic/16/76/08/17M58PICu9V_1024.jpg",
            "http://pic.58pic.com/58pic/16/76/08/17M58PICu9V_1024.jpg",
            "http://pic.58pic.com/58pic/16/76/08/17M58PICu9V_1024.jpg"};
    /**
     * 广告栏数据适配器
     */
    private FragmentPagerItemAdapter fragmentPagerItemAdapter;
    /**
     * 轮播广告图片间隔
     */
    private long lCarouselAdInterval = 1000L;
    private WeakReference<Handler> carouselAdHandler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_page, container, false);
        stlAdTab = (SmartTabLayout) view.findViewById(R.id.stlAdTab);
        vpAd = (ViewPager) view.findViewById(R.id.vpAd);
        initAd();
        return view;
    }

    /**
     * 初始化广告栏
     */
    private void initAd() {
        FragmentPagerItems.Creator creator = FragmentPagerItems.with(getActivity());
        for (String url : adUrls) {
            Bundle bundle = new Bundle();
            bundle.putString(SingleImageFragment.IMAGE_URL_KEY, url);
            creator.add("", SingleImageFragment.class, bundle);
        }

        fragmentPagerItemAdapter = new FragmentPagerItemAdapter(getChildFragmentManager(), creator.create());
        vpAd.setAdapter(fragmentPagerItemAdapter);
        stlAdTab.setViewPager(vpAd);
    }

    /**
     * 开始轮播广告图片
     */
    private void startCarouselAd() {
        carouselAdHandler = new WeakReference<Handler>(new Handler(Looper.myLooper()){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
//                postDelayed()
            }
        });

    }
}
