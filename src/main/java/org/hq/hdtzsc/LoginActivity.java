package org.hq.hdtzsc;/**
 * Description:
 * Author:       WuRuiqiang (263454190@qq.com)
 * CreateDate:   2015/6/2-0:36
 * UpdateUser:
 * UpdateDate:
 * UpdateRemark:
 * Version:      [v1.0]
 */

import android.os.Bundle;
import android.view.View;

import com.dd.processbutton.iml.ActionProcessButton;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.hq.hdtzsc.base.BaseActivity;
import org.hq.hdtzsc.bean.UserBean;

import cn.bmob.v3.listener.SaveListener;

/**
 * Description:
 * Author:       WuRuiqiang (263454190@qq.com)
 * CreateDate:   2015/6/2-0:36
 * UpdateUser:
 * UpdateDate:
 * UpdateRemark:
 * Version:      [v1.0]
 */
public class LoginActivity extends BaseActivity {

    private MaterialEditText metUserName;

    private MaterialEditText metPassWord;

    private ActionProcessButton btnSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setMyTitle(R.string.login);

        metUserName = (MaterialEditText) findViewById(R.id.metUserName);
        metPassWord = (MaterialEditText) findViewById(R.id.metPassWord);
        metUserName.setShowClearButton(true);
        metPassWord.setShowClearButton(true);
        btnSignIn = (ActionProcessButton) findViewById(R.id.btnSignIn);

        clickSignIn();
    }

    private void clickSignIn() {
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (metUserName.getText().toString().trim().length() < metUserName.getMinCharacters()) {
                    metUserName.setError(getString(R.string.error_username_too_short));
                    return;
                }
                if (metPassWord.getText().toString().trim().length() < metPassWord.getMinCharacters()) {
                    metPassWord.setError(getString(R.string.error_password_too_short));
                    return;
                }
                btnSignIn.setProgress(1);
                btnSignIn.setEnabled(false);
                metUserName.setEnabled(false);
                metPassWord.setEnabled(false);
                UserBean userBean = new UserBean();
                userBean.setUsername(metUserName.getText().toString());
                userBean.setPassword(metPassWord.getText().toString());
                userBean.login(LoginActivity.this, new SaveListener() {
                    @Override
                    public void onSuccess() {
                        btnSignIn.setProgress(100);
                        finish();
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        btnSignIn.setProgress(-1);
                        btnSignIn.setEnabled(true);
                        metUserName.setEnabled(true);
                        metPassWord.setEnabled(true);
                        metPassWord.setError(s);
                    }
                });
            }
        });
    }

}
