package org.hq.hdtzsc.base;/**
 * Description:
 * Author:       WuRuiqiang (263454190@qq.com)
 * CreateDate:   2015/6/1-0:48
 * UpdateUser:
 * UpdateDate:
 * UpdateRemark:
 * Version:      [v1.0]
 */

import android.support.v4.app.Fragment;

import org.hq.hdtzsc.R;

import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.header.RentalsSunHeaderView;

/**
 *
 * Description:  
 * Author:       WuRuiqiang (263454190@qq.com)   
 * CreateDate:   2015/6/1-0:48  
 * UpdateUser:   
 * UpdateDate:    
 * UpdateRemark: 
 * Version:      [v1.0] 
 */
public class BaseFragment extends Fragment{

    protected PtrFrameLayout mPtrFrameLayout;

    @Override
    public void onResume() {
        super.onResume();
        if (getView() == null) {
            return;
        }
        mPtrFrameLayout = (PtrFrameLayout) getView().findViewById(R.id.pflTitle);
        if (mPtrFrameLayout != null) {
            initPullTitle();
        }
    }

    protected void initPullTitle() {
        RentalsSunHeaderView rentalsSunHeaderView = new RentalsSunHeaderView(getActivity());

        rentalsSunHeaderView.setUp(mPtrFrameLayout);
        mPtrFrameLayout.setLoadingMinTime(0);
        mPtrFrameLayout.setDurationToCloseHeader(1000);
        mPtrFrameLayout.setHeaderView(rentalsSunHeaderView);
        mPtrFrameLayout.addPtrUIHandler(rentalsSunHeaderView);
        mPtrFrameLayout.setKeepHeaderWhenRefresh(false);
    }
}
