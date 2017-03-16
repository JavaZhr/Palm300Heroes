package fragment.Heroes.HeroesDetail;

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

import adapter.SkinRecyclerViewAdapter;
import cn.nicolite.palm300heroes.R;
import database.DBUtil;
import database.HeroD;
import database.SkinD;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import util.HttpUtil;
import util.Util;

/**
 * Created by NICOLITE on 2016/11/7 0007.
 */

public class HeroesSkinFragment extends Fragment implements SkinRecyclerViewAdapter.OnItemClickListener{
    private List<SkinD> dataList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R. layout.heroes_detail_skin_fragment, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.heroes_skin_recycler_view);
        HeroD heroes = (HeroD) getActivity().getIntent().getSerializableExtra("heroes_data");
        dataList.addAll(DataSupport.where("hero = ?", heroes.getHeroName()).find(SkinD.class));
        SkinRecyclerViewAdapter recycleAdapter = new SkinRecyclerViewAdapter(getActivity(), dataList);
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
}
