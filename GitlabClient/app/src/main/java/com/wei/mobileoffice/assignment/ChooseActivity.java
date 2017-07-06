package com.wei.mobileoffice.assignment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.wei.mobileoffice.BaseActivity;
import com.wei.mobileoffice.R;

public class ChooseActivity extends BaseActivity implements View.OnClickListener {
private int groupId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);
        groupId = getIntent().getIntExtra("assignmentId", 38);
        initView();
    }

    private void initView() {
        ((TextView) findViewById(R.id.title_bar_title)).setText("选择查看");


        findViewById(R.id.rl_analysis).setOnClickListener(this);
        findViewById(R.id.rl_readme).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.rl_analysis:
                intent = new Intent(ChooseActivity.this, AnaDetailActivity.class);
                intent.putExtra("assignmentId", groupId);
                startActivity(intent);
                break;
            case R.id.rl_readme:
                intent = new Intent(ChooseActivity.this, ReadMeActivity.class);
                startActivity(intent);
                break;
        }
    }
}
