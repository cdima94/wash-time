package app.washtime.com.washtime.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import app.washtime.com.washtime.R;
import app.washtime.com.washtime.task.StudentTask;
import app.washtime.com.washtime.task.impl.StudentTaskImpl;

public class LoginActivity extends Activity {

    private Button mLogin;
    private Button mCreateAccount;
    private TextView mForgotPasswordText;
    private EditText mUserNameText;
    private EditText mPasswordText;
    private TextInputLayout mUserName;
    private TextInputLayout mPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_layout_login);

        mUserName = (TextInputLayout) findViewById(R.id.lg_user_name);
        mUserName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        mPassword = (TextInputLayout) findViewById(R.id.lg_password);
        mUserNameText = mUserName.getEditText();
        mPasswordText = mPassword.getEditText();

        addListenerForLogin();
        addListenerForCreateAccount();
        addListenerForForgotPasswordText();
    }

    private void addListenerForLogin() {
        mLogin = (Button) findViewById(R.id.lg_login_button);

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StudentTask taskLogin = new StudentTaskImpl();
                if (mUserNameText.getText().length() == 0 && mPasswordText.getText().length() == 0) {
                    mUserName.setErrorEnabled(true);
                    mUserName.setError("Please introduce the user name");
                } else if (mUserNameText.getText().length() > 0 && mPasswordText.getText().length() == 0) {
                    mUserName.setErrorEnabled(false);
                    mPassword.setErrorEnabled(true);
                    mPassword.setError("Please introduce the password");
                } else if (mPasswordText.getText().length() > 0 && mUserNameText.getText().length() == 0) {
                    mPassword.setErrorEnabled(false);
                    mUserName.setErrorEnabled(true);
                    mUserName.setError("Please introduce the user name");
                } else {
                    mPassword.setErrorEnabled(false);
                    mUserName.setErrorEnabled(false);
                    taskLogin.login(getApplicationContext(), mUserNameText.getText().toString(), mPasswordText.getText().toString());
                }
            }
        });
    }

    private void addListenerForCreateAccount() {
        mCreateAccount = (Button) findViewById(R.id.lg_create_new_account);

        mCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newAccount = new Intent(getApplicationContext(), AddNewAccountActivity.class);
                startActivity(newAccount);
            }
        });
    }

    private void addListenerForForgotPasswordText() {
        mForgotPasswordText = (TextView) findViewById(R.id.lg_forgot_password);

        mForgotPasswordText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }
}
