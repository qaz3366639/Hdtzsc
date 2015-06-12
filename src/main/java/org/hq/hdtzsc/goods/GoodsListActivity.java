package org.hq.hdtzsc.goods;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.hq.hdtzsc.R;
import org.hq.hdtzsc.base.BaseActivity;
import org.hq.hdtzsc.widget.LayoutGoodsList;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Description:
 * Author: WuRuiqiang(263454190@qq.com)
 * Date: 2015-06-10 10:36
 */
public class GoodsListActivity extends BaseActivity {

    private LayoutGoodsList layoutGoodsList;

    private LinearLayout llGoodsFilter;

    private final String[] GOODS_LIST_ORDER = new String[]{"-goodsPrice", "goodsPrice"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_list);
        setMyTitle(R.string.goods_list);

        llGoodsFilter           = (LinearLayout) findViewById(R.id.llGoodsFilter);
        FrameLayout flGoodsList = (FrameLayout) findViewById(R.id.flGoodsList);

        String goodsId          = getIntent().getStringExtra("goodsId");

        layoutGoodsList         = new LayoutGoodsList(flGoodsList, this, goodsId);

        layoutGoodsList.start();

        initFilter();
    }

    private void initFilter() {
        final List<TextView> textViews = new ArrayList<>(0);
        for (int i = 0; i < llGoodsFilter.getChildCount(); i++) {
            LinearLayout linearLayout = (LinearLayout) llGoodsFilter.getChildAt(i);
            TextView textView = (TextView) linearLayout.getChildAt(0);
            textViews.add(textView);
        }
        for (int i = 0; i < textViews.size(); i++) {
            final int temp = i;
            textViews.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (int j = 0; j < textViews.size(); j++) {
                        textViews.get(j).setSelected(temp == j);
                    }
                    layoutGoodsList.requestGoodsList(false, GOODS_LIST_ORDER[temp]);
                }
            });
        }
    }
}
