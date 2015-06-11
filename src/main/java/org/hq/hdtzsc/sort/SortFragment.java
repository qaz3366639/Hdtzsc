package org.hq.hdtzsc.sort;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import org.hq.hdtzsc.R;
import org.hq.hdtzsc.base.BaseFragment;
import org.hq.hdtzsc.bean.goodsSort;
import org.hq.hdtzsc.bean.goodsSortChild;
import org.hq.hdtzsc.utils.IntentFactory;
import org.hq.hdtzsc.utils.ToastFactory;
import org.rc.rclibrary.widget.NoScrollGridView;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Description:
 * Author: WuRuiqiang(263454190@qq.com)
 * Date: 2015-05-25 11:12
 */
public class SortFragment extends BaseFragment {

    private ListView lvSort;

    private SortAdapter sortAdapter;

    private NoScrollGridView gvChildSort;

    private ChildSortAdapter childSortAdapter;

    public int currentSortIndex = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view        = inflater.inflate(R.layout.fragment_sort, container, false);
        lvSort           = (ListView) view.findViewById(R.id.lvSort);
        gvChildSort      = (NoScrollGridView) view.findViewById(R.id.gvChildSort);
        sortAdapter      = new SortAdapter(getActivity(), R.layout.item_sort_list_parent);
        childSortAdapter = new ChildSortAdapter(getActivity(), R.layout.item_sort_list_child);

        lvSort.setAdapter(sortAdapter);
        sortAdapter.setLvSort(lvSort);
        gvChildSort.setAdapter(childSortAdapter);

        showChildSort();

        openGoodsList();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        measureChildSortWidth();
    }

    @Override
    public void update() {
        requestGoodsSort();
    }

    private void requestGoodsSort() {

        BmobQuery<goodsSort> bmobQuery = new BmobQuery<>();
        bmobQuery.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);
        bmobQuery.setMaxCacheAge(5 * 60 * 1000);
        bmobQuery.setLimit(50);
        bmobQuery.order("-sortName");
        bmobQuery.findObjects(getActivity(), new FindListener<goodsSort>() {
            @Override
            public void onSuccess(List<goodsSort> list) {
                sortAdapter.refresh(list);
                selectSort(currentSortIndex, true);
            }

            @Override
            public void onError(int i, String s) {
//                ToastFactory.loadGoodsSortError(getActivity());
            }
        });
    }

    private void measureChildSortWidth() {
        int width;
        width = gvChildSort.getMeasuredWidth() - (gvChildSort.getNumColumns() + 1) *
                getResources().getDimensionPixelOffset(R.dimen.child_sort_hor_spacing);
        gvChildSort.setColumnWidth(width);
    }

    private void showChildSort() {
        lvSort.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                currentSortIndex = position;
                selectSort(position, false);
            }
        });
    }

    public void selectSort(int position, boolean isSetItemState) {

        if (sortAdapter.getData().size() <= 0) {
            return;
        }

        if (isSetItemState) {
            lvSort.setItemChecked(position, true);
        }

        sortAdapter.notifyDataSetChanged();
        childSortAdapter.refresh(null);

        BmobQuery<goodsSortChild> bmobQuery = new BmobQuery<>();
        bmobQuery.addWhereEqualTo("parentSort", sortAdapter.getData().get(position)
                .getObjectId());
        bmobQuery.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);
        bmobQuery.setMaxCacheAge(5 * 60 * 1000);
        bmobQuery.setLimit(50);
        bmobQuery.findObjects(getActivity(), new FindListener<goodsSortChild>() {
            @Override
            public void onSuccess(List<goodsSortChild> list) {
                childSortAdapter.refresh(list);
            }

            @Override
            public void onError(int i, String s) {
//                        ToastFactory.loadGoodsSortError(getActivity());
            }
        });
    }

    /**
     * 打开商品列表
     */
    private void openGoodsList() {
        gvChildSort.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = IntentFactory.getGoodsListActivity(getActivity());
                intent.putExtra("goodsId", childSortAdapter.getData().get(position).getObjectId());
                startActivity(intent);
            }
        });
    }
}
