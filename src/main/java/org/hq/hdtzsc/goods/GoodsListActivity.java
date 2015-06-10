package org.hq.hdtzsc.goods;

import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;

import org.hq.hdtzsc.R;
import org.hq.hdtzsc.base.BaseActivity;
import org.hq.hdtzsc.widget.LayoutGoodsList;

import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Description:
 * Author: WuRuiqiang(263454190@qq.com)
 * Date: 2015-06-10 10:36
 */
public class GoodsListActivity extends BaseActivity {

    private LayoutGoodsList layoutGoodsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_list);
        setMyTitle(R.string.goods_list);

        FrameLayout llGoodsList = (FrameLayout) findViewById(R.id.llGoodsList);

        String goodsId = getIntent().getStringExtra("goodsId");

        layoutGoodsList = new LayoutGoodsList(llGoodsList, this, goodsId);

        layoutGoodsList.start();
    }
}
