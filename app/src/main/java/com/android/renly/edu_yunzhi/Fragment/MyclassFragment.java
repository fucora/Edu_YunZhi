package com.android.renly.edu_yunzhi.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.renly.edu_yunzhi.Bean.Course;
import com.android.renly.edu_yunzhi.Common.BaseFragment;
import com.android.renly.edu_yunzhi.Common.MyApplication;
import com.android.renly.edu_yunzhi.R;
import com.android.renly.edu_yunzhi.UI.CustomLinearLayoutManager;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyclassFragment extends BaseFragment {
    @Bind(R.id.tv_myclass_addclass)
    public TextView tvMyclassAddclass;
    @Bind(R.id.recycler_myclass_learning)
    public RecyclerView recyclerMyclassLearning;
    @Bind(R.id.tv_myclass_seeall)
    public TextView tvMyclassSeeall;
    @Bind(R.id.recycler_myclass_learnt)
    public RecyclerView recyclerMyclassLearnt;

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
        //初始化数据
        initCoursedata();
        //初始化列表
        initList();
    }

    @Override
    public int getLayoutid() {
        return R.layout.fragment_myclass;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public List<Course> learningData;//正在学习的课程
    public List<Course> learntData;//已经学习的课程
    public courseInfoAdapter learningAdapter;
    public courseInfoAdapter learntAdapter;

    //初始化课程列表【暂时写死
    public void initCoursedata() {
        learningData = new ArrayList<>();
        learntData = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            //1.
            Course firstCourse = new Course();
            firstCourse.name = "Python免费公开课：零基础入门课程";
            firstCourse.imgUrl = "http://edu-image.nosdn.127.net/73b5696e-4dfa-4eeb-8525-4a65f05c3b05.jpg?imageView&quality=100";
            firstCourse.teacherName = "翁凯";

            //2.
            Course secondCourse = new Course();
            secondCourse.name = "Android开发基础教程";
            secondCourse.imgUrl = "http://edu-image.nosdn.127.net/F230B3E8A64D765AC0F35C4173AECA03.jpg?imageView&thumbnail=223y124&quality=100";
            secondCourse.teacherName = "孙麒";

            //3.
            Course thirdCourse = new Course();
            thirdCourse.name = "Spark大数据技术";
            thirdCourse.imgUrl = "http://edu-image.nosdn.127.net/0731fab7-4070-4c1d-ba6a-2291a42f32cd.jpg?imageView&quality=100";
            thirdCourse.teacherName = "云智教育";

            learningData.add(firstCourse);
            learningData.add(secondCourse);
            learningData.add(thirdCourse);

            learntData.add(secondCourse);
            learntData.add(firstCourse);
            learntData.add(thirdCourse);
        }
    }

    @OnClick(R.id.tv_myclass_addclass)
    public void onViewClicked() {
        Toast.makeText(MyApplication.context,"长度为" + learningData.size(),Toast.LENGTH_SHORT).show();
    }

    public class courseInfoAdapter extends RecyclerView.Adapter<courseInfoAdapter.ViewHolder> {
        private List<Course> learningData;//正在学习的课程

        class ViewHolder extends RecyclerView.ViewHolder {
            ImageView img;
            TextView title;
            TextView teacher;

            public ViewHolder(View view) {
                super(view);
                img = view.findViewById(R.id.iv_item_class_img);
                title = view.findViewById(R.id.tv_item_class_title);
                teacher = view.findViewById(R.id.tv_item_class_teacher);
            }
        }

        public courseInfoAdapter(List<Course> learningData) {
            this.learningData = learningData;
        }

        //加载item 的布局  创建ViewHolder实例
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_class, parent, false);
            ViewHolder holder = new ViewHolder(view);
            return holder;
        }

        //对RecyclerView子项数据进行赋值
        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Course course = learningData.get(position);
            //设置数据
            holder.title.setText(course.name);
            holder.teacher.setText(course.teacherName);
            String imagePath = course.imgUrl;
            Picasso.with(MyApplication.context).load(imagePath).into(holder.img);
        }

        //返回子项个数
        @Override
        public int getItemCount() {
            return learningData.size();
        }
    }

    protected static final int WHAT_REQUEST_SUCCESS = 1;
    protected static final int WHAT_REQUEST_ERROR = 2;

    private void initList() {

        learningAdapter = new courseInfoAdapter(learningData);
//        learntAdapter = new LearningFragment.courselearntInfoAdapter(learntData);
        CustomLinearLayoutManager layoutmanager = new CustomLinearLayoutManager(MyApplication.context);
        layoutmanager.setScrollEnabled(false);
        //设置RecyclerView 布局
//        recyclerMyclassLearnt.setLayoutManager(layoutmanager);
        recyclerMyclassLearning.setLayoutManager(layoutmanager);
        new Thread() {
            @Override
            public void run() {
                try {
                    //暂时模拟读取json数据
                    handler.sendEmptyMessage(WHAT_REQUEST_SUCCESS);
                } catch (Exception e) {
                    handler.sendEmptyMessage(WHAT_REQUEST_ERROR);
                    Log.e("TAG", "加载数据失败");
                }
            }
        }.start();
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case WHAT_REQUEST_SUCCESS:
                    recyclerMyclassLearning.setAdapter(learningAdapter);
//                    recyclerMyclassLearnt.setAdapter(learningAdapter);
                    break;
                case WHAT_REQUEST_ERROR:
                    Toast.makeText(MyApplication.context, "加载数据失败", Toast.LENGTH_LONG).show();
                    break;

            }
        }
    };
}