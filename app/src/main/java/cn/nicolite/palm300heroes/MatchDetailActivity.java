package cn.nicolite.palm300heroes;

import android.app.ProgressDialog;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import java.io.IOException;

import adapter.MatchDetailRecyclerViewAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import model.recordLogger.MatchDetail;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import other.DividerItemDecoration;
import util.HttpUtil;
import util.Util;

public class MatchDetailActivity extends BaseActivity implements MatchDetailRecyclerViewAdapter.OnItemClickListener {
    @BindView(R.id.match_detail_recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.match_detail_toolbar)
    Toolbar toolbar;
    private MatchDetailRecyclerViewAdapter adapter;
    private MatchDetail matchDetail;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_detail);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        updateData();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        adapter = new MatchDetailRecyclerViewAdapter(this, matchDetail);
        adapter.setOnItemClickListener(this);
        //设置Adapter
        recyclerView.setAdapter(adapter);
        //设置布局管理器
        recyclerView.setLayoutManager(layoutManager);
        //设置为垂直布局，这也是默认的
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        //设置分隔线
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        //设置增加或删除条目的动画
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onItemClick(View view, int position) {

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

    private void updateData(){
        showProgressDialog();
        String address ="http://300report.jumpw.com/api/getmatch?id=" + getIntent().getStringExtra("match_id");
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
                MatchDetail matchDetail = Util.handleMatchDetailResponse(response.body().string());
                if (matchDetail != null && matchDetail.Result.equals("OK")){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            closeProgressDialog();
                        }
                    });
                }
            }
        });
    }
}
