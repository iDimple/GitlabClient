package com.wei.mobileoffice.assignment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.wei.mobileoffice.BaseActivity;
import com.wei.mobileoffice.R;
import com.wei.mobileoffice.model.Readme;
import com.wei.mobileoffice.util.DataTypeChange;
import com.wei.mobileoffice.util.UIHelper;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.apache.commons.codec.binary.Base64;

import okhttp3.Call;

import static android.content.ContentValues.TAG;

public class ReadMeActivity extends BaseActivity implements View.OnClickListener  {
    private TextView readMe;
    private Readme readme;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_me);
        initView();
    }
    private void initView() {
        ((TextView) findViewById(R.id.title_bar_title)).setText("ReadMe");
        readMe = (TextView) findViewById(R.id.readme);
        getInfo();
    }

    @Override
    public void onClick(View v) {}
    boolean jsonTovo(String json) {
        Gson gson = new Gson();
        if (json.equals("[]")) {
            return false;
        }

        readme = gson.fromJson(json, Readme.class);

        System.out.println("readme" + readme.getContent());
        return true;
    }

    private void getInfo() {
        final ProgressDialog progressDialog = UIHelper.showProDialog(this, "", "加载中", null);
        byte[] tokenByte = Base64.encodeBase64(("nanguangtailang" + ":" + 123).getBytes());
        String tokenStr = DataTypeChange.bytesSub2String(tokenByte, 0, tokenByte.length);
        String token = "Basic " + tokenStr;
        String url = "http://115.29.184.56:8090/api/assignment/";
        int groupId = getIntent().getIntExtra("assignmentId", 38);
        url += 98;
        url += "/student/";
        url += "227";
        url += "/question/";
        url+="26";
        OkHttpUtils
                .get()
                .addHeader("Authorization", token)
                .url(url)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
                        UIHelper.closeProgress(progressDialog);
                        UIHelper.showToastAsCenter(ReadMeActivity.this, "连接失败，请重试！");
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
                readMe.setText(readme.getContent());
            }
        }
