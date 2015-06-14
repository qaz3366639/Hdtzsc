package org.hq.hdtzsc.goods;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import org.hq.hdtzsc.R;
import org.hq.hdtzsc.base.BaseFragmentActivity;
import org.hq.hdtzsc.bean.Goods;
import org.hq.hdtzsc.bean.UserBean;
import org.hq.hdtzsc.utils.IntentFactory;
import org.hq.hdtzsc.utils.ToastFactory;
import org.hq.hdtzsc.widget.SingleImageFragment;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.GetListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 *
 * Description:  
 * Author:       WuRuiqiang (263454190@qq.com)   
 * CreateDate:   2015/6/13-0:05  
 * UpdateUser:   
 * UpdateDate:    
 * UpdateRemark: 
 * Version:      [v1.0] 
 */
public class GoodsDetailActivity extends BaseFragmentActivity {

    public static final String GOODS = "goods";

    /**
     * 商品图片导航圆点
     */
    private SmartTabLayout stlGoodsImage;
    /**
     * 商品图片栏
     */
    private ViewPager vpGoodsImage;

    private TextView tvGoodsName;

    private TextView tvGoodsPrice;

    private TextView tvSeller;

    private TextView tvMobilePhone;

    private TextView tvDial;

    private LinearLayout llBottom;

    private Button btnEditGoods;

    private Button btnShelve;

    private List<String> images;

    private Goods mGoods;

    private UserBean mUserBean;

    private FragmentPagerItemAdapter fragmentPagerItemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_detail);
        setMyTitle(R.string.goods_detail);

        stlGoodsImage = (SmartTabLayout) findViewById(R.id.stlGoodsImage);
        vpGoodsImage  = (ViewPager) findViewById(R.id.vpGoodsImage);
        tvGoodsName   = (TextView) findViewById(R.id.tvGoodsName);
        tvGoodsPrice  = (TextView) findViewById(R.id.tvGoodsPrice);
        tvSeller      = (TextView) findViewById(R.id.tvSeller);
        tvMobilePhone = (TextView) findViewById(R.id.tvMobilePhone);
        tvDial        = (TextView) findViewById(R.id.tvDial);
        llBottom      = (LinearLayout) findViewById(R.id.llBottom);
        btnEditGoods  = (Button) findViewById(R.id.btnEditGoods);
        btnShelve    = (Button) findViewById(R.id.btnShelve);

        mGoods        = (Goods) getIntent().getSerializableExtra(GOODS);
        images        = new ArrayList<>();
        mUserBean     = new UserBean();

        if (mGoods == null) {
            throw new IllegalArgumentException("mGoods must not be null");
        }

        if (mGoods.getGoodsImage1() != null) {
            images.add(mGoods.getGoodsImage1());
        }

        if (mGoods.getGoodsImage2() != null) {
            images.add(mGoods.getGoodsImage2());
        }

        if (mGoods.getGoodsImage3() != null) {
            images.add(mGoods.getGoodsImage3());
        }

        if (mGoods.getGoodsImage4() != null) {
            images.add(mGoods.getGoodsImage4());
        }

        initGoodsImage();

        BmobQuery<UserBean> beanBmobQuery = new BmobQuery<>();
        beanBmobQuery.getObject(this, mGoods.getUserName().getObjectId(), new GetListener<UserBean>() {
            @Override
            public void onSuccess(UserBean userBean) {
                mUserBean = userBean;
                displayGoodsInfo();
            }

            @Override
            public void onFailure(int i, String s) {

            }
        });

    }

    private void initGoodsImage() {
        FragmentPagerItems.Creator creator = FragmentPagerItems.with(this);
        for (String url : images) {
            Bundle bundle = new Bundle();
            bundle.putString(SingleImageFragment.IMAGE_URL_KEY, url);
            creator.add("", SingleImageFragment.class, bundle);
        }

        fragmentPagerItemAdapter = new FragmentPagerItemAdapter(getSupportFragmentManager(), creator.create());
        vpGoodsImage.setAdapter(fragmentPagerItemAdapter);
        stlGoodsImage.setViewPager(vpGoodsImage);
    }

    private void displayGoodsInfo() {
        tvGoodsName.setText(mGoods.getGoodsName());
        tvGoodsPrice.setText("￥" + mGoods.getGoodsPrice());
        tvSeller.setText("卖家：" + mUserBean.getUsername());
        tvMobilePhone.setText("手机号码：" + mUserBean.getMobilePhoneNumber());

        tvDial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + tvMobilePhone.getText()
                        .toString()));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        if (BmobUser.getCurrentUser(this) == null) {
            llBottom.setVisibility(View.GONE);
        } else if (BmobUser.getCurrentUser(this).getObjectId().equals(mGoods.getUserName()
                .getObjectId())){
            llBottom.setVisibility(View.VISIBLE);
            shelveOrUnShelve();
            btnShelve.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Goods goods = new Goods();
                    goods.setIsUnShelve(!mGoods.isShelve());
                    goods.update(GoodsDetailActivity.this, mGoods.getObjectId(), new UpdateListener() {
                        @Override
                        public void onSuccess() {
                            mGoods.setIsUnShelve(!mGoods.isShelve());
                            shelveOrUnShelve();
                        }

                        @Override
                        public void onFailure(int i, String s) {
                            ToastFactory.requestFail(GoodsDetailActivity.this);
                        }
                    });
                }
            });

            btnEditGoods.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = IntentFactory.getUploadGoodsActivity(GoodsDetailActivity.this);
                    intent.putExtra(GOODS, mGoods);
                    startActivity(intent);
                }
            });
        }

    }

    private void shelveOrUnShelve() {
        if (mGoods.isShelve()) {
            btnShelve.setText(R.string.shelve);
        } else {
            btnShelve.setText(R.string.unshelve);
        }
    }
}
