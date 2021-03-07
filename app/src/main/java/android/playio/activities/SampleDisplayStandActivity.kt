package android.playio.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.playio.R
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit

class SampleDisplayStandActivity : AppCompatActivity(R.layout.activity_sample_display_stand) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val fragmentClassName = intent.getStringExtra(EXTRA_KEY_FRAGMENT_CLASS_NAME)
        Log.d(TAG, "onCreate, fragmentClassName: $fragmentClassName")
        if (!TextUtils.isEmpty(fragmentClassName)) {
            supportFragmentManager.commit {
                replace(R.id.fragmentContainerView, Class.forName(fragmentClassName!!) as Class<out Fragment?>,
                        Bundle())
            }
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume")
    }

    companion object {
        private val TAG = SampleDisplayStandActivity::class.java.simpleName

        public val EXTRA_KEY_FRAGMENT_CLASS_NAME = "fragmentClassName"
    }
}