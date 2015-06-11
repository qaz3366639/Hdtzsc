package org.hq.hdtzsc.goods;

import android.content.Context;
import android.widget.TextView;

import org.hq.hdtzsc.R;
import org.hq.hdtzsc.bean.goodsSort;
import org.rc.rclibrary.adapter.RCBaseAdapter;
import org.rc.rclibrary.adapter.RCBaseViewHolder;

/**
 *
 * Description:  
 * Author:       WuRuiqiang (263454190@qq.com)   
 * CreateDate:   2015/6/11-23:08  
 * UpdateUser:   
 * UpdateDate:    
 * UpdateRemark: 
 * Version:      [v1.0] 
 */
public class ParentSortAdapter extends RCBaseAdapter<goodsSort> {

    public ParentSortAdapter(Context context, int layoutId) {
        super(context, layoutId);
    }

    @Override
    protected void displayItem(RCBaseViewHolder viewHolder, int position) {
        super.displayItem(viewHolder, position);
        TextView tvSortName = viewHolder.getView(R.id.tvSortName);
        tvSortName.setText(data.get(position).getSortName());
    }
}
