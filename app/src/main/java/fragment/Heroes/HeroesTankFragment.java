package fragment.Heroes;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import adapter.HeroesRecyclerViewAdapter;
import cn.nicolite.palm300heroes.HeroesDetailActivity;
import cn.nicolite.palm300heroes.R;
import database.HeroD;

/**
 * Created by NICOLITE on 2016/10/30 0030.
 */

public class HeroesTankFragment extends Fragment implements HeroesRecyclerViewAdapter.OnItemClickListener{
    private HeroesRecyclerViewAdapter recycleAdapter;
    private List<HeroD> dataList = new ArrayList<>() ;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.heroes_detail_fragment, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.heroes_detail_recycler_view);
        dataList = DataSupport.where("herotype like ?", "%坦克%").find(HeroD.class);
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
}
