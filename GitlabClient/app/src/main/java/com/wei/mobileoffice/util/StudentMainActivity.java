package com.wei.mobileoffice.util;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.wei.mobileoffice.R;
import com.wei.mobileoffice.home.StudentHomeFragment;
import com.wei.mobileoffice.model.Nanguangtailang;
import com.wei.mobileoffice.personalInfo.StudentInfoFragment;
import com.wei.mobileoffice.ui.MainFragmentTabHost;

public class StudentMainActivity extends FragmentActivity {

    /**
     * FragmentTabhost
     */
    private MainFragmentTabHost mTabHost;

    /**
     * 布局填充器
     */
    private LayoutInflater mLayoutInflater;

    /**
     * Fragment数组界面
     */
    private Class mFragmentArray[] = {StudentHomeFragment.class, StudentInfoFragment.class};
    /**
     * 存放按钮图片数组
     */
    private int mImageArray[] = {R.drawable.tab_home_btn, R.drawable.tab_selfinfo_btn};

    /**
     * 选修卡文字
     */
    private String mTextArray[] = {"首页", "我的"};
    public static StudentMainActivity thisInstance = null;
    private Nanguangtailang nanguangtailang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_main);
        if (thisInstance != null) {
            thisInstance.finish();
        }
        thisInstance = this;
        Intent intent = getIntent();
        String result = intent.getStringExtra("StudentInfo");
        jsonTovo(result);
        initView();




    }



    void jsonTovo(String json) {
        Gson gson = new Gson();
        JsonParser parser = new JsonParser();
        JsonElement jsonEl = parser.parse(json);
        JsonObject jsonObj = null;
        jsonObj = jsonEl.getAsJsonObject();

            nanguangtailang= gson.fromJson(jsonObj, Nanguangtailang.class);
            System.out.println(nanguangtailang.getName());
        System.out.println(nanguangtailang.getId());
    }

    /**
     * 初始化组件
     */
    private void initView() {
        mLayoutInflater = LayoutInflater.from(this);

        // 找到TabHost
        mTabHost = (MainFragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
        // 得到fragment的个数
        int count = mFragmentArray.length;
        for (int i = 0; i < count; i++) {
            // 给每个Tab按钮设置图标、文字和内容
            TabHost.TabSpec tabSpec = mTabHost.newTabSpec(mTextArray[i])
                    .setIndicator(getTabItemView(i));
            // 将Tab按钮添加进Tab选项卡中
            mTabHost.addTab(tabSpec, mFragmentArray[i], null);
            // 设置Tab按钮的背景
            mTabHost.getTabWidget().getChildAt(i)
                    .setBackgroundResource(R.drawable.selector_tab_background);
        }

    }

    /**
     * 给每个Tab按钮设置图标和文字
     */
    private View getTabItemView(int index) {
        View view = mLayoutInflater.inflate(R.layout.tab_item_view, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.imageview);
        imageView.setImageResource(mImageArray[index]);
        TextView textView = (TextView) view.findViewById(R.id.textview);
        textView.setText(mTextArray[index]);

        return view;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (thisInstance == this) {
            thisInstance = null;
        }
    }

}
