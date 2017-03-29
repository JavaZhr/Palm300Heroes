package adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.nicolite.palm300heroes.R;
import model.recordLogger.MatchDetail;
import util.LogUtil;

/**
 * Created by NICOLITE on 2017/3/20 0020.
 */

public class MatchDetailRecyclerViewAdapter extends RecyclerView.Adapter<MatchDetailRecyclerViewAdapter.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private OnItemClickListener onItemClickListener = null;
    private List<MatchDetail.Match.WinSide> winSideList;
    private List<MatchDetail.Match.LoseSide> loseSideList;
    private int flags = 0;

    public MatchDetailRecyclerViewAdapter(Context context, List<MatchDetail.Match.WinSide> winSideList) {
        this.context = context;
        this.winSideList = winSideList;
        inflater = LayoutInflater.from(context);
    }

    public MatchDetailRecyclerViewAdapter(Context context, List<MatchDetail.Match.LoseSide> loseSideList, int flags) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.loseSideList = loseSideList;
        this.flags = flags;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.match_detail_recyclerview_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        initView(holder, position);
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
        if (flags != 0){
            return loseSideList.size();
        }
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

    private void initView(ViewHolder holder, int position){
        String roleIcon;
        String roleName;
        String performance;
        String equip1 = null;
        String equip2 = null;
        String equip3 = null;
        String equip4 = null;
        String equip5 = null;
        String equip6 = null;
        String result;
        int equipSize = 0;
        if (flags != 0){
            roleIcon = "http://300report.jumpw.com/static/images/" + loseSideList.get(position).hero.IconFile;
            roleName = loseSideList.get(position).RoleName;
            performance = loseSideList.get(position).KillCount + "/" + loseSideList.get(position).DeathCount + "/" + loseSideList.get(position).AssistCount;
            equipSize = loseSideList.get(position).equipList.size();
            if (equipSize == 1){
                equip1 = "http://300report.jumpw.com/static/images/" + loseSideList.get(position).equipList.get(0).IconFile;
            }else if (equipSize == 2){
                equip1 = "http://300report.jumpw.com/static/images/" + loseSideList.get(position).equipList.get(0).IconFile;
                equip2 = "http://300report.jumpw.com/static/images/" + loseSideList.get(position).equipList.get(1).IconFile;
            }else if (equipSize == 3){
                equip1 = "http://300report.jumpw.com/static/images/" + loseSideList.get(position).equipList.get(0).IconFile;
                equip2 = "http://300report.jumpw.com/static/images/" + loseSideList.get(position).equipList.get(1).IconFile;
                equip3 = "http://300report.jumpw.com/static/images/" + loseSideList.get(position).equipList.get(2).IconFile;
            }else if (equipSize == 4){
                equip1 = "http://300report.jumpw.com/static/images/" + loseSideList.get(position).equipList.get(0).IconFile;
                equip2 = "http://300report.jumpw.com/static/images/" + loseSideList.get(position).equipList.get(1).IconFile;
                equip3 = "http://300report.jumpw.com/static/images/" + loseSideList.get(position).equipList.get(2).IconFile;
                equip4 = "http://300report.jumpw.com/static/images/" + loseSideList.get(position).equipList.get(3).IconFile;
            }else if (equipSize ==5){
                equip1 = "http://300report.jumpw.com/static/images/" + loseSideList.get(position).equipList.get(0).IconFile;
                equip2 = "http://300report.jumpw.com/static/images/" + loseSideList.get(position).equipList.get(1).IconFile;
                equip3 = "http://300report.jumpw.com/static/images/" + loseSideList.get(position).equipList.get(2).IconFile;
                equip4 = "http://300report.jumpw.com/static/images/" + loseSideList.get(position).equipList.get(3).IconFile;
                equip5 = "http://300report.jumpw.com/static/images/" + loseSideList.get(position).equipList.get(4).IconFile;
            }else if (equipSize == 6){
                equip1 = "http://300report.jumpw.com/static/images/" + loseSideList.get(position).equipList.get(0).IconFile;
                equip2 = "http://300report.jumpw.com/static/images/" + loseSideList.get(position).equipList.get(1).IconFile;
                equip3 = "http://300report.jumpw.com/static/images/" + loseSideList.get(position).equipList.get(2).IconFile;
                equip4 = "http://300report.jumpw.com/static/images/" + loseSideList.get(position).equipList.get(3).IconFile;
                equip5 = "http://300report.jumpw.com/static/images/" + loseSideList.get(position).equipList.get(4).IconFile;
                equip6 = "http://300report.jumpw.com/static/images/" + loseSideList.get(position).equipList.get(5).IconFile;
            }
            if (loseSideList.get(position).Result == 1){
                result = "胜";
            }else {
                result = "负";
            }
        }else {
            roleIcon = "http://300report.jumpw.com/static/images/" + winSideList.get(position).hero.IconFile;
            roleName = winSideList.get(position).RoleName;
            performance = winSideList.get(position).KillCount + "/" + winSideList.get(position).DeathCount + "/" + winSideList.get(position).AssistCount;
            equipSize = winSideList.get(position).equipList.size();
            if (equipSize == 1){
                equip1 = "http://300report.jumpw.com/static/images/" + winSideList.get(position).equipList.get(0).IconFile;
            }else if (equipSize == 2){
                equip1 = "http://300report.jumpw.com/static/images/" + winSideList.get(position).equipList.get(0).IconFile;
                equip2 = "http://300report.jumpw.com/static/images/" + winSideList.get(position).equipList.get(1).IconFile;
            }else if (equipSize == 3){
                equip1 = "http://300report.jumpw.com/static/images/" + winSideList.get(position).equipList.get(0).IconFile;
                equip2 = "http://300report.jumpw.com/static/images/" + winSideList.get(position).equipList.get(1).IconFile;
                equip3 = "http://300report.jumpw.com/static/images/" + winSideList.get(position).equipList.get(2).IconFile;
            }else if (equipSize == 4){
                equip1 = "http://300report.jumpw.com/static/images/" + winSideList.get(position).equipList.get(0).IconFile;
                equip2 = "http://300report.jumpw.com/static/images/" + winSideList.get(position).equipList.get(1).IconFile;
                equip3 = "http://300report.jumpw.com/static/images/" + winSideList.get(position).equipList.get(2).IconFile;
                equip4 = "http://300report.jumpw.com/static/images/" + winSideList.get(position).equipList.get(3).IconFile;
            }else if (equipSize == 5){
                equip1 = "http://300report.jumpw.com/static/images/" + winSideList.get(position).equipList.get(0).IconFile;
                equip2 = "http://300report.jumpw.com/static/images/" + winSideList.get(position).equipList.get(1).IconFile;
                equip3 = "http://300report.jumpw.com/static/images/" + winSideList.get(position).equipList.get(2).IconFile;
                equip4 = "http://300report.jumpw.com/static/images/" + winSideList.get(position).equipList.get(3).IconFile;
                equip5 = "http://300report.jumpw.com/static/images/" + winSideList.get(position).equipList.get(4).IconFile;
            }else if (equipSize == 6){
                equip1 = "http://300report.jumpw.com/static/images/" + winSideList.get(position).equipList.get(0).IconFile;
                equip2 = "http://300report.jumpw.com/static/images/" + winSideList.get(position).equipList.get(1).IconFile;
                equip3 = "http://300report.jumpw.com/static/images/" + winSideList.get(position).equipList.get(2).IconFile;
                equip4 = "http://300report.jumpw.com/static/images/" + winSideList.get(position).equipList.get(3).IconFile;
                equip5 = "http://300report.jumpw.com/static/images/" + winSideList.get(position).equipList.get(4).IconFile;
                equip6 = "http://300report.jumpw.com/static/images/" + winSideList.get(position).equipList.get(5).IconFile;
            }
            if (winSideList.get(position).Result == 1){
                result = "胜";
            }else {
                result = "负";
            }
        }

        Glide
                .with(context)
                .load(roleIcon)
                .dontAnimate()
                .into(holder.getRoleIcon());
        holder.getRoleName().setText(roleName);
        holder.getPerformance().setText(performance);
        Glide
                .with(context)
                .load(equip1)
                .dontAnimate()
                .into(holder.getEquip1());
        Glide
                .with(context)
                .load(equip2)
                .dontAnimate()
                .into(holder.getEquip2());
        Glide
                .with(context)
                .load(equip3)
                .dontAnimate()
                .into(holder.getEquip3());
        Glide
                .with(context)
                .load(equip4)
                .dontAnimate()
                .into(holder.getEquip4());
        Glide
                .with(context)
                .load(equip5)
                .dontAnimate()
                .into(holder.getEquip5());
        Glide
                .with(context)
                .load(equip6)
                .dontAnimate()
                .into(holder.getEquip6());

        holder.getResult().setText(result);
    }
}
