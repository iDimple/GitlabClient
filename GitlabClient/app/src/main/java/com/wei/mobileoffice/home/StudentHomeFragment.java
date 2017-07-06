package com.wei.mobileoffice.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wei.mobileoffice.BaseFragment;
import com.wei.mobileoffice.R;
import com.wei.mobileoffice.exercise.ExerciseActivity;
import com.wei.mobileoffice.homework.HomeworkActivity;
import com.wei.mobileoffice.exam.ExamActivity;
import com.wei.mobileoffice.assignment.AnalyseActivity;

import java.util.Locale;


public class StudentHomeFragment extends BaseFragment implements View.OnClickListener {

    @Override
    protected View doCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.fragment_student_home, container, false);
        initView();
        return fragmentView;
    }

    @Override
    protected void doActivityCreated(Bundle savedInstanceState) {
        this.createPage(this.getClass().getSimpleName().toLowerCase(Locale.getDefault()));
    }

    private void initView(){
        (fragmentView.findViewById(R.id.tab_contacts_img)).setOnClickListener(this);
        (fragmentView.findViewById(R.id.tab_opportunity_img)).setOnClickListener(this);
        (fragmentView.findViewById(R.id.tab_contract_img)).setOnClickListener(this);
        (fragmentView.findViewById(R.id.tab_product_img)).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.tab_contacts_img:
                intent = new Intent(this.getActivity(), ExerciseActivity.class);
                startActivity(intent);
                break;
            case R.id.tab_contract_img:
                intent = new Intent(this.getActivity(), HomeworkActivity.class);
                startActivity(intent);
                break;
            case R.id.tab_opportunity_img:
                intent = new Intent(this.getActivity(), ExamActivity.class);
                startActivity(intent);
                break;
            case R.id.tab_product_img:
                intent = new Intent(this.getActivity(), AnalyseActivity.class);
                startActivity(intent);
                break;
        }
    }
}
