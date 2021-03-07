package android.playio.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.*
import androidx.fragment.app.Fragment
import android.playio.R

class WebviewFragment : Fragment(R.layout.fragment_webview) {
    private var mWebView: WebView? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mWebView = view.findViewById(R.id.webview)
    }

    override fun onResume() {
        super.onResume()
        initWebView(mWebView)
        mWebView?.apply {
            loadUrl("https://developer.android.com")
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebView(webView: WebView?) {
        if (webView == null) {
            return
        }
        val settings = webView.settings
        // 开启JavaScript支持
        settings.javaScriptEnabled = true
        settings.setGeolocationEnabled(true)
        WebView.setWebContentsDebuggingEnabled(true)
        mWebView!!.settings.domStorageEnabled = true

        // 解决有时加载图片失败的问题
        try {
            val clz: Class<*> = settings.javaClass
            val method = clz.getMethod("setMixedContentMode", Int::class.javaPrimitiveType)
            method.invoke(settings, 0)
        } catch (e: Exception) {
            Log.e(TAG, "Exception ", e)
        }
        webView.webViewClient = CustomWebViewClient()
        webView.webChromeClient = CustomWebChromeClient()
    }

    private class CustomWebChromeClient : WebChromeClient() {
        override fun onProgressChanged(view: WebView, progress: Int) {
            Log.i(TAG, "onProgressChanged...$progress")
            super.onProgressChanged(view, progress)
        }

        override fun onGeolocationPermissionsShowPrompt(origin: String, callback: GeolocationPermissions.Callback) {
            callback.invoke(origin, true, false)
            super.onGeolocationPermissionsShowPrompt(origin, callback)
        }
    }

    private inner class CustomWebViewClient : WebViewClient() {
        override fun shouldOverrideUrlLoading(webView: WebView, request: WebResourceRequest): Boolean {
            val url = request.url.toString()
            val scheme = Uri.parse(url).scheme
            try {
                val context = context
                // 避免跳转特殊协议而出错
                if (URI_SCHEME_HTTP == scheme || URI_SCHEME_HTTPS == scheme) {
                    webView.loadUrl(url)
                } else {
                    var intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    if (URI_SCHEME_INTENT == scheme) {
                        intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME)
                    }
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    context!!.startActivity(intent)
                }
            } catch (e: Exception) {
                Log.e(TAG, "shouldOverrideUrlLoading error: ", e)
                Log.e(TAG, "shouldOverrideUrlLoading url: $url")
            }
            return true
        }
    }

    companion object {
        private const val TAG = "WebviewFragment"
        private const val URI_SCHEME_HTTP = "http"
        private const val URI_SCHEME_HTTPS = "https"
        private const val URI_SCHEME_INTENT = "intent"
    }
}