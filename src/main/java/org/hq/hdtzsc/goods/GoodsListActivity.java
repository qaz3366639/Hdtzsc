package org.hq.hdtzsc.goods;

import android.os.Bundle;
import android.widget.ListView;

import org.hq.hdtzsc.R;
import org.hq.hdtzsc.base.BaseActivity;

/**
 * Description:
 * Author: WuRuiqiang(263454190@qq.com)
 * Date: 2015-06-10 10:36
 */
public class GoodsListActivity extends BaseActivity {

    private ListView lvSort;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_list);
        setMyTitle(R.string.goods_list);

        lvSort = (ListView) findViewById(R.id.lvSort);

    }
}
