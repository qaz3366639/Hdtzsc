package org.hq.hdtzsc.homepage;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.ViewPagerItem;
import com.ogaclejapan.smarttablayout.utils.ViewPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.ViewPagerItems;

import org.hq.hdtzsc.R;

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
    private ViewPagerItemAdapter viewPagerItemAdapter;

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
        ViewPagerItems.Creator creator = ViewPagerItems.with(getActivity());
        for (String url : adUrls) {
            creator.add("", R.layout.ietm_home_page_ad);
        }

        viewPagerItemAdapter = new ViewPagerItemAdapter(creator.create());
        vpAd.setAdapter(viewPagerItemAdapter);
        stlAdTab.setViewPager(vpAd);
        stlAdTab.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                ImageLoader.getInstance().displayImage(adUrls[position],
                        (ImageView) viewPagerItemAdapter.getPage(position));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
