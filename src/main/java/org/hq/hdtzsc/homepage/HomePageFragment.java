package org.hq.hdtzsc.homepage;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import org.hq.hdtzsc.R;
import org.hq.hdtzsc.base.BaseFragment;
import org.hq.hdtzsc.bean.goodsSort;
import org.hq.hdtzsc.widget.SingleImageFragment;
import org.rc.rclibrary.widget.NoScrollGridView;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Description:
 * Author: WuRuiqiang
 * CreateDate: 2015/5/25-2:04
 * UpdateUser:
 * UpdateDate:
 * UpdateRemark:
 * Version: [v1.0]
 */
public class HomePageFragment extends BaseFragment {

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
            "http://pic4.nipic.com/20091021/2647736_112912049567_2.jpg",
            "http://pic5.nipic.com/20100226/4174632_102000007517_2.jpg",
            "http://pic12.nipic.com/20110211/3334559_094414394318_2.jpg",
            "http://pic7.nipic.com/20100617/2318085_152320096158_2.jpg"};
    /**
     * 广告栏数据适配器
     */
    private FragmentPagerItemAdapter fragmentPagerItemAdapter;
    /**
     * 轮播广告图片间隔
     */
    private long lCarouselAdInterval = 2000L;

    private NoScrollGridView gvSort;

    private HomePageSortAdapter homePageSortAdapter;

    private Handler carouselAdHandler;
    private Timer timer;

    private TimerTask carouselAdTask;
    private Runnable carouselAdRunnable;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        timer = new Timer(true);
        carouselAdHandler = new Handler();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view   = inflater.inflate(R.layout.fragment_home_page, container, false);
        stlAdTab    = (SmartTabLayout) view.findViewById(R.id.stlAdTab);
        vpAd        = (ViewPager) view.findViewById(R.id.vpAd);
        gvSort      = (NoScrollGridView) view.findViewById(R.id.gvSort);

        initAd();

        startCarouselAd();

        measureSortWidth();

        requestGoodsSort();
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

        if (timer != null) {
            if (carouselAdTask != null) {
                carouselAdTask.cancel();
            }

            carouselAdRunnable = new Runnable() {
                @Override
                public void run() {
                    vpAd.setCurrentItem((vpAd.getCurrentItem() + 1) % fragmentPagerItemAdapter.getCount(), false);
                }
            };

            carouselAdTask = new TimerTask() {
                @Override
                public void run() {
                    carouselAdHandler.post(carouselAdRunnable);
                }
            };

            timer.schedule(carouselAdTask, 0, lCarouselAdInterval);
        }
    }

    private void measureSortWidth() {

        int width, column, horSpace;
        column = getResources().getInteger(R.integer.home_page_sort_column_count);
        horSpace = getResources().getDimensionPixelOffset(R.dimen.home_page_sort_hor_spacing);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        width = (displayMetrics.widthPixels - horSpace * (column + 1)) / column;
        gvSort.setColumnWidth(width);

        homePageSortAdapter = new HomePageSortAdapter(getActivity(), R.layout.item_home_page_sort
                , width / 2);
        gvSort.setAdapter(homePageSortAdapter);
    }

    private void requestGoodsSort() {
        BmobQuery<goodsSort> bmobQuery = new BmobQuery<goodsSort>();
        bmobQuery.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);
        bmobQuery.setLimit(50);
        bmobQuery.order("-sortName");
        bmobQuery.findObjects(getActivity(), new FindListener<goodsSort>() {
            @Override
            public void onSuccess(List<goodsSort> list) {
                homePageSortAdapter.refresh(list);
            }

            @Override
            public void onError(int i, String s) {
                Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show();
//                ToastFactory.loadGoodsSortError(getActivity());
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (carouselAdTask != null) {
            carouselAdTask.cancel();
        }
    }
}
