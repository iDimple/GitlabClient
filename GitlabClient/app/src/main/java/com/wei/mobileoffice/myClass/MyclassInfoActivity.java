package com.wei.mobileoffice.myClass;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.wei.mobileoffice.BaseFragment;
import com.wei.mobileoffice.BaseFragmentActivity;
import com.wei.mobileoffice.R;
import com.wei.mobileoffice.model.Student;
import com.wei.mobileoffice.ui.CircleImageView;
import com.wei.mobileoffice.ui.pulltorefresh.PullToRefreshBase;
import com.wei.mobileoffice.ui.pulltorefresh.PullToRefreshListView;
import com.wei.mobileoffice.util.DataTypeChange;
import com.wei.mobileoffice.util.UIHelper;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.apache.commons.codec.binary.Base64;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;

import static android.content.ContentValues.TAG;

public class MyclassInfoActivity extends BaseFragmentActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {

    private HashMap<Integer, Fragment> mFragments = new HashMap<Integer, Fragment>();
    StudentListAdapter adapter;
    private PullToRefreshListView listview;
    private List<Student> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_info);
        initView();
    }

    private void initView() {
        ((TextView) findViewById(R.id.title_bar_title)).setText("学生列表");


        findViewById(R.id.title_bar_back).setOnClickListener(this);

        getInfo();
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

    boolean jsonTovo(String json) {
        Gson gson = new Gson();
        if (json.equals("[]")) {
            return false;
        }
        JsonParser parser = new JsonParser();
        JsonElement jsonEl = parser.parse(json);
        JsonArray jsonArray = null;
        jsonArray = jsonEl.getAsJsonArray();

        Student[] student = gson.fromJson(jsonArray, Student[].class);
        for (int i = 0; i < student.length; i++) {
            list.add(student[i]);
        }
        System.out.println(student[0].getName());
        return true;
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
            adapter = new StudentListAdapter(MyclassInfoActivity.this);
            listview.setAdapter(adapter);
            listview.setOnItemClickListener(listViewItemClickListener);
            System.out.println("finish listview");
        } else {
            System.out.println("listview not  null");
            adapter.notifyDataSetChanged();
            listview.onRefreshComplete();
        }

    }

    private void getInfo() {
        final ProgressDialog progressDialog = UIHelper.showProDialog(this, "", "加载中", null);
        byte[] tokenByte = Base64.encodeBase64(("liuqin" + ":" + 123).getBytes());
        String tokenStr = DataTypeChange.bytesSub2String(tokenByte, 0, tokenByte.length);
        String token = "Basic " + tokenStr;
        String url = "http://115.29.184.56:8090/api/group/";
        int groupId = getIntent().getIntExtra("classid", 1);
        System.out.println(groupId);
        url += groupId;
        url += "/students";
        OkHttpUtils
                .get()
                .addHeader("Authorization", token)
                .url(url)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
                        UIHelper.closeProgress(progressDialog);
                        UIHelper.showToastAsCenter(MyclassInfoActivity.this, "连接失败，请重试！");
                    }

                    @Override
                    public void onResponse(String response) {
                        UIHelper.closeProgress(progressDialog);
                        String result = response;
                        Log.i(TAG, result);
                        System.out.println(getIntent().getIntExtra("classid", 1));
                        jsonTovo(result);
                        System.out.println("custorminfo");
                        showSucceed();
                    }
                });


    }


    private AdapterView.OnItemClickListener listViewItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            final int pos = position - 1;
            Student student = list.get(pos);
            Intent intent = new Intent(MyclassInfoActivity.this, MyclassDetailActivity.class);
            Bundle mBundle = new Bundle();
            mBundle.putSerializable("theStudent", student);
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
            final MyclassInfoActivity.StudentListAdapter.StudentItemView itemView;
            if (convertView == null) {
                //  System.out.println("convertview is  null");
                itemView = new MyclassInfoActivity.StudentListAdapter.StudentItemView();
                convertView = LayoutInflater.from(context).inflate(
                        R.layout.student_item, null);
                itemView.name = (TextView) convertView.findViewById(R.id.student_username);
                itemView.username = (TextView) convertView.findViewById(R.id.student_name);
                itemView.head_icon = (CircleImageView) convertView.findViewById(R.id.head_icon);
                convertView.setTag(itemView);
            } else {
                itemView = (MyclassInfoActivity.StudentListAdapter.StudentItemView) convertView.getTag();
            }
            Student model = list.get(position);
            if (model == null) {
                //  System.out.println("modelis  null");
                return convertView;
            }
            itemView.username.setText(model.getUsername());
            itemView.name.setText(model.getName());
            return convertView;
        }

        private class StudentItemView {
            TextView username;
            TextView name;
            CircleImageView head_icon;
        }
    }
}
