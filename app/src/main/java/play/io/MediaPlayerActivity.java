package play.io;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;

public class MediaPlayerActivity extends AppCompatActivity {

    private MediaPlayer mMediaPlayer;
    private final static String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle(MediaPlayerActivity.class.getSimpleName());
        mMediaPlayer = new MediaPlayer();
        playAssetsAudio("music.mp3");
    }

    private void playRawAudio() {
        mMediaPlayer = MediaPlayer.create(this, R.raw.music);
        mMediaPlayer.start(); // no need to call prepare(); create() does that for you
    }

    private void playAssetsAudio(final String name) {
        AssetFileDescriptor fd = null;
        try {
            fd = getAssets().openFd(name);
            mMediaPlayer = new MediaPlayer();
            mMediaPlayer.reset();
            mMediaPlayer.setDataSource(fd.getFileDescriptor(), fd.getStartOffset(), fd.getLength());
            mMediaPlayer.prepareAsync();
            mMediaPlayer.setOnPreparedListener((MediaPlayer mediaPlayer) -> {
                Log.d(TAG, "onPrepared: " + name);
                mediaPlayer.start();
                Log.d(TAG, "is playing: " + mediaPlayer.isPlaying());
            });
        } catch (Exception e) {
            Log.d(TAG, "catch error");
            try {
                if (fd != null) {
                    fd.close();
                }
            } catch (Exception e1) {
                Log.e(TAG, "Exception close fd: ", e1);
            }
        } finally {
            Log.d(TAG, "finally");
            if (fd != null) {
                try {
                    fd.close();
                } catch (IOException e) {
                    Log.e(TAG, "Finally, close fd ", e);
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMediaPlayer.stop();
        mMediaPlayer.release();
    }
}