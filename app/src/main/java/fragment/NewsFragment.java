package fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import activity.WebViewActivity;
import adapter.NewsAdapter;
import cn.nicolite.palm300heros.R;
import model.News;
import util.HttpCallbackListener;
import util.HttpUtil;
import util.LogUtil;
import util.Util;

/**
 * Created by NICOLITE on 2016/10/12 0012.
 */

public class NewsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    private List<News> dataList = new ArrayList<News>();
    private ListView listView;
    private NewsAdapter adapter;
    private SwipeRefreshLayout swipeLayout;
    private static final int REFRESH_COMPLETE = 11;
    private static final String ADDRESS = "http://nicolite.cn/api/get_category_posts/?slug=300heros";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news_fragment, container, false);
        swipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setColorSchemeColors(getResources().getColor(android.R.color.holo_blue_bright) , getResources().getColor(android.R.color.holo_red_light) , getResources().getColor(android.R.color.holo_orange_light) , getResources().getColor(android.R.color.holo_red_light) );
        onRefresh();
        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        listView = (ListView) view.findViewById(R.id.news_listview);
        adapter = new NewsAdapter(getActivity(), R.layout.listview_layout, dataList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String url = sharedPreferences.getString("url", "");
                Intent intent = new Intent(getActivity(), WebViewActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("url", url);
                intent.putExtras(bundle);
                getActivity().startActivity(intent);
                Toast.makeText(getActivity(), "你点击了" + sharedPreferences.getString("title", "") , Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    @Override
    public void onRefresh() {
        /*联网加载数据*/
        initNewsDate();
        Toast.makeText(getActivity(), "正在加载", Toast.LENGTH_SHORT).show();

       new Handler().postDelayed(new Runnable() {
           @Override
           public void run() {
               {
                   adapter.notifyDataSetChanged();
                   swipeLayout.setRefreshing(false);
               }
           }
       }, REFRESH_COMPLETE);
    }


    /*初始化资讯数据*/
    private void initNewsDate() {
                /*联网加载数据*/
        HttpUtil.sendHttpRequest(ADDRESS, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                Util.handleNewsResponse(getActivity(), response);
            }

            @Override
            public void onError(Exception e) {
                Toast.makeText(getActivity(), "加载失败", Toast.LENGTH_SHORT).show();
            }
        });

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        int sencond = c.get(Calendar.SECOND);
        String category = sharedPreferences.getString("category", "");
        String url = sharedPreferences.getString("url", "");
        String title = sharedPreferences.getString("title", "");
        String content = sharedPreferences.getString("content", "");
        String date = sharedPreferences.getString("date", "");
        String imageURL = "http://ol01.tgbusdata.cn/v2/thumb/jpg/MjJkNCwwLDAsNCwzLDEsLTEsMCxyazUw/u/olpic.tgbusdata.cn/uploads/allimg/160420/229-160420094538.jpg";
        News latestDate = new News(category, url, title, content, date, imageURL);
        dataList.add(0,latestDate);
    }


}


