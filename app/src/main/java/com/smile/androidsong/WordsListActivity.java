package com.smile.androidsong;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.smile.model.Language;
import com.smile.smilelibraries.utilities.ScreenUtil;

import java.util.ArrayList;

public class WordsListActivity extends AppCompatActivity {

    private static final String TAG = new String("WordsListActivity");
    private String languageTitle;
    private float textFontSize;
    private ListView wordsListView;
    private MyListAdapter myListAdapter;
    private ArrayList<Pair<Integer, String>> wordsList;

    private int orderedFrom = 0;
    private Language language;

    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        float defaultTextFontSize = ScreenUtil.getDefaultTextSizeFromTheme(this, AndroidSongApp.FontSize_Scale_Type, null);
        textFontSize = ScreenUtil.suitableFontSize(this, defaultTextFontSize, AndroidSongApp.FontSize_Scale_Type, 0.0f);

        orderedFrom = 0;
        String wordsListTitle = getString(R.string.wordsListString);
        languageTitle = "";
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            orderedFrom = extras.getInt("OrderedFrom", 0);
            languageTitle = extras.getString("LanguageTitle").trim();
            language = extras.getParcelable("LanguageParcelable");
        }

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_words_list);

        final TextView wordsListMenuTextView = findViewById(R.id.wordsListMenuTextView);
        ScreenUtil.resizeTextSize(wordsListMenuTextView, textFontSize, AndroidSongApp.FontSize_Scale_Type);
        wordsListMenuTextView.setText(languageTitle + " " + wordsListTitle);

        wordsList = new ArrayList<Pair<Integer, String>>();
        wordsList.add(new Pair(1, getString(R.string.oneWordOrderString)));
        wordsList.add(new Pair(2, getString(R.string.twoWordsOrderString)));
        wordsList.add(new Pair(3, getString(R.string.threeWordsOrderString)));
        wordsList.add(new Pair(4, getString(R.string.fourWordsOrderString)));
        wordsList.add(new Pair(5, getString(R.string.fiveWordsOrderString)));
        wordsList.add(new Pair(6, getString(R.string.sixWordsOrderString)));
        wordsList.add(new Pair(7, getString(R.string.sevenWordsOrderString)));
        wordsList.add(new Pair(8, getString(R.string.eightWordsOrderString)));
        wordsList.add(new Pair(9, getString(R.string.nineWordsOrderString)));
        wordsList.add(new Pair(10, getString(R.string.tenWordsOrderString)));

        myListAdapter = new MyListAdapter(this, R.layout.words_list_item, wordsList);

        wordsListView = findViewById(R.id.wordsListView);
        wordsListView.setAdapter(myListAdapter);
        wordsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int numOfWords = wordsList.get(i).first;
                String itemName = wordsList.get(i).second;
                ScreenUtil.showToast(WordsListActivity.this, itemName, textFontSize, AndroidSongApp.FontSize_Scale_Type, Toast.LENGTH_SHORT);

                Intent songsIntent = new Intent(WordsListActivity.this, SongsListActivity.class);
                songsIntent.putExtra("OrderedFrom", AndroidSongApp.LanguageWordsOrdered);
                songsIntent.putExtra("SongsListActivityTitle", languageTitle + " " + itemName);
                songsIntent.putExtra("LanguageParcelable", language);
                songsIntent.putExtra("NumOfWords", numOfWords);
                startActivity(songsIntent);
            }
        });

        final Button wordsListReturnButton = findViewById(R.id.wordsListReturnButton);
        ScreenUtil.resizeTextSize(wordsListReturnButton, textFontSize, AndroidSongApp.FontSize_Scale_Type);
        wordsListReturnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnToPrevious();
            }
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        returnToPrevious();
    }

    private void returnToPrevious() {
        finish();
    }

    private class MyListAdapter extends ArrayAdapter {
        int layoutId;
        TextView wordsOrderNameTextView;
        ArrayList<Pair<Integer, String>> listItems;

        @SuppressWarnings("unchecked")
        public MyListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Pair<Integer, String>> listItems) {
            super(context, resource, listItems);
            this.layoutId = resource;
            this.listItems = listItems;
        }

        @Nullable
        @Override
        public Object getItem(int position) {
            return super.getItem(position);
        }

        @SuppressWarnings("unchecked")
        @Override
        public int getPosition(@Nullable Object item) {
            return super.getPosition(item);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            View view = getLayoutInflater().inflate(layoutId, parent, false);
            wordsOrderNameTextView = view.findViewById(R.id.wordsOrderNameTextView);
            ScreenUtil.resizeTextSize(wordsOrderNameTextView, textFontSize, AndroidSongApp.FontSize_Scale_Type);
            wordsOrderNameTextView.setText(listItems.get(position).second);

            return view;
        }
    }
}
