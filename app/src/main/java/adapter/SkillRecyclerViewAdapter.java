package adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import cn.nicolite.palm300heroes.R;
import model.Skill;
import utilty.LogUtil;

/**
 * Created by NICOLITE on 2016/11/16 0016.
 */

public class SkillRecyclerViewAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<Skill> dataList;
    private LayoutInflater inflater;
    private OnItemClickListener onItemClickListener = null;

    public SkillRecyclerViewAdapter(Context context, List<Skill> dataList) {
        this.context = context;
        this.dataList = dataList;
        inflater = LayoutInflater.from(context);
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView skillPicture;
        private TextView skillName;
        private TextView skillConsumption;
        private TextView skillChilldown;
        private TextView skillShortCut;
        private TextView skillEffectiveness;

        public ViewHolder(View itemView) {
            super(itemView);
            skillPicture = (ImageView) itemView.findViewById(R.id.heroes_skill_pic);
            skillName = (TextView) itemView.findViewById(R.id.heroes_skill_name);
            skillConsumption = (TextView) itemView.findViewById(R.id.heroes_skill_consumption);
            skillChilldown = (TextView) itemView.findViewById(R.id.heroes_skill_chilldown);
            skillShortCut = (TextView) itemView.findViewById(R.id.heroes_skill_shortcut);
            skillEffectiveness = (TextView) itemView.findViewById(R.id.heroes_skill_effectiveness);
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
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.skill_recyclerview_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ViewHolder viewHolder = (ViewHolder) holder;
        Skill skill = dataList.get(position);

       /* LogUtil.d("onBindViewHolder", "HeroesDName : " + skill.getName());
        LogUtil.d("onBindViewHolder", "SkillPictureUrl : " + dataList.get(position).getPictureUrl());*/

        Glide
                .with(context)
                .load(skill.getPictureUrl())
                .thumbnail(0.1f)
                .skipMemoryCache(true)
                .dontAnimate()
                .into(viewHolder.getSkillPicture());

        viewHolder.getSkillName().setText(skill.getName());
        viewHolder.getSkillConsumption().setText(skill.getConsumption());
        viewHolder.getSkillChilldown().setText(skill.getChilldown());
        viewHolder.getSkillShortCut().setText(skill.getShortcut());
        viewHolder.getSkillEffectiveness().setText(skill.getEffectiveness());

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
        void onItemClick(View view,int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }
}
