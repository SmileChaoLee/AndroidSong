package com.smile.androidsong;

import android.graphics.Color;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.smile.model.Constants;
import com.smile.model.SingerTypeList;
import com.smile.retrofit_package.RestApi;
import com.smile.view_adapter.SingerTypeAdapter;
import com.smile.smilelibraries.alertdialogfragment.AlertDialogFragment;
import com.smile.smilelibraries.utilities.ScreenUtil;

import retrofit2.Call;
import retrofit2.Response;

public class SingerTypeListActivity extends AppCompatActivity implements RestApi<SingerTypeList> {

    private static final String TAG = "SingerTypesListActivity";
    private float textFontSize;
    private RecyclerView singerTypesRecyclerView;
    private TextView singerTypesListEmptyTextView;
    private SingerTypeAdapter myViewAdapter;
    private SingerTypeList singerTypeList;
    private String noResultString;
    private String failedMessage;
    private String loadingString;
    private final AlertDialogFragment loadingDialog
            = AlertDialogFragment.newInstance(loadingString,
            Constants.FontSize_Scale_Type,
            textFontSize, Color.RED, 0, 0, true);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        noResultString = getString(R.string.noResultString);
        failedMessage = getString(R.string.failedMessage);
        loadingString = getString(R.string.loadingString);

        float defaultTextFontSize = ScreenUtil.getDefaultTextSizeFromTheme(this, Constants.FontSize_Scale_Type, null);
        textFontSize = ScreenUtil.suitableFontSize(this, defaultTextFontSize, Constants.FontSize_Scale_Type, 0.0f);

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_singer_types_list);

        final TextView singerTypesListMenuTextView = findViewById(R.id.singerTypesListMenuTextView);
        ScreenUtil.resizeTextSize(singerTypesListMenuTextView, textFontSize, Constants.FontSize_Scale_Type);

        singerTypesRecyclerView = findViewById(R.id.singerTypesRecyclerView);
        singerTypesListEmptyTextView = findViewById(R.id.singerTypesListEmptyTextView);
        ScreenUtil.resizeTextSize(singerTypesListEmptyTextView, textFontSize, Constants.FontSize_Scale_Type);
        singerTypesListEmptyTextView.setVisibility(View.GONE);
        final Button singerTypesListReturnButton = findViewById(R.id.singerTypesListReturnButton);
        ScreenUtil.resizeTextSize(singerTypesListReturnButton, textFontSize, Constants.FontSize_Scale_Type);
        singerTypesListReturnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnToPrevious();
            }
        });


        loadingDialog.show(getSupportFragmentManager(), "LoadingDialogTag");
        getAllSingerTypes();
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
    public void onResponse(Call<SingerTypeList> call, Response<SingerTypeList> response) {
        Log.d(TAG, "onResponse");
        loadingDialog.dismissAllowingStateLoss();
        Log.d(TAG, "onResponse.response.isSuccessful() = " + response.isSuccessful());
        if (response.isSuccessful()) {
            singerTypeList = response.body();
            if (singerTypeList.getSingerTypes().size() == 0) {
                singerTypesListEmptyTextView.setText(noResultString);
                singerTypesListEmptyTextView.setVisibility(View.VISIBLE);
            } else {
                singerTypesListEmptyTextView.setVisibility(View.GONE);
            }
        } else {
            singerTypeList = new SingerTypeList();
            singerTypesListEmptyTextView.setText("response.isSuccessful() = false.");
            singerTypesListEmptyTextView.setVisibility(View.VISIBLE);
        }
        myViewAdapter = new SingerTypeAdapter(SingerTypeListActivity.this, singerTypeList.getSingerTypes(), textFontSize);
        singerTypesRecyclerView.setAdapter(myViewAdapter);
        singerTypesRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }

    @Override
    public void onFailure(Call<SingerTypeList> call, Throwable t) {
        Log.d(TAG, "onFailure." + t.toString());
        loadingDialog.dismissAllowingStateLoss();
        singerTypeList = new SingerTypeList();
        singerTypesListEmptyTextView.setText(failedMessage);
        singerTypesListEmptyTextView.setVisibility(View.VISIBLE);
    }
}
