package com.smile.androidsong;

import android.content.Context;
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
import com.smile.model.SingerTypesList;
import com.smile.recyclerview_adapter.SingerTypesAdapter;
import com.smile.retrofit_package.RetrofitApiInterface;
import com.smile.retrofit_package.RetrofitClient;
import com.smile.smilelibraries.alertdialogfragment.AlertDialogFragment;
import com.smile.smilelibraries.utilities.ScreenUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SingerTypesListActivity extends AppCompatActivity {

    private static final String TAG = "SingerTypesListActivity";
    private float textFontSize;
    private ListView singerTypesListView;
    private RecyclerView singerTypesRecyclerView;
    private TextView singerTypesListEmptyTextView;
    // private SingerTypesListViewAdapter mMyListAdapter;
    private SingerTypesAdapter myRecyclerViewAdapter;
    private SingerTypesList singerTypesList;
    private final String noResultString = AndroidSongApp.AppResources.getString(R.string.noResultString);
    private final String failedMessage = AndroidSongApp.AppResources.getString(R.string.failedMessage);
    private final String loadingString = AndroidSongApp.AppResources.getString(R.string.loadingString);

    private RetrofitRetrieveSingerType retrofitRetrieveSingerType;

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

                SingerType singerType = singerTypesList.getSingerTypes().get(i);
                String singersListActivityTitle = "";
                if (singerType != null) {
                    singersListActivityTitle = singerType.getAreaNa();
                }
                ScreenUtil.showToast(SingerTypesListActivity.this, singersListActivityTitle,
                        textFontSize, AndroidSongApp.FontSize_Scale_Type, Toast.LENGTH_SHORT);
                Intent singersIntent = new Intent(getApplicationContext(), SingersListActivity.class);
                singersIntent.putExtra("SingersListActivityTitle", singersListActivityTitle);
                singersIntent.putExtra("SingerTypeParcelable", singerType);
                startActivity(singersIntent);
            }
        });
        //

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

        retrofitRetrieveSingerType = new RetrofitRetrieveSingerType(this);
        retrofitRetrieveSingerType.startRetrieve();
    }


    @Override
    public void onBackPressed() {
        returnToPrevious();
    }

    private void returnToPrevious() {
        finish();
    }

    // implement Callback<List<SingerType>> of RetrofitApiInterface
    // implement Retrofit to get results asynchronously
    private class RetrofitRetrieveSingerType implements Callback<SingerTypesList> {
        private final String failedMessage = getApplicationContext().getString(R.string.failedMessage);
        private final String noResultString = getApplicationContext().getString(R.string.noResultString);
        private final Context context;
        private final AlertDialogFragment loadingDialog;

        public RetrofitRetrieveSingerType(Context context) {
            this.context = context;
            loadingDialog = AlertDialogFragment.newInstance(loadingString, AndroidSongApp.FontSize_Scale_Type, textFontSize, Color.RED, 0, 0, true);
        }

        public void startRetrieve() {
            Log.d(TAG, "RetrofitRetrieveSingerType.startRetrieve");
            loadingDialog.show(getSupportFragmentManager(), "LoadingDialogTag");

            Retrofit retrofit = RetrofitClient.getRetrofitInstance();     // get Retrofit client
            RetrofitApiInterface retrofitApiInterface = retrofit.create(RetrofitApiInterface.class);
            Call<SingerTypesList> call = retrofitApiInterface.getAllSingerTypes();
            Log.d(TAG, "RetrofitRetrieveSingerType.startRetrieve.call = " + call);
            call.enqueue(this);
        }

        @Override
        public void onResponse(Call<SingerTypesList> call, Response<SingerTypesList> response) {
            Log.d(TAG, "onResponse");
            loadingDialog.dismissAllowingStateLoss();

            if (response.isSuccessful()) {
                singerTypesList = response.body();
                if (singerTypesList.getSingerTypes().size() == 0) {
                    singerTypesListEmptyTextView.setText(noResultString);
                    singerTypesListEmptyTextView.setVisibility(View.VISIBLE);
                } else {
                    singerTypesListEmptyTextView.setVisibility(View.GONE);
                }
            } else {
                singerTypesList = new SingerTypesList();
                singerTypesListEmptyTextView.setText("response.isSuccessful() --> false.");
                singerTypesListEmptyTextView.setVisibility(View.VISIBLE);
            }

            myRecyclerViewAdapter = new SingerTypesAdapter(textFontSize, singerTypesList.getSingerTypes());
            singerTypesRecyclerView.setAdapter(myRecyclerViewAdapter);
            singerTypesRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        }

        @Override
        public void onFailure(Call<SingerTypesList> call, Throwable t) {
            Log.d(TAG, "onFailure." + t.toString());
            loadingDialog.dismissAllowingStateLoss();
            singerTypesList = new SingerTypesList();
            singerTypesListEmptyTextView.setText(failedMessage);
            singerTypesListEmptyTextView.setVisibility(View.VISIBLE);
        }
    }
}
