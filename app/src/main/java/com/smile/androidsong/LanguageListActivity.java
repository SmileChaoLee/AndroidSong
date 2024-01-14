package com.smile.androidsong;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

public class LanguageListActivity extends AppCompatActivity implements RestApi<LanguageList> {

    private static final String TAG = "LanguagesListActivity";
    private float textFontSize;
    private ListView languagesListView;
    private TextView languagesListEmptyTextView;
    private MyListAdapter mMyListAdapter;
    private LanguageList languageList = null;
    private String noResultString;
    private String failedMessage;
    private String loadingString;
    private final AlertDialogFragment loadingDialog
            = AlertDialogFragment.newInstance(loadingString,
            Constants.FontSize_Scale_Type,
            textFontSize, Color.RED, 0, 0, true);

    private int orderedFrom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        noResultString = getString(R.string.noResultString);
        failedMessage = getString(R.string.failedMessage);
        loadingString = getString(R.string.loadingString);

        float defaultTextFontSize = ScreenUtil.getDefaultTextSizeFromTheme(this, Constants.FontSize_Scale_Type, null);
        textFontSize = ScreenUtil.suitableFontSize(this, defaultTextFontSize, Constants.FontSize_Scale_Type, 0.0f);

        orderedFrom = 0;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            orderedFrom = extras.getInt("OrderedFrom", 0);
        }

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_languages_list);

        TextView menuTextView = findViewById(R.id.languagesListMenuTextView);
        ScreenUtil.resizeTextSize(menuTextView, textFontSize, Constants.FontSize_Scale_Type);
        switch (orderedFrom) {
            case 0:
                // from main activity (MyActivity)
                menuTextView.setText(getString(R.string.languagesListString));
                break;
            case Constants.NewSongOrdered:
                menuTextView.setText(getString(R.string.newSOngLanguagesListString));
                break;
            case Constants.HotSongOrdered:
                menuTextView.setText(getString(R.string.hotSongLanguagesListString));
                break;
        }

        languagesListView = findViewById(R.id.languagesListView);
        languagesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Language language = languageList.getLanguages().get(i);
                String languageTitle = "";
                if (language != null) {
                    languageTitle = language.getLangNa();
                }
                ScreenUtil.showToast(LanguageListActivity.this, languageTitle,
                        textFontSize, Constants.FontSize_Scale_Type, Toast.LENGTH_SHORT);
                switch (orderedFrom) {
                    case 0:
                        Intent wordsIntent = new Intent(LanguageListActivity.this, WordListActivity.class);
                        wordsIntent.putExtra("OrderedFrom", Constants.LanguageOrdered);
                        wordsIntent.putExtra("LanguageTitle", languageTitle);
                        wordsIntent.putExtra("LanguageParcelable", language);
                        startActivity(wordsIntent);
                        break;
                    case Constants.NewSongOrdered:
                        Intent newSongsIntent = new Intent(LanguageListActivity.this, SongListActivity.class);
                        newSongsIntent.putExtra("OrderedFrom", Constants.NewSongLanguageOrdered);
                        newSongsIntent.putExtra("SongsListActivityTitle", languageTitle + " " + getString(R.string.newString));
                        newSongsIntent.putExtra("LanguageParcelable", language);
                        startActivity(newSongsIntent);
                        break;
                    case Constants.HotSongOrdered:
                        Intent hotSongsIntent = new Intent(LanguageListActivity.this, SongListActivity.class);
                        hotSongsIntent.putExtra("OrderedFrom", Constants.HotSongLanguageOrdered);
                        hotSongsIntent.putExtra("SongsListActivityTitle", languageTitle + " " + getString(R.string.hotString));
                        hotSongsIntent.putExtra("LanguageParcelable", language);
                        startActivity(hotSongsIntent);
                        break;
                }
            }
        });

        languagesListEmptyTextView = findViewById(R.id.languagesListEmptyTextView);
        ScreenUtil.resizeTextSize(languagesListEmptyTextView, textFontSize, Constants.FontSize_Scale_Type);
        languagesListEmptyTextView.setVisibility(View.GONE);

        final Button languagesListReturnButton = findViewById(R.id.languagesListReturnButton);
        ScreenUtil.resizeTextSize(languagesListReturnButton, textFontSize, Constants.FontSize_Scale_Type);
        languagesListReturnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnToPrevious();
            }
        });


        loadingDialog.show(getSupportFragmentManager(), "LoadingDialogTag");
        getAllLanguages();
    }

    @Override
    public void onBackPressed() {
        returnToPrevious();
    }

    private void returnToPrevious() {
        Log.d(TAG, "returnToPrevious");
        finish();
    }

    @Override
    public void onResponse(Call<LanguageList> call, Response<LanguageList> response) {
        Log.d(TAG, "onResponse");
        loadingDialog.dismissAllowingStateLoss();
        Log.d(TAG, "onResponse.response.isSuccessful() = " + response.isSuccessful());
        if (response.isSuccessful()) {
            languageList = response.body();
            if (languageList.getLanguages().size() == 0) {
                languagesListEmptyTextView.setText(noResultString);
                languagesListEmptyTextView.setVisibility(View.VISIBLE);
            } else {
                languagesListEmptyTextView.setVisibility(View.GONE);
            }
        } else {
            languageList = new LanguageList();
            languagesListEmptyTextView.setText(failedMessage);
            languagesListEmptyTextView.setVisibility(View.VISIBLE);
        }
        mMyListAdapter = new MyListAdapter(getBaseContext(),
                R.layout.languages_list_item , languageList.getLanguages());
        languagesListView.setAdapter(mMyListAdapter);
    }
    @Override
    public void onFailure(Call<LanguageList> call, Throwable t) {
        Log.d(TAG, "onFailure." + t.toString());
        loadingDialog.dismissAllowingStateLoss();
        languageList = new LanguageList();
        languagesListEmptyTextView.setText(failedMessage);
        languagesListEmptyTextView.setVisibility(View.VISIBLE);
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
            ScreenUtil.resizeTextSize(positionNoTextView, textFontSize, Constants.FontSize_Scale_Type);
            positionNoTextView.setText(String.valueOf(position));

            languageNaTextView = view.findViewById(R.id.languageNaTextView);
            ScreenUtil.resizeTextSize(languageNaTextView, textFontSize, Constants.FontSize_Scale_Type);
            languageNaTextView.setText(languages.get(position).getLangNa());

            return view;
        }
    }
}
