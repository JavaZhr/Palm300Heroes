package fragment;

import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.nicolite.palm300heros.R;


/**
 * Created by NICOLITE on 2016/10/12 0012.
 */

public class NewsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    private List<String> data = new ArrayList<String>(Arrays.asList("Saber", "Archer", "Asina", "Korito"));
    private ListView listView;
    private  ArrayAdapter<String> adapter;
    private SwipeRefreshLayout mSwipeLayout;
    private static final int REFRESH_COMPLETE = 11;

    private Handler mhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case REFRESH_COMPLETE:
                    data.addAll(Arrays.asList("Luci", "Alpha", "Fili"));
                    adapter.notifyDataSetChanged();
                    mSwipeLayout.setRefreshing(false);
                    break;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news_fragment, container, false);
        listView = (ListView) view.findViewById(R.id.news_listview);
        mSwipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        mSwipeLayout.setOnRefreshListener(this);
        mSwipeLayout.setColorSchemeColors(getResources().getColor(android.R.color.holo_blue_bright) , getResources().getColor(android.R.color.holo_red_light) , getResources().getColor(android.R.color.holo_orange_light) , getResources().getColor(android.R.color.holo_red_light) );
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, data);
        listView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onRefresh() {
        mhandler.sendEmptyMessageDelayed(REFRESH_COMPLETE, 1000);
    }
}
