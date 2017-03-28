package cn.nicolite.palm300heroes;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import adapter.FightSkillRecyclerViewAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import database.FightSkillD;

/**
 * Created by NICOLITE on 2017/2/13 0013.
 */

public class FightSkillActivity extends BaseActivity {
    private List<FightSkillD> dataList = new ArrayList<>();
    @BindView(R.id.fight_skill_content)
     TextView fightSkillContent;
    @BindView(R.id.fight_skill_recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.fight_toolbar)
    Toolbar toolbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fight_skill_layout);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("战斗技能");
        }
        dataList = DataSupport.findAll(FightSkillD.class);
        FightSkillRecyclerViewAdapter adapter = new FightSkillRecyclerViewAdapter(this, dataList);
        recyclerView.setAdapter(adapter);
        adapter.setItemOnClickListener(new FightSkillRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, int postion) {
                fightSkillContent.setText(Html.fromHtml(dataList.get(postion).getContent()));
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
