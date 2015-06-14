package org.hq.hdtzsc;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.dd.processbutton.iml.ActionProcessButton;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.rengwuxian.materialedittext.validation.RegexpValidator;

import org.hq.hdtzsc.base.BaseActivity;
import org.hq.hdtzsc.bean.UserBean;
import org.rc.rclibrary.utils.RegularUtil;

import java.text.NumberFormat;
import java.text.ParseException;

import cn.bmob.v3.listener.SaveListener;

/**
 * Description:
 * Author: WuRuiqiang(263454190@qq.com)
 * Date: 2015-06-03 09:25
 */
public class RegisterActivity extends BaseActivity {

    /**
     * 用户名输入栏
     */
    private MaterialEditText metUserName;
    /**
     * 密码输入栏
     */
    private MaterialEditText metPassWord;
    /**
     * 确认密码输入栏
     */
    private MaterialEditText metConfirmPassWord;
    /**
     * 邮箱地址输入栏
     */
    private MaterialEditText metEmail;
    /**
     * 手机号码输入栏
     */
    private MaterialEditText metMobilePhone;
    /**
     * 注册按钮
     */
    private ActionProcessButton btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setMyTitle(R.string.register);

        initView();

        clickRegister();
    }

    private void initView() {
        metUserName = (MaterialEditText) findViewById(R.id.metUserName);
        metPassWord = (MaterialEditText) findViewById(R.id.metPassWord);
        metConfirmPassWord = (MaterialEditText) findViewById(R.id.metConfirmPassWord);
        metEmail = (MaterialEditText) findViewById(R.id.metEmail);
        metMobilePhone = (MaterialEditText) findViewById(R.id.metMobilePhone);
        btnRegister = (ActionProcessButton) findViewById(R.id.btnRegister);
    }

    private void clickRegister() {

        addValidator2Input();
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!metPassWord.getText().toString().equals(metConfirmPassWord.getText().toString())) {
                    metConfirmPassWord.setError(getString(R.string.error_password_not_same));
                    return;
                }
                if (metUserName.getError() == null && metPassWord.getError() == null &&
                        metConfirmPassWord.getError() == null && metEmail.getError() == null &&
                        metMobilePhone.getError() == null) {
                    btnRegister.setProgress(1);
                    UserBean userBean = new UserBean();
                    userBean.setUsername(metUserName.getText().toString());
                    userBean.setPassword(metPassWord.getText().toString());
                    userBean.setEmail(metEmail.getText().toString());
                    userBean.setMobilePhoneNumber(metMobilePhone
                            .getText().toString());
                    userBean.signUp(RegisterActivity.this, new SaveListener() {
                        @Override
                        public void onSuccess() {
                            btnRegister.setProgress(100);
                            finish();
                        }

                        @Override
                        public void onFailure(int i, String s) {
                            btnRegister.setProgress(-1);
                            Toast.makeText(RegisterActivity.this, s, Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });
    }

    /**
     * 为每个输入栏加入验证器
     */
    private void addValidator2Input() {
        metUserName.addValidator(new RegexpValidator(metUserName.getHint() + getString(R.string
                .error_must_not_empty), RegularUtil.getNotNullValidator()));
        metPassWord.addValidator(new RegexpValidator(metPassWord.getHint() + getString(R.string
                .error_must_not_empty), RegularUtil.getNotNullValidator()))
                .addValidator(new RegexpValidator(getString(R.string.error_password_format)
                        , RegularUtil.getPassWordValidator()));
        metConfirmPassWord.addValidator(new RegexpValidator(metConfirmPassWord.getHint() +
                getString(R.string.error_must_not_empty), RegularUtil.getNotNullValidator()))
                .addValidator(new RegexpValidator(getString(R.string.error_password_format)
                        , RegularUtil.getPassWordValidator()));
        metEmail.addValidator(new RegexpValidator(metEmail.getHint() + getString(R.string
                .error_must_not_empty), RegularUtil.getNotNullValidator()))
                .addValidator(new RegexpValidator(getString(R.string.error_email_format)
                        , RegularUtil.getEmailValidator()));
        metMobilePhone.addValidator(new RegexpValidator(metMobilePhone.getHint() + getString(R.string
                .error_must_not_empty), RegularUtil.getNotNullValidator()))
                .addValidator(new RegexpValidator(getString(R.string.error_mobile_number_format)
                        , RegularUtil.getMobileValidator()));
    }

    private void checkInputNotNull(MaterialEditText... text) {

        for (MaterialEditText materialEditText : text) {
            materialEditText.validate();
        }
    }
}
