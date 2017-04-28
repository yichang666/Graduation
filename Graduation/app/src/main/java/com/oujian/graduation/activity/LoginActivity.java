package com.oujian.graduation.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.oujian.graduation.R;
import com.oujian.graduation.base.BaseActivity;
import com.oujian.graduation.common.MyContext;
import com.oujian.graduation.manager.UserInfo;
import com.oujian.graduation.utils.PreferencesUtils;

import butterknife.Bind;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {
    private final String TAG = this.getClass().getSimpleName();
    /**
     * 用户账号
     */
    private String mAccount = "";
    /**
     * 登录密码
     */
    private String mPassword = "";
    /**
     * 是否记住密码
     */
    private boolean mIsSave = false;

    public static final String KEY_SHARE_SAVE_PASSWORD = "sp";
    public static final String KEY_SHARE_ACCOUNT = "ac";
    public static final String KEY_SHARE_PASSWORD = "d";

    @Bind(R.id.login_toolBar)
    Toolbar mToolbar;
    @Bind(R.id.login_phone_et)
    EditText mEditAccount;
    @Bind(R.id.login_password_et)
    EditText mEditPassword;
    @Bind(R.id.remind_cb)
    AppCompatCheckBox mRemindCb;
    @Override
    protected View getContentView() {
        View view =getLayoutInflater().inflate(R.layout.activity_login,null);
        return view;
    }

    @Override
    protected void initViews() {
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    @OnClick(R.id.login_regist_tv)
    public void regist(){
        startActivity(new Intent(LoginActivity.this,RegistActivity.class));
    }

    /**
     * 点击按钮保存信息
     */
    @OnClick(R.id.remind_cb)
    public void onSaveGroupClick() {
        //点击后改变记住状态
        mIsSave = !mIsSave;
    }
    /**
     * 登录点击
     */
    @OnClick(R.id.login_btn)
    public void onLoginBtnClick() {
        mAccount = mEditAccount.getText().toString();
        if (TextUtils.isEmpty(mAccount)) {
            Toast.makeText(LoginActivity.this, R.string.account_tips, Toast.LENGTH_SHORT).show();
            return;
        }
        mPassword = mEditPassword.getText().toString();
        if (TextUtils.isEmpty(mPassword)) {
            Toast.makeText(LoginActivity.this, R.string.password_tips, Toast.LENGTH_SHORT).show();
            return;
        }
        //保存当前的登录状态信息
        saveAccountInfoShare();
        //登录请求，登录完成返回主界面
        //假装完成先
        startActivity(new Intent(LoginActivity.this,MainActivity.class));
        //假装已经登录
        MyContext.getInstance().getUserInfo().setUserId("1212");
        finish();
    }
    @Override
    protected void initListeners() {

    }
    /**
     * 保存登录的账号和密码
     */
    private void saveAccountInfoShare() {
        PreferencesUtils.addToSharedPreferences(LoginActivity.this, KEY_SHARE_SAVE_PASSWORD, mIsSave);
        PreferencesUtils.addToSharedPreferences(LoginActivity.this, KEY_SHARE_ACCOUNT, mAccount);
        if (mIsSave) {
            PreferencesUtils.addToSharedPreferences(LoginActivity.this, KEY_SHARE_PASSWORD, mPassword);
        } else {
            PreferencesUtils.removeSharedPreferences(LoginActivity.this, KEY_SHARE_PASSWORD);
        }
    }
    @Override
    protected void initData() {
        Context context = MyContext.getInstance().getContext();
        mAccount = PreferencesUtils.getValueOfSharedPreferences(context, KEY_SHARE_ACCOUNT, "");
        mIsSave = PreferencesUtils.getValueOfSharedPreferences(context, KEY_SHARE_SAVE_PASSWORD, false);
        if (true == mIsSave) {
            mPassword = PreferencesUtils.getValueOfSharedPreferences(context, KEY_SHARE_PASSWORD, "");
            mRemindCb.setChecked(true);
        }else {
            mRemindCb.setChecked(false);
        }
        //更新状态
        mEditAccount.setText(mAccount);
        mEditPassword.setText(mPassword);
    }
}
