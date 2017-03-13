package fragment;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import adapter.GameRecyclerViewAdapter;
import cn.nicolite.palm300heroes.FightSkillActivity;
import cn.nicolite.palm300heroes.R;
import cn.nicolite.palm300heroes.RecordLoggerActivity;
import other.DividerItemDecoration;


/**
 * Created by NICOLITE on 2016/10/11 0011.
 */

public class GameFragment extends Fragment implements GameRecyclerViewAdapter.OnItemClickListener{
    private RecyclerView recyclerView;
    private LinearLayoutManager  layoutManager;
    private GameRecyclerViewAdapter recycleAdapter;
    private List<String> dataList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.game_fragment, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.game_fragment_recycler_view);

        initDate();
        recycleAdapter = new GameRecyclerViewAdapter(getActivity(), dataList);

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
        return view;
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = null;
        switch (position) {
            case 0 :
                intent = new Intent(getActivity(), RecordLoggerActivity.class);
                break;
            case 1 :
                intent = new Intent(getActivity(), FightSkillActivity.class);
            default: break;
        }
        if (intent != null) {
            getActivity().startActivity(intent);
        }
    }

    private void initDate() {
        dataList.add("战绩查询");
        dataList.add("战斗技能");
    }
}
