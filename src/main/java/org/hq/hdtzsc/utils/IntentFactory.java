package org.hq.hdtzsc.utils;

import android.content.Context;
import android.content.Intent;

import org.hq.hdtzsc.LoginActivity;
import org.hq.hdtzsc.RegisterActivity;
import org.hq.hdtzsc.goods.GoodsDetailActivity;
import org.hq.hdtzsc.goods.GoodsListActivity;
import org.hq.hdtzsc.goods.UploadGoodsActivity;

/**
 * Description:
 * Author: WuRuiqiang(263454190@qq.com)
 * Date: 2015-06-03 16:00
 */
public class IntentFactory {

    public static Intent getRegisterActivity(Context context) {
        return new Intent(context, RegisterActivity.class);
    }

    public static Intent getLoginActivity(Context context) {
        return new Intent(context, LoginActivity.class);
    }

    public static Intent getGoodsListActivity(Context context) {
        return new Intent(context, GoodsListActivity.class);
    }

    public static Intent getUploadGoodsActivity(Context context) {
        return new Intent(context, UploadGoodsActivity.class);
    }

    public static Intent getGoodsDetailActivity(Context context) {
        return new Intent(context, GoodsDetailActivity.class);
    }


}
