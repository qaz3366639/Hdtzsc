package org.hq.hdtzsc.more;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ecloud.pulltozoomview.PullToZoomScrollViewEx;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import org.hq.hdtzsc.R;
import org.hq.hdtzsc.base.BaseFragment;
import org.hq.hdtzsc.bean.UserBean;
import org.hq.hdtzsc.utils.ActivityStateCode;
import org.hq.hdtzsc.utils.IntentFactory;
import org.hq.hdtzsc.utils.LoginUtil;
import org.hq.hdtzsc.utils.ToastFactory;
import org.rc.rclibrary.utils.NetWorkState;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;

/**
 * Description:
 * Author: WuRuiqiang(263454190@qq.com)
 * Date: 2015-05-25 11:13
 */
public class MoreFragment extends BaseFragment {

    private PullToZoomScrollViewEx ptzsvHead;

    private LinearLayout llActionButton;

    private TextView tvRegister;

    private TextView tvLogin;

    private ImageView ivUserImage;

    private TextView tvUserName;

    private TextView tvClearCache;

    private TextView tvCheckUpdate;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_more, container, false);
        initView(view);

        return view;
    }

    private void initView(View view) {

        ptzsvHead       = (PullToZoomScrollViewEx) view.findViewById(R.id.ptzsvHead);
        llActionButton  = (LinearLayout) view.findViewById(R.id.llActionButton);
        tvRegister      = (TextView) view.findViewById(R.id.tvRegister);
        tvLogin         = (TextView) view.findViewById(R.id.tvLogin);
        ivUserImage     = (ImageView) view.findViewById(R.id.ivUserImage);
        tvUserName      = (TextView) view.findViewById(R.id.tvUserName);
        tvClearCache    = (TextView) view.findViewById(R.id.tvClearCache);
        tvCheckUpdate   = (TextView) view.findViewById(R.id.tvCheckUpdate);

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginUtil.openLogin(MoreFragment.this);
            }
        });

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = IntentFactory.getRegisterActivity(getActivity());
                startActivity(intent);
            }
        });

        tvClearCache.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BmobQuery.clearAllCachedResults(getActivity());
                ToastFactory.clearCacheSuccess(getActivity());
            }
        });

        tvCheckUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NetWorkState.isNetworkConnected(getActivity())) {
                    ToastFactory.currentVersionIsNew(getActivity());
                } else {
                    ToastFactory.checkYourNetWord(getActivity());
                }
            }
        });
    }

    @Override
    public void update() {
        isShowLogin();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ActivityStateCode.LOGIN_SUCCESS) {
            isShowLogin();
        }
    }

    private void isShowLogin() {

        UserBean userBean = BmobUser.getCurrentUser(getActivity(), UserBean.class);
        if (userBean != null) {
            llActionButton.setVisibility(View.INVISIBLE);
            tvUserName.setText(userBean.getUsername());
            if (userBean.getUserImage() != null) {
                DisplayImageOptions options = new DisplayImageOptions.Builder().displayer(
                        new RoundedBitmapDisplayer(getResources()
                                .getDimensionPixelOffset(R.dimen.more_user_image_rd))).build();
                ImageLoader.getInstance().displayImage(userBean.getUserImage().getFileUrl(getActivity()),
                        ivUserImage, options);
            }
        } else {
            llActionButton.setVisibility(View.VISIBLE);
        }

    }
}
