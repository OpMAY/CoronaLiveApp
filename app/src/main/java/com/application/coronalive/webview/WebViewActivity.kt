package com.application.coronalive.webview

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Bitmap
import android.net.http.SslError
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import android.widget.ProgressBar
import android.widget.Toast
import com.application.coronalive.R
import kotlinx.android.synthetic.main.activity_web_view.*

/** MainActivity 로부터 URL 을 받아서 넘어올 것임
 *  Intent.putExtra() 함수를 이용하여 버튼마다 URL 을 다르게 넘겨준 뒤
 *  해당 URL 을 WebActivity 로 실행하면 끝
 *  + 자바스크립트 사용 유/무에 따라 웹사이트에 표시되는 컨텐츠가 달라질 수 있음
 *  But) 자바스크립트로 인한 악성코드 감염 역시 있을 수 있음
 *  자바스크립트 사용 설정 버튼을 만드는게 제일 좋아보임
 */
@SuppressLint("SetJavaScriptEnabled")
class WebViewActivity : AppCompatActivity() {

    lateinit var mProgressBar : ProgressBar
    private var pressedTime : Long = 2000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)

        mProgressBar = findViewById(R.id.progress_webview)

        val url: String? = intent.getStringExtra("CLICKED URL")
        webView.apply {
            webViewClient = WebViewClientClass()
            webChromeClient = object : WebChromeClient() {
                override fun onCreateWindow(
                    view: WebView?,
                    isDialog: Boolean,
                    isUserGesture: Boolean,
                    resultMsg: Message?
                ): Boolean {
                    val newWebView = WebView(this@WebViewActivity).apply {
                        webViewClient = WebViewClient()
                        settings.javaScriptEnabled = true
                    }

                    val dialog = Dialog(this@WebViewActivity).apply {
                        setContentView(newWebView)
                        window!!.attributes.width = ViewGroup.LayoutParams.MATCH_PARENT
                        window!!.attributes.height = ViewGroup.LayoutParams.MATCH_PARENT
                        show()
                    }
                    newWebView.webChromeClient = object : WebChromeClient() {
                        override fun onCloseWindow(window: WebView?) {
                            dialog.dismiss()
                        }
                    }

                    (resultMsg?.obj as WebView.WebViewTransport).webView = newWebView
                    resultMsg.sendToTarget()
                    return true
                }
            }
            settings.javaScriptEnabled = true
            settings.setSupportMultipleWindows(true)
            settings.javaScriptCanOpenWindowsAutomatically = true
            settings.loadWithOverviewMode = true
            settings.useWideViewPort = true
            settings.setSupportZoom(true)
            settings.builtInZoomControls = true

            //Enable and Setup Web View Cache
            settings.cacheMode =
                WebSettings.LOAD_NO_CACHE
            settings.domStorageEnabled = true
            settings.displayZoomControls = true

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                settings.safeBrowsingEnabled = true //API 26
            }

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1){
                settings.mediaPlaybackRequiresUserGesture = true
            }
            settings.allowContentAccess = true
            settings.setGeolocationEnabled(true)
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN){
                settings.allowUniversalAccessFromFileURLs = true
            }

            settings.allowFileAccess = true
            fitsSystemWindows = true
        }
        webView.loadUrl(url)
    }

    override fun onBackPressed() {
        if(webView.canGoBack())
            webView.goBack()
        else {
            if (System.currentTimeMillis() - pressedTime <= 2000)
                finish()
            else {
                pressedTime = System.currentTimeMillis()
                Toast.makeText(this, "이전 버튼을 한 번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    inner class WebViewClientClass : WebViewClient(){
        override fun shouldOverrideUrlLoading(
            view: WebView,
            url: String
        ): Boolean {
            view.loadUrl(url)
            return true
        }

        override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            mProgressBar.visibility = ProgressBar.VISIBLE
            webView.visibility = View.INVISIBLE
        }

        override fun onPageCommitVisible(view: WebView, url: String) {
            super.onPageCommitVisible(view, url)
            mProgressBar.visibility = ProgressBar.GONE
            webView.visibility = View.VISIBLE
        }

        override fun onReceivedSslError(
            view: WebView,
            handler: SslErrorHandler,
            error: SslError
        ) {
            var builder : AlertDialog.Builder =
                AlertDialog.Builder(this@WebViewActivity)
            var message = "SSL CERTIFICATE ERROR"
            when(error.primaryError){
                SslError.SSL_UNTRUSTED -> message = "The certificate authority is not trusted"
                SslError.SSL_EXPIRED -> message = "The certificate has expired"
                SslError.SSL_IDMISMATCH -> message = "The certificate Hostname mismatch"
                SslError.SSL_NOTYETVALID -> message = "The certificate is not yet valid"
            }
            message += "Do you want to continue anyway?"
            builder.setTitle("SSL Certificate ERROR")
            builder.setMessage(message)
            builder.setPositiveButton("continue", DialogInterface.OnClickListener{_, _ -> handler.proceed()})
            builder.setNegativeButton("cancel", DialogInterface.OnClickListener { _, _ -> handler.cancel() })
            var dialog : AlertDialog? = builder.create()
            dialog?.show()
        }
    }
}