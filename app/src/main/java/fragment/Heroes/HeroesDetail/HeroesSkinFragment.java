package fragment.Heroes.HeroesDetail;

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

import java.util.ArrayList;
import java.util.List;

import adapter.SkinRecyclerViewAdapter;
import cn.nicolite.palm300heroes.R;
import database.Palm300heroesDB;
import model.Heroes;
import model.Skin;
import utilty.LogUtil;

/**
 * Created by NICOLITE on 2016/11/7 0007.
 */

public class HeroesSkinFragment extends Fragment implements SkinRecyclerViewAdapter.OnItemClickListener{
    private Palm300heroesDB palm300heroesDB;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private SkinRecyclerViewAdapter recycleAdapter;
    private List<Skin> dataList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R. layout.heroes_detail_skin_fragment, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.heroes_skin_recycler_view);

        initSkinDate();

        recycleAdapter = new SkinRecyclerViewAdapter(getActivity(), dataList);

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

        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener(){
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

    @Override
    public void onItemClick(View view, int position) {

    }

    private void initSkinDate() {
        palm300heroesDB = palm300heroesDB.getInstance(getActivity());
        List<Skin> list = palm300heroesDB.loadSkin();
        dataList.clear();
        Heroes heroes = (Heroes) getActivity().getIntent().getSerializableExtra("heroes_data");
        for (Skin skin : list) {
            if (skin.getHero().contains(heroes.getName())) {
                dataList.add(skin);
            }
        }
    }
}
