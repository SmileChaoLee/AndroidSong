package com.smile.androidsong;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.smile.model.Computer;

public class OrderOneSong extends AppCompatActivity {

    private static final String TAG = "OrderOneSong";
    private final Computer computerData = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_one_song);

        Bundle extras = getIntent().getExtras();
        String song_no, song_na, sing_na, lang_na;
        if (extras == null) {
            return;
        } else {
            song_no = extras.getString("song_no").trim();
            song_na = extras.getString("song_na").trim();
            sing_na  = extras.getString("sing_na").trim();
            lang_na = extras.getString("lang_na").trim();
        }

        if (song_no.equals("")) {
            return;
        }

        TextView songNameView = findViewById(R.id.songName);
        songNameView.setText(song_na);
        TextView songNoView = findViewById(R.id.songNo);
        songNoView.setText(song_no);
        TextView singerNameView = findViewById(R.id.singerName);
        singerNameView.setText(sing_na);
        TextView languageNameView = findViewById(R.id.languageName);
        languageNameView.setText(lang_na);

        Button SubmitButton = findViewById(R.id.SubmitButton);
        SubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "SubmitButton.onClick() = " + computerData.getComputer_id());
                final EditText editText = findViewById(R.id.inputComputerId);
                String computerID = editText.getText().toString().trim();
                if (computerID.length() >= 5) {
                    Log.d(TAG, "computerData.getComputer_id() = " + computerData.getComputer_id());
                    Log.d(TAG, "computerData.getBranch_id() = " + computerData.getBranch_id());
                    Log.d(TAG, "computerData.getRoom_no() = " + computerData.getRoom_no());
                    Log.d(TAG, "computerData.getSong_no() = " + computerData.getSong_no());
                }
                finish();
            }
        });

        Button CancelButton = findViewById(R.id.CancelButton);
        CancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
