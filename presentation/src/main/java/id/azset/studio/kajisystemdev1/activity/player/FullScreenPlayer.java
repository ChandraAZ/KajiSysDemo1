package id.azset.studio.kajisystemdev1.activity.player;


import android.content.Intent;
import android.graphics.drawable.Drawable;

import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;

import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaControllerCompat;

import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.azset.studio.domain.model.KajiModel;
import id.azset.studio.kajisystemdev1.R;
import id.azset.studio.kajisystemdev1.utilities.LogHelper;
import id.azset.studio.kajisystemdev1.utilities.MediaBrowserAdapter;

import static android.view.View.GONE;

public class FullScreenPlayer extends AppCompatActivity implements IFSPlayerView {
    private static final String TAG = LogHelper.makeLogTag(FullScreenPlayer.class);
    private MediaSeekBar mSeekBarAudio;
    private MediaBrowserAdapter mMediaBrowserAdapter;
    private boolean mIsPlaying;
    IFSPlayerPresenter presenter;
    //@BindView(R.id.background_image)ImageView mBackgroundImage;
    @BindView(R.id.play_pause)ImageView mPlayPause;
    @BindView(R.id.next)ImageView mSkipNext;
    @BindView(R.id.prev)ImageView mSkipPrev;
    @BindView(R.id.startText)TextView mStart;
    @BindView(R.id.endText)TextView mEnd;
    //@BindView(R.id.seekBar1)SeekBar mSeekbar;
    @BindView(R.id.line1)TextView mLine1;
    @BindView(R.id.line2)TextView mLine2;
    @BindView(R.id.line3)TextView mLine3;
    @BindView(R.id.line4)TextView mLine4;
    @BindView(R.id.progressBar1)ProgressBar mLoading;
    @BindView(R.id.controllers)View mControllers;


    private Drawable mPauseDrawable;
    private Drawable mPlayDrawable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_player);
        ButterKnife.bind(this);
        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        Long keyID = intent.getLongExtra("KeyID",0);
        long idKaji = keyID;
        presenter = new FSPlayerPresenter(this, new FSPlayerInteractor(this.getApplicationContext()));
        mPauseDrawable = ContextCompat.getDrawable(this, R.drawable.uamp_ic_pause_white_48dp);
        mPlayDrawable = ContextCompat.getDrawable(this, R.drawable.uamp_ic_play_arrow_white_48dp);
        int id = this.getApplication().getResources().getIdentifier("bg", "drawable", this.getApplication().getPackageName());
        //mBackgroundImage.setImageResource(id);
        mSeekBarAudio = (MediaSeekBar) findViewById(R.id.seekbar_audio);
        mControllers.setVisibility(View.VISIBLE);
        mLoading.setVisibility(View.INVISIBLE);
        //presenter.getKajianbyID(idKaji);
        mMediaBrowserAdapter = new MediaBrowserAdapter(this);
        mMediaBrowserAdapter.addListener(new MediaBrowserListener());


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void showProgress(){
        mLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress(){
        mLoading.setVisibility(GONE);
    }
    @Override
    public void setKajian(KajiModel dataKajian){
        //mLine1.setText(dataKajian.getTitle());
        //mLine2.setText(dataKajian.getpemateri());
        //mLine3.setText("00:00");
        //mLine4.setText(dataKajian.getDuration());
    }

    @Override
    public void setTimeBar(String a){
        mLine3.setText(a);
    }

    @Override
    public void setNext(){
        try {
            mMediaBrowserAdapter.getTransportControls().skipToNext();
        } catch (Exception e)
        {
            e.getMessage();
        }
    }

    @OnClick(R.id.play_pause) void btnPlayPouseOnClick(View view){
        try {
            if (mIsPlaying) {
                mMediaBrowserAdapter.getTransportControls().pause();
            } else {
                mMediaBrowserAdapter.getTransportControls().play();
            }
        }catch(Exception e){
            e.getMessage();
        }
    }

    @OnClick(R.id.next) void btnNextOnClick(View view){
        try {
            mMediaBrowserAdapter.getTransportControls().skipToNext();
        } catch (Exception e)
        {
            e.getMessage();
        }
    }

    @OnClick (R.id.prev) void btnPrevOnClieck(View view){
        try {
            mMediaBrowserAdapter.getTransportControls().skipToPrevious();
        } catch(Exception e){
            e.getMessage();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mMediaBrowserAdapter.onStart();
        //if(mMediaBrowserAdapter) {
        //    mMediaBrowserAdapter.getTransportControls().play();
        //}
    }

    @Override
    public void onStop(){
        super.onStop();
        mSeekBarAudio.disconnectController();
        mMediaBrowserAdapter.onStop();
    }


    // jangan di hapus woy !!
    private class MediaBrowserListener extends MediaBrowserAdapter.MediaBrowserChangeListener {

        @Override
        public void onConnected(@Nullable MediaControllerCompat mediaController) {
            super.onConnected(mediaController);
            mSeekBarAudio.setMediaController(mediaController);
        }

        @Override
        public void onPlaybackStateChanged(PlaybackStateCompat playbackState) {
            mIsPlaying = playbackState != null && playbackState.getState() == PlaybackStateCompat.STATE_PLAYING;
            //mBackgroundImage.setPressed(mIsPlaying);
            if(mIsPlaying){
                mPlayPause.setVisibility(View.VISIBLE);
                mPlayPause.setImageDrawable(mPauseDrawable);
            }
            else{
                mPlayPause.setVisibility(View.VISIBLE);
                mPlayPause.setImageDrawable(mPlayDrawable);
            }
        }

        @Override
        public void onMetadataChanged(MediaMetadataCompat mediaMetadata) {
            if (mediaMetadata == null) {
                return;
            }
            //mTitleTextView.setText(mediaMetadata.getString(MediaMetadataCompat.METADATA_KEY_TITLE));
            //mArtistTextView.setText(mediaMetadata.getString(MediaMetadataCompat.METADATA_KEY_ARTIST));
            //mAlbumArt.setImageBitmap(MusicLibrary.getAlbumBitmap(FullScreenPlayer.this,mediaMetadata.getString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID)));
            mLine1.setText(mediaMetadata.getString(MediaMetadataCompat.METADATA_KEY_TITLE));
            mLine2.setText(mediaMetadata.getString(MediaMetadataCompat.METADATA_KEY_ARTIST));
            mLine4.setText(getDate(mediaMetadata.getLong(MediaMetadataCompat.METADATA_KEY_DURATION)));
            //mLine3.setText("00:00");
            mMediaBrowserAdapter.getTransportControls().play();
        }

        private String getDate(long milliSeconds) {
            int seconds = (int) (milliSeconds / 1000) % 60 ;
            int minutes = (int) ((milliSeconds / (1000*60)) % 60);
            int hours   = (int) ((milliSeconds / (1000*60*60)) % 24);
            return String.format("%02d:%02d:%02d",hours,minutes,seconds);
        }
    }
}
