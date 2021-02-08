package com.application.coronalive.webview

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebViewClient
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)

        val url : String? = intent.getStringExtra("NEWS URL")
        webView.apply {
            webViewClient = WebViewClient()
            settings.javaScriptEnabled = true
            loadUrl(url)
        }
    }
}