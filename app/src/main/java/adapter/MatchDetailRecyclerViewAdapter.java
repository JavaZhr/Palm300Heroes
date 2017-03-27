package adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.nicolite.palm300heroes.R;
import model.recordLogger.MatchDetail;

/**
 * Created by NICOLITE on 2017/3/20 0020.
 */

public class MatchDetailRecyclerViewAdapter extends RecyclerView.Adapter<MatchDetailRecyclerViewAdapter.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private OnItemClickListener onItemClickListener = null;
    private List<MatchDetail.Match.WinSide> winSideList = new ArrayList<>();
    private List<MatchDetail.Match.LoseSide> loseSideList = new ArrayList<>();
    public MatchDetailRecyclerViewAdapter(Context context, MatchDetail matchDetail) {
        this.context = context;
        this.winSideList = matchDetail.match.winSideList;
        this.loseSideList = matchDetail.match.loseSideList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.match_detail_recyclerview_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        if (onItemClickListener != null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(holder.itemView, holder.getAdapterPosition());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return winSideList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.role_icon_match_detail)
        ImageView roleIcon;
        @BindView(R.id.role_name_match_detail)
        TextView roleName;
        @BindView(R.id.performance_match_detail)
        TextView performance;
        @BindView(R.id.equip1_match_detail)
        ImageView equip1;
        @BindView(R.id.equip2_match_detail)
        ImageView equip2;
        @BindView(R.id.equip3_match_detail)
        ImageView equip3;
        @BindView(R.id.equip4_match_detail)
        ImageView equip4;
        @BindView(R.id.equip5_match_detail)
        ImageView equip5;
        @BindView(R.id.equip6_match_detail)
        ImageView equip6;
        @BindView(R.id.result_match_detail)
        TextView result;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public ImageView getRoleIcon() {
            return roleIcon;
        }

        public TextView getRoleName() {
            return roleName;
        }

        public TextView getPerformance() {
            return performance;
        }

        public ImageView getEquip1() {
            return equip1;
        }

        public ImageView getEquip2() {
            return equip2;
        }

        public ImageView getEquip3() {
            return equip3;
        }

        public ImageView getEquip4() {
            return equip4;
        }

        public ImageView getEquip5() {
            return equip5;
        }

        public ImageView getEquip6() {
            return equip6;
        }

        public TextView getResult() {
            return result;
        }
    }

    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;

    }
}
