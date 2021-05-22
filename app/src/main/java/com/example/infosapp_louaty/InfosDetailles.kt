package com.example.infosapp_louaty

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.infosapp_louaty.R
import dmax.dialog.SpotsDialog
import kotlinx.android.synthetic.main.infos_detailles.*


class InfosDetailles : AppCompatActivity() {

    lateinit var alertDialog:AlertDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.infos_detailles)

        alertDialog = SpotsDialog(this)
        alertDialog.show()

        webView.settings.javaScriptEnabled = true
        webView.webChromeClient= WebChromeClient()
        webView.webViewClient = object :WebViewClient(){
            override fun onPageFinished(view: WebView?, url: String?) {
                alertDialog.dismiss()
            }
        }

        if(intent != null)
            if(!intent.getStringExtra("webURL")!!.isEmpty())
                webView.loadUrl(intent.getStringExtra("webURL")!!)


    }
}