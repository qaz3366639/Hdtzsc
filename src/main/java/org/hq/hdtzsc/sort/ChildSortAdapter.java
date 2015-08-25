package org.hq.hdtzsc.sort;

import android.content.Context;
import android.widget.TextView;

import org.hq.hdtzsc.R;
import org.hq.hdtzsc.bean.goodsSortChild;
import org.rc.rclibrary.adapter.RCBaseAdapter;
import org.rc.rclibrary.adapter.RCBaseViewHolder;

/**
 * Description:
 * Author: WuRuiqiang(263454190@qq.com)
 * Date: 2015-06-09 16:32
 */
public class ChildSortAdapter extends RCBaseAdapter<goodsSortChild> {

    public ChildSortAdapter(Context context, int layoutId) {
        super(context, layoutId);
    }

    @Override
    protected void displayItem(RCBaseViewHolder viewHolder, int position) {
        TextView tvSortName = viewHolder.getView(R.id.tvSortName);
        tvSortName.setText(data.get(position).getGoodsSortChildName());
    }
}
