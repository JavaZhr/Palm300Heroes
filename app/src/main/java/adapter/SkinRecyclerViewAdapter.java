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
import database.SkinD;

/**
 * Created by NICOLITE on 2016/11/25 0025.
 */

public class SkinRecyclerViewAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<SkinD> dataList;
    private LayoutInflater inflater;
    private OnItemClickListener onItemClickListener;

    public SkinRecyclerViewAdapter(Context context, List<SkinD> dataList) {
        this.context = context;
        this.dataList = dataList;
        inflater = LayoutInflater.from(context);
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.heroes_skin_pic) ImageView skinPicture;
        @BindView(R.id.heroes_skin_name) TextView skinName;
        @BindView(R.id.heroes_skin_price) TextView skinPrice;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public ImageView getSkinPicture() {
            return skinPicture;
        }

        public TextView getSkinName() {
            return skinName;
        }

        public TextView getSkinPrice() {
            return skinPrice;
        }
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.skin_recyclerview_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final ViewHolder viewHolder = (ViewHolder) holder;
        SkinD skin = dataList.get(holder.getAdapterPosition());

        Glide
                .with(context)
                .load("http://ogbna06ji.bkt.clouddn.com/heroes/skin/" + Uri.encode(skin.getUrl()))
                .dontAnimate()
                .into(viewHolder.getSkinPicture());

        viewHolder.getSkinName().setText(skin.getName());
        viewHolder.getSkinPrice().setText(skin.getPrice());

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
