package com.wei.mobileoffice.assignment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.wei.mobileoffice.BaseActivity;
import com.wei.mobileoffice.R;
import com.wei.mobileoffice.model.Score;
import com.wei.mobileoffice.util.DataTypeChange;
import com.wei.mobileoffice.util.UIHelper;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.apache.commons.codec.binary.Base64;

import java.io.Serializable;
import java.util.HashMap;

import okhttp3.Call;

import static android.content.ContentValues.TAG;


public class TeacherAssignDetailActivity extends BaseActivity implements View.OnClickListener {

    private TextView title;
    private TextView description;
    private TextView startAt;
    private HashMap<Integer, Fragment> mFragments = new HashMap<Integer, Fragment>();
    private Score score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_detail);
        initView();
    }

    private void initView() {
        ((TextView) findViewById(R.id.title_bar_title)).setText("详细信息");

        title = (TextView) findViewById(R.id.title);
        description = (TextView) findViewById(R.id.description);
        startAt = (TextView) findViewById(R.id.startAt);

        findViewById(R.id.title_bar_back).setOnClickListener(this);

        findViewById(R.id.rl_endAt).setOnClickListener(this);
        getInfo();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_endAt:
                System.out.println("学生分数详情");
                Intent intent = new Intent(TeacherAssignDetailActivity.this, StudentLIstActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("scoreList", (Serializable)score.getQuestions().get(0).getStudents());
                intent.putExtras(bundle);
                startActivity(intent);
                break;

        }
    }


    boolean jsonTovo(String json) {
        Gson gson = new Gson();
        if (json.equals("[]")) {
            return false;
        }
        score = gson.fromJson(json, Score.class);
        System.out.println("asd"+score.getAssignmentId());
        System.out.println("getinfo"+score.getQuestions().get(0).getQuestionInfo().getTitle());
        return true;
    }


    private void getInfo() {
        final ProgressDialog progressDialog = UIHelper.showProDialog(this, "", "加载中", null);
        byte[] tokenByte = Base64.encodeBase64(("liuqin" + ":" + 123).getBytes());
        String tokenStr = DataTypeChange.bytesSub2String(tokenByte, 0, tokenByte.length);
        String token = "Basic " + tokenStr;
        String url = "http://115.29.184.56:8090/api/assignment/";
        int groupId = getIntent().getIntExtra("assignmentId", 38);
        url += groupId;
        url += "/score";
        OkHttpUtils
                .get()
                .addHeader("Authorization", token)
                .url(url)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
                        UIHelper.closeProgress(progressDialog);
                        UIHelper.showToastAsCenter(TeacherAssignDetailActivity.this, "连接失败，请重试！");
                    }

                    @Override
                    public void onResponse(String response) {
                        UIHelper.closeProgress(progressDialog);
                        String result = response;
                        Log.i(TAG, result);
                        jsonTovo(result);

                        setInfo();

                    }
                });


    }

    private void setInfo() {
        UIHelper.runInMainThread(new Runnable() {
            @Override
            public void run() {

                title.setText(score.getQuestions().get(0).getQuestionInfo().getTitle());
                description.setText(score.getQuestions().get(0).getQuestionInfo().getDescription());
                startAt.setText(score.getQuestions().get(0).getQuestionInfo().getType());

            }
        });
    }

}
