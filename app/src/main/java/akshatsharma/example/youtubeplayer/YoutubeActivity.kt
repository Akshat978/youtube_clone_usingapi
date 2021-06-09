package akshatsharma.example.youtubeplayer

import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView

const val YOUTUBE_VIDEO_ID = "6Pd-3nvYDRk"
const val YOUTUBE_PLAYLIST = "PLBm3q1VIzvkZHsAL8ajiGxY6ElF76fBEC"

class YoutubeActivity : YouTubeBaseActivity(), YouTubePlayer.OnInitializedListener {
    private val TAG="YoutubeActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //  setContentView(R.layout.activity_youtube)

        val layout = layoutInflater.inflate(R.layout.activity_youtube, null) as ConstraintLayout
        setContentView(layout)
        val playerView = YouTubePlayerView(this)
        playerView.layoutParams = ConstraintLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        layout.addView(playerView)

        playerView.initialize(getString(R.string.GOOGLE_API_KEY),this)
    }

    override fun onInitializationSuccess(
        provider: YouTubePlayer.Provider?,
        youTubePlayer: YouTubePlayer?,
        wasRestored: Boolean
    ) {
        Log.d(TAG, "onInitialization Success: provider is ${provider?.javaClass}")
        Log.d(TAG, "onInitialization Success: youTubePlayer is ${youTubePlayer?.javaClass}")
        Toast.makeText(this, "Initialized YouTube Player successfully", Toast.LENGTH_SHORT).show()

        youTubePlayer?.setPlaybackEventListener(playbackEventListener)
        youTubePlayer?.setPlayerStateChangeListener(playerStateChangeListener)

        if(!wasRestored){
            youTubePlayer?.loadVideo(YOUTUBE_VIDEO_ID)
        }
        else{
            youTubePlayer?.play()
        }
    }

    override fun onInitializationFailure(
        provider: YouTubePlayer.Provider?,
        youTubeInitializationResult: YouTubeInitializationResult?
    ) {
        val REQUEST_CODE=0
        if(youTubeInitializationResult?.isUserRecoverableError== true){
            youTubeInitializationResult.getErrorDialog(this, REQUEST_CODE).show()
        }
        else{
            val errorMessage= "There was an error initializing the YouTube App Player $youTubeInitializationResult"
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
        }
    }
    private val playbackEventListener=object :YouTubePlayer.PlaybackEventListener{
        override fun onSeekTo(p0: Int) {

        }


        override fun onBuffering(p0: Boolean) {

        }

        override fun onPlaying() {
            Toast.makeText(this@YoutubeActivity, "Video has started playing", Toast.LENGTH_SHORT).show()
        }

        override fun onStopped() {
            Toast.makeText(this@YoutubeActivity, "Video has stopped playing", Toast.LENGTH_SHORT).show()
        }

        override fun onPaused() {
            Toast.makeText(this@YoutubeActivity, "You have paused the video", Toast.LENGTH_SHORT).show()
        }
    }
    private val playerStateChangeListener=object : YouTubePlayer.PlayerStateChangeListener{
        override fun onAdStarted() {
            Toast.makeText(this@YoutubeActivity, "Click on the add to make the creator rich", Toast.LENGTH_SHORT).show()
        }

        override fun onLoading() {

        }

        override fun onVideoStarted() {
            Toast.makeText(this@YoutubeActivity, "Video has started playing", Toast.LENGTH_SHORT).show()
        }

        override fun onLoaded(p0: String?) {

        }

        override fun onVideoEnded() {
            Toast.makeText(this@YoutubeActivity, "You have completed the video", Toast.LENGTH_SHORT).show()
        }

        override fun onError(p0: YouTubePlayer.ErrorReason?) {
            
        }
    }
}