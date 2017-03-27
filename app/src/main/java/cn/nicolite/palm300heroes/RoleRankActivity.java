package cn.nicolite.palm300heroes;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;

import adapter.RoleRankRecyclerViewAdapter;
import model.recordLogger.Role;
import other.DividerItemDecoration;

/**
 * Created by NICOLITE on 2017/2/6 0006.
 */

public class RoleRankActivity extends BaseActivity implements RoleRankRecyclerViewAdapter.OnItemClickListener{
    private List<Role.Rank> dataList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //透明导航栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);

            actionBar.setDisplayHomeAsUpEnabled(true);

            actionBar.setDisplayShowHomeEnabled(true);

            actionBar.setDisplayShowTitleEnabled(false);
            //透明ActionBar
            actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
        }
        setContentView(R.layout.role_rank_layout);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.role_rank_recycler_view);
        Role role = (Role) getIntent().getSerializableExtra("role_data");
        if (role.Rank != null){
            dataList.addAll(role.Rank);
        }
        RoleRankRecyclerViewAdapter recyclerAdapter = new RoleRankRecyclerViewAdapter(this, dataList);
        recyclerAdapter.setOnItemClickListener(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        //设置布局管理器
        recyclerView.setLayoutManager(layoutManager);
        //设置为垂直布局，这也是默认的
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        //设置Adapter
        recyclerView.setAdapter(recyclerAdapter);
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

    }
}
