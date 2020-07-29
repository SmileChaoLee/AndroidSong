package com.smile.androidsong;

import android.content.Context;
import android.content.Intent;
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

import com.smile.model.Singer;
import com.smile.model.SingerType;
import com.smile.model.SingersList;
import com.smile.retrofit_package.GetDataByRetrofitRestApi;
import com.smile.smilelibraries.alertdialogfragment.AlertDialogFragment;
import com.smile.smilelibraries.utilities.ScreenUtil;

import java.util.ArrayList;
import java.util.List;


public class SingersListActivity extends AppCompatActivity {

    private static final String TAG = new String("SingersListActivity");
    private String singersListActivityTitle;
    private float textFontSize;
    private EditText searchEditText;
    private boolean isSearchEditTextChanged;
    private String filterString;
    private ListView singersListView;
    private TextView singersListEmptyTextView;
    private MyListAdapter mMyListAdapter;
    private SingersList singersList = null;
    private SingerType singerType;

    private int pageNo = 1;
    private int pageSize = 10;
    private int totalPages = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        float defaultTextFontSize = ScreenUtil.getDefaultTextSizeFromTheme(this, AndroidSongApp.FontSize_Scale_Type, null);
        textFontSize = ScreenUtil.suitableFontSize(this, defaultTextFontSize, AndroidSongApp.FontSize_Scale_Type, 0.0f);

