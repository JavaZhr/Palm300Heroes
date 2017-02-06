package adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cn.nicolite.palm300heroes.R;
import model.ServerRanking;

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
        public ViewHolder(View itemView) {
            super(itemView);
            infoR = (TextView) itemView.findViewById(R.id.server_ranking_info);
        }

        public TextView getInfoR() {
            return infoR;
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
        ServerRanking serverRanking = dataList.get(position);

        viewHolder.getInfoR().setText(serverRanking.getInfoR());
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
