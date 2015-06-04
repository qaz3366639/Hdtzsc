package org.hq.hdtzsc;/**
 * Description:
 * Author:       WuRuiqiang (263454190@qq.com)
 * CreateDate:   2015/6/2-0:36
 * UpdateUser:
 * UpdateDate:
 * UpdateRemark:
 * Version:      [v1.0]
 */

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.dd.processbutton.iml.ActionProcessButton;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.rengwuxian.materialedittext.validation.RegexpValidator;

import org.hq.hdtzsc.base.BaseActivity;
import org.hq.hdtzsc.bean.UserBean;
import org.hq.hdtzsc.utils.ActivityStateCode;
import org.hq.hdtzsc.utils.IntentFactory;
import org.rc.rclibrary.utils.RegularUtil;
import org.w3c.dom.Text;

import cn.bmob.v3.BmobUser;
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

    private TextView tvRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setMyTitle(R.string.login);

        metUserName = (MaterialEditText) findViewById(R.id.metUserName);
        metPassWord = (MaterialEditText) findViewById(R.id.metPassWord);
        tvRegister = (TextView) findViewById(R.id.tvRegister);
        btnSignIn = (ActionProcessButton) findViewById(R.id.btnSignIn);

        clickSignIn();
        clickRegister();
    }

    @Override
    protected void onResume() {
        super.onResume();
        metUserName.requestFocus();
    }

    private void clickSignIn() {
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!metUserName.isCharactersCountValid()) {
                    metUserName.setError(getString(R.string.error_username_too_short));
                    return;
                }
                if (!metPassWord.isCharactersCountValid()) {
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
                        setResult(ActivityStateCode.LOGIN_SUCCESS);
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

    @Override
    protected void onDestroy() {
        setResult(ActivityStateCode.LOGIN_CANCEL);
        super.onDestroy();
    }

    private void clickRegister() {
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = IntentFactory.getRegisterActivity(LoginActivity.this);
                startActivity(intent);
            }
        });
    }

}
