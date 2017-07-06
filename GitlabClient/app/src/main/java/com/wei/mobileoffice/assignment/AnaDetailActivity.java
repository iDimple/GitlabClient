package com.wei.mobileoffice.assignment;

import android.app.ProgressDialog;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.wei.mobileoffice.BaseActivity;
import com.wei.mobileoffice.R;
import com.wei.mobileoffice.model.Analysis;
import com.wei.mobileoffice.model.MetricData;
import com.wei.mobileoffice.model.QuestionResults;
import com.wei.mobileoffice.model.ScoreResult;
import com.wei.mobileoffice.model.TestResult;
import com.wei.mobileoffice.model.Testcase;
import com.wei.mobileoffice.util.DataTypeChange;
import com.wei.mobileoffice.util.UIHelper;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.apache.commons.codec.binary.Base64;

import java.util.HashMap;

import okhttp3.Call;

import static android.content.ContentValues.TAG;

//可以返回学生的得分，测试用例通过情况和统计分析
public class AnaDetailActivity extends BaseActivity implements View.OnClickListener {

    private TextView title;
    private TextView startAt;
    private TextView endAt;
    private TextView scored;
    private TextView testResultgit_url;
    private TextView compile_succeeded;
    private TextView tested;
    private TextView testcasesname;
    private TextView passed;
    private TextView metricDatagit_url;
    private TextView measured;
    private TextView total_line_count;
    private TextView comment_line_count;
    private TextView field_count;
    private TextView method_count;
    private TextView max_coc;
    private HashMap<Integer, Fragment> mFragments = new HashMap<Integer, Fragment>();
    private Analysis analysis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ana_detail);
        initView();
    }

    private void initView() {
        ((TextView) findViewById(R.id.title_bar_title)).setText("详细信息");

        title = (TextView) findViewById(R.id.title);
        startAt = (TextView) findViewById(R.id.startAt);
        endAt = (TextView) findViewById(R.id.endAt);
        scored = (TextView) findViewById(R.id.scored);
        testResultgit_url = (TextView) findViewById(R.id.testResultgit_url);
        compile_succeeded = (TextView) findViewById(R.id.compile_succeeded);
        tested = (TextView) findViewById(R.id.tested);
        testcasesname = (TextView) findViewById(R.id.testcasesname);
        passed = (TextView) findViewById(R.id.passed);
        metricDatagit_url = (TextView) findViewById(R.id.metricDatagit_url);
        measured = (TextView) findViewById(R.id.measured);
        total_line_count = (TextView) findViewById(R.id.total_line_count);
        comment_line_count = (TextView) findViewById(R.id.comment_line_count);
        field_count = (TextView) findViewById(R.id.field_count);
        method_count = (TextView) findViewById(R.id.method_count);
        max_coc = (TextView) findViewById(R.id.max_coc);

        findViewById(R.id.title_bar_back).setOnClickListener(this);

        findViewById(R.id.rl_endAt).setOnClickListener(this);
        getInfo();
    }

    @Override
    public void onClick(View v) {

    }


    boolean jsonTovo(String json) {
        Gson gson = new Gson();
        if (json.equals("[]")) {
            System.out.println("asd" + analysis.getStudentId());
            return false;
        }

        analysis = gson.fromJson(json, Analysis.class);
        if (analysis.getQuestionResults().size() == 0) {
            System.out.println("sizeis0");
            return false;
        }
        System.out.println("asd" + analysis.getStudentId());
        System.out.println("getinfo" + analysis.getAssignmentId());
        return true;
    }


    private void getInfo() {
        final ProgressDialog progressDialog = UIHelper.showProDialog(this, "", "加载中", null);
        byte[] tokenByte = Base64.encodeBase64(("nanguangtailang" + ":" + 123).getBytes());
        String tokenStr = DataTypeChange.bytesSub2String(tokenByte, 0, tokenByte.length);
        String token = "Basic " + tokenStr;
        String url = "http://115.29.184.56:8090/api/assignment/";
        int groupId = getIntent().getIntExtra("assignmentId", 38);
        url += groupId;
        url += "/student/";
        url += "281";
        url += "/analysis";
        OkHttpUtils
                .get()
                .addHeader("Authorization", token)
                .url(url)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
                        UIHelper.closeProgress(progressDialog);
                        UIHelper.showToastAsCenter(AnaDetailActivity.this, "连接失败，请重试！");
                    }

                    @Override
                    public void onResponse(String response) {
                        UIHelper.closeProgress(progressDialog);
                        String result = response;
                        Log.i(TAG, result);
                        if (jsonTovo(result)) {

                            setInfo();
                        }

                    }
                });


    }

    private void setInfo() {
        UIHelper.runInMainThread(new Runnable() {
            @Override
            public void run() {
                QuestionResults qr = analysis.getQuestionResults().get(0);
                title.setText(qr.getQuestionTitle());
                ScoreResult sr = qr.getScoreResult();
                startAt.setText(sr.getGit_url());
                endAt.setText(sr.getScore() + "");
                if (sr.isScored()) {
                    scored.setText("true");
                } else {
                    scored.setText("false");
                }
                TestResult tr = qr.getTestResult();
                testResultgit_url.setText(tr.getGit_url());
                if (tr.isCompile_succeeded()) {
                    compile_succeeded.setText("true");
                } else {
                    compile_succeeded.setText("false");
                }
                if (tr.isTested()) {
                    tested.setText("true");
                } else {
                    tested.setText("false");
                }
                Testcase tc = tr.getTestcases().get(0);
                testcasesname.setText(tc.getName());
                if (tc.isPassed()) {
                    passed.setText("true");
                } else {
                    passed.setText("false");
                }
                MetricData md = qr.getMetricData();
                metricDatagit_url.setText(md.getGit_url());
                if (md.isMeasured()) {
                    measured.setText("true");
                } else {
                    measured.setText("false");
                }
                total_line_count.setText(md.getTotal_line_count() + "");
                comment_line_count.setText(md.getComment_line_count() + "");
                field_count.setText(md.getField_count() + "");
                method_count.setText(md.getMethod_count() + "");
                max_coc.setText(md.getMax_coc() + "");
            }
        });
    }

}
