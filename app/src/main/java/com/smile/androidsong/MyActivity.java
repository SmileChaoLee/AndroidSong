package com.smile.androidsong;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.smile.Utility.FontSizeAndTheme;

public class MyActivity extends AppCompatActivity {

    private static final String TAG = new String("MyActivity");
    private float textFontSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        textFontSize = FontSizeAndTheme.GetTextFontSizeAndSetTheme(this);

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_my);

        // getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        final Button singerOrderButton = findViewById(R.id.singerOrderButton);
        singerOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent singerTypesIntent = new Intent(MyActivity.this, SingerTypesListActivity.class);
                startActivity(singerTypesIntent);
            }
        });

        final Button newSongOrderButton = findViewById(R.id.newSongOrderButton);
        newSongOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent languagesIntent = new Intent(MyActivity.this, LanguagesListActivity.class);
                languagesIntent.putExtra("OrderedFrom", AndroidSongApp.NewSongOrdered);
                startActivity(languagesIntent);
            }
        });

        final Button hotSongOrderButton = findViewById(R.id.hotSongOrderButton);
        hotSongOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent languagesIntent = new Intent(MyActivity.this, LanguagesListActivity.class);
                languagesIntent.putExtra("OrderedFrom", AndroidSongApp.HotSongOrdered);
                startActivity(languagesIntent);
            }
        });

        final Button songLanguageOrderButton = findViewById(R.id.songLanguageOrderButton);
        songLanguageOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_LanguageOrder = new Intent(MyActivity.this , LanguagesListActivity.class);
                startActivity(intent_LanguageOrder);
            }
        });

        final Button exitProgramButton = findViewById(R.id.exitProgramButton);
        exitProgramButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quitApplication();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        quitApplication();
    }

    private void quitApplication() {
        final Handler handlerClose = new Handler();
        final int timeDelay = 200;
        handlerClose.postDelayed(new Runnable() {
            public void run() {
                // quit game
                finish();
            }
        },timeDelay);
    }
}
