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

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.nicolite.palm300heroes.R;
import model.FightSkill;

/**
 * Created by NICOLITE on 2017/2/14 0014.
 */

public class FightSkillRecyclerViewAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<FightSkill> dataList;
    private LayoutInflater inflater;
    private OnItemClicikListener onItemClicikListener;
    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.fight_skill_pic)
        ImageView fightSkillPic;
        @BindView(R.id.fight_skill_text)
        TextView fightSkillText;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public ImageView getFightSkillPic() {
            return fightSkillPic;
        }

        public TextView getFightSkillText() {
            return fightSkillText;
        }
    }

    public FightSkillRecyclerViewAdapter(Context context, List<FightSkill> dataList) {
        this.context = context;
        this.dataList = dataList;
        this.inflater = inflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.fight_skill_recycler_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ViewHolder viewHolder = (ViewHolder) holder;
        FightSkill fightSkill = dataList.get(position);

        Glide
                .with(context)
                .load(fightSkill.getPicture())
                .thumbnail(0.1f)
                .dontAnimate()
                .into(viewHolder.getFightSkillPic());

        viewHolder.getFightSkillText().setText(fightSkill.getName());
        if (onItemClicikListener != null) {
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClicikListener.OnItemClick(viewHolder.itemView, position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public interface OnItemClicikListener{
        void OnItemClick(View view, int postion);
    }

    public void setItemOnClickListener(OnItemClicikListener onItemClicikListener){
        this.onItemClicikListener = onItemClicikListener;
    }
}
