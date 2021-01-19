package play.io.widgets;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.lang.reflect.Method;

import play.io.R;

public class CustomView extends FrameLayout {

    private static final String TAG = CustomView.class.getSimpleName();

    public CustomView(@NonNull Context context) {
        super(context);
    }

    public CustomView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            Log.d(TAG, "dispatchKeyEvent: backPressed");
        }
        return false;
    }

    public static class WebviewFragment extends Fragment {
        private static final String TAG = "WebviewFragment";

        private static final String URI_SCHEME_HTTP = "http";
        private static final String URI_SCHEME_HTTPS = "https";
        private static final String URI_SCHEME_INTENT = "intent";

        private WebView mWebView;

        @Override
        public void onAttach(@NonNull Context context) {
            super.onAttach(context);
        }

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            Log.d(TAG, String.format("onCreate: %s", savedInstanceState));
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_webview, container);
            mWebView = view.findViewById(R.id.webview);
            initWebView(mWebView);
            Log.d(TAG, "onCreateView: " + savedInstanceState);
            return null;
        }

        @Override
        public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
            super.onViewStateRestored(savedInstanceState);
            Log.d(TAG, String.format("onViewStateRestored: %s", savedInstanceState));
        }

        @Override
        public void onStop() {
            super.onStop();
            Log.d(TAG, "onStop");
        }

        @Override
        public void onResume() {
            super.onResume();
            Log.d(TAG, "onResume");
        }

        @Override
        public void onPause() {
            super.onPause();
            Log.d(TAG, "onPause");
        }

        @Override
        public void onSaveInstanceState(@NonNull Bundle outState) {
            super.onSaveInstanceState(outState);
            outState.putString("foo", "bar");
            Log.d(TAG, "onSaveInstanceState");
        }

        @SuppressLint("SetJavaScriptEnabled")
        private void initWebView(WebView webView) {
            if (webView == null) {
                return;
            }
            WebSettings settings = webView.getSettings();
            // 开启JavaScript支持
            settings.setJavaScriptEnabled(true);
            settings.setGeolocationEnabled(true);

            WebView.setWebContentsDebuggingEnabled(true);
            mWebView.getSettings().setDomStorageEnabled(true);

            // 解决有时加载图片失败的问题
            try {
                Class<?> clz = settings.getClass();
                Method method = clz.getMethod("setMixedContentMode", int.class);
                method.invoke(settings, 0);
            } catch (Exception e) {
                Log.e(TAG, "Exception ", e);
            }

            webView.setWebViewClient(new CustomWebViewClient());
            webView.setWebChromeClient(new CustomWebChromeClient());
            webView.loadUrl("https://www.baidu.com");
        }

        private static class CustomWebChromeClient extends WebChromeClient {

            @Override
            public void onProgressChanged(WebView view, int progress) {
                Log.i(TAG, "GreetingWebChromeClient onProgressChanged..." + progress);
                super.onProgressChanged(view, progress);
            }

            @Override
            public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
                callback.invoke(origin, true, false);
                super.onGeolocationPermissionsShowPrompt(origin, callback);
            }
        }

        private class CustomWebViewClient extends WebViewClient {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.i(TAG, "CustomWebViewClient shouldOverrideUrlLoading..." + url);

                try {
                    Log.i(TAG, "shouldOverrideUrlLoading: " + url);
                    String scheme = Uri.parse(url).getScheme();
                    Context context = getContext().getApplicationContext();
                    // 避免跳转特殊协议而出错
                    if (URI_SCHEME_HTTP.equals(scheme) || URI_SCHEME_HTTPS.equals(scheme)) {
                        view.loadUrl(url);
                    } else if (URI_SCHEME_INTENT.equals(scheme)) {
                        Intent intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    } else {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                    return true;
                } catch (Exception e) {
                    Log.e(TAG, "Exception ", e);
                    return true;
                }
            }
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            Log.d(TAG, "onDestroy: destroy");
        }
    }
}
