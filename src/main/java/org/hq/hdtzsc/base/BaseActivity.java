package org.hq.hdtzsc.base;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.hq.hdtzsc.R;

/**
 * Description:
 * Author: WuRuiqiang(263454190@qq.com)
 * Date: 2015-06-02 10:25
 */
public class BaseActivity extends Activity {

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
