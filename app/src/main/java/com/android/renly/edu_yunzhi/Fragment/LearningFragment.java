package com.android.renly.edu_yunzhi.Fragment;


import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RadioGroup;

import com.android.renly.edu_yunzhi.Common.BaseFragment;
import com.android.renly.edu_yunzhi.R;
import com.android.renly.edu_yunzhi.UI.BatchRadioButton;
import com.loopj.android.http.RequestParams;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LearningFragment extends BaseFragment implements View.OnClickListener {

    @Bind(R.id.btn_1)
    BatchRadioButton btn1;
    @Bind(R.id.btn_2)
    BatchRadioButton btn2;
    @Bind(R.id.btn_3)
    BatchRadioButton btn3;
    @Bind(R.id.btn_change)
    RadioGroup btnChange;
    @Bind(R.id.fl_learning)
    FrameLayout flLearning;

    @Override
    protected String getUrl() {
        return null;
    }

    @Override
    protected RequestParams getParams() {
        return null;
    }

    @Override
    protected void initData(String content) {
        //默认显示首页
        setSelect(0);
    }

    private MyclassFragment myclassFragment;
    private MyworkFragment myworkFragment;
    private MynoteFragment mynoteFragment;
    private FragmentTransaction transaction;

    private void setSelect(int select) {
        FragmentManager fragmentManager = this.getFragmentManager();
        transaction = fragmentManager.beginTransaction();
        //隐藏所有fragment
        hideFragments();
        switch (select) {
            case 0:
                if (myclassFragment == null) {
                    myclassFragment = new MyclassFragment();//commit()后调用生命周期方法
                    transaction.add(R.id.fl_learning, myclassFragment);
                }
                transaction.show(myclassFragment);//显示当前的Fragment
                break;
            case 1:
                if (myworkFragment == null) {
                    myworkFragment = new MyworkFragment();//commit()后调用生命周期方法
                    transaction.add(R.id.fl_learning, myworkFragment);
                }
                transaction.show(myworkFragment);//显示当前的Fragment
                break;
            case 2:
                if (mynoteFragment == null) {
                    mynoteFragment = new MynoteFragment();//commit()后调用生命周期方法
                    transaction.add(R.id.fl_learning, mynoteFragment);
                }
                transaction.show(mynoteFragment);//显示当前的Fragment
                break;
        }
        //提交事务
        transaction.commit();
    }

    private void hideFragments() {
        if (myclassFragment != null) {
            transaction.hide(myclassFragment);
        }
        if (myworkFragment != null) {
            transaction.hide(myworkFragment);
        }
        if (mynoteFragment != null) {
            transaction.hide(mynoteFragment);
        }
    }

    @Override
    public int getLayoutid() {
        return R.layout.fragment_learning;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        btn1.setText("课程");
        btn2.setText("作业");
        btn3.setText("笔记");
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        //移除所有的未被执行的消息
//        handler.removeCallbacksAndMessages(null);
    }

    @OnClick({R.id.btn_1, R.id.btn_2, R.id.btn_3})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_1:
                setSelect(0);
                break;
            case R.id.btn_2:
                setSelect(1);
                break;
            case R.id.btn_3:
                setSelect(2);
                break;
        }
    }
}
