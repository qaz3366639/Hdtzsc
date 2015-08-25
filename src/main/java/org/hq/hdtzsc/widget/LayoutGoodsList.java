package org.hq.hdtzsc.widget;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import org.hq.hdtzsc.R;
import org.hq.hdtzsc.bean.Goods;
import org.hq.hdtzsc.goods.GoodsDetailActivity;
import org.hq.hdtzsc.goods.GoodsListAdapter;
import org.hq.hdtzsc.utils.IntentFactory;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import in.srain.cube.views.loadmore.LoadMoreContainer;
import in.srain.cube.views.loadmore.LoadMoreHandler;
import in.srain.cube.views.loadmore.LoadMoreListViewContainer;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.MaterialHeader;

/**
 * Description:
 * Author: WuRuiqiang(263454190@qq.com)
 * Date: 2015-06-10 14:42
 */
public class LayoutGoodsList {

    private Context mContext;

    private FrameLayout flGoodsList;

    private PtrFrameLayout pflGoodsList;

    private LoadMoreListViewContainer loadMoreListViewContainer;

    private RelativeLayout rlEmpty;

    private RelativeLayout rlFail;

    private ListView lvGoodsList;

    private GoodsListAdapter goodsListAdapter;

    private MaterialHeader header;
    /**
     * 分类ID
     */
    private String sortId;
    /**
     * 条件ID
     */
    private String conditionId;
    /**
     * 条件值
     */
    private String condition;
    String order;

    private ViewState viewState = ViewState.LOADING;

    private final int PAGE_ITEM_COUNT = 20;

    public LayoutGoodsList(FrameLayout flGoodsList, Context context, String sortId) {

        if (flGoodsList == null) {
            throw new IllegalArgumentException("flGoodsList must not be null");
        }

        if (context == null) {
            throw new IllegalArgumentException("context must not be null");
        }

        if (sortId == null) {
            throw new IllegalArgumentException("sortId must not be null");
        }

        this.sortId          = sortId;

        initView(flGoodsList, context);
    }

    public LayoutGoodsList(FrameLayout flGoodsList, Context context, String conditionId, String condition) {

        if (flGoodsList == null) {
            throw new IllegalArgumentException("flGoodsList must not be null");
        }

        if (context == null) {
            throw new IllegalArgumentException("context must not be null");
        }

        if (conditionId == null) {
            throw new IllegalArgumentException("conditionId must not be null");
        }

        if (condition == null) {
            throw new IllegalArgumentException("condition must not be null");
        }

        this.conditionId = conditionId;
        this.condition   = condition;

        initView(flGoodsList, context);
    }

    private void initView(FrameLayout flGoodsList, Context context) {

        this.flGoodsList      = flGoodsList;
        this.mContext         = context;
        this.goodsListAdapter = new GoodsListAdapter(context, R.layout.item_goods_list);

        rlEmpty      = (RelativeLayout) flGoodsList.findViewById(R.id.rlEmpty);
        rlFail       = (RelativeLayout) flGoodsList.findViewById(R.id.rlFail);
        pflGoodsList = (PtrFrameLayout) flGoodsList.findViewById(R.id.pflGoodsList);
        lvGoodsList  = (ListView) flGoodsList.findViewById(R.id.lvGoodsList);
        lvGoodsList.setAdapter(goodsListAdapter);

        //设置下拉刷新的head
        if ((header = (MaterialHeader) pflGoodsList.getHeaderView()) == null) {
            MaterialHeader header = new MaterialHeader(context);
            int[] colors = mContext.getResources().getIntArray(R.array.google_colors);
            header.setColorSchemeColors(colors);
            header.setPadding(0, 30, 0, 30);
            header.setPtrFrameLayout(pflGoodsList);
            pflGoodsList.setHeaderView(header);
            pflGoodsList.addPtrUIHandler(header);
        }

        //设置上拉加载更多的footer
        loadMoreListViewContainer = (LoadMoreListViewContainer) flGoodsList
                .findViewById(R.id.load_more_list_view_container);
        if (lvGoodsList.getFooterViewsCount() == 0) {
            loadMoreListViewContainer.useDefaultHeader();
        }

        loadMoreListViewContainer.setLoadMoreHandler(new LoadMoreHandler() {
            @Override
            public void onLoadMore(LoadMoreContainer loadMoreContainer) {
                loadGoodsList();
            }
        });

        pflGoodsList.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout ptrFrameLayout, View view, View view1) {
                return PtrDefaultHandler.checkContentCanBePulledDown(ptrFrameLayout, lvGoodsList, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout ptrFrameLayout) {
                requestGoodsList(false, order);
            }
        });

        lvGoodsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = IntentFactory.getGoodsDetailActivity(mContext);
                intent.putExtra(GoodsDetailActivity.GOODS, goodsListAdapter.getData().get(position));
                mContext.startActivity(intent);
            }
        });
    }

    public void start() {
        pflGoodsList.postDelayed(new Runnable() {
            @Override
            public void run() {
                pflGoodsList.autoRefresh(false);
            }
        }, 100);
    }

    /**
     * 请求商品列表
     * @param isLoad true：加载更多商品 false：刷新商品列表
     * @param order 商品列表排序条件
     */
    public void requestGoodsList(final boolean isLoad, String order) {

        this.order = order;

        showViewState(ViewState.LOADING);

        BmobQuery<Goods> bq1 = new BmobQuery<>();
        BmobQuery<Goods> bq2 = new BmobQuery<>();
        if (sortId != null) {
            bq1.addWhereEqualTo("goodsSort", sortId);
            //未下架的商品
            bq2.addWhereEqualTo("shelve", false);
        } else {
            bq1.addWhereEqualTo(conditionId, condition);
        }

        //组装完整的and条件
        List<BmobQuery<Goods>> andQuerys = new ArrayList<>();
        andQuerys.add(bq1);
        andQuerys.add(bq2);
        BmobQuery<Goods> bmobQuery = new BmobQuery<>();
        bmobQuery.and(andQuerys);
        bmobQuery.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);
        bmobQuery.setMaxCacheAge(5 * 60 * 1000);
        bmobQuery.setLimit(PAGE_ITEM_COUNT);
        bmobQuery.order(order);

        bmobQuery.findObjects(mContext, new FindListener<Goods>() {
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

        BmobQuery<Goods> bq1 = new BmobQuery<>();
        BmobQuery<Goods> bq2 = new BmobQuery<>();
        if (sortId != null) {
            bq1.addWhereEqualTo("goodsSort", sortId);
            //未下架的商品
            bq2.addWhereEqualTo("shelve", false);
        }else {
            bq1.addWhereEqualTo(conditionId, condition);
        }

        //组装完整的and条件
        List<BmobQuery<Goods>> andQuerys = new ArrayList<>();
        andQuerys.add(bq1);
        andQuerys.add(bq2);
        BmobQuery<Goods> bmobQuery = new BmobQuery<>();
        bmobQuery.and(andQuerys);
        bmobQuery.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);
        bmobQuery.setMaxCacheAge(5 * 60 * 1000);
        bmobQuery.setLimit(PAGE_ITEM_COUNT);
        bmobQuery.setSkip(goodsListAdapter.getCount());
        bmobQuery.order(order);

        bmobQuery.findObjects(mContext, new FindListener<Goods>() {
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

    public PtrFrameLayout getPflGoodsList() {
        return pflGoodsList;
    }

    public enum ViewState {
        EMPTY, FAIL, LOADING, SUCCESS
    }
}
