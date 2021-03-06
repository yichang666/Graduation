package com.oujian.graduation.fragment;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.oujian.graduation.R;
import com.oujian.graduation.activity.ChangePasswordActivity;
import com.oujian.graduation.activity.LoginActivity;
import com.oujian.graduation.activity.ModifiedInfoActivity;
import com.oujian.graduation.base.BaseFragment;
import com.oujian.graduation.common.MyContext;
import com.oujian.graduation.utils.PreferencesUtils;
import com.oujian.graduation.view.ExitDialog;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyFragment extends BaseFragment {

    private View mRootView;
    @Bind(R.id.my_rl_exit)
    RelativeLayout mExit;
    @Bind(R.id.nicke_name_tv)
    TextView mNickName;
    public MyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if(mRootView == null){
            mRootView = inflater.inflate(R.layout.fragment_my, container, false);
        }
        return mRootView;
    }

    @Override
    protected void initViews(View rootView) {

    }

    @Override
    protected void initListeners() {
        mExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getExitDialog();
            }
        });
    }

    @Override
    protected void initData() {
        mNickName.setText(MyContext.getInstance().getUserInfo().getNickName());
    }
    @OnClick(R.id.my_modified_info)
    public void gotoModifiedInfo(){
        startActivity(new Intent(getActivity(), ModifiedInfoActivity.class));
    }
    @OnClick(R.id.my_change_password)
    public void gotoChangePassword(){
        startActivity(new Intent(getActivity(), ChangePasswordActivity.class));
    }
    private void getExitDialog(){
        ExitDialog.Builder builder = new ExitDialog.Builder(getActivity());
        builder.setPositiveButton(new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                //清除已经登录的标志和登录密码，记住密码的标志
                PreferencesUtils.removeSharedPreferences(getActivity(), LoginActivity.KEY_SHARE_PASSWORD);
                PreferencesUtils.removeSharedPreferences(getActivity(), LoginActivity.KEY_SHARE_SAVE_PASSWORD);
                //设置你的操作事项
                MyContext.getInstance().resetUserInfo();//重置个人信息
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });
        builder.setNegativeButton(new android.content.DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.create().show();
    }
}
