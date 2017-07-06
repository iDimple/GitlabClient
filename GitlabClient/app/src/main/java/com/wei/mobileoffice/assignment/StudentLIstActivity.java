package com.wei.mobileoffice.assignment;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wei.mobileoffice.BaseFragment;
import com.wei.mobileoffice.BaseFragmentActivity;
import com.wei.mobileoffice.R;
import com.wei.mobileoffice.model.Students;
import com.wei.mobileoffice.ui.CircleImageView;
import com.wei.mobileoffice.ui.pulltorefresh.PullToRefreshBase;
import com.wei.mobileoffice.ui.pulltorefresh.PullToRefreshListView;
import com.wei.mobileoffice.util.UIHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StudentLIstActivity extends BaseFragmentActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {

    private HashMap<Integer, Fragment> mFragments = new HashMap<Integer, Fragment>();
    StudentLIstActivity.StudentListAdapter adapter;
    private PullToRefreshListView listview;
    private List<Students> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);
        list=(List)getIntent().getSerializableExtra("scoreList");
        initView();
    }








    private void initView() {
        ((TextView) findViewById(R.id.title_bar_title)).setText("学生列表");


        findViewById(R.id.title_bar_back).setOnClickListener(this);
        showSucceed();

    }

    @Override
    public void onClick(View v) {

    }


    @Override
    public void onPageScrollStateChanged(int arg0) {

    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {

    }

    @Override
    public void onPageSelected(int position) {
        BaseFragment fragment = (BaseFragment) adapter.getItem(position);
        fragment.load();
    }



    private void showSucceed() {

        if (list == null || list.size() < 1) {
            showEmpty();
            return;
        }
        findViewById(R.id.rl_student_list).setVisibility(View.VISIBLE);
        findViewById(R.id.progress).setVisibility(View.GONE);
        findViewById(R.id.loading_page_error).setVisibility(View.GONE);
        findViewById(R.id.loading_page_empty).setVisibility(View.GONE);
        if (listview == null) {
            System.out.println("listview is  null");
            listview = (PullToRefreshListView) findViewById(R.id.student_list);
            listview.setMode(PullToRefreshBase.Mode.BOTH);
            adapter = new StudentLIstActivity.StudentListAdapter(StudentLIstActivity.this);
            listview.setAdapter(adapter);
            listview.setOnItemClickListener(listViewItemClickListener);
            System.out.println("finish listview");
        } else {
            System.out.println("listview not  null");
            adapter.notifyDataSetChanged();
            listview.onRefreshComplete();
        }

    }



    private AdapterView.OnItemClickListener listViewItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            final int pos = position - 1;
            Students student = list.get(pos);
            Intent intent = new Intent(StudentLIstActivity.this, ScoreListActivity .class);
            Bundle mBundle = new Bundle();
            mBundle.putSerializable("detailScore", student);
            intent.putExtras(mBundle);
            startActivity(intent);
        }
    };

    private void showEmpty() {
        UIHelper.runInMainThread(new Runnable() {
            @Override
            public void run() {
                findViewById(R.id.rl_student_list).setVisibility(View.GONE);
                findViewById(R.id.progress).setVisibility(View.GONE);
                findViewById(R.id.loading_page_error).setVisibility(View.GONE);
                findViewById(R.id.loading_page_empty).setVisibility(View.VISIBLE);
            }
        });
    }

    class StudentListAdapter extends BaseAdapter {
        private Context context;

        public StudentListAdapter(Context c) {
            context = c;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final StudentLIstActivity.StudentListAdapter.StudentItemView itemView;
            if (convertView == null) {
                //  System.out.println("convertview is  null");
                itemView = new StudentLIstActivity.StudentListAdapter.StudentItemView();
                convertView = LayoutInflater.from(context).inflate(
                        R.layout.student_item, null);
                itemView.name = (TextView) convertView.findViewById(R.id.student_username);
                itemView.username = (TextView) convertView.findViewById(R.id.student_name);
                itemView.head_icon = (CircleImageView) convertView.findViewById(R.id.head_icon);
                convertView.setTag(itemView);
            } else {
                itemView = (StudentLIstActivity.StudentListAdapter.StudentItemView) convertView.getTag();
            }
            Students model = list.get(position);
            if (model == null) {
                //  System.out.println("modelis  null");
                return convertView;
            }
            itemView.name.setText("学号  "+model.getStudentNumber()+"");
            itemView.username.setText("分数  "+model.getScore());
            return convertView;
        }

        private class StudentItemView {
            TextView username;
            TextView name;
            CircleImageView head_icon;
        }
    }
}
