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

    public static void uploadGoodsInfoFail(Context context) {
        Toast.makeText(context, "上传商品信息失败", Toast.LENGTH_SHORT).show();
    }

    public static void uploadGoodsInfoSuccess(Context context) {
        Toast.makeText(context, "上传商品信息成功", Toast.LENGTH_SHORT).show();
    }

    public static void updateGoodsInfoFail(Context context) {
        Toast.makeText(context, "修改商品信息失败", Toast.LENGTH_SHORT).show();
    }

    public static void updateGoodsInfoSuccess(Context context) {
        Toast.makeText(context, "修改商品信息成功", Toast.LENGTH_SHORT).show();
    }

    public static void pleaseUploadGoodsInfo(Context context) {
        Toast.makeText(context, "请先上传商品基本信息", Toast.LENGTH_SHORT).show();
    }

    public static void uploadGoodsImageSuccess(Context context) {
        Toast.makeText(context, "上传商品图片成功", Toast.LENGTH_SHORT).show();
    }

    public static void uploadGoodsImageFail(Context context) {
        Toast.makeText(context, "上传商品图片失败", Toast.LENGTH_SHORT).show();
    }

    public static void currentVersionIsNew(Context context) {
        Toast.makeText(context, "当前已是最新版本", Toast.LENGTH_SHORT).show();
    }

    public static void checkYourNetWord(Context context) {
        Toast.makeText(context, "请先检测您的网络", Toast.LENGTH_SHORT).show();
    }
}
