package org.hq.hdtzsc.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Description:
 * Author: WuRuiqiang(263454190@qq.com)
 * Date: 2015-06-05 18:17
 */
public class ToastFactory {

    public static void loadGoodsSortError(Context context) {
        Toast.makeText(context, "加载商品分类信息失败", Toast.LENGTH_SHORT).show();
    }

}
