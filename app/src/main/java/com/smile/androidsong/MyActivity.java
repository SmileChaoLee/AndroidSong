package com.smile.androidsong;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MyActivity extends AppCompatActivity {

    private final String TAG = "MyActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_my);

        Button singerOrderButton = (Button) findViewById(R.id.singerOrderButton);
        singerOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_SingerTypeOrder = new Intent(MyActivity.this , SingerTypesListActivity.class);
                startActivity(intent_SingerTypeOrder);
            }
        });

        Button newSongOrderButton = (Button) findViewById(R.id.newSongOrderButton);
        newSongOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        Button hotSongOrderButton = (Button) findViewById(R.id.hotSongOrderButton);
        newSongOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        Button songLanguageOrderButton = (Button) findViewById(R.id.songLanguageOrderButton);
        songLanguageOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_SongLanguageOrder = new Intent(MyActivity.this , LanguagesListActivity.class);
                startActivity(intent_SongLanguageOrder);
            }
        });

        Button songNameOrderButton = (Button) findViewById(R.id.songNameOrderButton);
        songNameOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent SongNameOrder = new Intent(MyActivity.this,SongsListActivity.class);
                startActivity(SongNameOrder);
            }
        });

        Button songNoOrderButton = (Button) findViewById(R.id.songNoOrderButton);
        songNoOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_SongNoOrder = new Intent(MyActivity.this,SongsListActivity.class);
                startActivity(intent_SongNoOrder);
            }
        });

        Button exitProgramButton = (Button) findViewById(R.id.exitProgramButton);
        exitProgramButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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

    public void onDestroy() {
        super.onDestroy();
    }
}
