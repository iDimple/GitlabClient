package com.wei.mobileoffice.exam;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.wei.mobileoffice.BaseFragmentActivity;
import com.wei.mobileoffice.R;
import com.wei.mobileoffice.model.Question;

public class QuestionActivity extends BaseFragmentActivity implements ViewPager.OnPageChangeListener, View.OnClickListener {


    private Question question;

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
        setContentView(R.layout.activity_question);
        question = (Question) getIntent().getSerializableExtra("detailQues");
        initView();
    }

    private void initView() {
        ((TextView) findViewById(R.id.title_bar_title)).setText("题目信息");

        title = (TextView) findViewById(R.id.title);
        description = (TextView) findViewById(R.id.description);
        startAt = (TextView) findViewById(R.id.startAt);
        endAt = (TextView) findViewById(R.id.endAt);
        questions = (TextView) findViewById(R.id.creator);
        course = (TextView) findViewById(R.id.course);
        status = (TextView) findViewById(R.id.status);

        findViewById(R.id.title_bar_back).setOnClickListener(this);

        findViewById(R.id.rl_creator).setOnClickListener(this);
        setInfo();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_creator:
                Intent intent = new Intent(QuestionActivity.this, CreatorActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("detailCreator", question.getCreator());
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
        if (question != null) {
            if (question.getTitle() != null)
                title.setText(question.getTitle());
            if (question.getDescription() != null)
                description.setText(question.getDescription());
            if (question.getDifficulty() != null)
                startAt.setText(question.getDifficulty() + "");
            if (question.getGitUrl() != null)
                endAt.setText(question.getGitUrl());
            if (question.getCreator().getName() != null)
                questions.setText(question.getCreator().getName());
            course.setText(question.getDuration() + "");
            //   status.setText();
        }
    }
}
