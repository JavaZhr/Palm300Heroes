package adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.nicolite.palm300heroes.R;

/**
 * Created by NICOLITE on 2017/2/5 0005.
 */

public class GameRecyclerViewAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<String> dataList = new ArrayList<>();
    private OnItemClickListener onItemClickListener = null;
    private LayoutInflater inflater;

    public GameRecyclerViewAdapter(Context context, List<String> dataList) {
        this.context = context;
        this.dataList = dataList;
        inflater = inflater.from(context);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView gameSelectorName;
        private TextView gameSelectorHint;

        public ViewHolder(View itemView) {
            super(itemView);
            gameSelectorName = (TextView) itemView.findViewById(R.id.game_selector_name);
            gameSelectorHint = (TextView) itemView.findViewById(R.id.game_selector_hint);
        }

        public TextView getGameSelectorName() {
            return gameSelectorName;
        }

        public TextView getGameSelectorHint() {
            return gameSelectorHint;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.game_recyclerview_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ViewHolder viewHolder = (ViewHolder) holder;

        viewHolder.getGameSelectorName().setText(dataList.get(position));
        viewHolder.getGameSelectorHint().setText("");
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

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }
}
