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

        /*
        // the following is very important for JDBC connector
        langutable = new LanguTable();
        if (langutable.getConnectionYN() == -1) {
            message = "Failed to connect to langu table with JDBC !!";
            textview.setText(message);
            return;
        }

        textview.setText("\0");
        // add other areas' singers' Button
        queryCondition = "order by lang_no";
        langutable.setQueryCondition(queryCondition);
        langus = langutable.getNextPageOfLanguTable();
        */

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
                , ViewGroup.LayoutParams.WRAP_CONTENT);
        // lp.weight = 1f;
        int dfBGR = android.R.drawable.btn_default;
        LinearLayout lLayout = (LinearLayout) findViewById(R.id.languButtons);
        String lang_no=new String(""),lang_na=new String(""),lang_en=new String("");
        for (int i=0 ; i<langus.size() ; i++) {
            lang_no = langus.get(i).getLang_no();
            lang_na = langus.get(i).getLang_na()+" 新歌";
            lang_en = "New "+langus.get(i).getLang_en();

            Button btn = new Button(this);
            btn.setId(i);
            btn.setText(lang_na+"\n"+lang_en);
            // btn.setBackground(dfBG);    // API Level must be 16 or over
            // btn.setTextColor(0xff501fff);
            btn.setTextColor(0xff501fff);
            btn.setTextScaleX(1.0f);
            btn.setTextSize(18.0f);  // because double lines
            btn.setTypeface(null, Typeface.BOLD);
            btn.setBackgroundResource(dfBGR);

            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent_Song2Query = new Intent(LanguagesListActivity.this , SongsListActivity.class);
                    intent_Song2Query.putExtra("option","NewSong");
                    intent_Song2Query.putExtra("suboption",langus.get(v.getId()).getLang_no());
                    // OR
                    Bundle extras = new Bundle();
                    extras.putString("option","NewSong");
                    extras.putString("suboption",langus.get(v.getId()).getLang_no());
                    intent_Song2Query.putExtras(extras);

                    startActivity(intent_Song2Query);
                }
            });

            lLayout.addView(btn, lp);
        }


        Button ReturnToPreviousButton = new Button(this);
        ReturnToPreviousButton.setText("回上一個功能\nReturn To Previous Function");
        // ReturnToPreviousButton.setBackground(dfBG);    // API Level must be 16 or over
        ReturnToPreviousButton.setTextColor(0xff000000);
        ReturnToPreviousButton.setTextScaleX(1.0f);
        ReturnToPreviousButton.setTextSize(18.0f);  // because double lines
        ReturnToPreviousButton.setTypeface(null, Typeface.BOLD);
        lLayout.addView(ReturnToPreviousButton,lp);
        ReturnToPreviousButton.setBackgroundResource(dfBGR);

        ReturnToPreviousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Buttons added

        if (option.equals("2")) {
            // new song
        }
    }
}
