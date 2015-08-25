package org.hq.hdtzsc.sort;

import android.content.Context;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import org.hq.hdtzsc.R;
import org.hq.hdtzsc.bean.goodsSort;
import org.rc.rclibrary.adapter.RCBaseAdapter;
import org.rc.rclibrary.adapter.RCBaseViewHolder;

/**
 * Description:
 * Author: WuRuiqiang(263454190@qq.com)
 * Date: 2015-06-09 16:26
 */
public class SortAdapter extends RCBaseAdapter<goodsSort> {

    private ListView lvSort;

    public SortAdapter(Context context, int layoutId) {
        super(context, layoutId);
    }

    @Override
    protected void displayItem(RCBaseViewHolder viewHolder, int position) {
        TextView tvSortName = viewHolder.getView(R.id.tvSortName);
        tvSortName.setText(data.get(position).getSortName());
        tvSortName.setSelected(lvSort.getCheckedItemPositions().get(position));
        View view = viewHolder.getView(R.id.vDivider);
        view.setSelected(lvSort.getCheckedItemPositions().get(position));
    }

    public ListView getLvSort() {
        return lvSort;
    }

    public void setLvSort(ListView lvSort) {
        this.lvSort = lvSort;
    }
}
