package com.smile.androidsong;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.smile.smilelibraries.utilities.ScreenUtil;

public class MyActivity extends AppCompatActivity {

    private static final String TAG = "MyActivity";
    private float textFontSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        float defaultTextFontSize = ScreenUtil.getDefaultTextSizeFromTheme(this, AndroidSongApp.FontSize_Scale_Type, null);
        textFontSize = ScreenUtil.suitableFontSize(this, defaultTextFontSize, AndroidSongApp.FontSize_Scale_Type, 0.0f);

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_my);

        // getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        final TextView mainMenuTextView = findViewById(R.id.mainMenuTextView);
        ScreenUtil.resizeTextSize(mainMenuTextView, textFontSize, AndroidSongApp.FontSize_Scale_Type);

        final Button singerOrderButton = findViewById(R.id.singerOrderButton);
        ScreenUtil.resizeTextSize(singerOrderButton, textFontSize, AndroidSongApp.FontSize_Scale_Type);
        singerOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent singerTypesIntent = new Intent(MyActivity.this, SingerTypesListActivity.class);
                startActivity(singerTypesIntent);
            }
        });

        final Button newSongOrderButton = findViewById(R.id.newSongOrderButton);
        ScreenUtil.resizeTextSize(newSongOrderButton, textFontSize, AndroidSongApp.FontSize_Scale_Type);
        newSongOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent languagesIntent = new Intent(MyActivity.this, LanguagesListActivity.class);
                languagesIntent.putExtra("OrderedFrom", AndroidSongApp.NewSongOrdered);
                startActivity(languagesIntent);
            }
        });

        final Button hotSongOrderButton = findViewById(R.id.hotSongOrderButton);
        ScreenUtil.resizeTextSize(hotSongOrderButton, textFontSize, AndroidSongApp.FontSize_Scale_Type);
        hotSongOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent languagesIntent = new Intent(MyActivity.this, LanguagesListActivity.class);
                languagesIntent.putExtra("OrderedFrom", AndroidSongApp.HotSongOrdered);
                startActivity(languagesIntent);
            }
        });

        final Button songLanguageOrderButton = findViewById(R.id.songLanguageOrderButton);
        ScreenUtil.resizeTextSize(songLanguageOrderButton, textFontSize, AndroidSongApp.FontSize_Scale_Type);
        songLanguageOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_LanguageOrder = new Intent(MyActivity.this , LanguagesListActivity.class);
                startActivity(intent_LanguageOrder);
            }
        });

        final Button exitProgramButton = findViewById(R.id.exitProgramButton);
        ScreenUtil.resizeTextSize(exitProgramButton, textFontSize, AndroidSongApp.FontSize_Scale_Type);
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
