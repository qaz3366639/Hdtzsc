package org.hq.hdtzsc.goods;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import org.hq.hdtzsc.R;
import org.hq.hdtzsc.bean.Goods;
import org.rc.rclibrary.adapter.RCBaseAdapter;
import org.rc.rclibrary.adapter.RCBaseViewHolder;

/**
 * Description:
 * Author: WuRuiqiang(263454190@qq.com)
 * Date: 2015-06-10 16:22
 */
public class GoodsListAdapter extends RCBaseAdapter<Goods> {

    public GoodsListAdapter(Context context, int layoutId) {
        super(context, layoutId);
    }

    @Override
    protected void displayItem(RCBaseViewHolder viewHolder, int position) {
        super.displayItem(viewHolder, position);
        ImageView ivGoodsImage = viewHolder.getView(R.id.ivGoodsImage);
        TextView tvGoodsName = viewHolder.getView(R.id.tvGoodsName);
        TextView tvGoodsPrice = viewHolder.getView(R.id.tvGoodsPrice);

        tvGoodsName.setText(data.get(position).getGoodsName());
        tvGoodsPrice.setText("￥" + data.get(position).getGoodsPrice());
        ImageLoader.getInstance().displayImage(data.get(position).getGoodsImage1().getFileUrl(context),
                ivGoodsImage);
    }
}
