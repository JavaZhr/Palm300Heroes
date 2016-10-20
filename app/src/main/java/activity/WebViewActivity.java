package activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import cn.nicolite.palm300heros.R;
import utilty.LogUtil;

/**
 * Created by NICOLITE on 2016/10/15 0015.
 */

public class WebViewActivity extends AppCompatActivity {
    private WebView webView;
    private TextView title;
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
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle("");
        setContentView(R.layout.webview);
        title =(TextView) findViewById(R.id.webview_title);
        webView = (WebView) findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        title.setText("资讯详情");
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
        LogUtil.d("WebViewActivity", content);
        String head = "<head><style>img{max-width: 100%; width: auto; height: auto; }</style></head>";
        String html = "<html>" + head + "<body>" + content + "</body>" + "</html>";
        return html;
    }
}
