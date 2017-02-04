package activity;


import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.pgyersdk.crash.PgyCrashManager;

import cn.nicolite.palm300heroes.R;
import utilty.LogUtil;

/**
 * Created by NICOLITE on 2016/10/15 0015.
 */

public class NewsWebViewActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{
    private WebView webView;
    private ProgressBar progressBar;
    private SwipeRefreshLayout swipeRefreshLayout;
    private static final int REFRESH_COMPLETE_TIME = 2000;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0 :
                    swipeRefreshLayout.setRefreshing(false);
                    webView.loadDataWithBaseURL(null, getHtmlDate(), "text/html", "utf-8", null);
                    break;
                default:break;
            }
        }
    };
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
        setContentView(R.layout.news_webview);

        //蒲公英Crash捕获注册
        PgyCrashManager.register(this);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.news_webview_swipe_refresh_layout);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.orange));
        swipeRefreshLayout.setOnRefreshListener(this);

        progressBar = (ProgressBar) findViewById(R.id.news_webview_progressbar);

        webView = (WebView) findViewById(R.id.webview);
        //webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    progressBar.setVisibility(View.GONE);
                }else {
                    if (View.GONE == progressBar.getVisibility()) {
                        progressBar.setVisibility(View.VISIBLE);
                    }
                    progressBar.setProgress(newProgress);
                }
                super.onProgressChanged(view, newProgress);
            }
        });
        webView.loadDataWithBaseURL(null, getHtmlDate(), "text/html", "utf-8", null);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    private String getHtmlDate() {
        Intent intent = getIntent();
        String content = intent.getStringExtra("content");
        LogUtil.d("NewsWebViewActivity", content);
        String head = "<head><style>img{max-width: 100%; width: auto; height: auto; }</style></head>";

        return "<html>" + head + "<body>" + content + "</body>" + "</html>";
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        handler.sendEmptyMessageDelayed(0, REFRESH_COMPLETE_TIME);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //解除蒲公英Crash捕获注册
        PgyCrashManager.unregister();
    }
}
