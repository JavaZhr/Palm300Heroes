package fragment;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import activity.WebViewActivity;
import adapter.NewsAdapter;
import cn.nicolite.palm300heros.R;
import database.Palm300herosDB;
import model.News;
import myInterface.HttpCallbackListener;
import utilty.HttpUtil;
import utilty.LogUtil;
import utilty.Utilty;

/**
 * Created by NICOLITE on 2016/10/12 0012.
 */

public class NewsFragment extends Fragment{
    private List<News> dataList = new ArrayList<News>();
    private List<News> newsList;
    private News selectedNews;
    private ListView listView;
    private NewsAdapter adapter;
    private News news;
    private Palm300herosDB palm300herosDB;
    private SwipeRefreshLayout swipeRefreshLayout = null;
    private static final int REFRESH_COMPLETE_TIME = 2000;
    private static final String ADDRESS = "http://nicolite.cn/api/get_category_posts/?slug=300heros";

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0 :
                    swipeRefreshLayout.setRefreshing(false);
                    adapter.notifyDataSetChanged();
                    break;
                default:break;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news_fragment, container, false);
        listView = (ListView) view.findViewById(R.id.news_listview);
        adapter = new NewsAdapter(getActivity(), R.layout.listview_layout, dataList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedNews = newsList.get(position);
                String content = selectedNews.getContent();
                String imageUrl = selectedNews.getImageUrl();
                Intent intent = new Intent(getActivity(), WebViewActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("content", content);
                bundle.putString("imageUrl", imageUrl);
                intent.putExtras(bundle);
                getActivity().startActivity(intent);
            }
        });

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.orange));
        swipeRefreshLayout.setProgressViewEndTarget(true, 120);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        initNewsDate();
                        //dataList.clear();
                        queryNews();
                        handler.sendEmptyMessageDelayed(0, REFRESH_COMPLETE_TIME);
                    }
                }).start();
            }
        });
        return view;
    }

    /*初始化资讯数据*/
    private void initNewsDate() {
        palm300herosDB = Palm300herosDB.getInstance(getActivity());
        HttpUtil.sendHttpRequest(ADDRESS, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                Utilty.handleNewsResponse(palm300herosDB, response);
                queryNews();
            }

            @Override
            public void onError(Exception e) {
                LogUtil.d("initNewDate", "返回数据错误");
            }
        });

    }

    private void queryNews(){
        newsList = palm300herosDB.loadNews();
        if (newsList.size() > 0) {
            dataList.clear();
            for (News news : newsList) {
                    dataList.add(news);

            }
            //adapter.notifyDataSetChanged();
        }else {

        }
    }

}


