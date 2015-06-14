package org.hq.hdtzsc.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Description:
 * Author: WuRuiqiang(263454190@qq.com)
 * Date: 2015-06-10 16:23
 */
public class Goods extends BmobObject {

    private String goodsName;

    private Float goodsPrice;

    private UserBean userName;

    private String goodsImage1;

    private String goodsImage2;

    private String goodsImage3;

    private String goodsImage4;

    private String sortName;

    private goodsSortChild goodsSort;

    private goodsSort goodsParentSort;

    private boolean shelve;

    public String getSortName() {
        return sortName;
    }

    public void setSortName(String sortName) {
        this.sortName = sortName;
    }

    public void setShelve(boolean shelve) {
        this.shelve = shelve;
    }

    public boolean isShelve() {
        return shelve;
    }

    public void setIsUnShelve(boolean shelve) {
        this.shelve = shelve;
    }

    public org.hq.hdtzsc.bean.goodsSort getGoodsParentSort() {
        return goodsParentSort;
    }

    public void setGoodsParentSort(org.hq.hdtzsc.bean.goodsSort goodsParentSort) {
        this.goodsParentSort = goodsParentSort;
    }

    private String goodsSmallImage;

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Float getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(Float goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public UserBean getUserName() {
        return userName;
    }

    public void setUserName(UserBean userName) {
        this.userName = userName;
    }

    public String getGoodsImage1() {
        return goodsImage1;
    }

    public void setGoodsImage1(String goodsImage1) {
        this.goodsImage1 = goodsImage1;
    }

    public String getGoodsImage2() {
        return goodsImage2;
    }

    public void setGoodsImage2(String goodsImage2) {
        this.goodsImage2 = goodsImage2;
    }

    public String getGoodsImage3() {
        return goodsImage3;
    }

    public void setGoodsImage3(String goodsImage3) {
        this.goodsImage3 = goodsImage3;
    }

    public String getGoodsImage4() {
        return goodsImage4;
    }

    public void setGoodsImage4(String goodsImage4) {
        this.goodsImage4 = goodsImage4;
    }

    public goodsSortChild getGoodsSort() {
        return goodsSort;
    }

    public void setGoodsSort(goodsSortChild goodsSort) {
        this.goodsSort = goodsSort;
    }

    public String getGoodsSmallImage() {
        return goodsSmallImage;
    }

    public void setGoodsSmallImage(String goodsSmallImage) {
        this.goodsSmallImage = goodsSmallImage;
    }
}
