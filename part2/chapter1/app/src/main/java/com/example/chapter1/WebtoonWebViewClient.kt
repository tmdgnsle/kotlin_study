package com.example.chapter1

import android.graphics.Bitmap
import android.view.View
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import androidx.core.view.isVisible

class WebtoonWebViewClient(
    private val progressBar: ProgressBar,
    private val saveData: (String) -> Unit
) : WebViewClient() {


    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
        //
        if(request != null && request.url.toString().contains("comic.naver.com/webtoom/detail")){
            saveData(request.url.toString())
        }
        //
        return super.shouldOverrideUrlLoading(view, request)
    }

    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)

        progressBar.visibility = View.GONE
//        progressBar.isVisible = false
    }

    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        super.onPageStarted(view, url, favicon)

        progressBar.visibility = View.VISIBLE
//        progressBar.isVisible = true
    }


}