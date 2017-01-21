package fragment;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import activity.NewsWebViewActivity;
import adapter.NewsAdapter;
import cn.nicolite.palm300heroes.R;
import database.Palm300heroesDB;
import model.News;
import myInterface.HttpCallbackListener;
import utilty.HttpUtil;
import utilty.LogUtil;
import utilty.Utilty;


/**
 * Created by NICOLITE on 2016/10/12 0012.
 */

public class NewsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private List<News> dataList = new ArrayList<News>();
    private List<News> newsList;
    private News selectedNews;
    private ListView listView;
    private NewsAdapter adapter;
    private Palm300heroesDB palm300heroesDB;
    private SwipeRefreshLayout swipeRefreshLayout = null;
    private static final int REFRESH_COMPLETE_TIME = 2000;
    private static final String ADDRESS = "http://nicolite.cn/api/get_category_posts/?slug=300heroes";

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0 :
                    swipeRefreshLayout.setRefreshing(false);
                    adapter.notifyDataSetChanged();
                    listView.setSelection(0);
                    break;
                default:break;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            FragmentManager manager = getChildFragmentManager();
            manager.popBackStackImmediate(null, 1);
        }
        View view = inflater.inflate(R.layout.news_fragment, container, false);
        listView = (ListView) view.findViewById(R.id.news_listview);
        adapter = new NewsAdapter(getActivity(), R.layout.news_listview_layout, dataList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedNews = newsList.get(position);
                String content = selectedNews.getContent();
                Intent intent = new Intent(getActivity(), NewsWebViewActivity.class);
                intent.putExtra("content", content);
                getActivity().startActivity(intent);
            }
        });

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.news_swipe_refresh_layout);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.orange));
        swipeRefreshLayout.setOnRefreshListener(this);

        onRefresh();
        return view;
    }

    /*初始化资讯数据*/
    private void initNewsDate() {
        palm300heroesDB = Palm300heroesDB.getInstance(getActivity());
        HttpUtil.sendHttpRequest(ADDRESS, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                Utilty.handleNewsResponse(palm300heroesDB, response);
                queryNews();
            }
            @Override
            public void onError(Exception e) {
                LogUtil.d("initNewDate", "返回数据错误");
            }
        });

    }

    private void queryNews(){
        newsList = palm300heroesDB.loadNews();
        Utilty.sortByDate(newsList);
        if (newsList.size() > 0 && dataList.size() > 0) {
            for (News news : newsList) {
               if (news.getId() > dataList.size()) {
                    dataList.add(0, news);
                }
            }
        }else {
                 dataList.clear();
                 for (News news : newsList) {
                     LogUtil.d("queryNews", "NewsDate: " + news.getDate());
                    dataList.add(news);
                 }
        }
    }

    @Override
    public void onRefresh() {
        //swipeRefreshLayout.setRefreshing(true);
        initNewsDate();
        queryNews();
        handler.sendEmptyMessageDelayed(0, REFRESH_COMPLETE_TIME);
    }
}


