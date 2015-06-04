package org.hq.hdtzsc.utils;/**
 * Description:
 * Author:       WuRuiqiang (263454190@qq.com)
 * CreateDate:   2015/6/5-0:16
 * UpdateUser:
 * UpdateDate:
 * UpdateRemark:
 * Version:      [v1.0]
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import org.hq.hdtzsc.bean.UserBean;

import cn.bmob.v3.BmobUser;

/**
 *
 * Description:  
 * Author:       WuRuiqiang (263454190@qq.com)   
 * CreateDate:   2015/6/5-0:16  
 * UpdateUser:   
 * UpdateDate:    
 * UpdateRemark: 
 * Version:      [v1.0] 
 */
public class LoginUtil {

    /**
     * 判断APP当前登录状态
     * @param context 上下文
     */
    public static boolean isLogin(Context context) {

        UserBean userBean = BmobUser.getCurrentUser(context, UserBean.class);
        if (userBean == null) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 打开登录界面
     * @param context
     */
    public static void openLogin(Context context) {
        Intent intent = IntentFactory.getLoginActivity(context);
        if (context instanceof Activity) {
            ((Activity) context).startActivityForResult(intent, 0);
        } else {
            context.startActivity(intent);
        }
    }

    /**
     * 打开登录界面
     * @param fragment
     */
    public static void openLogin(Fragment fragment) {

        Intent intent = IntentFactory.getLoginActivity(fragment.getActivity());
        fragment.startActivityForResult(intent, 0);
    }

}
