package cn.nicolite.palm300heroes;

import android.app.ProgressDialog;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import adapter.FragAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import fragment.Heroes.recordLogger.MatchLostFragment;
import fragment.Heroes.recordLogger.MatchWinFragment;
import model.recordLogger.MatchDetail;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import util.HttpUtil;
import util.LogUtil;
import util.Util;

public class MatchDetailActivity extends BaseActivity{
    @BindView(R.id.match_detail_toolbar)
    Toolbar toolbar;
    @BindView(R.id.match_detail_viewpager)
    ViewPager viewPager;
    @BindView(R.id.match_detail_tab)
    TabLayout tabLayout;
    private ProgressDialog progressDialog;
    private List<Fragment> fragmentList = new ArrayList<>();
    private List<String> typeList = new ArrayList<>();
    private MatchWinFragment matchWinFragment;
    private MatchLostFragment matchLostFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_detail);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("战斗详情");
        }
        viewPager.setAdapter(new FragAdapter(getSupportFragmentManager(), getFragmentList(), getTypeList()));
        tabLayout.setupWithViewPager(viewPager);
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

    private List<Fragment> getFragmentList(){
        fragmentList.clear();
        if (matchWinFragment == null){
            matchWinFragment = new MatchWinFragment();
        }
        if (matchLostFragment == null){
            matchLostFragment = new MatchLostFragment();
        }
        fragmentList.add(matchWinFragment);
        fragmentList.add(matchLostFragment);
        return fragmentList;
    }

    private List<String> getTypeList(){
        typeList.add("获胜方");
        typeList.add("失败方");
        return typeList;
    }

}
