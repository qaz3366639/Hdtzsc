package org.hq.hdtzsc.bean;

import cn.bmob.v3.datatype.BmobFile;

/**
 * Description:
 * Author: WuRuiqiang(263454190@qq.com)
 * Date: 2015-06-10 16:23
 */
public class Goods {

    private String goodsName;

    private Float goodsPrice;

    private UserBean userName;

    private BmobFile goodsImage1;

    private BmobFile goodsImage2;

    private BmobFile goodsImage3;

    private BmobFile goodsImage4;

    private goodsSortChild goodsSort;

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

    public BmobFile getGoodsImage1() {
        return goodsImage1;
    }

    public void setGoodsImage1(BmobFile goodsImage1) {
        this.goodsImage1 = goodsImage1;
    }

    public BmobFile getGoodsImage2() {
        return goodsImage2;
    }

    public void setGoodsImage2(BmobFile goodsImage2) {
        this.goodsImage2 = goodsImage2;
    }

    public BmobFile getGoodsImage3() {
        return goodsImage3;
    }

    public void setGoodsImage3(BmobFile goodsImage3) {
        this.goodsImage3 = goodsImage3;
    }

    public BmobFile getGoodsImage4() {
        return goodsImage4;
    }

    public void setGoodsImage4(BmobFile goodsImage4) {
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
