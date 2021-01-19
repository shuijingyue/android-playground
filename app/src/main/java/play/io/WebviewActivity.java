package play.io;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import play.io.fragments.WebviewFragment;

public class WebviewActivity extends AppCompatActivity {

    private static final String TAG = WebviewActivity.class.getSimpleName();

    public static final String EXTRA_KEY_URL = "url";

    private Bundle mExtra;

    private WebviewFragment mWebviewFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        getSupportActionBar().setTitle(WebviewActivity.class.getSimpleName());
        mExtra = getIntent().getExtras();
        mWebviewFragment = (WebviewFragment) getSupportFragmentManager().findFragmentById(R.id.webview_fragment);
        if (mExtra != null) {
            mWebviewFragment.loadUrl(mExtra.getString(EXTRA_KEY_URL));
        }
    }
}