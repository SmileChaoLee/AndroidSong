package com.smile.androidsong;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.smile.model.Computer;

import java.util.List;


public class OrderOneSong extends AppCompatActivity {

    private Computer computerData=null;
    private List<Computer> computers=null;

    private String song_no = new String("");
    private String song_na = new String("");
    private String sing_na  = new String("");
    private String lang_na  = new String("");

    private String queryCondition = new String("");
    private String message = new String("");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_one_song);

        /*
        computerTable = new ComputerTable();
        if (computerTable.getConnectionYN() == -1) {
            message = "Failed to connect to computer table with JDBC !!";
            return;
        }
        */

        Bundle extras = getIntent().getExtras();
        if (extras==null) {
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

        TextView songNameView = (TextView) findViewById(R.id.songName);
        songNameView.setText(song_na);
        TextView songNoView = (TextView) findViewById(R.id.songNo);
        songNoView.setText(song_no);
        TextView singerNameView = (TextView) findViewById(R.id.singerName);
        singerNameView.setText(sing_na);
        TextView languageNameView = (TextView) findViewById(R.id.languageName);
        languageNameView.setText(lang_na);

        Button SubmitButton = (Button) findViewById(R.id.SubmitButton);
        SubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText editText = (EditText) findViewById(R.id.inputComputerId);
                String computerID = editText.getText().toString().trim();
                if (computerID.length()>=5) {
                    computerID = computerID.substring(0, Math.min(5, computerID.length()));
                    // System.out.println("computer id="+computerID);
                    // computerTable.updateSongNoOfComputerTable(computerID, song_no);
                    // computerData = computerTable.getCurrentRecord();
                    System.out.println(computerData.getComputer_id());
                    System.out.println(computerData.getBranch_id());
                    System.out.println(computerData.getRoom_no());
                    System.out.println(computerData.getSong_no());
                }
                finish();
            }
        });

        Button CancelButton = (Button) findViewById(R.id.CancelButton);
        CancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
