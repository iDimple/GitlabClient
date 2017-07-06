package com.wei.mobileoffice.assignment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wei.mobileoffice.BaseFragment;
import com.wei.mobileoffice.R;
import com.wei.mobileoffice.ui.CircleImageView;
import com.wei.mobileoffice.ui.pulltorefresh.PullToRefreshBase;
import com.wei.mobileoffice.ui.pulltorefresh.PullToRefreshListView;
import com.wei.mobileoffice.util.UIHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class AnalyseFragment extends BaseFragment {

    private PullToRefreshListView listview;
    private AnalyseFragment.ProductListAdapter adapter;
    private List<Integer> list = new ArrayList<>();
    private boolean isStopRefresh = false;//停止刷新

    @Override
    protected View doCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        System.out.println("create");
        fragmentView = inflater.inflate(R.layout.fragment_analyse, container, false);
        list.add(38);
        list.add(93);
        initView();
        return fragmentView;
    }

    @Override
    protected void doActivityCreated(Bundle savedInstanceState) {
        this.createPage(this.getClass().getSimpleName().toLowerCase(Locale.getDefault()));
    }

    private void initView() {
        System.out.println("initVies");
        new Thread(new AnalyseFragment.TaskRunnable()).start();
        fragmentView.findViewById(R.id.refresh_page).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new AnalyseFragment.TaskRunnable()).start();
            }
        });
    }


    class TaskRunnable implements Runnable {
        @Override
        public void run() {
            System.out.println("run");
            showSucceed();

        }
    }


    private void showEmpty() {
        UIHelper.runInMainThread(new Runnable() {
            @Override
            public void run() {
                fragmentView.findViewById(R.id.rl_product_list).setVisibility(View.GONE);
                fragmentView.findViewById(R.id.progress).setVisibility(View.GONE);
                fragmentView.findViewById(R.id.loading_page_error).setVisibility(View.GONE);
                fragmentView.findViewById(R.id.loading_page_empty).setVisibility(View.VISIBLE);
            }
        });
    }

    private void showSucceed() {
        UIHelper.runInMainThread(new Runnable() {
            @Override
            public void run() {
                System.out.println("ss");

                if (list == null || list.size() < 1) {
                    System.out.println("empty");
                    showEmpty();
                    return;
                }
                System.out.println("size" + list.size());
                fragmentView.findViewById(R.id.rl_product_list).setVisibility(View.VISIBLE);
                fragmentView.findViewById(R.id.progress).setVisibility(View.GONE);
                fragmentView.findViewById(R.id.loading_page_error).setVisibility(View.GONE);
                fragmentView.findViewById(R.id.loading_page_empty).setVisibility(View.GONE);
                if (listview == null) {
                    listview = (PullToRefreshListView) fragmentView.findViewById(R.id.product_list);
                    listview.setMode(PullToRefreshBase.Mode.BOTH);
                    adapter = new AnalyseFragment.ProductListAdapter(context);
                    listview.setAdapter(adapter);
                    listview.setOnItemClickListener(listViewItemClickListener);
                } else {
                    adapter.notifyDataSetChanged();
                    listview.onRefreshComplete();
                    if (isStopRefresh) {
                        listview.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                    } else {
                        listview.setMode(PullToRefreshBase.Mode.BOTH);
                    }
                }
            }
        });
    }


    private AdapterView.OnItemClickListener listViewItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            final int pos = position - 1;
            Intent intent = new Intent(getActivity(), ChooseActivity.class);
            intent.putExtra("assignmentId", list.get(pos));
            startActivity(intent);


        }
    };


    class ProductListAdapter extends BaseAdapter {
        private Context context;

        public ProductListAdapter(Context c) {
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
            final AnalyseFragment.ProductListAdapter.ProductItemView itemView;
            if (convertView == null) {
                itemView = new AnalyseFragment.ProductListAdapter.ProductItemView();
                convertView = LayoutInflater.from(context).inflate(
                        R.layout.product_item, null);
                itemView.product_picture = (CircleImageView) convertView.findViewById(R.id.product_picture);
                itemView.product_name = (TextView) convertView.findViewById(R.id.product_name);
                itemView.product_salesunit = (TextView) convertView.findViewById(R.id.product_salesunit);
                convertView.setTag(itemView);
            } else {
                itemView = (AnalyseFragment.ProductListAdapter.ProductItemView) convertView.getTag();
            }
            int model = list.get(position);

            itemView.product_salesunit.setText(model + "");
            System.out.println("model" + model);
            return convertView;
        }

        private class ProductItemView {
            CircleImageView product_picture;
            TextView product_name;
            TextView product_salesunit;
        }
    }
}