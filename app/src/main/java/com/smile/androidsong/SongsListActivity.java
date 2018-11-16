package com.smile.androidsong;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.smile.model.*;

import java.util.List;

public class SongsListActivity extends ListActivity {

    private Song song2data = null;
    private List<Song> songs = null;
    private String queryCondition = new String("");
    private String message = new String("");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_songs_list);

        String option = new String("");
        String suboption = new String("");

        TextView textview = (TextView) findViewById(R.id.songListTextView);

        /*
        // the following is very important for JDBC connector
        song2table = new Song2Table();
        if (song2table.getConnectionYN() == -1) {
            message = "Failed to connect to song2 table with JDBC !!";
            setListAdapter(new mListAdapter(new String[]{message}, new String[]{message}));
            return;
        }
        */

        option = getIntent().getStringExtra("option").toString().trim();
        // OR
        Bundle extras = getIntent().getExtras();
        if (extras!=null)
        {
            option = extras.getString("option").trim();
        }

        // queryCondition = " order by song_na";      // default order
        queryCondition = "order by song_na";
        if (option.equals("5")) {
            queryCondition = "order by song_na";
            textview.setText("歌名排序  Ordered By Song Title");
        } else if (option.equals("6")) {
            queryCondition = "order by song_no";
            textview.setText("歌號排序  Ordered By Song No.");
        } else if (option.equals("NewSong")) {
            suboption = extras.getString("suboption").trim();
            queryCondition = " where song2.lang_no=" +"'" + suboption+"' ";
            queryCondition = queryCondition + " order by in_date DESC , song_no DESC ";
            // song2table.setMaxPageOfQuery(false,5);  // has maxpage which is 5 pages
        } else {
            message = "No such function !!";
            setListAdapter(new mListAdapter(new String[]{message}, new String[]{message}));
            return;
        }

        // song2table.setQueryCondition(queryCondition);
        NextPageOfSong2Table();

        Button FirstPageButton = (Button) findViewById(R.id.FirstPageButton);
        FirstPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirstPageOfSong2Table();
            }
        });

        Button PrevPageButton = (Button) findViewById(R.id.PrevPageButton);
        PrevPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrevPageOfSong2Table();
            }
        });

        Button NextPageButton = (Button) findViewById(R.id.NextPageButton);
        NextPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NextPageOfSong2Table();
            }
        });

        Button LastPageButton = (Button) findViewById(R.id.LastPageButton);
        LastPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LastPageOfSong2Table();
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

    private void FirstPageOfSong2Table() {
        // songs = song2table.getFirstPageOfSong2Table();
        displayListViewOfSong2Table();
    }

    private void PrevPageOfSong2Table() {
        // songs = song2table.getPreviousPageOfSong2Table();
        displayListViewOfSong2Table();
    }

    private void NextPageOfSong2Table() {
        // songs = song2table.getNextPageOfSong2Table();
        displayListViewOfSong2Table();
    }

    private void LastPageOfSong2Table() {
        // songs = song2table.getLastPageOfSong2Table();
        displayListViewOfSong2Table();
    }

    private void displayListViewOfSong2Table() {

        String queryResult1[] , queryResult2[];

        queryResult1 = new String[songs.size()];
        queryResult2 = new String[songs.size()];

        int numChars = 0;
        int numOfBytes = 0;
        int numOfChineseWords = 0;
        int numOfAlphabet = 0;
        int requiredChars = 24;  // 12 chinese words, width of a chinese word is double of alphabet
        String space20 = new String(new char[requiredChars]).replace('\0', ' ');
        String ss = new String("");
        String s1 = new String("");
        String s2 = new String("");
        for (int i = 0; i < songs.size(); i++) {
            numOfChineseWords = 0;
            numOfAlphabet = 0;
            numChars = 0;
            s2 = "";
            ss = songs.get(i).getSong_na().trim();
            // ss = "ABCDEFGHIJKLMNO";
            for (int j = 0; ((j < ss.length()) && (numChars < requiredChars)); j++) {
                // s1 = ss.substring(j,j+1);
                s1 = String.valueOf(ss.charAt(j));
                s2 = s2 + s1;
                numOfBytes = s1.getBytes().length;
                if (numOfBytes == 3) {
                    // each chinese word has 3 bytes in UTF-8 encoding system
                    numOfChineseWords++;
                } else {
                    // english character ( alphabet )
                    numOfAlphabet++;
                }
                numChars = numOfAlphabet + numOfChineseWords * 2;
            }
            // this.queryResult1[i] = String.format("%-"+requiredChars+"s",this.queryResult1[i])
            queryResult1[i] = s2 + space20.substring(0, Math.max(0, requiredChars - numChars))
                    +" "+songs.get(i).getLang_na();
            queryResult2[i] = songs.get(i).getSong_no()+" "+songs.get(i).getSing_na1()+" "+songs.get(i).getSing_na2();
        }
        setListAdapter(new mListAdapter(queryResult1, queryResult2));

        queryResult1 = null;
        queryResult2 = null;
    }

    private class mListAdapter extends BaseAdapter {

        private String text1[] , text2[];  // or private String[] text1,text2;

        public mListAdapter() {
            this.text1 = new String[] {"No initialization"};
            this.text2 = new String[] {"No initialization"};
        }

        public mListAdapter(String[] text1, String[] text2) {
            this.text1 = text1;
            this.text2 = text2;
        }

        @Override
        public int getCount() {
            return this.text1.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup container) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.songs_list_item, container, false);
            }

            TextView vText1, vText2;
            vText1 = (TextView) convertView.findViewById(R.id.text1);
            vText1.setText(this.text1[position]);
            vText2 = (TextView) convertView.findViewById(R.id.text2);
            vText2.setText(this.text2[position]);

            // Because the list item contains multiple touch targets, you should not override
            // onListItemClick. Instead, set a click listener for each target individually.

            convertView.findViewById(R.id.primary_target).setOnClickListener(

                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // Toast.makeText(Song2Query.this,text1[position],Toast.LENGTH_SHORT).show();
                            Intent OrderOneSong = new Intent(SongsListActivity.this,OrderOneSong.class);

                            Bundle extras = new Bundle();
                            extras.putString("song_no",songs.get(position).getSong_no());
                            extras.putString("song_na",songs.get(position).getSong_na());
                            extras.putString("sing_na",songs.get(position).getSing_na1()+" "+songs.get(position).getSing_na2());
                            extras.putString("lang_na",songs.get(position).getLang_na());

                            OrderOneSong.putExtras(extras);
                            startActivity(OrderOneSong);
                        }
                    }
            );

            return convertView;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
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

        song2data = null;
        songs = null;
        queryCondition = null;
        message = null;
    }
}
