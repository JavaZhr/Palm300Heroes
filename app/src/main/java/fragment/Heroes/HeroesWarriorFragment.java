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
import adapter.RecyclerViewAdapter;
import cn.nicolite.palm300heroes.R;
import database.Palm300heroesDB;
import model.Heroes;
import myInterface.HttpCallbackListener;
import other.DividerItemDecoration;
import utilty.HttpUtil;
import utilty.LogUtil;
import utilty.Utilty;

/**
 * Created by NICOLITE on 2016/10/30 0030.
 */

public class HeroesWarriorFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, RecyclerViewAdapter.OnItemClickListener{
    private SwipeRefreshLayout swipeRefreshLayout;
    private Palm300heroesDB palm300heroesDB;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private RecyclerViewAdapter recycleAdapter;
    private static final int REFRESH_COMPLETE_TIME = 2000;
    private List<Heroes> dataList = new ArrayList<>() ;
    private final String ADDRESS = "http://og0oucran.bkt.clouddn.com/hero.json";

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

        palm300heroesDB = Palm300heroesDB.getInstance(getActivity());
        dataList = Palm300heroesDB.getHeroesTypeDate("战士");

        recyclerView = (RecyclerView) view.findViewById(R.id.heroes_detail_recyclerview);

        recycleAdapter = new RecyclerViewAdapter(getActivity(), dataList);

        recycleAdapter.setOnItemClickListener(this);

        layoutManager = new LinearLayoutManager(getActivity());
        //设置布局管理器
        recyclerView.setLayoutManager(layoutManager);
        //设置为垂直布局，这也是默认的
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        //设置Adapter
        recyclerView.setAdapter(recycleAdapter);
        //设置分隔线
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        //设置增加或删除条目的动画
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            int lastVisibleItem;
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == recycleAdapter.getItemCount()) {
                    //swipeRefreshLayout.setRefreshing(true);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {

                        }
                    }).start();
                    //分页获取数据
                    //获取完成swipeRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = layoutManager.findLastVisibleItemPosition();
            }
        });

        return view;
    }

    private void initHeroesData() {
        palm300heroesDB = Palm300heroesDB.getInstance(getActivity());
        HttpUtil.sendHttpRequest(ADDRESS, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                Utilty.handleHeroesResponse(palm300heroesDB, response);
            }

            @Override
            public void onError(Exception e) {
                LogUtil.d("initHeroesData", "返回数据错误");
            }
        });
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(getActivity(), HeroesDetailActivity.class);
        intent.putExtra("heroes_data", dataList.get(position));
        getActivity().startActivity(intent);
    }

    @Override
    public void onRefresh() {
        initHeroesData();
        dataList = Palm300heroesDB.getHeroesTypeDate("战士");
        handler.sendEmptyMessageDelayed(0, REFRESH_COMPLETE_TIME);
        //重新获取数据
        //获取完成swipeRefreshLayout.setRefreshing(false);
    }
}
