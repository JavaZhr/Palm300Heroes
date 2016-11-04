package adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

import cn.nicolite.palm300heros.R;
import model.Heros;
import utilty.LogUtil;

/**
 * Created by NICOLITE on 2016/10/30 0030.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<Heros> dataList;
    private LayoutInflater inflater;
    private OnItemClickListener onItemClickListener = null;

    public RecyclerViewAdapter(Context context,List<Heros> dataList) {
        this.context = context;
        this.dataList = dataList;
        inflater = LayoutInflater.from(context);
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView herosAvatar;
        private TextView herosName;
        private TextView herosType;
        private TextView herosCoinsPrice;
        private TextView herosDiamondPrice;

        public ViewHolder(View itemView) {
            super(itemView);
            herosAvatar = (ImageView) itemView.findViewById(R.id.heros_avatar);
            herosName = (TextView) itemView.findViewById(R.id.heros_name);
            herosType = (TextView) itemView.findViewById(R.id.heros_type);
            herosCoinsPrice = (TextView) itemView.findViewById(R.id.heros_coins_price);
            herosDiamondPrice = (TextView) itemView.findViewById(R.id.heros_diamond_price);
        }

        public ImageView getHerosAvatar() {
            return herosAvatar;
        }

        public TextView getHerosName() {
            return herosName;
        }

        public TextView getHerosType() {
            return herosType;
        }

        public TextView getHerosCoinsPrice() {
            return herosCoinsPrice;
        }

        public TextView getHerosDiamondPrice() {
            return herosDiamondPrice;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.heros_recyclerview_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        final ViewHolder viewHolder = (ViewHolder) holder;
        Heros heros = dataList.get(position);
        LogUtil.d("onBindViewHolder", "HerosDName : " + heros.getName());
        LogUtil.d("onBindViewHolder", "avatarUrl : " + dataList.get(position).getAvatarUrl());
        Glide
                .with(context)
                .load(heros.getAvatarUrl())
                .thumbnail(0.1f)
                .skipMemoryCache(true)
                .dontAnimate()
                .into(viewHolder.getHerosAvatar());

        viewHolder.getHerosName().setText(heros.getName());
        viewHolder.getHerosType().setText("定位：" + heros.getType());
        viewHolder.getHerosCoinsPrice().setText("金币：" + heros.getCoinsPrice());
        viewHolder.getHerosDiamondPrice().setText("钻石：" + heros.getDiamondPrice());

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
