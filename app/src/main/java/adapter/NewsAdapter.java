package adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import cn.nicolite.palm300heros.R;
import model.News;

/**
 * Created by NICOLITE on 2016/10/15 0015.
 */

public class NewsAdapter extends ArrayAdapter<News> {

    private int resourceId;

    public NewsAdapter(Context context, int resource, List<News> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        News news = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);
        ImageView newsImage = (ImageView) view.findViewById(R.id.news_image_listview);
        TextView newsTitle = (TextView) view.findViewById(R.id.news_title_listview);
        TextView newsCategory = (TextView) view.findViewById(R.id.news_categroy_listview);
        TextView newsDate = (TextView) view.findViewById(R.id.news_date_listview);

        newsCategory.setText(news.getCategory());
        newsTitle.setText(news.getTitle());
        newsDate.setText(news.getDate());
        //newsImage.setImageBitmap(loadImage(news.getImageUrl()));
        return view;
    }

    /*private Bitmap loadImage(String imageUrl) {
        Bitmap bitmap = null;
        try {
            URL url = new URL(imageUrl);
           bitmap = BitmapFactory.decodeStream(url.openStream());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }*/
}
