package fragment.Heroes;

import android.content.Intent;
import android.os.Build;
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

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import adapter.HeroesRecyclerViewAdapter;
import cn.nicolite.palm300heroes.HeroesDetailActivity;
import cn.nicolite.palm300heroes.R;
import database.DBUtil;
import database.HeroD;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import util.HttpUtil;
import util.Util;

/**
 * Created by NICOLITE on 2016/10/30 0030.
 */

public class HeroesShooterFragment extends Fragment implements HeroesRecyclerViewAdapter.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener{
    private SwipeRefreshLayout swipeRefreshLayout;
    private HeroesRecyclerViewAdapter recycleAdapter;
    private List<HeroD> dataList = new ArrayList<>() ;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.heroes_detail_fragment, container, false);

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.heroes_swipe_refresh_layout);
        if (Build.VERSION.SDK_INT>=23){
            swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.orange, null));
        }else {
            swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.orange));
        }
        swipeRefreshLayout.setOnRefreshListener(this);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.heroes_detail_recycler_view);
        dataList = DataSupport.where("herotype like ?", "%射手%").find(HeroD.class);
        recycleAdapter = new HeroesRecyclerViewAdapter(getActivity(), dataList);
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
        Intent intent = new Intent(getActivity(), HeroesDetailActivity.class);
        intent.putExtra("heroes_data", dataList.get(position));
        getActivity().startActivity(intent);
    }


    @Override
    public void onRefresh() {
        String heroAddress = "http://og0oucran.bkt.clouddn.com/hero.json";
        HttpUtil.sendOkHttpRequest(heroAddress, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                        Toast.makeText(getActivity(),"刷新失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                DBUtil.saveHero(Util.handleHeroResponse(response.body().string()), DBUtil.UPDATE);
                dataList.clear();
                dataList.addAll(DataSupport.where("herotype like ?", "%射手%").find(HeroD.class));
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                        recycleAdapter.notifyDataSetChanged();
                    }
                });
            }
        });
        //重新获取数据
        //获取完成swipeRefreshLayout.setRefreshing(false);
    }
}
