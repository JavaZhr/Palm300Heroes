package adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cn.nicolite.palm300heroes.R;
import model.recordLogger.RoleRank;

/**
 * Created by NICOLITE on 2017/2/6 0006.
 */

public class RoleRankRecyclerViewAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<RoleRank> dataList;
    private LayoutInflater inflater;
    private OnItemClickListener onItemClickListener = null;

    public RoleRankRecyclerViewAdapter(Context context, List<RoleRank> dataList) {
        this.context = context;
        this.dataList = dataList;
        inflater = LayoutInflater.from(context);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView rankInfo;
        private TextView rankName;
        private TextView valueName;
        private TextView value;
        public ViewHolder(View itemView) {
            super(itemView);
            rankInfo = (TextView) itemView.findViewById(R.id.role_rank_info);
            rankName = (TextView) itemView.findViewById(R.id.role_rank_name);
            valueName = (TextView) itemView.findViewById(R.id.role_value_name);
            value = (TextView) itemView.findViewById(R.id.role_rank_value);
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
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ViewHolder viewHolder = (ViewHolder) holder;
        String info;
        RoleRank roleRank = dataList.get(position);

        if (roleRank.getRankChange() >= 0) {
            info = "第" + roleRank.getRank() + "名 "+ "<font  color='#FF2D2D'>(↑"+ roleRank.getRankChange() +" )</font>";
        }else {
            info = "第" + roleRank.getRank() + "名 "+ "<font  color='#79FF79'>(↓"+ (-1) * roleRank.getRankChange() +" )</font>";
        }

        viewHolder.getRankName().setText(roleRank.getRankName());
        viewHolder.getRankInfo().setText(Html.fromHtml(info));
        viewHolder.getValueName().setText(roleRank.getValueName());
        viewHolder.getValue().setText(roleRank.getValue());
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
