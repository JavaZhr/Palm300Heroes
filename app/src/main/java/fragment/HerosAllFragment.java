package fragment;

import android.os.Bundle;
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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import adapter.RecyclerViewAdapter;
import cn.nicolite.palm300heros.R;
import database.Palm300herosDB;
import model.Heros;
import myInterface.HttpCallbackListener;
import other.DividerItemDecoration;
import utilty.HttpUtil;
import utilty.LogUtil;
import utilty.Utilty;

/**
 * Created by NICOLITE on 2016/10/30 0030.
 */

public class HerosAllFragment extends Fragment implements RecyclerViewAdapter.OnItemClickListener {
    private SwipeRefreshLayout swipeRefreshLayout;
    private Palm300herosDB palm300herosDB;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private RecyclerViewAdapter recycleAdapter;
    private List<Heros> dataList = new ArrayList<>() ;
    private final String ADDRESS = "http://og0oucran.bkt.clouddn.com/hero.json";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.heros_detail_fragment, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.heros_detail_recyclerview);

        initHerosData();

        dataList = palm300herosDB.loadHeros();

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

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.heros_swipe_refresh_layout);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.orange));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //重新获取数据
                //获取完成swipeRefreshLayout.setRefreshing(false);
            }
        });
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            int lastVisibleItem;
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == recycleAdapter.getItemCount()) {
                    swipeRefreshLayout.setRefreshing(true);
                    swipeRefreshLayout.setRefreshing(false);
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

    private void initHerosData() {
        palm300herosDB = Palm300herosDB.getInstance(getActivity());
        HttpUtil.sendHttpRequest(ADDRESS, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                Utilty.handleHerosResponse(palm300herosDB, response);
            }

            @Override
            public void onError(Exception e) {
                LogUtil.d("initHerosData", "返回数据错误");
            }
        });
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(getActivity(), "点击了 " + dataList.get(position).getName(), Toast.LENGTH_SHORT).show();
    }
}
