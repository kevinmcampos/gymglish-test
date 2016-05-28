package br.com.memorify.gymglishtest;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import br.com.memorify.gymglishtest.model.Website;

public class WebViewActivity extends AppCompatActivity {

    private static final String WEBSITE_TITLE_KEY = "WEBSITE_TITLE_KEY";
    private static final String WEBSITE_URL_KEY = "WEBSITE_URL_KEY";

    private View mLoadingView;
    private Toolbar mToolbar;
    private View mDismissButton;
    private TextView mTitleView;
    private TextView mUrlView;
    private WebView mWebView;

    private String mTitle;
    private String mInitialUrl;

    public static void startWebview(Activity activity, @NonNull Website website) {
        Intent intent = new Intent(activity, WebViewActivity.class);
        intent.putExtra(WEBSITE_TITLE_KEY, website.getTitle());
        intent.putExtra(WEBSITE_URL_KEY, website.getUrl());
        activity.startActivity(intent);
    }

    private final WebViewClient webViewClient = new WebViewClient() {

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            mLoadingView.setVisibility(View.GONE);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            mLoadingView.setVisibility(View.VISIBLE);
        }

        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            mUrlView.setText(url);
            return true;
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        retrieveData();
        bindViews();
        setupViews();

    }

    private void retrieveData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mTitle = bundle.getString(WEBSITE_TITLE_KEY);
            mInitialUrl = bundle.getString(WEBSITE_URL_KEY);
        }
    }

    private void bindViews() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mDismissButton = findViewById(R.id.dismiss_button);
        mTitleView = (TextView) findViewById(R.id.webview_title);
        mUrlView = (TextView) findViewById(R.id.webview_url);
        mWebView = (WebView) findViewById(R.id.webview);
        mLoadingView = findViewById(R.id.webview_progress);
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void setupViews() {
        setSupportActionBar(mToolbar);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
        }

        mDismissButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mTitleView.setText(mTitle);
        mUrlView.setText(mInitialUrl);

        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.loadUrl(mInitialUrl);
        mWebView.setWebViewClient(webViewClient);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_webview, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.share:
                // TODO: Implement share action
            case R.id.copy_clipboard:
                // TODO: Implement copy to clipboard
        }
        return super.onOptionsItemSelected(item);
    }
}
