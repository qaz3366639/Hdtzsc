package org.hq.hdtzsc.widget;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.hq.hdtzsc.R;
import org.hq.hdtzsc.bean.Goods;
import org.hq.hdtzsc.goods.GoodsListAdapter;

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

        MaterialHeader header = new MaterialHeader(context);

//        header.setColorSchemeColors(colors);
        header.setPadding(0, 30, 0, 30);
        header.setPtrFrameLayout(pflGoodsList);

        pflGoodsList.setHeaderView(header);
        pflGoodsList.addPtrUIHandler(header);

        loadMoreListViewContainer = (LoadMoreListViewContainer) llGoodsList.findViewById(R.id.load_more_list_view_container);
        loadMoreListViewContainer.useDefaultHeader();

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
                requestGoodsList();
            }
        });
    }

    public void start() {
        showViewState(ViewState.LOADING);
    }

    private void requestGoodsList() {

        BmobQuery<Goods> bmobQuery = new BmobQuery<>();
        bmobQuery.addWhereEqualTo("goodsSort", goodsId);
        bmobQuery.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);
        bmobQuery.setMaxCacheAge(5 * 60 * 1000);
        bmobQuery.setLimit(PAGE_ITEM_COUNT);

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
                pflGoodsList.refreshComplete();
                goodsListAdapter.refresh(list);
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
                        loadMoreListViewContainer.loadMoreFinish(false, true);
                    } else {
                        loadMoreListViewContainer.loadMoreFinish(false, false);
                    }
                }
                goodsListAdapter.load(list);
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
                pflGoodsList.autoRefresh();
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
