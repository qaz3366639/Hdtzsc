package org.hq.hdtzsc.widget;

import android.content.Context;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.ogaclejapan.smarttablayout.SmartTabLayout;

import org.hq.hdtzsc.R;
import org.hq.hdtzsc.bean.Goods;
import org.hq.hdtzsc.goods.GoodsListAdapter;

import cn.bmob.v3.BmobQuery;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.header.MaterialHeader;

/**
 * Description:
 * Author: WuRuiqiang(263454190@qq.com)
 * Date: 2015-06-10 14:42
 */
public class LayoutGoodsList {

    private Context context;

    private PtrFrameLayout pflGoodsList;

    private RelativeLayout rlEmpty;

    private RelativeLayout rlFail;

    private ListView lvGoodsList;

    private GoodsListAdapter goodsListAdapter;

    private String goodsId;

    public LayoutGoodsList(PtrFrameLayout pflGoodsList, Context context, String goodsId) {

        if (pflGoodsList == null) {
            throw new IllegalArgumentException("pflGoodsList must not be null");
        }

        if (context == null) {
            throw new IllegalArgumentException("context must not be null");
        }

        if (goodsId == null) {
            throw new IllegalArgumentException("goodsId must not be null");
        }

        this.pflGoodsList     = pflGoodsList;
        this.context          = context;
        this.goodsId          = goodsId;
        this.goodsListAdapter = new GoodsListAdapter(context, R.layout.item_goods_list);

        initView();
    }

    private void initView() {
        MaterialHeader header = new MaterialHeader(context);

//        header.setColorSchemeColors(colors);
        header.setLayoutParams(new PtrFrameLayout.LayoutParams(-1, -2));
        header.setPtrFrameLayout(pflGoodsList);

        pflGoodsList.setHeaderView(header);
        pflGoodsList.addPtrUIHandler(header);


        rlEmpty     = (RelativeLayout) pflGoodsList.findViewById(R.id.rlEmpty);
        rlFail      = (RelativeLayout) pflGoodsList.findViewById(R.id.rlFail);
        lvGoodsList = (ListView) pflGoodsList.findViewById(R.id.lvSort);
        lvGoodsList.setAdapter(goodsListAdapter);
    }

    private void requestGoodsList() {
        BmobQuery<Goods> bmobQuery = new BmobQuery<>();

        bmobQuery.addWhereEqualTo("goodsSort", goodsId);
        bmobQuery.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);
        bmobQuery.setMaxCacheAge(5 * 60 * 1000);
        bmobQuery.setLimit(20);
    }
}
