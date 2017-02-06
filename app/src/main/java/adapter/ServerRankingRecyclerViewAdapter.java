package adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cn.nicolite.palm300heroes.R;
import model.ServerRanking;
import utilty.LogUtil;

/**
 * Created by NICOLITE on 2017/2/6 0006.
 */

public class ServerRankingRecyclerViewAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<ServerRanking> dataList;
    private LayoutInflater inflater;
    private OnItemClickListener onItemClickListener = null;

    public ServerRankingRecyclerViewAdapter(Context context, List<ServerRanking> dataList) {
        this.context = context;
        this.dataList = dataList;
        inflater = LayoutInflater.from(context);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView infoR;
        private TextView infoTitle;
        public ViewHolder(View itemView) {
            super(itemView);
            infoR = (TextView) itemView.findViewById(R.id.server_ranking_info);
            infoTitle = (TextView) itemView.findViewById(R.id.server_ranking_title);
        }

        public TextView getInfoR() {
            return infoR;
        }

        public TextView getInfoTitle() {
            return infoTitle;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.server_ranking_recyclerview_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ViewHolder viewHolder = (ViewHolder) holder;
        String color ;
        ServerRanking serverRanking = dataList.get(position);
        if (serverRanking.getInfoR()[2].contains("↑")) {
            color = "<font  color='#FF2D2D'>" + serverRanking.getInfoR()[2] + serverRanking.getInfoR()[3] +"</font>";
        }else {
            color = "<font  color='#79FF79'>" + serverRanking.getInfoR()[2] + serverRanking.getInfoR()[3] +"</font>";
        }
        String info = serverRanking.getInfoR()[1]  + color + "   " +serverRanking.getInfoR()[4] + serverRanking.getInfoR()[5];
        viewHolder.getInfoTitle().setText(serverRanking.getInfoR()[0]);

        for (int i = 0; i < serverRanking.getInfoR().length; i++) {
            LogUtil.d("ServerRanking", serverRanking.getInfoR()[i]);
        }
        viewHolder.getInfoR().setText(Html.fromHtml(info));
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
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }
}
