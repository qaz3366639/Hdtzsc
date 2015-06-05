package org.hq.hdtzsc.homepage;/**
 * Description:
 * Author:       WuRuiqiang (263454190@qq.com)
 * CreateDate:   2015/6/5-23:13
 * UpdateUser:
 * UpdateDate:
 * UpdateRemark:
 * Version:      [v1.0]
 */

import android.content.Context;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.hq.hdtzsc.R;
import org.hq.hdtzsc.bean.goodsSort;
import org.rc.rclibrary.adapter.RCBaseAdapter;
import org.rc.rclibrary.adapter.RCBaseViewHolder;

/**
 *
 * Description:  
 * Author:       WuRuiqiang (263454190@qq.com)   
 * CreateDate:   2015/6/5-23:13  
 * UpdateUser:   
 * UpdateDate:    
 * UpdateRemark: 
 * Version:      [v1.0] 
 */
public class HomePageSortAdapter extends RCBaseAdapter<goodsSort> {

    private int imageSize;

    public HomePageSortAdapter(Context context, int layoutId, int imageSize) {
        super(context, layoutId);
        this.imageSize = imageSize;
    }

    @Override
    protected void displayItem(RCBaseViewHolder viewHolder, int position) {
        super.displayItem(viewHolder, position);
        ImageView ivSort    = viewHolder.getView(R.id.ivSort);
        TextView tvSortName = viewHolder.getView(R.id.tvSortName);

        tvSortName.setText(data.get(position).getSortName());

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(imageSize, imageSize);
        lp.gravity = Gravity.CENTER_HORIZONTAL;
        ivSort.setLayoutParams(lp);

        if (data.get(position).getSortImage() != null) {
            data.get(position).getSortImage().loadImageThumbnail(context, ivSort, imageSize, imageSize);
        }
    }
}
