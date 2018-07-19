package com.finance.recyclerviewdemo.web;

import android.content.Intent;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;

import com.finance.recyclerviewdemo.R;
import com.finance.recyclerviewdemo.config.BaseApplication;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Jackie on 2018/7/19.
 */
public class WebViewActivity extends AppCompatActivity{
    private WebView webView;
    private LinearLayout linearLayout;
    private static final String TAG = "WebViewActivity";
    private Button btn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        btn = findViewById(R.id.btn);
//        webView = findViewById(R.id.webview);
        linearLayout = findViewById(R.id.linear);
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                LinearLayout.LayoutParams params = new LinearLayout
//                        .LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
//                webView = BaseApplication.getBaseApplication().getWebView();
//                webView.setLayoutParams(params);
//                linearLayout.addView(webView);
//            }
//        });
        initWeb();





    }
    public void initWeb(){
        LinearLayout.LayoutParams params = new LinearLayout
                .LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        webView = new WebView(this);
        webView.setLayoutParams(params);
        linearLayout.addView(webView);
        webView.loadUrl("https://toutiao.china.com/");  //https://toutiao.china.com/
        //webview设置
        WebSettings webSettings = webView.getSettings();
        webSettings.setSupportZoom(true);
        webSettings.setJavaScriptEnabled(true);
//        webSettings.setBlockNetworkImage(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW); }
//        webView.loadUrl("http://www.sina.com.cn/");
        //webviewClient处理各种通知&请求事件
        webView.setWebViewClient(new WebViewClient(){

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if(url == null) return false;

                try {
                    if (url.startsWith("http:") || url.startsWith("https:"))
                    {
                        view.loadUrl(url);
                        return true;
                    }
                    else //之所以使用这种方式是，有些url的开头不是http,https所以采用这种方式加载
                    {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        startActivity(intent);
                        return true;
                    }
                } catch (Exception e) { //防止crash (如果手机上没有安装处理某个scheme开头的url的APP, 会导致crash)
                    return false;
                }
            }

//            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//            @Nullable
//            @Override
//            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
////                https://k.sinaimg.cn/n/news/transform/310/w710h400/20180719/Z93Y-fzrwiaz9051376.jpg/w710h400z1l50t1e28.jpg?by=comos
//                Log.i(TAG, "shouldInterceptRequest: ----"+request.getUrl().toString());
//                if (request.getUrl().toString().contains("Z93Y-fzrwiaz9051376.jpg")){
//                    InputStream inputStream = null;
//                    try {
//                        inputStream = getApplicationContext().getAssets().open("ic_launcher.png");
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    WebResourceResponse resourceResponse = new WebResourceResponse("ic_laucher.png","utf-8",inputStream);
//                    Log.i(TAG, "shouldInterceptRequest: ----1-----");
//                    return resourceResponse;
//                }
//
//
//
//                return super.shouldInterceptRequest(view, request);
//            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }

            @Override
            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);
                Log.i(TAG, "onLoadResource: ---图片资源-----"+url);
            }
        });
        //webChromeClient类 辅助webview处理js对话框,网站图标，网站标题
        webView.setWebChromeClient(new WebChromeClient(){


        });

    }


    @Override
    protected void onDestroy() {
        if (webView != null){
            webView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            webView.clearHistory();
            ((ViewGroup)webView.getParent()).removeView(webView);
            webView.destroy();
            webView = null;
        }
        super.onDestroy();
    }
}
