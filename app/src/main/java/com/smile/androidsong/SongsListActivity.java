package com.smile.androidsong;

import android.content.Context;
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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.smile.Utility.FontSizeAndTheme;
import com.smile.model.*;
import com.smile.retrofit_package.GetDataByRetrofitRestApi;
import com.smile.smilepublicclasseslibrary.alertdialogfragment.AlertDialogFragment;
import com.smile.smilepublicclasseslibrary.utilities.ScreenUtil;

import java.util.ArrayList;
import java.util.List;

import okhttp3.internal.Util;

public class SongsListActivity extends AppCompatActivity {

    private static final String TAG = new String("SongsListActivity");
    private float textFontSize;
    private EditText searchEditText;
    private ListView songsListView;
    private TextView songsListEmptyTextView;
    private MyListAdapter mMyListAdapter;
    private SongsList songsList = null;
    private Singer singer;
    private Language language;
    private Object objectPassed;

    private int orderedFrom = 0;
    private int numOfWords = 0;
    private int pageNo = 1;
    private int pageSize = 7;
    private int totalPages = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        textFontSize = FontSizeAndTheme.GetTextFontSizeAndSetTheme(this);    // smaller than MyActivity
        if (ScreenUtil.isTablet(this)) {
            pageSize = 9;
        }
        orderedFrom = 0;    // default value
        numOfWords = 0;
        Bundle extras = getIntent().getExtras();
        if (extras != null )
        {
            orderedFrom = extras.getInt("OrderedFrom", 0);
            switch (orderedFrom) {
                case AndroidSongApp.SingerOrdered:
                    singer = extras.getParcelable("SingerParcelable");
                    objectPassed = singer;
                    break;
                case AndroidSongApp.NewSongOrdered:
                    objectPassed = language;
                    break;
                case AndroidSongApp.NewSongLanguageOrdered:
                    language = extras.getParcelable("LanguageParcelable");
                    objectPassed = language;
                    break;
                case AndroidSongApp.HotSongOrdered:
                    objectPassed = null;
                    break;
                case AndroidSongApp.HotSongLanguageOrdered:
                    language = extras.getParcelable("LanguageParcelable");
                    objectPassed = language;
                    break;
                case AndroidSongApp.LanguageOrdered:
                    language = extras.getParcelable("LanguageParcelable");
                    objectPassed = language;
                case AndroidSongApp.LanguageWordsOrdered:
                    language = extras.getParcelable("LanguageParcelable");
                    objectPassed = language;
                    numOfWords = extras.getInt("NumOfWords");
                    break;
            }
        }

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_songs_list);

        searchEditText = findViewById(R.id.songSearchEditText);
        LinearLayout.LayoutParams searchEditLp = (LinearLayout.LayoutParams) searchEditText.getLayoutParams();
        searchEditLp.leftMargin = (int)(textFontSize * 2.0f);
        searchEditLp.rightMargin = (int)(textFontSize * 5.0f);
        // searchEditLp.setMargins(100, 0, (int)textFontSize*2, 0);

        songsListView = findViewById(R.id.songsListView);
        songsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Song song = songsList.getSongs().get(i);
                Toast.makeText(SongsListActivity.this, song.getSongNa().toString(), Toast.LENGTH_SHORT).show();
            }
        });

        songsListEmptyTextView = findViewById(R.id.songsListEmptyTextView);
        songsListEmptyTextView.setVisibility(View.GONE);

        float smallButtonFontSize = textFontSize * 0.7f;
        Button firstPageButton = (Button) findViewById(R.id.firstPageButton);
        firstPageButton.setTextSize(smallButtonFontSize);
        firstPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstPage();
            }
        });

        Button previousPageButton = findViewById(R.id.previousPageButton);
        previousPageButton.setTextSize(smallButtonFontSize);
        previousPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                previousPage();
            }
        });

        Button nextPageButton = findViewById(R.id.nextPageButton);
        nextPageButton.setTextSize(smallButtonFontSize);
        nextPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextPage();
            }
        });

        Button lastPageButton = findViewById(R.id.lastPageButton);
        lastPageButton.setTextSize(smallButtonFontSize);
        lastPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lastPage();
            }
        });

        final Button songsListReturnButton = findViewById(R.id.songsListReturnButton);
        songsListReturnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnToPrevious();
            }
        });

        AccessSongsAsyncTask accessSongsAsyncTask = new AccessSongsAsyncTask(objectPassed, numOfWords, pageSize, pageNo);
        accessSongsAsyncTask.execute();
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

    private void firstPage() {
        pageNo = 1;
        AccessSongsAsyncTask accessSongsAsyncTask = new AccessSongsAsyncTask(objectPassed, numOfWords, pageSize, pageNo);
        accessSongsAsyncTask.execute();
    }

    private void previousPage() {
        pageNo--;
        if (pageNo < 1) {
            pageNo = 1;
        }
        AccessSongsAsyncTask accessSongsAsyncTask = new AccessSongsAsyncTask(objectPassed, numOfWords, pageSize, pageNo);
        accessSongsAsyncTask.execute();
    }

    private void nextPage() {
        pageNo++;
        if (pageNo > totalPages) {
            pageNo = totalPages;
        }
        AccessSongsAsyncTask accessSongsAsyncTask = new AccessSongsAsyncTask(objectPassed, numOfWords, pageSize, pageNo);
        accessSongsAsyncTask.execute();
    }

    private void lastPage() {
        pageNo = -1;    // represent last page
        AccessSongsAsyncTask accessSongsAsyncTask = new AccessSongsAsyncTask(objectPassed, numOfWords, pageSize, pageNo);
        accessSongsAsyncTask.execute();
    }

    private class MyListAdapter extends ArrayAdapter {

        int layoutId;
        TextView positionNoTextView;
        TextView songNoTextView;
        TextView songNaTextView;
        TextView languageNameTextView;
        TextView singer1NameTextView;
        TextView singer2NameTextView;
        ArrayList<Song> songs;
        final float songNaFontSize = textFontSize * 0.8f;
        final float smallFontSize = textFontSize * 0.6f;

        @SuppressWarnings("unchecked")
        public MyListAdapter(@NonNull Context context, int resource, @NonNull List objects) {
            super(context, resource, objects);
            layoutId = resource;
            songs = (ArrayList<Song>)objects;
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

            positionNoTextView = view.findViewById(R.id.songItem_Layout_positionNoTextView);
            positionNoTextView.setTextSize(songNaFontSize);
            positionNoTextView.setText(String.valueOf(position));

            songNaTextView = view.findViewById(R.id.songNaTextView);
            songNaTextView.setTextSize(songNaFontSize);
            songNaTextView.setText(songs.get(position).getSongNa());

            songNoTextView = view.findViewById(R.id.songNoTextView);
            songNoTextView.setTextSize(smallFontSize);
            songNoTextView.setText(songs.get(position).getSongNo());

            languageNameTextView = view.findViewById(R.id.languageNameTextView);
            languageNameTextView.setTextSize(smallFontSize);
            languageNameTextView.setText(songs.get(position).getLanguageNa());

            singer1NameTextView = view.findViewById(R.id.singer1NameTextView);
            singer1NameTextView.setTextSize(smallFontSize);
            singer1NameTextView.setText(songs.get(position).getSinger1Na());

            singer2NameTextView = view.findViewById(R.id.singer2NameTextView);
            singer2NameTextView.setTextSize(smallFontSize);
            singer2NameTextView.setText(songs.get(position).getSinger2Na());

            return view;
        }
    }

    private class AccessSongsAsyncTask extends AsyncTask<Void, Void, Void> {

        private final String TAG = new String("AccessSongsAsyncTask");
        private final String failedMessage = getApplicationContext().getString(R.string.failedMessage);
        private final String noResultString = getApplicationContext().getString(R.string.noResultString);
        private final String loadingString = getApplicationContext().getString(R.string.loadingString);
        private AlertDialogFragment loadingDialog;

        private Object objectAsyncTask;
        private int numOfWordsAsyncTask;
        private int pageSizeAsyncTask;
        private int pageNoAsyncTask;

        public AccessSongsAsyncTask(Object objectAsyncTask, int numOfWordsAsyncTask, int pageSizeAsyncTask, int pageNoAsyncTask) {
            this.objectAsyncTask = objectAsyncTask;
            this.numOfWordsAsyncTask = numOfWordsAsyncTask;
            this.pageSizeAsyncTask = pageSizeAsyncTask;
            this.pageNoAsyncTask = pageNoAsyncTask;

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
            switch (orderedFrom) {
                case AndroidSongApp.SingerOrdered:
                    songsList = GetDataByRetrofitRestApi.getSongsBySinger((Singer)objectAsyncTask, pageSizeAsyncTask, pageNoAsyncTask);
                    break;
                case AndroidSongApp.NewSongOrdered:
                    break;
                case AndroidSongApp.NewSongLanguageOrdered:
                    songsList = GetDataByRetrofitRestApi.getNewSongsByLanguage((Language)objectAsyncTask, pageSizeAsyncTask, pageNoAsyncTask);
                    break;
                case AndroidSongApp.HotSongOrdered:
                    break;
                case AndroidSongApp.HotSongLanguageOrdered:
                    songsList = GetDataByRetrofitRestApi.getHotSongsByLanguage((Language)objectAsyncTask, pageSizeAsyncTask, pageNoAsyncTask);
                    break;
                case AndroidSongApp.LanguageOrdered:
                    songsList = GetDataByRetrofitRestApi.getSongsByLanguage((Language)objectAsyncTask, pageSizeAsyncTask, pageNoAsyncTask);
                    break;
                case AndroidSongApp.LanguageWordsOrdered:
                    songsList = GetDataByRetrofitRestApi.getSongsByLanguageNumOfWords((Language)objectAsyncTask, numOfWordsAsyncTask, pageSizeAsyncTask, pageNoAsyncTask);
                    break;
            }

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

            if (songsList == null) {
                // failed
                songsList = new SongsList();
                songsList.setPageNo(pageNoAsyncTask);
                songsList.setPageSize(pageSizeAsyncTask);
                songsList.setTotalRecords(0); // temporary
                songsList.setTotalPages(0);   // temporary
                totalPages = 0;

                songsListEmptyTextView.setText(failedMessage);
                songsListEmptyTextView.setVisibility(View.VISIBLE);
            } else {
                // successfully
                pageNo = songsList.getPageNo();         // get the back value from called function
                pageSize = songsList.getPageSize();     // get the back value from called function
                totalPages = songsList.getTotalPages(); // get the back value from called function

                if (songsList.getSongs().size() == 0) {
                    songsListEmptyTextView.setText(noResultString);
                    songsListEmptyTextView.setVisibility(View.VISIBLE);
                } else {
                    songsListEmptyTextView.setVisibility(View.GONE);
                }

                mMyListAdapter = new MyListAdapter(getBaseContext(), R.layout.songs_list_item ,songsList.getSongs());
                songsListView.setAdapter(mMyListAdapter);
            }
        }
    }
}
