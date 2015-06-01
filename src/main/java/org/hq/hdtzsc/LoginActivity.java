package org.hq.hdtzsc;/**
 * Description:
 * Author:       WuRuiqiang (263454190@qq.com)
 * CreateDate:   2015/6/2-0:36
 * UpdateUser:
 * UpdateDate:
 * UpdateRemark:
 * Version:      [v1.0]
 */

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.dd.processbutton.iml.ActionProcessButton;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.hq.hdtzsc.utils.ProgressGenerator;

/**
 * Description:
 * Author:       WuRuiqiang (263454190@qq.com)
 * CreateDate:   2015/6/2-0:36
 * UpdateUser:
 * UpdateDate:
 * UpdateRemark:
 * Version:      [v1.0]
 */
public class LoginActivity extends Activity implements ProgressGenerator.OnCompleteListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final MaterialEditText editEmail = (MaterialEditText) findViewById(R.id.metUserName);
        final MaterialEditText editPassword = (MaterialEditText) findViewById(R.id.metPassWord);

        final ProgressGenerator progressGenerator = new ProgressGenerator(this);
        final ActionProcessButton btnSignIn = (ActionProcessButton) findViewById(R.id.btnSignIn);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressGenerator.start(btnSignIn);
                btnSignIn.setEnabled(false);
                editEmail.setEnabled(false);
                editPassword.setEnabled(false);
            }
        });

    }

    @Override
    public void onComplete() {
        Toast.makeText(this, "加载完成", Toast.LENGTH_LONG).show();
    }
}
