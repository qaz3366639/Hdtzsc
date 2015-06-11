package org.hq.hdtzsc.goods;

import android.os.Bundle;

import org.hq.hdtzsc.R;
import org.hq.hdtzsc.base.BaseActivity;

/**
 * Description:
 * Author: WuRuiqiang(263454190@qq.com)
 * Date: 2015-06-11 18:22
 */
public class UploadGoodsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_goods);
        setMyTitle(R.string.upload_goods);
    }
}