        String singersListTitle = getString(R.string.singersListString);
        singersListActivityTitle = "";
        Bundle extras = getIntent().getExtras();
        if (extras != null )
        {
            singersListActivityTitle = extras.getString("SingersListActivityTitle").trim();
            singerType = extras.getParcelable("SingerTypeParcelable");
        }

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_singers_list);

        final TextView singersListMenuTextView = findViewById(R.id.singersListMenuTextView);
        ScreenUtil.resizeTextSize(singersListMenuTextView, textFontSize, AndroidSongApp.FontSize_Scale_Type);
        singersListMenuTextView.setText(singersListActivityTitle + " " + singersListTitle);

        filterString = "";
        searchEditText = findViewById(R.id.singerSearchEditText);
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
                    filterString = "SingNa+" + content;
                }
                pageNo = 1;
                isSearchEditTextChanged = true;
                // searchEditText.clearFocus();
                AccessSingersAsyncTask accessSingersAsyncTask = new AccessSingersAsyncTask();
                accessSingersAsyncTask.execute();
            }
        });

        singersListView = findViewById(R.id.singersListView);
        singersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Singer singer = singersList.getSingers().get(i);
                String songsListActivityTitle = "";
                if (singer != null) {
                    songsListActivityTitle = singer.getSingNa();
                }
                ScreenUtil.showToast(getApplicationContext(), songsListActivityTitle, textFontSize, AndroidSongApp.FontSize_Scale_Type, Toast.LENGTH_SHORT);
                Intent songsIntent = new Intent(SingersListActivity.this, SongsListActivity.class);
                songsIntent.putExtra("OrderedFrom", AndroidSongApp.SingerOrdered);
                songsIntent.putExtra("SongsListActivityTitle", songsListActivityTitle);
                songsIntent.putExtra("SingerParcelable", singer);
                startActivity(songsIntent);
            }
        });

        singersListEmptyTextView = findViewById(R.id.singersListEmptyTextView);
        ScreenUtil.resizeTextSize(singersListEmptyTextView, textFontSize, AndroidSongApp.FontSize_Scale_Type);
        singersListEmptyTextView.setVisibility(View.GONE);

        float smallButtonFontSize = textFontSize * 0.7f;
        final Button firstPageButton = findViewById(R.id.firstPageButton);
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

        final Button singersListReturnButton = findViewById(R.id.singersListReturnButton);
        ScreenUtil.resizeTextSize(singersListReturnButton, textFontSize, AndroidSongApp.FontSize_Scale_Type);
        singersListReturnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnToPrevious();
            }
        });

        AccessSingersAsyncTask accessSingersAsyncTask = new AccessSingersAsyncTask();
        accessSingersAsyncTask.execute();
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

    private void firstPage() {
        pageNo = 1;
        AccessSingersAsyncTask accessSingersAsyncTask = new AccessSingersAsyncTask();
        accessSingersAsyncTask.execute();
    }

    private void previousPage() {
        pageNo--;
        if (pageNo < 1) {
            pageNo = 1;
        }
        AccessSingersAsyncTask accessSingersAsyncTask = new AccessSingersAsyncTask();
        accessSingersAsyncTask.execute();
    }

    private void nextPage() {
        pageNo++;
        if (pageNo > totalPages) {
            pageNo = totalPages;
        }
        AccessSingersAsyncTask accessSingersAsyncTask = new AccessSingersAsyncTask();
        accessSingersAsyncTask.execute();
    }

    private void lastPage() {
        pageNo = -1;    // represent last page
        AccessSingersAsyncTask accessSingersAsyncTask = new AccessSingersAsyncTask();
        accessSingersAsyncTask.execute();
    }

    private class MyListAdapter extends ArrayAdapter {

        int layoutId;
        TextView positionNoTextView;
        TextView singerNoTextView;
        TextView singerNaTextView;
        ArrayList<Singer> singers;

        @SuppressWarnings("unchecked")
        public MyListAdapter(@NonNull Context context, int resource, @NonNull List objects) {
            super(context, resource, objects);
            layoutId = resource;
            singers = (ArrayList<Singer>)objects;
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

            positionNoTextView = view.findViewById(R.id.singerItem_Layout_positionNoTextView);
            ScreenUtil.resizeTextSize(positionNoTextView, textFontSize, AndroidSongApp.FontSize_Scale_Type);
            positionNoTextView.setText(String.valueOf(position));

            singerNoTextView = view.findViewById(R.id.singerNoTextView);
            ScreenUtil.resizeTextSize(singerNoTextView, textFontSize, AndroidSongApp.FontSize_Scale_Type);
            singerNoTextView.setText(singers.get(position).getSingNo());

            singerNaTextView = view.findViewById(R.id.singerNaTextView);
            ScreenUtil.resizeTextSize(singerNaTextView, textFontSize, AndroidSongApp.FontSize_Scale_Type);
            singerNaTextView.setText(singers.get(position).getSingNa());

            return view;
        }
    }

    private class AccessSingersAsyncTask extends AsyncTask<Void, Void, Void> {

        private final String TAG = new String("AccessSingersAsyncTask");
        private final String failedMessage = getApplicationContext().getString(R.string.failedMessage);
        private final String noResultString = getApplicationContext().getString(R.string.noResultString);
        private final String loadingString = getApplicationContext().getString(R.string.loadingString);
        private AlertDialogFragment loadingDialog;

        public AccessSingersAsyncTask() {

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
            singersList = GetDataByRetrofitRestApi.getSingersBySingerType(singerType, pageSize, pageNo, filterString);

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

            if (singersList == null) {
                // failed
                singersList = new SingersList();
                singersList.setPageNo(pageNo);
                singersList.setPageSize(pageSize);
                singersList.setTotalRecords(0); // temporary
                singersList.setTotalPages(0);   // temporary
                totalPages = 0;

                singersListEmptyTextView.setText(failedMessage);
                singersListEmptyTextView.setVisibility(View.VISIBLE);
            } else {
                // successfully
                pageNo = singersList.getPageNo();      // get the back value from called function
                pageSize = singersList.getPageSize();    // get the back value from called function
                totalPages = singersList.getTotalPages();

                if (singersList.getSingers().size() == 0) {
                    singersListEmptyTextView.setText(noResultString);
                    singersListEmptyTextView.setVisibility(View.VISIBLE);
                } else {
                    singersListEmptyTextView.setVisibility(View.GONE);
                }
            }
            mMyListAdapter = new MyListAdapter(getBaseContext(), R.layout.singers_list_item ,singersList.getSingers());
            singersListView.setAdapter(mMyListAdapter);

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
