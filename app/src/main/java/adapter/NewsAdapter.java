package adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import cn.nicolite.palm300heros.R;
import model.News;
import utilty.LogUtil;


/**
 * Created by NICOLITE on 2016/10/15 0015.
 */

public class NewsAdapter extends ArrayAdapter<News> {

    private int resourceId;
    private Context context;
    public NewsAdapter(Context context, int resource, List<News> objects) {
        super(context, resource, objects);
        resourceId = resource;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        News news = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);
        TextView newsTitle = (TextView) view.findViewById(R.id.news_title_listview);
        TextView newsCategory = (TextView) view.findViewById(R.id.news_author_listview);
        TextView newsDate = (TextView) view.findViewById(R.id.news_date_listview);
        ImageView newsImage = (ImageView) view.findViewById(R.id.news_image_listview);
        newsCategory.setText(news.getNickName());
        newsTitle.setText(news.getTitle());
        newsDate.setText(news.getDate());
        LogUtil.d("NewsAdapter", "imageUrl: " + news.getImageUrl());
        Glide
                .with(getContext())
                .load(news.getImageUrl())
                .dontAnimate()
                .thumbnail(0.1f)
                .centerCrop()
                .into(newsImage);

        return view;
    }

}
