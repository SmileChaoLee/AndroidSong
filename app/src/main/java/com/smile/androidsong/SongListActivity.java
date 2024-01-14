package com.smile.androidsong;

import android.content.Context;
import android.graphics.Color;
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
import com.smile.retrofit_package.RestApi;
import com.smile.smilelibraries.alertdialogfragment.AlertDialogFragment;
import com.smile.smilelibraries.utilities.ScreenUtil;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class SongListActivity extends AppCompatActivity implements RestApi<SongList> {

    private static final String TAG = "SongsListActivity";
    private String songsListActivityTitle;
    private float textFontSize;
    private EditText searchEditText;
    private boolean isSearchEditTextChanged;
    private String filterString;
    private ListView songsListView;
    private TextView songsListEmptyTextView;
    private MyListAdapter mMyListAdapter;
    private SongList songList = null;
    private Singer singer;
    private Language language;
    private Object objectPassed;

    private int orderedFrom = 0;
    private int numOfWords = 0;
    private int pageNo = 1;
    private int pageSize = 7;
    private int totalPages = 0;
    private String noResultString;
    private String failedMessage;
    private String loadingString;
    private AlertDialogFragment loadingDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        noResultString = getString(R.string.noResultString);
        failedMessage = getString(R.string.failedMessage);
        loadingString = getString(R.string.loadingString);

        float defaultTextFontSize = ScreenUtil.getDefaultTextSizeFromTheme(this, Constants.FontSize_Scale_Type, null);
        textFontSize = ScreenUtil.suitableFontSize(this, defaultTextFontSize, Constants.FontSize_Scale_Type, 0.0f);

        orderedFrom = 0;    // default value
        String songsListTitle = getString(R.string.songsListString);
        songsListActivityTitle = "";
        numOfWords = 0;
        Bundle extras = getIntent().getExtras();
        if (extras != null ) {
            orderedFrom = extras.getInt("OrderedFrom", 0);
            songsListActivityTitle = extras.getString("SongsListActivityTitle", songsListActivityTitle).trim();
            switch (orderedFrom) {
                case Constants.SingerOrdered -> {
                    singer = extras.getParcelable("SingerParcelable");
                    objectPassed = singer;
                }
                case Constants.NewSongOrdered -> objectPassed = language;
                case Constants.NewSongLanguageOrdered, Constants.HotSongLanguageOrdered -> {
                    language = extras.getParcelable("LanguageParcelable");
                    objectPassed = language;
                }
                case Constants.HotSongOrdered -> objectPassed = null;
                case Constants.LanguageOrdered, Constants.LanguageWordsOrdered -> {
                    language = extras.getParcelable("LanguageParcelable");
                    objectPassed = language;
                    numOfWords = extras.getInt("NumOfWords");
                }
            }
        }
        Log.d(TAG, "onCreate.orderedFrom = " + orderedFrom);

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_songs_list);

        TextView songsListMenuTextView = findViewById(R.id.songsListMenuTextView);
        ScreenUtil.resizeTextSize(songsListMenuTextView, textFontSize, Constants.FontSize_Scale_Type);
        songsListMenuTextView.setText(songsListActivityTitle + " " + songsListTitle);

        filterString = "";
        searchEditText = findViewById(R.id.songSearchEditText);
        ScreenUtil.resizeTextSize(searchEditText, textFontSize, Constants.FontSize_Scale_Type);
        LinearLayout.LayoutParams searchEditLp = (LinearLayout.LayoutParams) searchEditText.getLayoutParams();
        searchEditLp.leftMargin = (int)(textFontSize * 2.0f);
        searchEditLp.rightMargin = (int)(textFontSize * 5.0f);
        // searchEditLp.setMargins(100, 0, (int)textFontSize*2, 0);
        searchEditText.setText(filterString);
        isSearchEditTextChanged = false;
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.d(TAG, "searchEditText.addTextChangedListener.beforeTextChanged");
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.d(TAG, "searchEditText.addTextChangedListener.onTextChanged");
            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.d(TAG, "searchEditText.addTextChangedListener.afterTextChanged");
                String content = editable.toString().trim();
                filterString = content.isEmpty() ? "" : "SongNa+" + content;
                Log.d(TAG, "searchEditText.addTextChangedListener.afterTextChanged.filterString = "
                + filterString);
                pageNo = 1;
                isSearchEditTextChanged = true;
                // searchEditText.clearFocus();
                retrieveSongList();
            }
        });

        songsListView = findViewById(R.id.songsListView);
        songsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Song song = songList.getSongs().get(i);
                ScreenUtil.showToast(SongListActivity.this, song.getSongNa(), textFontSize, Constants.FontSize_Scale_Type, Toast.LENGTH_SHORT);
            }
        });

        songsListEmptyTextView = findViewById(R.id.songsListEmptyTextView);
        ScreenUtil.resizeTextSize(songsListEmptyTextView, textFontSize, Constants.FontSize_Scale_Type);
        songsListEmptyTextView.setVisibility(View.GONE);

        float smallButtonFontSize = textFontSize * 0.7f;
        final Button firstPageButton = findViewById(R.id.firstPageButton);
        ScreenUtil.resizeTextSize(firstPageButton, smallButtonFontSize, Constants.FontSize_Scale_Type);
        firstPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstPage();
            }
        });

        final Button previousPageButton = findViewById(R.id.previousPageButton);
        ScreenUtil.resizeTextSize(previousPageButton, smallButtonFontSize, Constants.FontSize_Scale_Type);
        previousPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                previousPage();
            }
        });

        final Button nextPageButton = findViewById(R.id.nextPageButton);
        ScreenUtil.resizeTextSize(nextPageButton, smallButtonFontSize, Constants.FontSize_Scale_Type);
        nextPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextPage();
            }
        });

        final Button lastPageButton = findViewById(R.id.lastPageButton);
        ScreenUtil.resizeTextSize(lastPageButton, smallButtonFontSize, Constants.FontSize_Scale_Type);
        lastPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lastPage();
            }
        });

        final Button songsListReturnButton = findViewById(R.id.songsListReturnButton);
        ScreenUtil.resizeTextSize(songsListReturnButton, textFontSize, Constants.FontSize_Scale_Type);
        songsListReturnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnToPrevious();
            }
        });

        retrieveSongList();
    }

    private void retrieveSongList() {
        Log.d(TAG, "retrieveSongList.orderedFrom = " + orderedFrom);
        if (loadingDialog == null) {
            loadingDialog = AlertDialogFragment.newInstance(loadingString,
                    Constants.FontSize_Scale_Type,
                    textFontSize, Color.RED, 0, 0, true);
            loadingDialog.show(getSupportFragmentManager(), "LoadingDialogTag");
        }
        switch (orderedFrom) {
            case Constants.SingerOrdered:
                Log.d(TAG, "retrieveSongList.SingerOrdered");
                if (filterString != null && !filterString.isEmpty()) {
                    getSongsBySinger((Singer) objectPassed, pageSize, pageNo, filterString);
                } else {
                    getSongsBySinger((Singer) objectPassed, pageSize, pageNo);
                }
                break;
            case Constants.NewSongOrdered :
                Log.d(TAG, "retrieveSongList.NewSongOrdered");
                break;
            case Constants.HotSongOrdered:
                Log.d(TAG, "retrieveSongList.HotSongOrdered");
                break;
            case Constants.NewSongLanguageOrdered:
                Log.d(TAG, "retrieveSongList.NewSongLanguageOrdered");
                if (filterString != null && !filterString.isEmpty()) {
                    getNewSongsByLanguage((Language) objectPassed, pageSize, pageNo, filterString);
                } else {
                    getNewSongsByLanguage((Language) objectPassed, pageSize, pageNo);
                }
                break;
            case Constants.HotSongLanguageOrdered:
                Log.d(TAG, "retrieveSongList.HotSongLanguageOrdered");
                if (filterString != null && !filterString.isEmpty()) {
                    getHotSongsByLanguage((Language) objectPassed, pageSize, pageNo, filterString);
                } else {
                    getHotSongsByLanguage((Language) objectPassed, pageSize, pageNo);
                }
                break;
            case Constants.LanguageOrdered:
                Log.d(TAG, "retrieveSongList.LanguageOrdered");
                if (filterString != null && !filterString.isEmpty()) {
                    getSongsByLanguage((Language) objectPassed, pageSize, pageNo, filterString);
                } else {
                    getSongsByLanguage((Language) objectPassed, pageSize, pageNo);
                }
                break;
            case Constants.LanguageWordsOrdered:
                Log.d(TAG, "retrieveSongList.LanguageWordsOrdered");
                if (filterString != null && !filterString.isEmpty()) {
                    getSongsByLanguageNumOfWords((Language) objectPassed, numOfWords, pageSize, pageNo, filterString);
                } else {
                    getSongsByLanguageNumOfWords((Language) objectPassed, numOfWords, pageSize, pageNo);
                }
                break;
        }
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
        Log.d(TAG, "returnToPrevious");
        finish();
    }

    private void firstPage() {
        pageNo = 1;
        retrieveSongList();
    }

    private void previousPage() {
        pageNo--;
        if (pageNo < 1) {
            pageNo = 1;
        }
        retrieveSongList();
    }

    private void nextPage() {
        pageNo++;
        if (pageNo > totalPages) {
            pageNo = totalPages;
        }
        retrieveSongList();
    }

    private void lastPage() {
        pageNo = -1;    // represent last page
        retrieveSongList();
    }

    @Override
    public void onResponse(Call<SongList> call, Response<SongList> response) {
        Log.d(TAG, "onResponse");
        if (loadingDialog != null) loadingDialog.dismissAllowingStateLoss();
        loadingDialog = null;
        Log.d(TAG, "onResponse.response.isSuccessful() = " + response.isSuccessful());
        songList = response.body();
        if (!response.isSuccessful() || songList == null) {
            songList = new SongList();
            songsListEmptyTextView.setText(failedMessage);
            songsListEmptyTextView.setVisibility(View.VISIBLE);
        } else {
            pageNo = songList.getPageNo();         // get the back value from called function
            pageSize = songList.getPageSize();     // get the back value from called function
            totalPages = songList.getTotalPages(); // get the back value from called function
            if (songList.getSongs().size() == 0) {
                songsListEmptyTextView.setText(noResultString);
                songsListEmptyTextView.setVisibility(View.VISIBLE);
            } else {
                songsListEmptyTextView.setVisibility(View.GONE);
            }
        }
        mMyListAdapter = new MyListAdapter(getBaseContext(), R.layout.songs_list_item , songList.getSongs());
        songsListView.setAdapter(mMyListAdapter);
        Log.d(TAG, "onResponse.response.isSearchEditTextChanged = " + isSearchEditTextChanged);
        if (isSearchEditTextChanged) {
            // searchEditText.setFocusable(true);              // needed for requestFocus()
            // searchEditText.setFocusableInTouchMode(true);   // needed for requestFocus()
            // searchEditText.requestFocus();  // needed for the next two statements
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            // imm.showSoftInput(null, InputMethodManager.SHOW_IMPLICIT);
            imm.showSoftInput(searchEditText, InputMethodManager.SHOW_IMPLICIT);
            isSearchEditTextChanged = false;
        }
    }

    @Override
    public void onFailure(Call<SongList> call, Throwable t) {
        Log.d(TAG, "onFailure." + t.toString());
        if (loadingDialog != null) loadingDialog.dismissAllowingStateLoss();
        loadingDialog = null;
        songList = new SongList();
        songsListEmptyTextView.setText(failedMessage);
        songsListEmptyTextView.setVisibility(View.VISIBLE);
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
            ScreenUtil.resizeTextSize(positionNoTextView, songNaFontSize, Constants.FontSize_Scale_Type);
            positionNoTextView.setText(String.valueOf(position));

            songNaTextView = view.findViewById(R.id.songNaTextView);
            ScreenUtil.resizeTextSize(songNaTextView, songNaFontSize, Constants.FontSize_Scale_Type);
            songNaTextView.setText(songs.get(position).getSongNa());

            songNoTextView = view.findViewById(R.id.songNoTextView);
            ScreenUtil.resizeTextSize(songNoTextView, smallFontSize, Constants.FontSize_Scale_Type);
            songNoTextView.setText(songs.get(position).getSongNo());

            languageNameTextView = view.findViewById(R.id.languageNameTextView);
            ScreenUtil.resizeTextSize(languageNameTextView, smallFontSize, Constants.FontSize_Scale_Type);
            languageNameTextView.setText(songs.get(position).getLanguageNa());

            singer1NameTextView = view.findViewById(R.id.singer1NameTextView);
            ScreenUtil.resizeTextSize(singer1NameTextView, smallFontSize, Constants.FontSize_Scale_Type);
            singer1NameTextView.setText(songs.get(position).getSinger1Na());

            singer2NameTextView = view.findViewById(R.id.singer2NameTextView);
            ScreenUtil.resizeTextSize(singer2NameTextView, smallFontSize, Constants.FontSize_Scale_Type);
            singer2NameTextView.setText(songs.get(position).getSinger2Na());

            return view;
        }
    }
}
