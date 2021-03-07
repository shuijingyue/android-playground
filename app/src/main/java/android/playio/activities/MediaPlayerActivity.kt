package android.playio.activities

import android.content.res.AssetFileDescriptor
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.playio.R
import java.io.IOException

class MediaPlayerActivity : AppCompatActivity() {
    private lateinit var mMediaPlayer: MediaPlayer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sample_navigation)
        supportActionBar!!.title = MediaPlayerActivity::class.java.simpleName
        mMediaPlayer = MediaPlayer()
        playAssetsAudio("audio/music.mp3")
    }

    private fun playRawAudio() {
//        mMediaPlayer = MediaPlayer.create(this, R.raw.music)
        mMediaPlayer.start() // no need to call prepare(); create() does that for you
    }

    private fun playAssetsAudio(name: String) {
        var fd: AssetFileDescriptor? = null
        try {
            fd = assets.openFd(name)
            mMediaPlayer = MediaPlayer()
            mMediaPlayer.reset()
            mMediaPlayer.setDataSource(fd.fileDescriptor, fd.startOffset, fd.length)
            mMediaPlayer.prepareAsync()
            mMediaPlayer.setOnPreparedListener { mediaPlayer ->
                Log.d(TAG, "onPrepared: $name")
                mediaPlayer.start()
                Log.d(TAG, "is playing: " + mediaPlayer.isPlaying)
            }
        } catch (e: Exception) {
            Log.d(TAG, "catch error: ${e.message}")
            try {
                fd?.close()
            } catch (e1: Exception) {
                Log.e(TAG, "Exception close fd: ", e1)
            }
        } finally {
            Log.d(TAG, "finally")
            if (fd != null) {
                try {
                    fd.close()
                } catch (e: IOException) {
                    Log.e(TAG, "Finally, close fd ", e)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mMediaPlayer.stop()
        mMediaPlayer.release()
    }

    companion object {
        private val TAG = SampleNavigationActivity::class.java.simpleName
    }
}