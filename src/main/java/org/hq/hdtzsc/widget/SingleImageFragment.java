package org.hq.hdtzsc.widget;/**
 * Description:
 * Author:       WuRuiqiang (263454190@qq.com)
 * CreateDate:   2015/5/28-0:30
 * UpdateUser:
 * UpdateDate:
 * UpdateRemark:
 * Version:      [v1.0]
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;

import org.hq.hdtzsc.R;

/**
 *
 * Description:  
 * Author:       WuRuiqiang (263454190@qq.com)   
 * CreateDate:   2015/5/28-0:30  
 * UpdateUser:   
 * UpdateDate:    
 * UpdateRemark: 
 * Version:      [v1.0] 
 */
public class SingleImageFragment extends Fragment {

    public static final String IMAGE_URL_KEY = "image_url_key";

    private String strImageUrl;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        strImageUrl = getArguments().getString(IMAGE_URL_KEY);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ImageView imageView = (ImageView) inflater.inflate(R.layout.fragment_single_image, container, false);
        ImageLoader.getInstance().displayImage(strImageUrl, imageView);
        return imageView;
    }
}
