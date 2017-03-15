package adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.nicolite.palm300heroes.R;
import database.SoundD;

/**
 * Created by NICOLITE on 2016/11/25 0025.
 */

public class SoundRecyclerViewAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<SoundD> dataList;
    private LayoutInflater inflater;
    private OnItemClickListener onItemClickListener;

    public SoundRecyclerViewAdapter(Context context, List<SoundD> dataList) {
        this.context = context;
        this.dataList = dataList;
        this.inflater = LayoutInflater.from(context);
    }

   public class ViewHolder extends RecyclerView.ViewHolder{
       @BindView(R.id.heroes_sound_content) TextView soundContent;
       @BindView(R.id.play_sound) ImageView playSound;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public TextView getSoundContent() {
            return soundContent;
        }

        public ImageView getPlaySound() {
            return playSound;
        }
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.sound_recyclerview_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final ViewHolder viewHolder = (ViewHolder) holder;
        SoundD sound = dataList.get(holder.getAdapterPosition());
        viewHolder.getSoundContent().setText(sound.getContent());
        if (onItemClickListener != null) {
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(viewHolder.itemView, viewHolder, viewHolder.getAdapterPosition());
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
        void onItemClick(View view, ViewHolder viewHolder, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

}
