package activity;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.pgyersdk.crash.PgyCrashManager;

import java.util.ArrayList;
import java.util.List;

import adapter.ServerRankingRecyclerViewAdapter;
import cn.nicolite.palm300heroes.R;
import model.Record;
import model.ServerRanking;
import other.DividerItemDecoration;

/**
 * Created by NICOLITE on 2017/2/6 0006.
 */

public class ServerRankingActivity extends AppCompatActivity implements ServerRankingRecyclerViewAdapter.OnItemClickListener{
    private RecyclerView recyclerView;
    private ServerRankingRecyclerViewAdapter recyclerAdapter;
    private LinearLayoutManager layoutManager;

    private List<ServerRanking> dataList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //透明导航栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        //透明ActionBar
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.transparent)));

        setContentView(R.layout.server_ranking_layout);

        //蒲公英Crash捕获注册
        PgyCrashManager.register(this);

        recyclerView = (RecyclerView) findViewById(R.id.server_ranking_recycler_view);

        dataList = (List<ServerRanking>) getIntent().getSerializableExtra("ranking_list");

        recyclerAdapter = new ServerRankingRecyclerViewAdapter(this, dataList);

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
    protected void onDestroy() {
        super.onDestroy();
        //解除蒲公英Crash捕获注册
        PgyCrashManager.unregister();
    }

    @Override
    public void onItemClick(View view, int position) {
        String clickUrl= dataList.get(position).getClickUrl();
        Toast.makeText(this, clickUrl , Toast.LENGTH_SHORT).show();
    }
}
