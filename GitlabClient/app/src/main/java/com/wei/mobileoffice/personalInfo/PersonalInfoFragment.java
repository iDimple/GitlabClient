package com.wei.mobileoffice.personalInfo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.wei.mobileoffice.BaseFragment;
import com.wei.mobileoffice.R;
import com.wei.mobileoffice.model.Liuqin;
import com.wei.mobileoffice.platform.StringUtil;
import com.wei.mobileoffice.util.CacheUtils;
import com.wei.mobileoffice.util.UIHelper;

import java.util.Locale;


public class PersonalInfoFragment extends BaseFragment implements View.OnClickListener {



    private TextView userId;
    private TextView nickTitle;
    private TextView nickName;
    private TextView department;
    private TextView address;
    private TextView phone;
    private TextView weixin;
    private Liuqin liuqin;



    @Override
    protected View doCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.settings_fragment, container ,false);

        initView();
        initData();

        return fragmentView;
    }

    @Override
    protected void doActivityCreated(Bundle savedInstanceState) {
        this.createPage(this.getClass().getSimpleName().toLowerCase(Locale.getDefault()));
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    private void initView() {
        userId = (TextView) fragmentView.findViewById(R.id.userId);
        nickTitle = (TextView) fragmentView.findViewById(R.id.nick_title);
        nickName = (TextView) fragmentView.findViewById(R.id.nickname);
        department = (TextView) fragmentView.findViewById(R.id.department);
        address = (TextView) fragmentView.findViewById(R.id.address);
        phone = (TextView) fragmentView.findViewById(R.id.communication);
        weixin = (TextView) fragmentView.findViewById(R.id.weixin);

        fragmentView.findViewById(R.id.rl_nick).setOnClickListener(this);
        fragmentView.findViewById(R.id.rl_communication).setOnClickListener(this);
        fragmentView.findViewById(R.id.rl_weixin).setOnClickListener(this);
        fragmentView.findViewById(R.id.rl_address).setOnClickListener(this);
        fragmentView.findViewById(R.id.logout_icon).setOnClickListener(this);
    }

    private void initData() {
        String result=CacheUtils.getString(context, "login", "");
        if (!StringUtil.isBlank(result)){
            jsonTovo(result);
            setUserInfo();
        } else {
        }
    }

    @Override
    public void onClick(View v) {

    }


    void jsonTovo(String json) {
        Gson gson = new Gson();
        JsonParser parser = new JsonParser();
        JsonElement jsonEl = parser.parse(json);
        JsonObject jsonObj = null;
        jsonObj = jsonEl.getAsJsonObject();

        liuqin = gson.fromJson(jsonObj, Liuqin.class);
        System.out.println(liuqin.getEmail());
    }
    /**
     * 登录页面设置操作
     */
    private void setUserInfo() {
        UIHelper.runInMainThread(new Runnable() {
            @Override
            public void run() {
//                if (com.wei.mobileoffice.platform.util.StringUtil.isNotBlank(staffModel.getAvatar())) {
//                    ImageLoader.getInstance().displayImage(staffModel.getAvatar(),  (CircleImageView) fragmentView.findViewById(R.id.head_icon), OfficeAppContext.getOptions());
//                }
                nickTitle.setText(liuqin.getUsername());
                userId.setText(liuqin.getUsername());
                nickName.setText( liuqin.getName());

                department.setText(liuqin.getType());
                address.setText(liuqin.getGender());
                phone.setText(liuqin.getEmail());
             //   weixin.setText(liuqin.getSchoolId());
            }
        });
    }
}
