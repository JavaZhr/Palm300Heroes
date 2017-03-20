package adapter;

import android.content.Context;
import android.net.Uri;
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
import database.FightSkillD;

/**
 * Created by NICOLITE on 2017/2/14 0014.
 */

public class FightSkillRecyclerViewAdapter extends RecyclerView.Adapter<FightSkillRecyclerViewAdapter.ViewHolder> {
    private Context context;
    private List<FightSkillD> dataList;
    private LayoutInflater inflater;
    private OnItemClickListener onItemClickListener;
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

    public FightSkillRecyclerViewAdapter(Context context, List<FightSkillD> dataList) {
        this.context = context;
        this.dataList = dataList;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.fight_skill_recycler_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ViewHolder viewHolder = holder;
        FightSkillD fightSkillD = dataList.get(holder.getAdapterPosition());

        Glide
                .with(context)
                .load("http://ogbna06ji.bkt.clouddn.com/heroes/fightSkill/" + Uri.encode(fightSkillD.getPicture()))
                .dontAnimate()
                .into(viewHolder.getFightSkillPic());

        viewHolder.getFightSkillText().setText(fightSkillD.getName());
        if (onItemClickListener != null) {
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.OnItemClick(viewHolder.itemView, viewHolder.getAdapterPosition());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public interface OnItemClickListener {
        void OnItemClick(View view, int postion);
    }

    public void setItemOnClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }
}
