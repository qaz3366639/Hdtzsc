package org.hq.hdtzsc.utils;/**
 * Description:
 * Author:       WuRuiqiang (263454190@qq.com)
 * CreateDate:   2015/6/2-0:39
 * UpdateUser:
 * UpdateDate:
 * UpdateRemark:
 * Version:      [v1.0]
 */

import android.os.Handler;

import com.dd.processbutton.ProcessButton;

import java.util.Random;

/**
 *
 * Description:  
 * Author:       WuRuiqiang (263454190@qq.com)   
 * CreateDate:   2015/6/2-0:39  
 * UpdateUser:   
 * UpdateDate:    
 * UpdateRemark: 
 * Version:      [v1.0] 
 */
public class ProgressGenerator {

    public interface OnCompleteListener {

        public void onComplete();
    }

    private OnCompleteListener mListener;
    private int mProgress;

    public ProgressGenerator(OnCompleteListener listener) {
        mListener = listener;
    }

    public void start(final ProcessButton button) {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mProgress += 10;
                button.setProgress(mProgress);
                if (mProgress < 100) {
                    handler.postDelayed(this, generateDelay());
                } else {
                    mListener.onComplete();
                }
            }
        }, generateDelay());
    }

    private Random random = new Random();

    private int generateDelay() {
        return random.nextInt(1000);
    }
}
