package com.android.renly.edu_yunzhi.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.android.renly.edu_yunzhi.Activity.AbilityActivity;
import com.android.renly.edu_yunzhi.Activity.MyInfoActivity;
import com.android.renly.edu_yunzhi.Activity.NoteActivity;
import com.android.renly.edu_yunzhi.Activity.NoticeActivity;
import com.android.renly.edu_yunzhi.Activity.PKActivity;
import com.android.renly.edu_yunzhi.Activity.QueryGradeActivity;
import com.android.renly.edu_yunzhi.Activity.SearchActivity;
import com.android.renly.edu_yunzhi.Activity.TaskActivity;
import com.android.renly.edu_yunzhi.Bean.News;
import com.android.renly.edu_yunzhi.Common.AppNetConfig;
import com.android.renly.edu_yunzhi.Common.BaseActivity;
import com.android.renly.edu_yunzhi.Common.BaseFragment;
import com.android.renly.edu_yunzhi.Common.MyApplication;
import com.android.renly.edu_yunzhi.MainActivity;
import com.android.renly.edu_yunzhi.R;
import com.android.renly.edu_yunzhi.UI.CircleImageView;
import com.android.renly.edu_yunzhi.UI.CustomLinearLayoutManager;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cz.msebera.android.httpclient.Header;

