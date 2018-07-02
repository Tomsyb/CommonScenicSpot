package com.android.daqsoft.androidbasics.ui.fragment.index;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.android.daqsoft.androidbasics.R;
import com.android.daqsoft.androidbasics.base.BaseFragment;
import com.android.daqsoft.androidbasics.utils.LogUtils;
import com.android.daqsoft.androidbasics.utils.ObjectUtils;
import com.android.daqsoft.androidbasics.utils.img.GlideUtils;
import com.android.daqsoft.androidbasics.view.Player;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
/**
 *语音导览
 * @author 严博
 * @date 2018-4-28.
 * @version 1.0.0
 * @since JDK 1.8
 */
public class IndexAudioFragment extends BaseFragment {
    @BindView(R.id.include_img_back)
    ImageView mBack;
    @BindView(R.id.include_tv_title)
    TextView mTvTitle;
    @BindView(R.id.iv_audio)
    ImageView ivAudio;
    @BindView(R.id.tv_audio_name)
    TextView tvAudioName;
    @BindView(R.id.tv_audio_time)
    TextView tvAudioTime;
    @BindView(R.id.bar_audio_progress)
    SeekBar barAudioProgress;
    @BindView(R.id.tv_audio_total)
    TextView tvAudioTotal;
    @BindView(R.id.iv_audio_play)
    ImageView mImgPlay;
    /**
     * 播放器
     */
    private Player player ;
    /**
     * 动画
     */
    private Animation an;
    /**
     * 名字
     */
    private String name = "";
    /**
     * 图片
     */
    private String img = "";
    /**
     * 音频
     */
    private String audio = "";
    //单列
    public static IndexAudioFragment newInstance(String name,String audio,String imgPath) {
        Bundle args = new Bundle();
        args.putString("name",name);
        args.putString("img",imgPath);
        args.putString("audio",audio);
        IndexAudioFragment fragment = new IndexAudioFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void init(Bundle savedInstanceState) {
        initAnimotion();
        initData();
        initView();
    }


    private void initData() {
        name = getArguments().getString("name");
        img = getArguments().getString("img");
        audio = getArguments().getString("audio");
        if (ObjectUtils.isNotEmpty(img)){
            GlideUtils.GlideCircleImg(_mActivity.getApplication(),img,ivAudio);
        }
    }

    private void initView() {
        mTvTitle.setText("语音导览");
        tvAudioName.setText(name);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fg_index_audio;
    }




    @OnClick({R.id.include_img_back, R.id.iv_audio_play})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.include_img_back:
                _mActivity.onBackPressed();
                break;
            case R.id.iv_audio_play://播放
                if(null==player){
                    player=new Player(barAudioProgress,tvAudioTime,tvAudioTotal);
                    ivAudio.startAnimation(an);
                    barAudioProgress.setOnSeekBarChangeListener(new SeekBarChangeEvent());
                    new Thread(new Runnable() {

                        @Override
                        public void run() {
                            //path = Utils.getImageUrl(resouce);
                            LogUtils.e("语音播放地址----"+audio);
                            player.playUrl(audio);
                        }
                    }).start();
                    mImgPlay.setImageResource(R.mipmap.tourism_products_play_button);
                }else{
                    if(null!=player&&player.mediaPlayer.isPlaying()){
                        player.pause();
                        ivAudio.clearAnimation();
                        mImgPlay.setImageResource(R.mipmap.tourism_products_suspended_button);
                    }else{
                        ivAudio.startAnimation(an);
                        player.play();
                        mImgPlay.setImageResource(R.mipmap.tourism_products_play_button);
                    }
                }
                break;
        }
    }

    class SeekBarChangeEvent implements SeekBar.OnSeekBarChangeListener {
        int progress;

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress,
                                      boolean fromUser) {
            this.progress = progress * player.mediaPlayer.getDuration()/ seekBar.getMax();
//			tvMusicCurrent.setText(getTimeFromInt(progress));
            //tvMusicTime.setText(getTimeFromInt(player.mediaPlayer.getDuration()));
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            player.mediaPlayer.seekTo(progress);
        }

    }
    public void initAnimotion(){
        an = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        an.setInterpolator(new LinearInterpolator());//不停顿
        an.setRepeatCount(-1);//重复次数
        an.setFillAfter(true);//停在最后
        an.setDuration(4000);
        an.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }
            @Override
            public void onAnimationRepeat(Animation animation) {

            }
            @Override
            public void onAnimationEnd(Animation animation) {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (player!=null){
            if (!player.mediaPlayer.isPlaying()){
                player.play();
                ivAudio.setImageResource(R.mipmap.tourism_products_play_button);
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (player!=null){
            if (player.mediaPlayer.isPlaying()){
                player.pause();
                ivAudio.setImageResource(R.mipmap.tourism_products_suspended_button);
            }
        }
    }
}
