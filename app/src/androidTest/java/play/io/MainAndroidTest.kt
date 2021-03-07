package play.io;

import android.util.Log
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4::class)
class MainAndroidTest {

    companion object {
        private const val TAG = "MainAndroidTest"
    }

    @Test
    fun testContext() {
        val context = InstrumentationRegistry.getInstrumentation().context
        Log.i(TAG, context.toString())
    }
}