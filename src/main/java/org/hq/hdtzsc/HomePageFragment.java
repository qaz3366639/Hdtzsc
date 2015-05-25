package org.hq.hdtzsc;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Description:
 * Author: WuRuiqiang
 * CreateDate: 2015/5/25-2:04
 * UpdateUser:
 * UpdateDate:
 * UpdateRemark:
 * Version: [v1.0]
 */
public class HomePageFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_page, container, false);
        return view;
    }
}
