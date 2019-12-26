package org.techtown.youtube;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class MainActivity extends YouTubeBaseActivity {
    YouTubePlayerView playerView;
    YouTubePlayer player;

    private static String API_KEY = "AIzaSyDWXmWuB7xjPaN5gwW2hlNtHr9nETESN7E";
    private static String videoId = "A22oy8dFjqc";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initPlayer();

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playVideo();
            }
        });

    }

    public void initPlayer() {
        playerView = findViewById(R.id.playerView);

        playerView.initialize(API_KEY, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                player = youTubePlayer;

                player.setPlayerStateChangeListener(new YouTubePlayer.PlayerStateChangeListener() {
                    @Override
                    public void onLoading() {}

                    @Override
                    public void onLoaded(String id) {
                        Log.d("PlayerView", "onLoaded 호출됨 : " + id);

                        player.play();
                    }

                    @Override
                    public void onAdStarted() {}

                    @Override
                    public void onVideoStarted() {}

                    @Override
                    public void onVideoEnded() {}

                    @Override
                    public void onError(YouTubePlayer.ErrorReason errorReason) {}
                });

            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }

        });
    }

    public void playVideo() {
        if (player != null) {
            if (player.isPlaying()) {
                player.pause();
            }

            player.cueVideo(videoId);
        }
    }

}
