package fragment.Heroes.HeroesDetail;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import adapter.SoundRecyclerViewAdapter;
import cn.nicolite.palm300heroes.R;
import database.Palm300heroesDB;
import model.Heroes;
import model.Sound;

/**
 * Created by NICOLITE on 2016/11/7 0007.
 */

public class HeroesSoundFragment extends Fragment implements SoundRecyclerViewAdapter.OnItemClickListener{
    private Palm300heroesDB palm300heroesDB;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private SoundRecyclerViewAdapter recycleAdapter;
    private List<Sound> dataList = new ArrayList<>();

    private ImageView playSound;
    private MediaPlayer mediaPlayer = null;
    private boolean click = true;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R. layout.heroes_detail_sound_fragment, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.heroes_sound_recycler_view);

        initSoundDate();

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
    public void onItemClick(View view, int position) {
        playSound = (ImageView) view.findViewById(R.id.play_sound);
        if (click){
            if (mediaPlayer == null) {
                mediaPlayer = new MediaPlayer();
            }
            click = false;
            playSound.setImageResource(R.drawable.ic_media_pause);
            Uri uri = Uri.parse(dataList.get(position).getUrl());
            try {
                mediaPlayer.setDataSource(getActivity(),uri);
                mediaPlayer.prepare();
                mediaPlayer.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    playSound.setImageResource(R.drawable.ic_media_play);
                    mediaPlayer.reset();
                    mediaPlayer.release();
                    mediaPlayer = null;
                }
            });
        }else {
            playSound.setImageResource(R.drawable.ic_media_play);
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer = null;
            click = true;
        }
    }

    public void initSoundDate(){
        palm300heroesDB = palm300heroesDB.getInstance(getActivity());
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
}
