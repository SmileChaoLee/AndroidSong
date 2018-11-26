package com.smile.androidsong;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.smile.model.Language;

import java.util.List;

public class LanguagesListActivity extends Activity {

    private Language langudata = null;
    // private LanguTable langutable = null;
    private List<Language> langus = null;
    private String queryCondition = new String("");
    private String message = new String("");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_languages);

        TextView textview = (TextView) findViewById(R.id.languMenuTextView);

        String option = new String("");
        option = getIntent().getStringExtra("option").toString().trim();
        // OR
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            return;
        }
        option = extras.getString("option").trim();

        // Buttons added

        if (option.equals("2")) {
            // new song
        }
    }
}
