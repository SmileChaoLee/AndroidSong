package com.smile.androidsong;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.smile.adapter.SingerTypesListViewAdapter;
import com.smile.model.SingerType;
import com.smile.model.SingerTypesList;
import com.smile.recyclerview_adapter.SingerTypesRecyclerViewAdapter;
import com.smile.retrofit_package.RetrofitApiInterface;
import com.smile.retrofit_package.RetrofitClient;
import com.smile.smilelibraries.alertdialogfragment.AlertDialogFragment;
import com.smile.smilelibraries.utilities.ScreenUtil;

import java.util.ArrayList;
import java.util.List;

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
    private SingerTypesRecyclerViewAdapter myRecyclerViewAdapter;
    private SingerTypesList singerTypesList;
    private final String noResultString = AndroidSongApp.AppResources.getString(R.string.noResultString);
    private final String failedMessage = AndroidSongApp.AppResources.getString(R.string.failedMessage);
    private final String loadingString = AndroidSongApp.AppResources.getString(R.string.loadingString);

    private RetrofitRetrieveSingerType retrofitRetrieveSingerType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

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
                ScreenUtil.showToast(getApplicationContext(), singersListActivityTitle, textFontSize, AndroidSongApp.FontSize_Scale_Type, Toast.LENGTH_SHORT);
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


        /*
        // the following works too
        loadingDialog = AlertDialogFragment.newInstance(loadingString, textFontSize, Color.RED, 0, 0, true);
        loadingDialog.show(getSupportFragmentManager(), "LoadingDialogTag");

        // implement the RetrofitApiInterface using anonymous Callback<List<SingerType>> class
        // implement Retrofit to get results asynchronously
        Retrofit retrofit = RetrofitClient.getRetrofitInstance();     // get Retrofit client
        RetrofitApiInterface retrofitApiInterface = retrofit.create(RetrofitApiInterface.class);
        Call<SingerTypesList> call = retrofitApiInterface.getAllSingerTypes();
        call.enqueue(new Callback<SingerTypesList>() {
            @Override
            public void onResponse(Call<SingerTypesList> call, Response<SingerTypesList> response) {
                loadingDialog.dismissAllowingStateLoss();

                if (response.isSuccessful()) {
                    singerTypesList = response.body();
                    if (singerTypesList.getSingerTypes().size() == 0) {
                        singerTypesListEmptyTextView.setText(noResultString);
                        singerTypesListEmptyTextView.setVisibility(View.VISIBLE);
                    } else {
                        singerTypesListEmptyTextView.setVisibility(View.GONE);
                    }
                    mMyListAdapter = new SingerTypesListViewAdapter(getBaseContext(), R.layout.singer_types_list_item, singerTypesList.getSingerTypes());
                    singerTypesListView.setAdapter(mMyListAdapter);
                } else {
                    singerTypesList = new SingerTypesList();
                    singerTypesListEmptyTextView.setText("response.isSuccessful() --> false.");
                    singerTypesListEmptyTextView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<SingerTypesList> call, Throwable t) {
                System.out.println("RetrofitRetrieveSingerType --> " + t.toString());

                loadingDialog.dismissAllowingStateLoss();

                singerTypesList = new SingerTypesList();
                singerTypesListEmptyTextView.setText(failedMessage);
                singerTypesListEmptyTextView.setVisibility(View.VISIBLE);
            }
        });
        */
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

        private final String TAG = new String("RetrofitRetrieveSingerType.class");
        private final String failedMessage = getApplicationContext().getString(R.string.failedMessage);
        private final String noResultString = getApplicationContext().getString(R.string.noResultString);
        private final Context context;
        private final AlertDialogFragment loadingDialog;

        public RetrofitRetrieveSingerType(Context context) {
            this.context = context;
            loadingDialog = AlertDialogFragment.newInstance(loadingString, AndroidSongApp.FontSize_Scale_Type, textFontSize, Color.RED, 0, 0, true);
        }

        public void startRetrieve() {
            loadingDialog.show(getSupportFragmentManager(), "LoadingDialogTag");

            Retrofit retrofit = RetrofitClient.getRetrofitInstance();     // get Retrofit client
            RetrofitApiInterface retrofitApiInterface = retrofit.create(RetrofitApiInterface.class);
            Call<SingerTypesList> call = retrofitApiInterface.getAllSingerTypes();
            call.enqueue(this);
        }

        @Override
        public void onResponse(Call<SingerTypesList> call, Response<SingerTypesList> response) {
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
            // mMyListAdapter = new SingerTypesListViewAdapter(context, textFontSize, R.layout.singer_types_list_item, singerTypesList.getSingerTypes());
            // singerTypesListView.setAdapter(mMyListAdapter);

            myRecyclerViewAdapter = new SingerTypesRecyclerViewAdapter(textFontSize, singerTypesList.getSingerTypes());
            singerTypesRecyclerView.setAdapter(myRecyclerViewAdapter);
            singerTypesRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        }

        @Override
        public void onFailure(Call<SingerTypesList> call, Throwable t) {
            System.out.println("RetrofitRetrieveSingerType --> " + t.toString());

            loadingDialog.dismissAllowingStateLoss();

            singerTypesList = new SingerTypesList();
            singerTypesListEmptyTextView.setText(failedMessage);
            singerTypesListEmptyTextView.setVisibility(View.VISIBLE);
        }
    }
}
