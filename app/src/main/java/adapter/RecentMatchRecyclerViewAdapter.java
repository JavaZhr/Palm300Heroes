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

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.nicolite.palm300heroes.R;
import model.recordLogger.RecentMatchList;

/**
 * Created by NICOLITE on 2017/2/7 0007.
 */

public class RecentMatchRecyclerViewAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<RecentMatchList.MatchList> dataList;
    private LayoutInflater inflater;
    private OnItemClickListener onItemClickListener = null;
    public RecentMatchRecyclerViewAdapter(Context context, List<RecentMatchList.MatchList> dataList) {
        this.context = context;
        this.dataList = dataList;
        inflater = LayoutInflater.from(context);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.match_list_head_pic) ImageView headPic;
        @BindView(R.id.match_list_type) TextView type;
        @BindView(R.id.match_list_result) TextView result;
        @BindView(R.id.match_list_date) TextView date;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
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
        View view = inflater.inflate(R.layout.recent_matchrecycleview_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final ViewHolder viewHolder = (ViewHolder) holder;
        RecentMatchList.MatchList matchList = dataList.get(holder.getAdapterPosition());

        String result = "<font  color='#79FF79'>" + "失败" + "</font>";
        String matchType = "战场";

        if (matchList.Result == 1) {
            result = "<font  color='#FF2D2D'>" + "胜利" + "</font>";
        }

        if (matchList.MatchType == 1) {
            matchType = "竞技场";
        }

        Glide
                .with(context)
                .load("http://300report.jumpw.com/static/images/" + matchList.hero.IconFile)
                .dontAnimate()
                .into(viewHolder.getHeadPic());

        viewHolder.getType().setText(matchType);
        viewHolder.getResult().setText(Html.fromHtml(result));
        viewHolder.getDate().setText(matchList.MatchDate.split("[0-9]{4}-")[1].split("[0-9]{2}:[0-9]{2}:[0-9]{2}")[0]);

        if (onItemClickListener != null) {
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(viewHolder.itemView, viewHolder.getAdapterPosition());
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
