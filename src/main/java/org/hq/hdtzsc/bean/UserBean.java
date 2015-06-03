package org.hq.hdtzsc.bean;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Description:
 * Author: WuRuiqiang(263454190@qq.com)
 * Date: 2015-06-02 11:12
 */
public class UserBean extends BmobUser {

    private BmobFile userImage;

    private Number mobilePhone;

    public BmobFile getUserImage() {
        return userImage;
    }

    public void setUserImage(BmobFile userImage) {
        this.userImage = userImage;
    }

    public Number getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(Number mobilePhone) {
        this.mobilePhone = mobilePhone;
    }
}
