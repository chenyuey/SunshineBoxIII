package com.tipchou.sunshineboxiii.ui.video;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;

import com.tipchou.sunshineboxiii.R;
import com.tipchou.sunshineboxiii.ui.base.BaseActivity;


public class VideoActivity extends BaseActivity {

    MyVideoView videoView;

    private final static String INTENT_EXTRA_VIDEO_URL = "shaolizhi.sunshinebox.ui.video.video_url";

    private String videoUrl;

    @Override
    protected int layoutId() {
        return R.layout.activity_video;
    }

    @Override
    protected void created(Bundle bundle) {
        videoView = findViewById(R.id.video_act_videoview);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            //隐藏ToolBar标题
            actionBar.setDisplayShowTitleEnabled(false);
        }
        videoUrl = getIntent().getStringExtra(INTENT_EXTRA_VIDEO_URL);
    }

    @Override
    protected void resume() {
        videoView.setMediaController(new MediaController(this));
        videoView.setVideoURI(Uri.parse(videoUrl));
        videoView.start();
        videoView.requestFocus();
    }

    public static Intent newIntent(Context packageContext, String videoUrl) {
        Intent intent = new Intent(packageContext, VideoActivity.class);
        intent.putExtra(INTENT_EXTRA_VIDEO_URL, videoUrl);
        return intent;
    }

}
