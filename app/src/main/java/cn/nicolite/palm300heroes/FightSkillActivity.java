package cn.nicolite.palm300heroes;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.List;

import adapter.FightSkillRecyclerViewAdapter;
import model.FightSkill;
import util.Util;

/**
 * Created by NICOLITE on 2017/2/13 0013.
 */

public class FightSkillActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FightSkillRecyclerViewAdapter adapter;
    private List<FightSkill.FightSkillInfo> dataList;
    private TextView fightSkillContent;
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
        }
        //透明ActionBar
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
        setContentView(R.layout.fight_skill_layout);

        dataList = Util.getFightSkill(this).fightSkillInfoList;
        recyclerView = (RecyclerView) findViewById(R.id.fight_skill_recycler_view);
        adapter = new FightSkillRecyclerViewAdapter(this, dataList);

        fightSkillContent = (TextView) findViewById(R.id.fight_skill_content);

        recyclerView.setAdapter(adapter);
        adapter.setItemOnClickListener(new FightSkillRecyclerViewAdapter.OnItemClicikListener() {
            @Override
            public void OnItemClick(View view, int postion) {
                fightSkillContent.setText(Html.fromHtml(dataList.get(postion).content));
            }
        });
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 5);
        //设置布局管理器
        recyclerView.setLayoutManager(layoutManager);
        //设置为垂直布局，这也是默认的
        //layoutManager.setOrientation(OrientationHelper.VERTICAL);
        //设置Adapter
        recyclerView.setAdapter(adapter);
        //设置分隔线
       // recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        //设置增加或删除条目的动画
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}
