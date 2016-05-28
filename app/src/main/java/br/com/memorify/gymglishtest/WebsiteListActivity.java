package br.com.memorify.gymglishtest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.com.memorify.gymglishtest.adapter.WebsiteListAdapter;
import br.com.memorify.gymglishtest.model.Website;

public class WebsiteListActivity extends AppCompatActivity {

    private static final String TAG = "WebsiteListActivity";
    private static final List<Website> WEBSITES;
    static {
        ArrayList<Website> websites = new ArrayList<>();
        websites.add(new Website("Gymglish", "https://www.gymglish.com/", "https://www.gymglish.com/images/gymglish-logo-248x60.png"));
        websites.add(new Website("Frantastique", "https://www.frantastique.com/", "https://www.frantastique.com/images/frantastique-logo-306x60.png"));
        websites.add(new Website("Rich Morning", "https://www.richmorning.com/", "https://www.richmorning.com/images/rms-logo-rich-morning-312x60.png"));
        websites.add(new Website("vatefaireconjuguer", "http://www.vatefaireconjuguer.com/", "http://www.vatefaireconjuguer.com/images/logo-vatefaireconjuguer.png"));
        websites.add(new Website("anglaisconjugaison", "http://www.anglais-conjugaison.com/", "http://static.anglais-conjugaison.com/img/logo.png"));
        websites.add(new Website("The Word of the Month", "http://www.thewordofthemonth.com/", "http://cdn8.staztic.com/app/i/2595/2595188/the-word-of-the-month-a-monthly-look-at-a-word-in-the-headlines-1-15801-l-140x140.png"));
        websites.add(new Website("CPF anglais avec", "http://www.anglais-cpf.fr/", "http://www.anglais-cpf.fr/documents/gymglish_diploma.png"));
        websites.add(new Website("comment utiliser son CPF", "http://www.comment-utiliser-son-cpf.fr/", "http://www.comment-utiliser-son-cpf.fr/static/images/logo.png"));
        websites.add(new Website("Delavigne Corp", "http://www.delavignecorp.com/", "http://www.delavignecorp.com/static/images/logo-delavignecorp.jpg"));
        websites.add(new Website("Coworking & Office Space", "https://worksweetwork.com/", "https://worksweetworkdotcom.files.wordpress.com/2015/01/befunky_img_81232.jpg"));
        WEBSITES = Collections.unmodifiableList(websites);
    }

    private RecyclerView websiteListView;
    private WebsiteListAdapter websiteListAdapter;

    public static void startWebsiteList(Activity activity) {
        activity.startActivity(new Intent(activity, WebsiteListActivity.class));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_websitelist);
        bindViews();
        setupViews();
    }

    private void bindViews() {
        websiteListView = (RecyclerView) findViewById(R.id.website_list);
    }

    private void setupViews() {
        websiteListAdapter = new WebsiteListAdapter(getBaseContext(), WEBSITES);
        websiteListAdapter.setWebsiteClickListener(new WebsiteListAdapter.WebsiteClickListener() {
            @Override
            public void onWebsiteClick(View view, Website website) {
                WebViewActivity.startWebview(WebsiteListActivity.this, website);
            }
        });
        websiteListView.setAdapter(websiteListAdapter);
        websiteListView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        websiteListView.setHasFixedSize(true);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_websitelist, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                LoginActivity.startLogin(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
        Toast.makeText(getBaseContext(), "NOTE: user need to explicit logout to back to login. Menu > Logout", Toast.LENGTH_LONG).show();
    }
}
