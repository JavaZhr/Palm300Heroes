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
import model.Heroes;
import utilty.LogUtil;

/**
 * Created by NICOLITE on 2016/10/30 0030.
 */

public class HeroesRecyclerViewAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<Heroes> dataList;
    private LayoutInflater inflater;
    private OnItemClickListener onItemClickListener = null;

    public HeroesRecyclerViewAdapter(Context context, List<Heroes> dataList) {
        this.context = context;
        this.dataList = dataList;
        inflater = LayoutInflater.from(context);
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView heroesAvatar;
        private TextView heroesName;
        private TextView heroesType;
        private TextView heroesCoinsPrice;
        private TextView heroesDiamondPrice;

        public ViewHolder(View itemView) {
            super(itemView);
            heroesAvatar = (ImageView) itemView.findViewById(R.id.heroes_avatar);
            heroesName = (TextView) itemView.findViewById(R.id.heroes_name);
            heroesType = (TextView) itemView.findViewById(R.id.heroes_type);
            heroesCoinsPrice = (TextView) itemView.findViewById(R.id.heroes_coins_price);
            heroesDiamondPrice = (TextView) itemView.findViewById(R.id.heroes_diamond_price);
        }

        public ImageView getHeroesAvatar() {
            return heroesAvatar;
        }

        public TextView getHeroesName() {
            return heroesName;
        }

        public TextView getHeroesType() {
            return heroesType;
        }

        public TextView getHeroesCoinsPrice() {
            return heroesCoinsPrice;
        }

        public TextView getHeroesDiamondPrice() {
            return heroesDiamondPrice;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.heroes_recyclerview_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        final ViewHolder viewHolder = (ViewHolder) holder;
        Heroes heroes = dataList.get(position);
       /* LogUtil.d("onBindViewHolder", "HeroesDName : " + heroes.getName());
        LogUtil.d("onBindViewHolder", "avatarUrl : " + dataList.get(position).getAvatarUrl());*/
        Glide
                .with(context)
                .load(heroes.getAvatarUrl())
                .thumbnail(0.1f)
                .skipMemoryCache(true)
                .dontAnimate()
                .into(viewHolder.getHeroesAvatar());

        viewHolder.getHeroesName().setText(heroes.getName());
        viewHolder.getHeroesType().setText("定位：" + heroes.getType());
        viewHolder.getHeroesCoinsPrice().setText("金币：" + heroes.getCoinsPrice());
        viewHolder.getHeroesDiamondPrice().setText("钻石：" + heroes.getDiamondPrice());

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
