package com.example.andoridconcepts

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doBeforeTextChanged
import androidx.core.widget.doOnTextChanged

class Homework_2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homework2)

        val webview = findViewById<WebView>(R.id.webview)

        // WebView 설정
        webview.settings.javaScriptEnabled = true // JavaScript 활성화
        webview.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                // false 반환 시 호스트 앱이 URL을 처리함 (WebView에서 로드)
                return false
            }
        }

        /// 이전 버전
//        webview.setWebViewClient(object : WebViewClient() {
//            override fun shouldOverrideUrlLoading(
//                view: WebView?, request: WebResourceRequest?
//            ): Boolean {
//                // true -> 주도권을 가져오지 않는다 (우리앱이)
//                // flase -> 주도권을 가져오겠다 (우리앱)
//                return false
//            }
//
//        })

        // Intent에서 URL 로드 (있는 경우)
        try {
            val intentUrl = intent.data?.toString()
            if (!intentUrl.isNullOrEmpty()) {
                Log.d("testt", "Intent에서 URL 로드: $intentUrl")
                webview.loadUrl(intentUrl)
            }
        } catch (exception: Exception) {
            Log.e("testt", "Intent에서 URL 로드 중 오류 발생", exception)
        }

        val urlPrefix = "http://"
        var finalUrl = ""

        val address = findViewById<EditText>(R.id.address)

        // TextWatcher를 익명 객체로 사용
        address.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                Log.d("testt", "텍스트 변경 전: $s")
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                Log.d("testt", "텍스트 변경 중: $s")
            }

            override fun afterTextChanged(s: Editable?) {
                finalUrl = urlPrefix + s.toString()
                Log.d("testt", "텍스트 변경 후: $s")
            }
        })

        // TextWatcher를 람다 함수로 사용
        address.doBeforeTextChanged { text, start, count, after -> }
        address.doOnTextChanged { text, start, before, count -> }
        address.doAfterTextChanged { }

        val open = findViewById<TextView>(R.id.open)
        open.setOnClickListener {
            var url = address.text.toString()
            // URL에 http:// 또는 https://가 없으면 http://를 추가
            if (!url.startsWith("http://") && !url.startsWith("https://")) {
                url = "http://$url"
            }
            // WebView에서 URL 로드
            webview.loadUrl(url)
        }
    }
}