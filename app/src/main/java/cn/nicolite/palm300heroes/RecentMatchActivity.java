package cn.nicolite.palm300heroes;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import adapter.RecentMatchRecyclerViewAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import model.recordLogger.RecentMatchList;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import other.DividerItemDecoration;
import util.HttpUtil;
import util.Util;

/**
 * Created by NICOLITE on 2017/2/6 0006.
 */

public class RecentMatchActivity extends BaseActivity implements RecentMatchRecyclerViewAdapter.OnItemClickListener{
    private RecentMatchRecyclerViewAdapter adapter;
    private List<RecentMatchList.MatchList> dataList = new ArrayList<>();
    private ProgressDialog progressDialog;
    @BindView(R.id.match_list_recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.recent_match_toolbar)
    Toolbar toolbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recent_match_layout);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("最近战斗");
        }
        updateData();
        adapter = new RecentMatchRecyclerViewAdapter(this, dataList);
        adapter.setOnItemClickListener(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        //设置布局管理器
        recyclerView.setLayoutManager(layoutManager);
        //设置为垂直布局，这也是默认的
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        //设置Adapter
        recyclerView.setAdapter(adapter);
        //设置分隔线
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        //设置增加或删除条目的动画
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onItemClick(View view, int position) {
        /*Intent intent = new Intent(this, MatchDetailActivity.class);
        intent.putExtra("match_id", dataList.get(position).MatchID);
        startActivity(intent);*/
    }

    private void updateData(){
        showProgressDialog();
        String roleName = getIntent().getStringExtra("role_name");
        String address = "http://300report.jumpw.com/api/getlist?name=" + Uri.encode(roleName);
        HttpUtil.sendOkHttpRequest(address, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        closeProgressDialog();
                        Toast.makeText(getApplicationContext(), "查询失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                RecentMatchList recentMatchList = Util.handleRecentMatchListResponse(response.body().string());
                if (recentMatchList != null && recentMatchList.Result.equals("OK")){
                    dataList.clear();
                    dataList.addAll(recentMatchList.matchListList);
                }else {
                    Toast.makeText(getApplicationContext(), "查询失败", Toast.LENGTH_SHORT).show();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        closeProgressDialog();
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }

    private void showProgressDialog(){
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("查询中...");
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();
    }

    private void closeProgressDialog() {
        if (progressDialog != null){
            progressDialog.dismiss();
        }
    }
}
