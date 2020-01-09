package com.smile.androidsong;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.smile.model.*;
import com.smile.retrofit_package.GetDataByRetrofitRestApi;
import com.smile.smilelibraries.alertdialogfragment.AlertDialogFragment;
import com.smile.smilelibraries.utilities.ScreenUtil;

import java.util.ArrayList;
import java.util.List;

public class SongsListActivity extends AppCompatActivity {

    private static final String TAG = new String("SongsListActivity");
    private String songsListActivityTitle;
    private float textFontSize;
    private EditText searchEditText;
    private boolean isSearchEditTextChanged;
    private String filterString;
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

        float defaultTextFontSize = ScreenUtil.getDefaultTextSizeFromTheme(this, AndroidSongApp.FontSize_Scale_Type, null);
        textFontSize = ScreenUtil.suitableFontSize(this, defaultTextFontSize, AndroidSongApp.FontSize_Scale_Type, 0.0f);

        orderedFrom = 0;    // default value
        String songsListTitle = getString(R.string.songsListString);
        songsListActivityTitle = "";
        numOfWords = 0;
        Bundle extras = getIntent().getExtras();
        if (extras != null ) {
            orderedFrom = extras.getInt("OrderedFrom", 0);
            songsListActivityTitle = extras.getString("SongsListActivityTitle", songsListActivityTitle).trim();
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

        TextView songsListMenuTextView = findViewById(R.id.songsListMenuTextView);
        ScreenUtil.resizeTextSize(songsListMenuTextView, textFontSize, AndroidSongApp.FontSize_Scale_Type);
        songsListMenuTextView.setText(songsListActivityTitle + " " + songsListTitle);

        filterString = "";
        searchEditText = findViewById(R.id.songSearchEditText);
        ScreenUtil.resizeTextSize(searchEditText, textFontSize, AndroidSongApp.FontSize_Scale_Type);
        LinearLayout.LayoutParams searchEditLp = (LinearLayout.LayoutParams) searchEditText.getLayoutParams();
        searchEditLp.leftMargin = (int)(textFontSize * 2.0f);
        searchEditLp.rightMargin = (int)(textFontSize * 5.0f);
        // searchEditLp.setMargins(100, 0, (int)textFontSize*2, 0);
        searchEditText.setText(filterString);
        isSearchEditTextChanged = false;
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String content = editable.toString().trim();
                filterString = "";
                if (!content.isEmpty()) {
                    filterString = "SongNa+" + content;
                }
                pageNo = 1;
                isSearchEditTextChanged = true;
                // searchEditText.clearFocus();
                AccessSongsAsyncTask accessSongsAsyncTask = new AccessSongsAsyncTask();
                accessSongsAsyncTask.execute();
            }
        });

        songsListView = findViewById(R.id.songsListView);
        songsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Song song = songsList.getSongs().get(i);
                ScreenUtil.showToast(getApplicationContext(), song.getSongNa().toString(), textFontSize, AndroidSongApp.FontSize_Scale_Type, Toast.LENGTH_SHORT);
            }
        });

        songsListEmptyTextView = findViewById(R.id.songsListEmptyTextView);
        ScreenUtil.resizeTextSize(songsListEmptyTextView, textFontSize, AndroidSongApp.FontSize_Scale_Type);
        songsListEmptyTextView.setVisibility(View.GONE);

        float smallButtonFontSize = textFontSize * 0.7f;
        final Button firstPageButton = (Button) findViewById(R.id.firstPageButton);
        ScreenUtil.resizeTextSize(firstPageButton, smallButtonFontSize, AndroidSongApp.FontSize_Scale_Type);
        firstPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstPage();
            }
        });

        final Button previousPageButton = findViewById(R.id.previousPageButton);
        ScreenUtil.resizeTextSize(previousPageButton, smallButtonFontSize, AndroidSongApp.FontSize_Scale_Type);
        previousPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                previousPage();
            }
        });

        final Button nextPageButton = findViewById(R.id.nextPageButton);
        ScreenUtil.resizeTextSize(nextPageButton, smallButtonFontSize, AndroidSongApp.FontSize_Scale_Type);
        nextPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextPage();
            }
        });

        final Button lastPageButton = findViewById(R.id.lastPageButton);
        ScreenUtil.resizeTextSize(lastPageButton, smallButtonFontSize, AndroidSongApp.FontSize_Scale_Type);
        lastPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lastPage();
            }
        });

        final Button songsListReturnButton = findViewById(R.id.songsListReturnButton);
        ScreenUtil.resizeTextSize(songsListReturnButton, textFontSize, AndroidSongApp.FontSize_Scale_Type);
        songsListReturnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnToPrevious();
            }
        });

        AccessSongsAsyncTask accessSongsAsyncTask = new AccessSongsAsyncTask();
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
        AccessSongsAsyncTask accessSongsAsyncTask = new AccessSongsAsyncTask();
        accessSongsAsyncTask.execute();
    }

    private void previousPage() {
        pageNo--;
        if (pageNo < 1) {
            pageNo = 1;
        }
        AccessSongsAsyncTask accessSongsAsyncTask = new AccessSongsAsyncTask();
        accessSongsAsyncTask.execute();
    }

    private void nextPage() {
        pageNo++;
        if (pageNo > totalPages) {
            pageNo = totalPages;
        }
        AccessSongsAsyncTask accessSongsAsyncTask = new AccessSongsAsyncTask();
        accessSongsAsyncTask.execute();
    }

    private void lastPage() {
        pageNo = -1;    // represent last page
        AccessSongsAsyncTask accessSongsAsyncTask = new AccessSongsAsyncTask();
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
            ScreenUtil.resizeTextSize(positionNoTextView, songNaFontSize, AndroidSongApp.FontSize_Scale_Type);
            positionNoTextView.setText(String.valueOf(position));

            songNaTextView = view.findViewById(R.id.songNaTextView);
            ScreenUtil.resizeTextSize(songNaTextView, songNaFontSize, AndroidSongApp.FontSize_Scale_Type);
            songNaTextView.setText(songs.get(position).getSongNa());

            songNoTextView = view.findViewById(R.id.songNoTextView);
            ScreenUtil.resizeTextSize(songNoTextView, smallFontSize, AndroidSongApp.FontSize_Scale_Type);
            songNoTextView.setText(songs.get(position).getSongNo());

            languageNameTextView = view.findViewById(R.id.languageNameTextView);
            ScreenUtil.resizeTextSize(languageNameTextView, smallFontSize, AndroidSongApp.FontSize_Scale_Type);
            languageNameTextView.setText(songs.get(position).getLanguageNa());

            singer1NameTextView = view.findViewById(R.id.singer1NameTextView);
            ScreenUtil.resizeTextSize(singer1NameTextView, smallFontSize, AndroidSongApp.FontSize_Scale_Type);
            singer1NameTextView.setText(songs.get(position).getSinger1Na());

            singer2NameTextView = view.findViewById(R.id.singer2NameTextView);
            ScreenUtil.resizeTextSize(singer2NameTextView, smallFontSize, AndroidSongApp.FontSize_Scale_Type);
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

        public AccessSongsAsyncTask() {

            loadingDialog = AlertDialogFragment.newInstance(loadingString, AndroidSongApp.FontSize_Scale_Type, textFontSize, Color.RED, 0, 0, true);
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
                    songsList = GetDataByRetrofitRestApi.getSongsBySinger((Singer)objectPassed, pageSize, pageNo, filterString);
                    break;
                case AndroidSongApp.NewSongOrdered:
                    break;
                case AndroidSongApp.NewSongLanguageOrdered:
                    songsList = GetDataByRetrofitRestApi.getNewSongsByLanguage((Language)objectPassed, pageSize, pageNo, filterString);
                    break;
                case AndroidSongApp.HotSongOrdered:
                    break;
                case AndroidSongApp.HotSongLanguageOrdered:
                    songsList = GetDataByRetrofitRestApi.getHotSongsByLanguage((Language)objectPassed, pageSize, pageNo, filterString);
                    break;
                case AndroidSongApp.LanguageOrdered:
                    songsList = GetDataByRetrofitRestApi.getSongsByLanguage((Language)objectPassed, pageSize, pageNo, filterString);
                    break;
                case AndroidSongApp.LanguageWordsOrdered:
                    songsList = GetDataByRetrofitRestApi.getSongsByLanguageNumOfWords((Language)objectPassed, numOfWords, pageSize, pageNo, filterString);
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
                songsList.setPageNo(pageNo);
                songsList.setPageSize(pageSize);
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
            }
            mMyListAdapter = new MyListAdapter(getBaseContext(), R.layout.songs_list_item ,songsList.getSongs());
            songsListView.setAdapter(mMyListAdapter);

            if (isSearchEditTextChanged) {
                // searchEditText.setFocusable(true);              // needed for requestFocus()
                // searchEditText.setFocusableInTouchMode(true);   // needed for requestFocus()
                // searchEditText.requestFocus();  // needed for the next two statements
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(null, InputMethodManager.SHOW_IMPLICIT);
                isSearchEditTextChanged = false;
            }
        }
    }
}
