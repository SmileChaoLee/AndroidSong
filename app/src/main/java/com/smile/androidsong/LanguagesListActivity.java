package com.smile.androidsong;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.smile.Utility.FontSizeAndTheme;
import com.smile.model.*;
import com.smile.retrofit_package.GetDataByRetrofitRestApi;
import com.smile.smilepublicclasseslibrary.alertdialogfragment.AlertDialogFragment;

import java.util.ArrayList;
import java.util.List;

public class LanguagesListActivity extends AppCompatActivity {

    private float textFontSize;
    private ListView languagesListView;
    private TextView languagesListEmptyTextView;
    private MyListAdapter mMyListAdapter;
    private LanguagesList languagesList = null;

    private int orderedFrom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        textFontSize = FontSizeAndTheme.GetTextFontSizeAndSetTheme(this);    // smaller than MyActivity
        orderedFrom = 0;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            orderedFrom = extras.getInt("OrderedFrom", 0);
        }

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_languages_list);

        TextView menuTextView = findViewById(R.id.languagesListMenuTextView);
        switch (orderedFrom) {
            case 0:
                // from main activity (MyActivity)
                menuTextView.setText(getString(R.string.languagesListString));
                break;
            case AndroidSongApp.NewSongOrdered:
                menuTextView.setText(getString(R.string.newSOngLanguagesListString));
                break;
            case AndroidSongApp.HotSongOrdered:
                menuTextView.setText(getString(R.string.hotSongLanguagesListString));
                break;
        }

        languagesListView = findViewById(R.id.languagesListView);
        languagesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Language language = languagesList.getLanguages().get(i);
                Toast.makeText(LanguagesListActivity.this, language.getLangNa().toString(), Toast.LENGTH_SHORT).show();
                switch (orderedFrom) {
                    case 0:
                        Intent wordsIntent = new Intent(LanguagesListActivity.this, WordsListActivity.class);
                        wordsIntent.putExtra("OrderedFrom", AndroidSongApp.LanguageOrdered);
                        wordsIntent.putExtra("LanguageParcelable", language);
                        startActivity(wordsIntent);
                        break;
                    case AndroidSongApp.NewSongOrdered:
                        Intent newSongsIntent = new Intent(LanguagesListActivity.this, SongsListActivity.class);
                        newSongsIntent.putExtra("OrderedFrom", AndroidSongApp.NewSongLanguageOrdered);
                        newSongsIntent.putExtra("LanguageParcelable", language);
                        startActivity(newSongsIntent);
                        break;
                    case AndroidSongApp.HotSongOrdered:
                        Intent hotSongsIntent = new Intent(LanguagesListActivity.this, SongsListActivity.class);
                        hotSongsIntent.putExtra("OrderedFrom", AndroidSongApp.HotSongLanguageOrdered);
                        hotSongsIntent.putExtra("LanguageParcelable", language);
                        startActivity(hotSongsIntent);
                        break;
                }
            }
        });

        languagesListEmptyTextView = findViewById(R.id.languagesListEmptyTextView);
        languagesListEmptyTextView.setVisibility(View.GONE);

        final Button languagesListReturnButton = findViewById(R.id.languagesListReturnButton);
        languagesListReturnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnToPrevious();
            }
        });

        AccessLanguagesAsyncTask accessLanguagesAsyncTask = new AccessLanguagesAsyncTask();
        accessLanguagesAsyncTask.execute();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
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
        TextView positionNoTextView;
        TextView languageNaTextView;
        ArrayList<Language> languages;

        @SuppressWarnings("unchecked")
        public MyListAdapter(@NonNull Context context, int resource, @NonNull List objects) {
            super(context, resource, objects);
            layoutId = resource;
            languages = (ArrayList<Language>)objects;
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

            positionNoTextView = view.findViewById(R.id.languageItem_Layout_positionNoTextView);
            positionNoTextView.setText(String.valueOf(position));

            languageNaTextView = view.findViewById(R.id.languageNaTextView);
            languageNaTextView.setText(languages.get(position).getLangNa());

            return view;
        }
    }

    private class AccessLanguagesAsyncTask extends AsyncTask<Void, Void, Void> {

        private final String TAG = new String("AccessLanguagesAsyncTask");
        private final String failedMessage = getApplicationContext().getString(R.string.failedMessage);
        private final String noResultString = getApplicationContext().getString(R.string.noResultString);
        private final String loadingString = getApplicationContext().getString(R.string.loadingString);
        private AlertDialogFragment loadingDialog;

        public AccessLanguagesAsyncTask() {

            loadingDialog = AlertDialogFragment.newInstance(loadingString, textFontSize, Color.RED, 0, 0, true);
        }

        @Override
        protected void onPreExecute() {
            loadingDialog.show(getSupportFragmentManager(), "LoadingDialogTag");
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Log.i(TAG, "doInBackground() started.");

            // implement Retrofit to get results synchronously
            languagesList = GetDataByRetrofitRestApi.getAllLanguages();

            Log.i(TAG, "doInBackground() finished.");

            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.i(TAG, "onPostExecute() started.");

            loadingDialog.dismissAllowingStateLoss();

            if (languagesList == null) {
                // failed
                languagesList = new LanguagesList();

                languagesListEmptyTextView.setText(failedMessage);
                languagesListEmptyTextView.setVisibility(View.VISIBLE);
            } else {
                // successfully

                if (languagesList.getLanguages().size() == 0) {
                    languagesListEmptyTextView.setText(noResultString);
                    languagesListEmptyTextView.setVisibility(View.VISIBLE);
                } else {
                    languagesListEmptyTextView.setVisibility(View.GONE);
                }

                mMyListAdapter = new MyListAdapter(getBaseContext(), R.layout.languages_list_item ,languagesList.getLanguages());
                languagesListView.setAdapter(mMyListAdapter);
            }
        }
    }
}
