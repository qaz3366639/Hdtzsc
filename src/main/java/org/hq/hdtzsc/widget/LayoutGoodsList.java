package org.hq.hdtzsc.widget;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.hq.hdtzsc.R;
import org.hq.hdtzsc.bean.Goods;
import org.hq.hdtzsc.goods.GoodsListAdapter;
import org.hq.hdtzsc.utils.IntentFactory;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import in.srain.cube.views.loadmore.LoadMoreContainer;
import in.srain.cube.views.loadmore.LoadMoreHandler;
import in.srain.cube.views.loadmore.LoadMoreListViewContainer;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.MaterialHeader;

/**
 * Description:
 * Author: WuRuiqiang(263454190@qq.com)
 * Date: 2015-06-10 14:42
 */
public class LayoutGoodsList {

    private Context context;

    private FrameLayout llGoodsList;

    private PtrFrameLayout pflGoodsList;

    private LoadMoreListViewContainer loadMoreListViewContainer;

    private RelativeLayout rlEmpty;

    private RelativeLayout rlFail;

    private ListView lvGoodsList;

    private GoodsListAdapter goodsListAdapter;

    private String goodsId;

    private ViewState viewState = ViewState.LOADING;

    private final int PAGE_ITEM_COUNT = 20;

    public LayoutGoodsList(FrameLayout llGoodsList, Context context, String goodsId) {

        if (llGoodsList == null) {
            throw new IllegalArgumentException("llGoodsList must not be null");
        }

        if (context == null) {
            throw new IllegalArgumentException("context must not be null");
        }

        if (goodsId == null) {
            throw new IllegalArgumentException("goodsId must not be null");
        }

        this.llGoodsList     = llGoodsList;
        this.context          = context;
        this.goodsId          = goodsId;
        this.goodsListAdapter = new GoodsListAdapter(context, R.layout.item_goods_list);

        initView();
    }

    private void initView() {

        rlEmpty      = (RelativeLayout) llGoodsList.findViewById(R.id.rlEmpty);
        rlFail       = (RelativeLayout) llGoodsList.findViewById(R.id.rlFail);
        pflGoodsList = (PtrFrameLayout) llGoodsList.findViewById(R.id.pflGoodsList);
        lvGoodsList  = (ListView) llGoodsList.findViewById(R.id.lvGoodsList);
        lvGoodsList.setAdapter(goodsListAdapter);
//        lvGoodsList.setFooterDividersEnabled(false);

        //设置下拉刷新的head
        MaterialHeader header = new MaterialHeader(context);
//        header.setColorSchemeColors(colors);
        header.setPadding(0, 30, 0, 30);
        header.setPtrFrameLayout(pflGoodsList);
        pflGoodsList.setHeaderView(header);
        pflGoodsList.addPtrUIHandler(header);

        //设置上拉加载更多的footer
        loadMoreListViewContainer = (LoadMoreListViewContainer) llGoodsList.findViewById(R.id.load_more_list_view_container);
        loadMoreListViewContainer.useDefaultHeader();
        lvGoodsList.setFooterDividersEnabled(false);

        loadMoreListViewContainer.setLoadMoreHandler(new LoadMoreHandler() {
            @Override
            public void onLoadMore(LoadMoreContainer loadMoreContainer) {
                loadGoodsList();
            }
        });

        pflGoodsList.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout ptrFrameLayout, View view, View view1) {
                return viewState != ViewState.LOADING;
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout ptrFrameLayout) {
                requestGoodsList(false, null);
            }
        });

        lvGoodsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = IntentFactory.getUploadGoodsActivity(context);
                context.startActivity(intent);
            }
        });
    }

    /**
     * 请求商品列表
     * @param isLoad true：加载更多商品 false：刷新商品列表
     * @param order 商品列表排序条件
     */
    public void requestGoodsList(final boolean isLoad, String order) {

        showViewState(ViewState.LOADING);

        BmobQuery<Goods> bmobQuery = new BmobQuery<>();
        bmobQuery.addWhereEqualTo("goodsSort", goodsId);
        bmobQuery.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);
        bmobQuery.setMaxCacheAge(5 * 60 * 1000);
        bmobQuery.setLimit(PAGE_ITEM_COUNT);
        bmobQuery.order(order);

        bmobQuery.findObjects(context, new FindListener<Goods>() {
            @Override
            public void onSuccess(List<Goods> list) {
                if (list.size() == 0) {
                    showViewState(ViewState.EMPTY);
                } else {
                    showViewState(ViewState.SUCCESS);

                    if (list.size() < PAGE_ITEM_COUNT) {
                        loadMoreListViewContainer.loadMoreFinish(false, false);
                    } else {
                        loadMoreListViewContainer.loadMoreFinish(false, true);
                    }
                }
                if (!isLoad) {
                    pflGoodsList.refreshComplete();
                    goodsListAdapter.refresh(list);
                } else {
                    goodsListAdapter.load(list);
                }
            }

            @Override
            public void onError(int i, String s) {
                showViewState(ViewState.FAIL);
            }
        });
    }

    private void loadGoodsList() {

        BmobQuery<Goods> bmobQuery = new BmobQuery<>();
        bmobQuery.addWhereEqualTo("goodsSort", goodsId);
        bmobQuery.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);
        bmobQuery.setMaxCacheAge(5 * 60 * 1000);
        bmobQuery.setLimit(PAGE_ITEM_COUNT);
        bmobQuery.setSkip(goodsListAdapter.getCount());

        bmobQuery.findObjects(context, new FindListener<Goods>() {
            @Override
            public void onSuccess(List<Goods> list) {
                if (list.size() == 0) {
                    showViewState(ViewState.EMPTY);
                } else {
                    showViewState(ViewState.SUCCESS);

                    if (list.size() < PAGE_ITEM_COUNT) {
                        loadMoreListViewContainer.loadMoreFinish(false, false);
                    } else {
                        loadMoreListViewContainer.loadMoreFinish(false, true);
                    }
                }

            }

            @Override
            public void onError(int i, String s) {
                showViewState(ViewState.FAIL);
            }
        });
    }

    private void showViewState(ViewState viewState) {
        this.viewState = viewState;

        switch (viewState) {
            case LOADING:
                lvGoodsList.setVisibility(View.GONE);
                rlEmpty.setVisibility(View.GONE);
                rlFail.setVisibility(View.GONE);
                break;
            case SUCCESS:
                lvGoodsList.setVisibility(View.VISIBLE);
                rlEmpty.setVisibility(View.GONE);
                rlFail.setVisibility(View.GONE);
                break;
            case EMPTY:
                lvGoodsList.setVisibility(View.GONE);
                rlEmpty.setVisibility(View.VISIBLE);
                rlFail.setVisibility(View.GONE);
                break;
            case FAIL:
                lvGoodsList.setVisibility(View.GONE);
                rlEmpty.setVisibility(View.GONE);
                rlFail.setVisibility(View.VISIBLE);
                break;
        }
    }

    public enum ViewState {
        EMPTY, FAIL, LOADING, SUCCESS
    }
}
