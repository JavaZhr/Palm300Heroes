package fragment.Heroes.HeroesDetail;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import adapter.SkillRecyclerViewAdapter;
import cn.nicolite.palm300heroes.R;
import database.Palm300heroesDB;
import model.Heroes;
import model.Skill;
import myInterface.HttpCallbackListener;
import other.DividerItemDecoration;
import utilty.HttpUtil;
import utilty.LogUtil;
import utilty.Utilty;

/**
 * Created by NICOLITE on 2016/11/7 0007.
 */

public class HeroesSkillFragment extends Fragment implements SkillRecyclerViewAdapter.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener{
    private List<Skill> dataList = new ArrayList<>();
    private SkillRecyclerViewAdapter recycleAdapter;

    private SwipeRefreshLayout swipeRefreshLayout;
    private static final int REFRESH_COMPLETE_TIME = 2000;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0 :
                    swipeRefreshLayout.setRefreshing(false);
                    recycleAdapter.notifyDataSetChanged();
                    break;
                default:break;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R. layout.heroes_detail_skill_fragment, container, false);

        readSkillDate();

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.skill_swipe_refresh_layout);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.orange));
        swipeRefreshLayout.setOnRefreshListener(this);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.heroes_skill_recycler_view);

        recycleAdapter = new SkillRecyclerViewAdapter(getActivity(), dataList);

        recycleAdapter.setOnItemClickListener(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        //设置布局管理器
        recyclerView.setLayoutManager(layoutManager);
        //设置为垂直布局，这也是默认的
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        //设置Adapter
        recyclerView.setAdapter(recycleAdapter);
        //设置分隔线
        //recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        //设置增加或删除条目的动画
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        return view;
    }

    @Override
    public void onItemClick(View view, int position) {

    }

    private void readSkillDate(){
        Palm300heroesDB palm300heroesDB = Palm300heroesDB.getInstance(getActivity());
        List<Skill> list = palm300heroesDB.loadSkill();
        dataList.clear();
        Heroes heroes = (Heroes) getActivity().getIntent().getSerializableExtra("heroes_data");
        for (Skill skill : list) {
            if (skill.getHero().equals(heroes.getName())) {
                dataList.add(skill);
            }
        }
    }

    @Override
    public void onRefresh() {
        Utilty.initSkillDate(getActivity());
        readSkillDate();
        handler.sendEmptyMessageDelayed(0, REFRESH_COMPLETE_TIME);
    }
}
