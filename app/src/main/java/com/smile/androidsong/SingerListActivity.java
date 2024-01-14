package com.smile.androidsong;

import android.content.Context;
import android.content.Intent;
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

import com.smile.model.Constants;
import com.smile.model.Singer;
import com.smile.model.SingerType;
import com.smile.model.SingerList;
import com.smile.retrofit_package.RestApi;
import com.smile.smilelibraries.alertdialogfragment.AlertDialogFragment;
import com.smile.smilelibraries.utilities.ScreenUtil;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class SingerListActivity extends AppCompatActivity
        implements RestApi<SingerList> {

    private static final String TAG = "SingersListActivity";
    private float textFontSize;
    private EditText searchEditText;
    private boolean isSearchEditTextChanged;
    private String filterString;
    private ListView singersListView;
    private TextView singersListEmptyTextView;
    private MyListAdapter mMyListAdapter;
    private SingerList singerList = null;
    private SingerType singerType;

    private int pageNo = 1;
    private int pageSize = 10;
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

        String singersListTitle = getString(R.string.singersListString);
        String singersListActivityTitle = "";
        Bundle extras = getIntent().getExtras();
        if (extras != null ) {
            singersListActivityTitle = extras.getString("SingersListActivityTitle").trim();
            singerType = extras.getParcelable("SingerTypeParcelable");
        }

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_singers_list);

        final TextView singersListMenuTextView = findViewById(R.id.singersListMenuTextView);
        ScreenUtil.resizeTextSize(singersListMenuTextView, textFontSize, Constants.FontSize_Scale_Type);
        singersListMenuTextView.setText(singersListActivityTitle + " " + singersListTitle);

        filterString = "";
        searchEditText = findViewById(R.id.singerSearchEditText);
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

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.d(TAG, "searchEditText.addTextChangedListener.afterTextChanged");
                String content = editable.toString().trim();
                filterString = content.isEmpty() ? "" : "SingNa+" + content;
                Log.d(TAG, "searchEditText.addTextChangedListener.afterTextChanged.filterString = "
                        + filterString);
                pageNo = 1;
                isSearchEditTextChanged = true;
                // searchEditText.clearFocus();
                retrieveSingerList();
            }
        });

        singersListView = findViewById(R.id.singersListView);
        singersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d(TAG, "singersListView.onItemClick");
                Singer singer = singerList.getSingers().get(i);
                String songsListActivityTitle = "";
                if (singer != null) {
                    songsListActivityTitle = singer.getSingNa();
                }
                ScreenUtil.showToast(SingerListActivity.this, songsListActivityTitle, textFontSize, Constants.FontSize_Scale_Type, Toast.LENGTH_SHORT);
                Intent songsIntent = new Intent(SingerListActivity.this, SongListActivity.class);
                songsIntent.putExtra("OrderedFrom", Constants.SingerOrdered);
                songsIntent.putExtra("SongsListActivityTitle", songsListActivityTitle);
                songsIntent.putExtra("SingerParcelable", singer);
                startActivity(songsIntent);
            }
        });

        singersListEmptyTextView = findViewById(R.id.singersListEmptyTextView);
        ScreenUtil.resizeTextSize(singersListEmptyTextView, textFontSize, Constants.FontSize_Scale_Type);
        singersListEmptyTextView.setVisibility(View.GONE);

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

        final Button singersListReturnButton = findViewById(R.id.singersListReturnButton);
        ScreenUtil.resizeTextSize(singersListReturnButton, textFontSize, Constants.FontSize_Scale_Type);
        singersListReturnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnToPrevious();
            }
        });

        retrieveSingerList();
    }

    private void retrieveSingerList() {
        Log.d(TAG, "retrieveSingerList.filterString = " + filterString);
        if (loadingDialog == null) {
            loadingDialog = AlertDialogFragment.newInstance(loadingString,
                    Constants.FontSize_Scale_Type,
                    textFontSize, Color.RED, 0, 0, true);
            loadingDialog.show(getSupportFragmentManager(), "LoadingDialogTag");
        }
        if (filterString != null && !filterString.isEmpty()) {
            getSingersBySingerType(singerType, pageSize, pageNo, filterString);
        } else {
            getSingersBySingerType(singerType, pageSize, pageNo);
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
        retrieveSingerList();
    }

    private void previousPage() {
        pageNo--;
        if (pageNo < 1) {
            pageNo = 1;
        }
        retrieveSingerList();
    }

    private void nextPage() {
        pageNo++;
        if (pageNo > totalPages) {
            pageNo = totalPages;
        }
        retrieveSingerList();
    }

    private void lastPage() {
        pageNo = -1;    // represent last page
        retrieveSingerList();
    }

    @Override
    public void onResponse(Call<SingerList> call, Response<SingerList> response) {
        if (loadingDialog != null) loadingDialog.dismissAllowingStateLoss();
        loadingDialog = null;
        Log.d(TAG, "onResponse.response.isSuccessful() = " + response.isSuccessful());
        singerList = response.body();
        if (!response.isSuccessful() || singerList == null) {
            singerList = new SingerList();
            singersListEmptyTextView.setText(failedMessage);
            singersListEmptyTextView.setVisibility(View.VISIBLE);
        } else {
            pageNo = singerList.getPageNo();         // get the back value from called function
            pageSize = singerList.getPageSize();     // get the back value from called function
            totalPages = singerList.getTotalPages(); // get the back value from called function
            if (singerList.getSingers().size() == 0) {
                singersListEmptyTextView.setText(noResultString);
                singersListEmptyTextView.setVisibility(View.VISIBLE);
            } else {
                singersListEmptyTextView.setVisibility(View.GONE);
            }
        }
        mMyListAdapter = new MyListAdapter(getBaseContext(), R.layout.singers_list_item , singerList.getSingers());
        singersListView.setAdapter(mMyListAdapter);
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
    public void onFailure(Call call, Throwable t) {
        Log.d(TAG, "onFailure." + t.toString());
        if (loadingDialog != null) loadingDialog.dismissAllowingStateLoss();
        loadingDialog = null;
        singerList = new SingerList();
        singersListEmptyTextView.setText(failedMessage);
        singersListEmptyTextView.setVisibility(View.VISIBLE);
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
            ScreenUtil.resizeTextSize(positionNoTextView, textFontSize, Constants.FontSize_Scale_Type);
            positionNoTextView.setText(String.valueOf(position));

            singerNoTextView = view.findViewById(R.id.singerNoTextView);
            ScreenUtil.resizeTextSize(singerNoTextView, textFontSize, Constants.FontSize_Scale_Type);
            singerNoTextView.setText(singers.get(position).getSingNo());

            singerNaTextView = view.findViewById(R.id.singerNaTextView);
            ScreenUtil.resizeTextSize(singerNaTextView, textFontSize, Constants.FontSize_Scale_Type);
            singerNaTextView.setText(singers.get(position).getSingNa());

            return view;
        }
    }
}
