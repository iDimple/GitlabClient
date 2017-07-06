package com.wei.mobileoffice.exam;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.wei.mobileoffice.BaseFragmentActivity;
import com.wei.mobileoffice.R;
import com.wei.mobileoffice.model.Exam;


public class ExamInfoActivity extends BaseFragmentActivity implements ViewPager.OnPageChangeListener, View.OnClickListener {

    private Exam exam;

    private TextView title;
    private TextView description;
    private TextView startAt;
    private TextView endAt;
    private TextView questions;
    private TextView course;
    private TextView status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.opportunity_info);
        exam = (Exam) getIntent().getSerializableExtra("detailExam");
        System.out.println(exam.getTitle());
        initView();
    }

    private void initView() {
        ((TextView) findViewById(R.id.title_bar_title)).setText("详细信息");

        title = (TextView) findViewById(R.id.title);
        description = (TextView) findViewById(R.id.description);
        startAt = (TextView) findViewById(R.id.startAt);
        endAt = (TextView) findViewById(R.id.endAt);
        questions = (TextView) findViewById(R.id.questions);
        course = (TextView) findViewById(R.id.course);
        status = (TextView) findViewById(R.id.status);

        findViewById(R.id.title_bar_back).setOnClickListener(this);

        findViewById(R.id.rl_questions).setOnClickListener(this);
        setInfo();
    }

    @Override
    public void onClick(View v) {


        switch (v.getId()) {
            case R.id.rl_questions:
                System.out.println("问题列表");
                Intent intent = new Intent(ExamInfoActivity.this, QuestionActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("detailQues", exam.getQuestions().get(0));
                intent.putExtras(bundle);
                startActivity(intent);
                break;

        }
    }


    @Override
    public void onPageScrollStateChanged(int arg0) {

    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {

    }

    @Override
    public void onPageSelected(int position) {

    }


    private void setInfo() {
        if (exam != null) {
            title.setText(exam.getTitle());
            description.setText(exam.getDescription());
            startAt.setText(exam.getStartAt());
            endAt.setText(exam.getEndAt());
            //  questions.setText(exam.getQuestions());
            //         course.setText(exam.getCourse());
            switch (exam.getStatus()) {
                case "newly":
                    status.setText("新建态");
                case "initing":
                    status.setText("正在初始化");
                case "initFail":
                    status.setText("初始化失败");
                case "initSuccess":
                    status.setText("初始化成功");
                case "ongoing":
                    status.setText("考试正在进行");
                case "timeup":
                    status.setText("考试时间到");
                case "analyzing":
                    status.setText("正在分析结果");
                default:
                    status.setText("结果分析完毕");
            }
        }
    }

}
