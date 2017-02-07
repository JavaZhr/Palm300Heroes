package adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import cn.nicolite.palm300heroes.R;
import model.recordLogger.LatestMatch;

/**
 * Created by NICOLITE on 2017/2/7 0007.
 */

public class LatestMatchRecyclerViewAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<LatestMatch> dataList;
    private LayoutInflater inflater;
    private OnItemClickListener onItemClickListener = null;
    public LatestMatchRecyclerViewAdapter(Context context, List<LatestMatch> dataList) {
        this.context = context;
        this.dataList = dataList;
        inflater = LayoutInflater.from(context);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView headPic;
        private TextView type;
        private TextView result;
        private TextView date;

        public ViewHolder(View itemView) {
            super(itemView);
            headPic = (ImageView) itemView.findViewById(R.id.match_list_head_pic);
            type = (TextView) itemView.findViewById(R.id.match_list_type);
            result = (TextView) itemView.findViewById(R.id.match_list_result);
            date = (TextView) itemView.findViewById(R.id.match_list_date);
        }

        public ImageView getHeadPic() {
            return headPic;
        }

        public TextView getType() {
            return type;
        }

        public TextView getResult() {
            return result;
        }

        public TextView getDate() {
            return date;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.latest_matchrecycleview_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ViewHolder viewHolder = (ViewHolder) holder;
        LatestMatch latestMatch = dataList.get(position);

        String result = "<font  color='#79FF79'>" + "失败" + "</font>";
        String matchType = "战场";

        if (latestMatch.getResult() == 1) {
            result = "<font  color='#FF2D2D'>" + "胜利" + "</font>";
        }

        if (latestMatch.getMatchType() == 1) {
            matchType = "竞技场";
        }

        Glide
                .with(context)
                .load(latestMatch.getHeroIcon())
                .thumbnail(0.f)
                .skipMemoryCache(true)
                .dontAnimate()
                .into(viewHolder.getHeadPic());

        viewHolder.getType().setText(matchType);
        viewHolder.getResult().setText(Html.fromHtml(result));
        viewHolder.getDate().setText(latestMatch.getMatchDate().split("[0-9]{4}-")[1].split("[0-9]{2}:[0-9]{2}:[0-9]{2}")[0]);

        if (onItemClickListener != null) {
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(viewHolder.itemView, position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    /*item点击实践接口*/
    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }
}
