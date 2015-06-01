package org.hq.hdtzsc;

import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import org.hq.hdtzsc.homepage.HomePageFragment;
import org.hq.hdtzsc.mine.MineFragment;
import org.hq.hdtzsc.more.MoreFragment;
import org.hq.hdtzsc.sort.SortFragment;


public class MainActivity extends FragmentActivity {

    private int[] layout = new int[]{R.layout.item_tab_homepage, R.layout.item_tab_sort, R.layout.item_tab_mine, R.layout.item_tab_more};
    /**
     * 底部四个fragment数据适配器
     */
    private FragmentPagerItemAdapter adapter;
    /**
     * 底部Tab布局
     */
    private SmartTabLayout viewPagerTab;
    /**
     * 四个fragment的容器
     */
    private ViewPager viewPager;
    /**
     * 标题头text
     */
    private TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = (ViewPager) findViewById(R.id.vpContent);
        viewPagerTab = (SmartTabLayout) findViewById(R.id.stlTab);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvTitle.setText(getResources().getStringArray(R.array.tab_string_list)[0]);

        initFragment();
        setTabStyle();
    }

    /**
     * 初始化四个tab的fragment
     */
    private void initFragment() {
        adapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(), FragmentPagerItems.with(this)
                .add(null, HomePageFragment.class)
                .add(null, SortFragment.class)
                .add(null, MineFragment.class)
                .add(null, MoreFragment.class)
                .create());

        viewPager.setAdapter(adapter);
    }

    /**
     * 设置底部tab样式
     */
    private void setTabStyle() {
        //设置底部tab

        viewPagerTab.setCustomTabView(new SmartTabLayout.TabProvider() {
            @Override
            public View createTabView(ViewGroup viewGroup, int i, PagerAdapter pagerAdapter) {
                View tabView = LayoutInflater.from(MainActivity.this).inflate(layout[i], viewGroup, false);
                TextView tabTitleView = null;

                if(tabView != null) {
                    tabTitleView = (TextView)tabView.findViewById(R.id.tvTab);
                }

                if (tabTitleView != null) {
                    tabTitleView.setText(getResources().getStringArray(R.array.tab_string_list)[i]);
                }
                return tabView;
            }
        });
        viewPagerTab.setViewPager(viewPager);

        viewPagerTab.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tvTitle.setText(getResources().getStringArray(R.array.tab_string_list)[position]);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
