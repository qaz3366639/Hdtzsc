package org.hq.hdtzsc.bean;

import cn.bmob.v3.BmobObject;

/**
 * Description:
 * Author: WuRuiqiang(263454190@qq.com)
 * Date: 2015-06-09 16:37
 */
public class goodsSortChild extends BmobObject {

    private String goodsSortChildName;

    private goodsSort parentSort;

    public String getGoodsSortChildName() {
        return goodsSortChildName;
    }

    public void setGoodsSortChildName(String goodsSortChildName) {
        this.goodsSortChildName = goodsSortChildName;
    }

    public goodsSort getParentSort() {
        return parentSort;
    }

    public void setParentSort(goodsSort parentSort) {
        this.parentSort = parentSort;
    }
}
