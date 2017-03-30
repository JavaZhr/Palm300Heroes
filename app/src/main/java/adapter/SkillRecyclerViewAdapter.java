package adapter;

import android.content.Context;
import android.net.Uri;
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
import database.SkillD;

/**
 * Created by NICOLITE on 2016/11/16 0016.
 */

public class SkillRecyclerViewAdapter extends RecyclerView.Adapter<SkillRecyclerViewAdapter.ViewHolder> {
    private Context context;
    private List<SkillD> dataList;
    private LayoutInflater inflater;
    private OnItemClickListener onItemClickListener = null;

    public SkillRecyclerViewAdapter(Context context, List<SkillD> dataList) {
        this.context = context;
        this.dataList = dataList;
        inflater = LayoutInflater.from(context);
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.heroes_skill_pic) ImageView skillPicture;
        @BindView(R.id.heroes_skill_name) TextView skillName;
        @BindView(R.id.heroes_skill_consumption) TextView skillConsumption;
        @BindView(R.id.heroes_skill_chilldown) TextView skillChilldown;
        @BindView(R.id.heroes_skill_shortcut) TextView skillShortCut;
        @BindView(R.id.heroes_skill_effectiveness) TextView skillEffectiveness;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public ImageView getSkillPicture() {
            return skillPicture;
        }

        public TextView getSkillName() {
            return skillName;
        }

        public TextView getSkillConsumption() {
            return skillConsumption;
        }

        public TextView getSkillChilldown() {
            return skillChilldown;
        }

        public TextView getSkillShortCut() {
            return skillShortCut;
        }

        public TextView getSkillEffectiveness() {
            return skillEffectiveness;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.skill_recyclerview_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ViewHolder viewHolder = holder;
        SkillD skill = dataList.get(holder.getAdapterPosition());

       /* LogUtil.d("onBindViewHolder", "HeroesDName : " + skill.getName());
        LogUtil.d("onBindViewHolder", "SkillPictureUrl : " + dataList.get(position).getPictureUrl());*/

        Glide
                .with(context)
                .load("http://ogbna06ji.bkt.clouddn.com/heroes/skill/" + Uri.encode(skill.getPictureUrl()))
                .dontAnimate()
                .into(viewHolder.getSkillPicture());

        viewHolder.getSkillName().setText(skill.getName());
        viewHolder.getSkillConsumption().setText(skill.getConsumption());
        viewHolder.getSkillChilldown().setText(skill.getChilldown());
        viewHolder.getSkillShortCut().setText("【 " + skill.getShortcut() + " 】");
        viewHolder.getSkillEffectiveness().setText(Html.fromHtml(skill.getEffectiveness()));

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
        void onItemClick(View view,int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }
}
