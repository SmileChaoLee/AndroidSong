package com.smile.androidsong;

import android.content.Intent;
import android.graphics.Color;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.smile.model.SingerType;
import com.smile.model.SingerTypeList;
import com.smile.retrofit_package.RestApiKotlin;
import com.smile.view_adapter.SingerTypeAdapter;
import com.smile.smilelibraries.alertdialogfragment.AlertDialogFragment;
import com.smile.smilelibraries.utilities.ScreenUtil;

import retrofit2.Call;
import retrofit2.Response;

public class SingerTypeListActivity extends AppCompatActivity {

    private static final String TAG = "SingerTypesListActivity";
    private float textFontSize;
    private ListView singerTypesListView;
    private RecyclerView singerTypesRecyclerView;
    private TextView singerTypesListEmptyTextView;
    private SingerTypeAdapter myViewAdapter;
    private SingerTypeList singerTypeList;
    private final String noResultString = AndroidSongApp.AppResources.getString(R.string.noResultString);
    private final String failedMessage = AndroidSongApp.AppResources.getString(R.string.failedMessage);
    private final String loadingString = AndroidSongApp.AppResources.getString(R.string.loadingString);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        float defaultTextFontSize = ScreenUtil.getDefaultTextSizeFromTheme(this, AndroidSongApp.FontSize_Scale_Type, null);
        textFontSize = ScreenUtil.suitableFontSize(this, defaultTextFontSize, AndroidSongApp.FontSize_Scale_Type, 0.0f);

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_singer_types_list);

        final TextView singerTypesListMenuTextView = findViewById(R.id.singerTypesListMenuTextView);
        ScreenUtil.resizeTextSize(singerTypesListMenuTextView, textFontSize, AndroidSongApp.FontSize_Scale_Type);

        // deprecated
        singerTypesListView = findViewById(R.id.singerTypesListView);
        singerTypesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                SingerType singerType = singerTypeList.getSingerTypes().get(i);
                String singersListActivityTitle = "";
                if (singerType != null) {
                    singersListActivityTitle = singerType.getAreaNa();
                }
                ScreenUtil.showToast(SingerTypeListActivity.this, singersListActivityTitle,
                        textFontSize, AndroidSongApp.FontSize_Scale_Type, Toast.LENGTH_SHORT);
                Intent singersIntent = new Intent(getApplicationContext(), SingerListActivity.class);
                singersIntent.putExtra("SingersListActivityTitle", singersListActivityTitle);
                singersIntent.putExtra("SingerTypeParcelable", singerType);
                startActivity(singersIntent);
            }
        });

        singerTypesRecyclerView = findViewById(R.id.singerTypesRecyclerView);
        singerTypesListEmptyTextView = findViewById(R.id.singerTypesListEmptyTextView);
        ScreenUtil.resizeTextSize(singerTypesListEmptyTextView, textFontSize, AndroidSongApp.FontSize_Scale_Type);
        singerTypesListEmptyTextView.setVisibility(View.GONE);
        final Button singerTypesListReturnButton = findViewById(R.id.singerTypesListReturnButton);
        ScreenUtil.resizeTextSize(singerTypesListReturnButton, textFontSize, AndroidSongApp.FontSize_Scale_Type);
        singerTypesListReturnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnToPrevious();
            }
        });

        final AlertDialogFragment loadingDialog
            = AlertDialogFragment.newInstance(loadingString,
                AndroidSongApp.FontSize_Scale_Type,
                textFontSize, Color.RED, 0, 0, true);
        loadingDialog.show(getSupportFragmentManager(), "LoadingDialogTag");

        new RestApiKotlin<SingerTypeList>() {
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
                myViewAdapter = new SingerTypeAdapter(textFontSize, singerTypeList.getSingerTypes());
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
        }.getAllSingerTypes();
    }

    @Override
    public void onBackPressed() {
        returnToPrevious();
    }

    private void returnToPrevious() {
        Log.d(TAG, "returnToPrevious");
        finish();
    }
}
