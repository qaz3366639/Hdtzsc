package org.hq.hdtzsc.base;

import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.hq.hdtzsc.R;

/**
 *
 * Description:  
 * Author:       WuRuiqiang (263454190@qq.com)   
 * CreateDate:   2015/6/13-0:22  
 * UpdateUser:   
 * UpdateDate:    
 * UpdateRemark: 
 * Version:      [v1.0] 
 */
public class BaseFragmentActivity extends FragmentActivity {

    @Override
    protected void onResume() {
        super.onResume();
        Button btnBack = (Button) findViewById(R.id.btnBack);
        if (btnBack != null) {
            btnBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
    }

    protected void setMyTitle(int resId) {
        TextView tvTitle = (TextView) findViewById(R.id.tvTitle);
        if (tvTitle != null) {
            tvTitle.setText(resId);
        }
    }
}
