package fragment.Heroes;

import android.content.Intent;
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

import activity.HeroesDetailActivity;
import adapter.HeroesRecyclerViewAdapter;
import cn.nicolite.palm300heroes.R;
import database.Palm300heroesDB;
import model.hero.Heroes;
import utilty.Utilty;

/**
 * Created by NICOLITE on 2016/10/30 0030.
 */

public class HeroesWarriorFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, HeroesRecyclerViewAdapter.OnItemClickListener{
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private HeroesRecyclerViewAdapter recycleAdapter;
    private static final int REFRESH_COMPLETE_TIME = 2000;
    private List<Heroes> dataList = new ArrayList<>() ;

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
        View view = inflater.inflate(R.layout.heroes_detail_fragment, container, false);

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.heroes_swipe_refresh_layout);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.orange));
        swipeRefreshLayout.setOnRefreshListener(this);

        readHeroDate();

        recyclerView = (RecyclerView) view.findViewById(R.id.heroes_detail_recycler_view);

        recycleAdapter = new HeroesRecyclerViewAdapter(getActivity(), dataList);

        recycleAdapter.setOnItemClickListener(this);

        layoutManager = new LinearLayoutManager(getActivity());
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

    public void readHeroDate() {
        dataList = Palm300heroesDB.getHeroesTypeDate("战士");
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(getActivity(), HeroesDetailActivity.class);
        intent.putExtra("heroes_data", dataList.get(position));
        getActivity().startActivity(intent);
    }

    @Override
    public void onRefresh() {
        Utilty.initHeroData(getActivity(), 1);
        readHeroDate();
        handler.sendEmptyMessageDelayed(0, REFRESH_COMPLETE_TIME);
        //重新获取数据
        //获取完成swipeRefreshLayout.setRefreshing(false);
    }
}
