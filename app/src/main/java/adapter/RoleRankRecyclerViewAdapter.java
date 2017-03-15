package adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.nicolite.palm300heroes.R;
import model.recordLogger.Role;

/**
 * Created by NICOLITE on 2017/2/6 0006.
 */

public class RoleRankRecyclerViewAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<Role.Rank> dataList;
    private LayoutInflater inflater;
    private OnItemClickListener onItemClickListener = null;

    public RoleRankRecyclerViewAdapter(Context context, List<Role.Rank> dataList) {
        this.context = context;
        this.dataList = dataList;
        inflater = LayoutInflater.from(context);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.role_rank_info) TextView rankInfo;
        @BindView(R.id.role_rank_name) TextView rankName;
        @BindView(R.id.role_value_name) TextView valueName;
        @BindView(R.id.role_rank_value) TextView value;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public TextView getRankInfo() {
            return rankInfo;
        }

        public TextView getRankName() {
            return rankName;
        }

        public TextView getValueName() {
            return valueName;
        }

        public TextView getValue() {
            return value;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.role_rank_recyclerview_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final ViewHolder viewHolder = (ViewHolder) holder;
        String info;
        Role.Rank roleRank = dataList.get(holder.getAdapterPosition());

        if (roleRank.RankChange >= 0) {
            info = "第" + roleRank.Rank + "名 "+ "<font  color='#FF2D2D'>(↑"+ roleRank.RankChange +" )</font>";
        }else {
            info = "第" + roleRank.Rank + "名 "+ "<font  color='#79FF79'>(↓"+ (-1) * roleRank.RankChange +" )</font>";
        }

        viewHolder.getRankName().setText(roleRank.RankName);
        viewHolder.getRankInfo().setText(Html.fromHtml(info));
        viewHolder.getValueName().setText(roleRank.ValueName);
        viewHolder.getValue().setText(roleRank.Value);
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
