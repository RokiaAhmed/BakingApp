package com.udacity.bakingapp.ui.stepdetails;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.udacity.bakingapp.R;
import com.udacity.bakingapp.model.Step;

public class StepFragment extends Fragment implements ExoPlayer.EventListener {

    private SimpleExoPlayerView mPlayerView;
    private SimpleExoPlayer mExoPlayer;
    private String videoUrl, shortDescription, description;
    private TextView shortDescriptionTextView, descriptionTextView;

    public StepFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_step, container, false);

        if (savedInstanceState != null && mExoPlayer != null) {
            mExoPlayer.setPlayWhenReady(savedInstanceState.getBoolean("PLAYER_IS_READY_KEY"));
            mExoPlayer.seekTo(savedInstanceState.getLong("PLAYER_CURRENT_POS_KEY"));
         }

        // Initialize the player view.
        mPlayerView = (SimpleExoPlayerView) rootView.findViewById(R.id.playerView);
        // Initialize the player.
        if (videoUrl != null){
            initializePlayer(Uri.parse(videoUrl));
        }
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            // In ORIENTATION_PORTRAIT
            shortDescriptionTextView = rootView.findViewById(R.id.tv_step_short_description);
            descriptionTextView = rootView.findViewById(R.id.tv_step_description);
            shortDescriptionTextView.setText(shortDescription);
            descriptionTextView.setText(description);

        }
        return rootView;
    }

    public void setStepDetails(Step stepDetails) {
        this.videoUrl = stepDetails.getVideoURL();
        shortDescription = stepDetails.getShortDescription();
        description = stepDetails.getDescription();
        Log.d("videoUrl", videoUrl);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mExoPlayer == null) {
            return;
        }
            outState.putLong("PLAYER_CURRENT_POS_KEY", mExoPlayer.getCurrentPosition());
        outState.putBoolean("PLAYER_IS_READY_KEY", mExoPlayer.getPlayWhenReady());
    }

    /**
     * Initialize ExoPlayer.
     *
     * @param mediaUri The URI of the sample to play.
     */
    private void initializePlayer(Uri mediaUri) {
        if (mExoPlayer == null) {
            // Create an instance of the ExoPlayer.
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector, loadControl);
            mPlayerView.setPlayer(mExoPlayer);
            // Set the ExoPlayer.EventListener to this activity.
            mExoPlayer.addListener(this);
            // Prepare the MediaSource.
            String userAgent = Util.getUserAgent(getActivity(), "BakingApp");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    getActivity(), userAgent), new DefaultExtractorsFactory(), null, null);
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(true);
        }
    }


    private void releasePlayer() {
        if (mExoPlayer != null) {
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        if (videoUrl != null){
            initializePlayer(Uri.parse(videoUrl));
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        releasePlayer();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        if ((playbackState == ExoPlayer.STATE_READY) && playWhenReady) {
//            Log.d(TAG, "onPlayerStateChanged: PLAYING");
        } else if ((playbackState == ExoPlayer.STATE_READY)) {
//            Log.d(TAG, "onPlayerStateChanged: PAUSED");
        } else if (playbackState == ExoPlayer.STATE_ENDED) {
            mExoPlayer.seekTo(0);
            mExoPlayer.setPlayWhenReady(false);
        }

    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPositionDiscontinuity() {

    }
}
