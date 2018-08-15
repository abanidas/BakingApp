package com.abani.exercise.android.bakingapp;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.abani.exercise.android.bakingapp.models.Step;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeGuideFragment extends Fragment {

    @BindView(R.id.video_recipe_guide)
    PlayerView videoRecipeGuide;
    @BindView(R.id.txt_step_label)
    TextView txtStepLabel;
    @BindView(R.id.txt_step_description)
    TextView txtStepDescription;
    @BindView(R.id.fab_next)
    FloatingActionButton fabNext;
    @BindView(R.id.fab_prev)
    FloatingActionButton fabPrev;

    private static final String SAVED_INSTANCE_POSITION = "position";
    private static final String SAVED_PLAYBACK_POSITION = "playback_position";
    private static final String SAVED_PLAYBACK_WINDOW = "current_window";
    private static final String SAVED_PLAY_WHEN_READY = "play_when_ready";

    private SimpleExoPlayer mPlayer;

    private List<Step> allSteps;
    private Step currentStep;
    private int currentStepPosition;
    private long playbackPosition = 0;
    private int currentWindow = 0;
    private boolean playWhenReady = true;

    public RecipeGuideFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (savedInstanceState != null){
            currentStepPosition = savedInstanceState.getInt(SAVED_INSTANCE_POSITION, 0);
            playbackPosition = savedInstanceState.getLong(SAVED_PLAYBACK_POSITION, 0);
            currentWindow = savedInstanceState.getInt(SAVED_PLAYBACK_WINDOW, 0);
            playWhenReady = savedInstanceState.getBoolean(SAVED_PLAY_WHEN_READY, false);

        }
        View view = inflater.inflate(R.layout.fragment_recipe_guide, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    private void initViews() {

        int currentOrientation = getResources().getConfiguration().orientation;
        if (currentOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            hideSystemUi();
        } else {

            if (currentStep != null) {
                txtStepLabel.setText(currentStep.getShortDescription());
                txtStepDescription.setText(currentStep.getDescription());
            }
            setupListeners();
        }

    }

    private void setupListeners() {

        if (!fabNext.hasOnClickListeners()) {

            fabNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (currentStepPosition < (allSteps.size() - 1)) {
                        setCurrentStep(currentStepPosition + 1);
                        initViews();
                        releasePlayer();
                        setPlaybackPosition(0);
                        initializePlayer();
                    }
                }
            });
        }

        if (!fabPrev.hasOnClickListeners()) {

            fabPrev.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (currentStepPosition > 0) {
                        setCurrentStep(currentStepPosition - 1);
                        initViews();
                        releasePlayer();
                        setPlaybackPosition(0);
                        initializePlayer();
                    }
                }
            });
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onStart() {
        super.onStart();

        if (Util.SDK_INT > 23) {
            initViews();
            initializePlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if ((Util.SDK_INT <= 23 || mPlayer == null)) {
            initViews();
            initializePlayer();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        releasePlayer();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mPlayer != null) {
            releasePlayer();
        }
    }

    private void initializePlayer(){
        if (mPlayer == null) {
            mPlayer = ExoPlayerFactory.newSimpleInstance(
                    new DefaultRenderersFactory(getContext()),
                    new DefaultTrackSelector(), new DefaultLoadControl());

            videoRecipeGuide.setPlayer(mPlayer);
            mPlayer.setPlayWhenReady(playWhenReady);
            MediaSource mediaSource = buildMediaSource(currentStep.getVideoURL());
            mPlayer.prepare(mediaSource, true, false);
            mPlayer.seekTo(currentWindow, playbackPosition);
        }
    }

    private MediaSource buildMediaSource(String videoURL) {
        return new ExtractorMediaSource.Factory(
                new DefaultHttpDataSourceFactory("exoplayer-baking-app"))
                .createMediaSource(Uri.parse(videoURL));
    }

    private void hideSystemUi() {
        videoRecipeGuide.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) videoRecipeGuide.getLayoutParams();
        params.width = params.MATCH_PARENT;
        params.height = params.MATCH_PARENT;
        videoRecipeGuide.setLayoutParams(params);
    }

    private void releasePlayer(){
        if (mPlayer != null){
            playbackPosition = mPlayer.getCurrentPosition();
            currentWindow = mPlayer.getCurrentWindowIndex();
            playWhenReady = mPlayer.getPlayWhenReady();
            mPlayer.release();
            mPlayer = null;
        }
    }

    public void setCurrentStep(int currentStepPosition) {
        currentStep = allSteps.get(currentStepPosition);
        this.currentStepPosition = currentStepPosition;
    }

    public void setAllSteps(List<Step> allSteps) {
        this.allSteps = allSteps;
    }

    public void setPlaybackPosition(long playbackPosition) {
        this.playbackPosition = playbackPosition;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SAVED_INSTANCE_POSITION, currentStepPosition);
        outState.putLong(SAVED_PLAYBACK_POSITION, playbackPosition);
        outState.putInt(SAVED_PLAYBACK_WINDOW, currentWindow);
        outState.putBoolean(SAVED_PLAY_WHEN_READY, playWhenReady);

    }
}
