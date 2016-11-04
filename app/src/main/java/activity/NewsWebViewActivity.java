package activity;


import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import cn.nicolite.palm300heros.R;
import utilty.LogUtil;

/**
 * Created by NICOLITE on 2016/10/15 0015.
 */

public class NewsWebViewActivity extends AppCompatActivity {
    private WebView webView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //透明导航栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        //透明ActionBar
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
        //actionBar.setTitle("资讯详情");
        setContentView(R.layout.webview);

        webView = (WebView) findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new WebChromeClient());
        webView.loadDataWithBaseURL(null, getHtmlDate(), "text/html", "utf-8", null);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    private String getHtmlDate() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String content = bundle.getString("content");
        LogUtil.d("NewsWebViewActivity", content);
        String head = "<head><style>img{max-width: 100%; width: auto; height: auto; }</style></head>";
        String html = "<html>" + head + "<body>" + content + "</body>" + "</html>";
        return html;
    }
}
