package fragment.Heroes.HeroesDetail;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import adapter.SoundRecyclerViewAdapter;
import cn.nicolite.palm300heroes.R;
import database.Palm300heroesDB;
import model.Heroes;
import model.Sound;
import utilty.Utilty;

import static android.media.AudioManager.STREAM_MUSIC;

/**
 * Created by NICOLITE on 2016/11/7 0007.
 */

public class HeroesSoundFragment extends Fragment implements SoundRecyclerViewAdapter.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener{
    private Palm300heroesDB palm300heroesDB;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private SoundRecyclerViewAdapter recycleAdapter;
    private List<Sound> dataList = new ArrayList<>();

    private MediaPlayer mediaPlayer;
    private SoundRecyclerViewAdapter.ViewHolder oldViewHolder;

    private SwipeRefreshLayout swipeRefreshLayout;
    private static final int REFRESH_COMPLETE_TIME = 4000;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0 :
                    swipeRefreshLayout.setRefreshing(false);
                    recycleAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R. layout.heroes_detail_sound_fragment, container, false);

        readSoundDate();

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.sound_swipe_refresh_layout);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.orange));
        swipeRefreshLayout.setOnRefreshListener(this);

        recyclerView = (RecyclerView) view.findViewById(R.id.heroes_sound_recycler_view);

        recycleAdapter = new SoundRecyclerViewAdapter(getActivity(), dataList);

        recycleAdapter.setOnItemClickListener(this);

        layoutManager = new LinearLayoutManager(getActivity());
        //设置布局管理器
        recyclerView.setLayoutManager(layoutManager);
        //设置为垂直布局，这也是默认的
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        //设置Adapter
        recyclerView.setAdapter(recycleAdapter);
        //设置分隔线
        //recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        //设置增加或删除条目的动画
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        return view;
    }

    @Override
    public void onItemClick(View view, final SoundRecyclerViewAdapter.ViewHolder viewHolder, int position) {

        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
        }
        String url = dataList.get(position).getUrl();

        if (oldViewHolder != viewHolder && oldViewHolder != null) {
            oldViewHolder.getPlaySound().setImageResource(R.drawable.ic_media_play);
        }
        oldViewHolder = viewHolder;
        try {
            viewHolder.getPlaySound().setImageResource(R.drawable.ic_media_pause);
            mediaPlayer.stop();
            mediaPlayer.reset();
            mediaPlayer.setDataSource(url);
            mediaPlayer.setAudioStreamType(STREAM_MUSIC);
            mediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }

        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.start();
            }
        });
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                viewHolder.getPlaySound().setImageResource(R.drawable.ic_media_play);
                mp.stop();
                mp.reset();
            }
        });

        mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                viewHolder.getPlaySound().setImageResource(R.drawable.ic_media_play);
                mp.stop();
                mp.reset();
                mp.release();
                mediaPlayer = null;
                Toast.makeText(getActivity(),"播放失败",Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    public void readSoundDate(){
        palm300heroesDB = Palm300heroesDB.getInstance(getActivity());
        List<Sound> list = palm300heroesDB.loadSound();
        dataList.clear();
        Heroes heroes = (Heroes) getActivity().getIntent().getSerializableExtra("heroes_data");
        for (Sound sound : list) {
            if (sound.getHero().equals(heroes.getName())) {
                dataList.add(sound);
            }
        }
    }

    void destoryMediaPlay(){
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        destoryMediaPlay();
    }

    @Override
    public void onRefresh() {
        Utilty.initSoundDate(getActivity());
        readSoundDate();
        handler.sendEmptyMessageDelayed(0, REFRESH_COMPLETE_TIME);
    }

}