public class HomeFragment extends BaseFragment implements View.OnClickListener {
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.iv_home_first)
    ImageView ivHomeFirst;
    @BindView(R.id.tv_home_first)
    TextView tvHomeFirst;
    @BindView(R.id.ll_home_first)
    LinearLayout llHomeFirst;
    @BindView(R.id.iv_home_second)
    ImageView ivHomeSecond;
    @BindView(R.id.tv_home_second)
    TextView tvHomeSecond;
    @BindView(R.id.ll_home_second)
    LinearLayout llHomeSecond;
    @BindView(R.id.iv_home_third)
    ImageView ivHomeThird;
    @BindView(R.id.tv_home_third)
    TextView tvHomeThird;
    @BindView(R.id.ll_home_third)
    LinearLayout llHomeThird;
    @BindView(R.id.iv_home_fourth)
    ImageView ivHomeFourth;
    @BindView(R.id.tv_home_fourth)
    TextView tvHomeFourth;
    @BindView(R.id.ll_home_fourth)
    LinearLayout llHomeFourth;
    @BindView(R.id.iv_home_fifth)
    ImageView ivHomeFifth;
    @BindView(R.id.tv_home_fifth)
    TextView tvHomeFifth;
    @BindView(R.id.ll_home_fifth)
    LinearLayout llHomeFifth;
    @BindView(R.id.iv_home_sixth)
    ImageView ivHomeSixth;
    @BindView(R.id.tv_home_sixth)
    TextView tvHomeSixth;
    @BindView(R.id.ll_home_sixth)
    LinearLayout llHomeSixth;
    @BindView(R.id.iv_home_seventh)
    ImageView ivHomeSeventh;
    @BindView(R.id.tv_home_seventh)
    TextView tvHomeSeventh;
    @BindView(R.id.ll_home_seventh)
    LinearLayout llHomeSeventh;
    @BindView(R.id.iv_home_eighth)
    ImageView ivHomeEighth;
    @BindView(R.id.tv_home_eighth)
    TextView tvHomeEighth;
    @BindView(R.id.ll_home_eighth)
    LinearLayout llHomeEighth;
    @BindView(R.id.lv_home_newsList)
    RecyclerView lvHomeNewsList;
    @BindView(R.id.CircleImageView)
    com.android.renly.edu_yunzhi.UI.CircleImageView CircleImageView;
    @BindView(R.id.iv_home_search)
    ImageView ivHomeSearch;
    @BindView(R.id.civ_scool)
    com.android.renly.edu_yunzhi.UI.CircleImageView civScool;
    @BindView(R.id.rl_home_school)
    RelativeLayout rlHomeSchool;
    @BindView(R.id.tv_home_titleName)
    TextView tvHomeTitleName;
    @BindView(R.id.fl_home_title)
    FrameLayout flHomeTitle;
    @BindView(R.id.tv_home_school)
    TextView tvHomeSchool;

    private Unbinder unbinder;
    private boolean isStudent;
    private String schoolName;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    protected static final int WHAT_REQUEST_SUCCESS = 1;
    protected static final int WHAT_REQUEST_ERROR = 2;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case WHAT_REQUEST_SUCCESS:
                    lvHomeNewsList.setAdapter(adapter);
                    break;
                case WHAT_REQUEST_ERROR:
                    Toast.makeText(MyApplication.context, "加载数据失败", Toast.LENGTH_LONG).show();
                    break;

            }
        }
    };

    @Override
    protected void initData(Context content) {
        initView();
        initOnclickEvent();
        initNewsdata();
        //初始化轮播图
        initBanner();

    }

    private void initList() {
        adapter = new itemInfoAdapter(data);
        CustomLinearLayoutManager layoutmanager = new CustomLinearLayoutManager(MyApplication.context);
        layoutmanager.setScrollEnabled(false);
        //设置RecyclerView 布局
        lvHomeNewsList.setLayoutManager(layoutmanager);
        new Thread() {
            @Override
            public void run() {
                try {
                    handler.sendEmptyMessage(WHAT_REQUEST_SUCCESS);
                } catch (Exception e) {
                    handler.sendEmptyMessage(WHAT_REQUEST_ERROR);
                }

            }
        }.start();
    }

    private void initOnclickEvent() {
        llHomeFirst.setOnClickListener(this);
        llHomeSecond.setOnClickListener(this);
        llHomeThird.setOnClickListener(this);
        llHomeFourth.setOnClickListener(this);
        llHomeFifth.setOnClickListener(this);
        llHomeSixth.setOnClickListener(this);
        llHomeSeventh.setOnClickListener(this);
        llHomeEighth.setOnClickListener(this);
        ivHomeSearch.setOnClickListener(this);
        CircleImageView.setOnClickListener(this);
    }

    private void initBanner() {
        //设置banner样式
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片地址构成的集合
        ArrayList<String> imagesUrl = new ArrayList<>(4);
        imagesUrl.add("http://m.qpic.cn/psb?/V13Hh3Xy2gxYy4/D7gx6zteCgux70QDC*nRwyaHeYgg1Yju9Zd570YdMNs!/b/dPIAAAAAAAAA&bo=AAocAgAAAAARFzQ!&rf=viewer_4");
        imagesUrl.add("http://m.qpic.cn/psb?/V13Hh3Xy2gxYy4/x5bOR*YlDgUeyzILeiva2RZMRQkoDHvj6KA*8GNdlf0!/b/dEUBAAAAAAAA&bo=AAocAgAAAAARFzQ!&rf=viewer_4");
        imagesUrl.add("http://m.qpic.cn/psb?/V13Hh3Xy2gxYy4/DStr.yGNWRFrqYSGCFmQ42*xqrImIydbrZ74erwfpMo!/b/dPIAAAAAAAAA&bo=sAQYAQAAAAARF40!&rf=viewer_4");
        imagesUrl.add("http://m.qpic.cn/psb?/V13Hh3Xy2gxYy4/umhRQOXz6eIBa.p5DOq4bSHgej2r2R7pMop*x8c28yQ!/b/dFYBAAAAAAAA&bo=sAQYAQAAAAARF40!&rf=viewer_4");
//        ArrayList<String> imagesUrl = new ArrayList<>(index.images.size());
//        for (int i = 0; i < index.images.size(); i++) {
//            imagesUrl.add(index.images.get(i).ImaPAURL);
//        }
        banner.setImages(imagesUrl);
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.DepthPage);
        //设置标题集合（当banner样式有显示title时)
        String[] titles = new String[]{"高考锋芒行动", "一人，一名师", "用一场旅行，玩转暑期长假", "跳槽去新加坡，互联网圈怎么样"};
        banner.setBannerTitles(Arrays.asList(titles));
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播事件
        banner.setDelayTime(1500);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CENTER);
        //banner设置方法全部调用完毕时最后调用
        banner.start();
    }

    @Override
    public int getLayoutid() {
        return R.layout.fragment_home;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onClick(View v) {
        MainActivity mainActivity = (MainActivity) getActivity();
        switch (v.getId()) {
            case R.id.ll_home_first:
                mainActivity.gotoLearningFragment();
                break;
            case R.id.ll_home_second:
                ((BaseActivity) this.getActivity()).goToActivity(NoticeActivity.class, null);
                break;
            case R.id.ll_home_third:
                mainActivity.gotoLearningFragment();
                break;
            case R.id.ll_home_fourth:
                startActivity(new Intent(MyApplication.context, PKActivity.class));
                break;
            case R.id.ll_home_fifth:
                startActivity(new Intent(MyApplication.context, QueryGradeActivity.class));
                break;
            case R.id.ll_home_sixth:
                startActivity(new Intent(MyApplication.context, NoteActivity.class));
                break;
            case R.id.ll_home_seventh:
                ((BaseActivity) this.getActivity()).goToActivity(TaskActivity.class, null);
                break;
            case R.id.ll_home_eighth:
                startActivity(new Intent(MyApplication.context, AbilityActivity.class));
                break;
            case R.id.iv_home_search:
                startActivity(new Intent(MyApplication.context, SearchActivity.class));
                break;
            case R.id.CircleImageView:
                gotoMyInfoActivity();
                break;
        }
    }

    private void gotoMyInfoActivity() {
        Intent intent = new Intent(getActivity(), MyInfoActivity.class);
        intent.putExtra("backInfo", "首页");
        startActivity(intent);
    }

    public class GlideImageLoader extends ImageLoader {
        //picasso 加载图片简单用法
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Picasso.with(context).load((String) path).into(imageView);
        }
    }

    public List<News> data;
    public List<String> imgs;
    public itemInfoAdapter adapter;

    //广告
    public void initNewsdata() {
        data = new ArrayList<>();
        imgs = new ArrayList<>();
        imgs.add("http://m.qpic.cn/psb?/V13Hh3Xy2gxYy4/FRp*yIwJptgrSPi272ndSLj3OyHQnVqfiCU.AARr6Rc!/b/dAgBAAAAAAAA&bo=wAY4BEALCAcDCZI!&rf=viewer_4");
        imgs.add("http://m.qpic.cn/psb?/V13Hh3Xy2gxYy4/dpXhe5yTB4cUOd7h16wy*P3EwgYd24tcF7WedTIFGbA!/b/dEMBAAAAAAAA&bo=wAY4BEALCAcDGYI!&rf=viewer_4");
        imgs.add("http://m.qpic.cn/psb?/V13Hh3Xy2gxYy4/OLlz35YPjnY23QvJaVbfJhEh0tbQPn28DF49A6XE5jw!/b/dPMAAAAAAAAA&bo=wAY4BEALCAcDCZI!&rf=viewer_4");
        RequestParams params = new RequestParams();
        AsyncHttpClient client = new AsyncHttpClient();
        client.post(AppNetConfig.GET_ARTICLE, null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String response = new String(responseBody);
                JSONArray jsonArray = JSON.parseArray(response);
                for (int i = 0; i < jsonArray.size(); i++) {
                    News ad = new News();
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    ad.title = jsonObject.getString("tittle");
                    ad.content = jsonObject.getString("context");
                    ad.replyCount = (int) (Math.random() * 2333);
                    ad.img = imgs.get(i % 3);
                    ad.time = (int) (Math.random() * 58) + 1;
                    JSONObject departmentObject = jsonObject.getJSONObject("department");
                    JSONObject userObject = jsonObject.getJSONObject("user");
                    ad.username = departmentObject.getString("name") + " - " + userObject.getString("realname");
                    data.add(ad);
                }
                initFailData();
                initList();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                initFailData();
                initList();
            }
        });
    }

    public void initFailData(){
        for (int i = 0; i < 3; i++) {
            //1.
            News firstAd = new News();
            firstAd.title = "区块链 技术峰会";
            firstAd.content = "《麻省理工科技评论》第二届区块链技术峰会将于4月22日在该平台中文同传...";
            firstAd.replyCount = 233;
            firstAd.username = "微社区";
            firstAd.img = "http://m.qpic.cn/psb?/V13Hh3Xy2gxYy4/FRp*yIwJptgrSPi272ndSLj3OyHQnVqfiCU.AARr6Rc!/b/dAgBAAAAAAAA&bo=wAY4BEALCAcDCZI!&rf=viewer_4";
            firstAd.time = 1;

            //2.
            News secondAd = new News();
            secondAd.title = "一起来看看色彩与树洞的故事";
            secondAd.content = "由学生社团联合会主办、观鸟协会联合美术学院承办的...";
            secondAd.replyCount = 568;
            secondAd.username = "资讯";
            secondAd.img = "http://m.qpic.cn/psb?/V13Hh3Xy2gxYy4/dpXhe5yTB4cUOd7h16wy*P3EwgYd24tcF7WedTIFGbA!/b/dEMBAAAAAAAA&bo=wAY4BEALCAcDGYI!&rf=viewer_4";
            secondAd.time = 2;

            //3.
            News thirdAd = new News();
            thirdAd.title = "水情教育进校园，传递节水正能量";
            thirdAd.content = "3月22日至28日期间，肇庆学院在发展门口正门、紫荆校道悬挂中国水周宣传口号...";
            thirdAd.replyCount = 1024;
            thirdAd.username = "家里蹲大学";
            thirdAd.img = "http://m.qpic.cn/psb?/V13Hh3Xy2gxYy4/OLlz35YPjnY23QvJaVbfJhEh0tbQPn28DF49A6XE5jw!/b/dPMAAAAAAAAA&bo=wAY4BEALCAcDCZI!&rf=viewer_4";
            thirdAd.time = 10;

            data.add(firstAd);
            data.add(secondAd);
            data.add(thirdAd);
        }
    }


    public class itemInfoAdapter extends RecyclerView.Adapter<itemInfoAdapter.ViewHolder> {
        private List<News> newsList;

        class ViewHolder extends RecyclerView.ViewHolder {
            ImageView iv_news_img;
            TextView tv_item_title;
            TextView tv_item_content;
            TextView tv_item_commentCount;
            TextView tv_item_name;
            TextView tv_item_time;

            public ViewHolder(View view) {
                super(view);
                iv_news_img = view.findViewById(R.id.iv_news_img);
                tv_item_title = view.findViewById(R.id.tv_item_title);
                tv_item_content = view.findViewById(R.id.tv_item_content);
                tv_item_commentCount = view.findViewById(R.id.tv_item_commentCount);
                tv_item_name = view.findViewById(R.id.tv_item_name);
                tv_item_time = view.findViewById(R.id.tv_item_time);
            }
        }

        public itemInfoAdapter(List<News> newsList) {
            this.newsList = newsList;
        }

        //加载item 的布局  创建ViewHolder实例
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false);
            ViewHolder holder = new ViewHolder(view);
            return holder;
        }

        //对RecyclerView子项数据进行赋值
        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            News news = newsList.get(position);
            //设置数据
            holder.tv_item_name.setText(news.username);
            holder.tv_item_title.setText(news.title);
            holder.tv_item_content.setText(news.content);
            holder.tv_item_commentCount.setText(news.replyCount + "");
            holder.tv_item_time.setText(news.time + "");
            String imagePath = news.img;
            Picasso.with(MyApplication.context).load(imagePath).into(holder.iv_news_img);
        }

        //返回子项个数
        @Override
        public int getItemCount() {
            return newsList.size();
        }

    }

    private void initView() {
        SharedPreferences sp = this.getActivity().getSharedPreferences("user_info", Context.MODE_PRIVATE);
        String name = sp.getString("username", "");
        isStudent = sp.getBoolean("isStudent", false);
        schoolName = sp.getString("schoolName","");

        if (TextUtils.isEmpty(name)) {
            //本地没有保存过用户信息，给出提示：登录
            doLogin();
            Log.e("print","isEmpty()");
        } else {
            if (isStudent) {
                //学生角色
                CircleImageView.setImageDrawable(getResources().getDrawable(R.drawable.user1));
                rlHomeSchool.setVisibility(View.VISIBLE);
                flHomeTitle.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                Log.e("print","isStudent()");
            } else {
                //教师角色
                CircleImageView.setImageDrawable(getResources().getDrawable(R.drawable.user1));
                rlHomeSchool.setVisibility(View.VISIBLE);
                tvHomeTitleName.setText("云智教育教师端");
                tvHomeThird.setText("批改作业");
                ivHomeFourth.setImageDrawable(getResources().getDrawable(R.drawable.activity));
                tvHomeFourth.setText("各类活动");
                flHomeTitle.setBackgroundColor(getResources().getColor(R.color.colorTeacherPrimary));
                Log.e("print","isTeacher()");
            }
        }
        tvHomeSchool.setText(schoolName);
    }

    @Override
    public void onResume() {
        super.onResume();
        data.clear();
        initNewsdata();

    }

    //给出提示：登录
    private void doLogin() {
        Toast.makeText(MyApplication.context, "您还没有登录", Toast.LENGTH_SHORT).show();
    }
}
