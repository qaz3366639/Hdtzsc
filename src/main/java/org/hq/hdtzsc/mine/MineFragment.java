package org.hq.hdtzsc.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.hq.hdtzsc.LoginActivity;
import org.hq.hdtzsc.R;
import org.hq.hdtzsc.base.BaseFragment;
import org.hq.hdtzsc.utils.ActivityStateCode;
import org.hq.hdtzsc.utils.IntentFactory;
import org.hq.hdtzsc.utils.LoginUtil;
import org.hq.hdtzsc.widget.LayoutGoodsList;

import cn.bmob.v3.BmobUser;

/**
 * Description:
 * Author: WuRuiqiang(263454190@qq.com)
 * Date: 2015-05-25 11:12
 */
public class MineFragment extends BaseFragment {

    private LinearLayout llNotLogin;

    private LinearLayout llIsLogin;

    private Button btnLogin;

    private TextView tvMyGoods;

    private FrameLayout flGoodsList;

    private LayoutGoodsList layoutGoodsList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view       = inflater.inflate(R.layout.fragment_mine, container, false);
        llNotLogin      = (LinearLayout) view.findViewById(R.id.llNotLogin);
        llIsLogin       = (LinearLayout) view.findViewById(R.id.llIsLogin);
        btnLogin        = (Button) view.findViewById(R.id.btnLogin);
        tvMyGoods       = (TextView) view.findViewById(R.id.tvMyGoods);
        flGoodsList     = (FrameLayout) view.findViewById(R.id.flGoodsList);

        return view;
    }

    @Override
    public void update() {
        if (!LoginUtil.isLogin(getActivity())) {
            llIsLogin.setVisibility(View.GONE);
            llNotLogin.setVisibility(View.VISIBLE);

            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LoginUtil.openLogin(MineFragment.this);
                }
            });
        } else {
            llIsLogin.setVisibility(View.VISIBLE);
            llNotLogin.setVisibility(View.GONE);

            layoutGoodsList = new LayoutGoodsList(flGoodsList, getActivity(), "userName",
                    BmobUser.getCurrentUser(getActivity()).getObjectId());
            layoutGoodsList.start();

            tvMyGoods.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    layoutGoodsList.start();
                }
            });
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ActivityStateCode.LOGIN_SUCCESS) {
            update();
        }
    }
}
