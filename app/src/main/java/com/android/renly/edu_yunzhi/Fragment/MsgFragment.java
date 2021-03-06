package com.android.renly.edu_yunzhi.Fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.android.renly.edu_yunzhi.Common.BaseFragment;
import com.android.renly.edu_yunzhi.R;
import com.loopj.android.http.RequestParams;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MsgFragment extends BaseFragment {
    @BindView(R.id.fl_msg)
    FrameLayout flMsg;
    @BindView(R.id.btn_1)
    RadioButton btn1;
    @BindView(R.id.btn_2)
    RadioButton btn2;
    @BindView(R.id.btn_3)
    RadioButton btn3;
    @BindView(R.id.btn_change)
    RadioGroup btnChange;
    @BindView(R.id.fl_msg_title)
    FrameLayout flMsgTitle;

    private static HotNewsFragment hotNewsFragment;
    private static MyMsgFragment myMsgFragment;
    private FragmentTransaction transaction;

    @Override
    protected void initData(Context content) {
        initView();
    }

    private void initView() {
        SharedPreferences sp = this.getActivity().getSharedPreferences("user_info", Context.MODE_PRIVATE);
        boolean isStudent = sp.getBoolean("isStudent", false);
        if (isStudent) {
            //学生角色
            flMsgTitle.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            btn1.setBackground(getResources().getDrawable(R.drawable.switch_left_bg));
            btn2.setBackground(getResources().getDrawable(R.drawable.switch_center_bg));
            btn3.setBackground(getResources().getDrawable(R.drawable.switch_right_bg));
            btn1.setTextColor(getResources().getColorStateList(R.color.text_primary_white));
            btn2.setTextColor(getResources().getColorStateList(R.color.text_primary_white));
            btn3.setTextColor(getResources().getColorStateList(R.color.text_primary_white));
        } else {
            //教师角色
            flMsgTitle.setBackgroundColor(getResources().getColor(R.color.colorTeacherPrimary));
            btn1.setBackground(getResources().getDrawable(R.drawable.switch_left_teacher_bg));
            btn2.setBackground(getResources().getDrawable(R.drawable.switch_center_teacher_bg));
            btn3.setBackground(getResources().getDrawable(R.drawable.switch_right_teacher_bg));
            btn1.setTextColor(getResources().getColorStateList(R.color.text_primary_teacher));
            btn2.setTextColor(getResources().getColorStateList(R.color.text_primary_teacher));
            btn3.setTextColor(getResources().getColorStateList(R.color.text_primary_teacher));
        }

    }

    @Override
    public int getLayoutid() {
        return R.layout.fragment_msg;
    }

    private void setSelect(int select) {
        FragmentManager fragmentManager = this.getFragmentManager();
        transaction = fragmentManager.beginTransaction();
        //隐藏所有fragment
        hideFragments();
        switch (select) {
            case 0:
                if (hotNewsFragment == null) {
                    hotNewsFragment = new HotNewsFragment();//commit()后调用生命周期方法
                    transaction.add(R.id.fl_msg, hotNewsFragment);
                }
                transaction.show(hotNewsFragment);//显示当前的Fragment
                break;
            case 1:
                if (myMsgFragment == null) {
                    myMsgFragment = new MyMsgFragment();//commit()后调用生命周期方法
                    transaction.add(R.id.fl_msg, myMsgFragment);
                }
                transaction.show(myMsgFragment);//显示当前的Fragment
                break;
        }
        //提交事务
        transaction.commit();
    }

    private void hideFragments() {
        if (hotNewsFragment != null) {
            transaction.hide(hotNewsFragment);
        }
        if (myMsgFragment != null) {
            transaction.hide(myMsgFragment);
        }
    }

    private Unbinder unbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        btn1.setText("动态圈");
        btn2.setVisibility(View.GONE);
        btn3.setText("我的消息");
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.btn_1, R.id.btn_3})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_1:
                setSelect(0);
                break;
            case R.id.btn_3:
                setSelect(1);
                break;
        }
    }
}
