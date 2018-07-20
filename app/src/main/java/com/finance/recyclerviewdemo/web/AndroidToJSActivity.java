package com.finance.recyclerviewdemo.web;

import android.content.DialogInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import com.finance.recyclerviewdemo.R;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Jackie on 2018/7/19.
 */
public class AndroidToJSActivity extends AppCompatActivity {
    private WebView webView;
    private Button button;
    private static final String TAG = "AndroidToJSActivity";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_js);
        webView = findViewById(R.id.webview);
        button = findViewById(R.id.btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                AndroidToJSActivity.this.runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        //FIXME:第一种Android调用JS方法
//                        webView.loadUrl("javascript:callJS()");
//                        Toast.makeText(AndroidToJSActivity.this,"调用JS的方法",Toast.LENGTH_SHORT).show();
//                    }
//                });

                AndroidToJSActivity.this.runOnUiThread(new Runnable() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void run() {
                        //FIXME:第二种Android调用JS方法
                        webView.evaluateJavascript("javascript:callJS()", new ValueCallback<String>() {
                            @Override
                            public void onReceiveValue(String value) {
                                Toast.makeText(AndroidToJSActivity.this
                                        ,"JS返回的结果"+value,Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

            }
        });
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
//        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setDatabaseEnabled(true);

        webView.addJavascriptInterface(new DemoJavaScriptInterface(),"andrroid");
        webView.loadUrl("file:///android_asset/index.html");

        webView.setWebChromeClient(new WebChromeClient(){
            //FIXME-----------JS通过webView调用Android方法---第三种方法---------------------
            @Override
            public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
//                Log.i(TAG, "onJsAlert: "+message);
//                AlertDialog.Builder builder = new AlertDialog.Builder(AndroidToJSActivity.this);
//                builder.setTitle("Alert");
//                builder.setMessage(message);
//                builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        result.confirm();
//                    }
//                });
//                builder.setCancelable(false);
//                builder.create().show();
//                return true;
                return super.onJsAlert(view,url,message,result);
            }

            @Override
            public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
                return super.onJsConfirm(view, url, message, result);
            }

            @Override
            public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
                Uri uri = Uri.parse(message);
                if (uri.getScheme().equals("js")){
                    if (uri.getAuthority().equals("webview")){
//                        Toast.makeText(AndroidToJSActivity.this
//                                ,"调用JS调用安卓第三种方式",Toast.LENGTH_SHORT).show();
                        HashMap<String,String> params = new HashMap<>();
                        String arg1 = uri.getQueryParameter("arg1");
                        Log.i(TAG, "shouldOverrideUrlLoading: --3--"+arg1);
                        result.confirm("JS调用Android方法成功");
//                        HashSet<String> collection = (HashSet<String>) uri.getQueryParameterNames();
//                        for (int i = 0;i<collection.size();i++){
//                            Log.i(TAG, "shouldOverrideUrlLoading: --------"+collection.);
//                        }
                    }
                    return true;
                }



                return super.onJsPrompt(view, url, message, defaultValue, result);
            }
        });
        webView.setWebViewClient(new WebViewClient(){
            //FIXME-----------JS通过webView调用Android方法---第二种方法---------------------
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //根据协议的参数，判断是否是需要的url
                Uri uri = Uri.parse(url);
                if (uri.getScheme().equals("js")){
                    if (uri.getAuthority().equals("webview")){
                        Toast.makeText(AndroidToJSActivity.this
                                ,"调用JS调用安卓第二种方式",Toast.LENGTH_SHORT).show();
                        HashMap<String,String> params = new HashMap<>();
                        String arg1 = uri.getQueryParameter("arg1");
                        Log.i(TAG, "shouldOverrideUrlLoading: ----"+arg1);
//                        HashSet<String> collection = (HashSet<String>) uri.getQueryParameterNames();
//                        for (int i = 0;i<collection.size();i++){
//                            Log.i(TAG, "shouldOverrideUrlLoading: --------"+collection.);
//                        }
                    }
                }



                return super.shouldOverrideUrlLoading(view, url);
            }
        });
    }
    //FIXME-----------JS通过webView调用Android方法---第一种方法---------------------
    class DemoJavaScriptInterface{

        DemoJavaScriptInterface(){}
        @JavascriptInterface
        public void clickOnAndroid(){
//            Toast.makeText(AndroidToJSActivity.this,"调用安卓的方法",Toast.LENGTH_SHORT).show();
            AndroidToJSActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    webView.loadUrl("javascript:callJS()");
                    Toast.makeText(AndroidToJSActivity.this,"调用安卓方法，安卓再调用JS的方法",Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
















}
