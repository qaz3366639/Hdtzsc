package org.hq.hdtzsc.service.impl;

import org.hq.hdtzsc.bean.goodsSort;
import org.hq.hdtzsc.service.IFindService;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Description:
 * Author: WuRuiqiang(263454190@qq.com)
 * Date: 2015-06-09 16:17
 */
public class GoodsSortImpl implements IFindService {

    private BmobQuery<goodsSort> bmobQuery;

    public GoodsSortImpl() {
        BmobQuery<goodsSort> bmobQuery = new BmobQuery<>();
        bmobQuery.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);
        bmobQuery.setMaxCacheAge(5 * 60 * 1000);
        bmobQuery.setLimit(50);
        bmobQuery.order("-sortName");
    }

    @Override
    public void request(FindListener findListener) {

    }
}
