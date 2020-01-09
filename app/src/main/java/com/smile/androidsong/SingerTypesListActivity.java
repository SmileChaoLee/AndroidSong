package com.smile.androidsong;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.smile.model.SingerType;
import com.smile.model.SingerTypesList;
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

    private static final String TAG = new String("SingerTypesListActivity");
    private float textFontSize;
    private ListView singerTypesListView;
    private TextView singerTypesListEmptyTextView;
    private MyListAdapter mMyListAdapter;
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

        retrofitRetrieveSingerType = new RetrofitRetrieveSingerType();
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
                    mMyListAdapter = new MyListAdapter(getBaseContext(), R.layout.singer_types_list_item, singerTypesList.getSingerTypes());
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

    private class MyListAdapter extends ArrayAdapter {

        int layoutId;
        TextView positionNoTextView;
        TextView singerAreaNaTextView;
        TextView singerSexTextView;
        ArrayList<SingerType> singerTypes;

        @SuppressWarnings("unchecked")
        public MyListAdapter(@NonNull Context context, int resource, @NonNull List objects) {
            super(context, resource, objects);
            layoutId = resource;
            singerTypes = (ArrayList<SingerType>)objects;
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

            positionNoTextView = view.findViewById(R.id.singerTypeItem_Layout_positionNoTextView);
            ScreenUtil.resizeTextSize(positionNoTextView, textFontSize, AndroidSongApp.FontSize_Scale_Type);
            positionNoTextView.setText(String.valueOf(position));

            singerAreaNaTextView = view.findViewById(R.id.singerAreaNaTextView);
            ScreenUtil.resizeTextSize(singerAreaNaTextView, textFontSize, AndroidSongApp.FontSize_Scale_Type);
            singerAreaNaTextView.setText(singerTypes.get(position).getAreaNa());

            singerSexTextView = view.findViewById(R.id.singerSexTextView);
            ScreenUtil.resizeTextSize(singerSexTextView, textFontSize, AndroidSongApp.FontSize_Scale_Type);
            String sex = singerTypes.get(position).getSex();
            String sexString;
            switch (sex) {
                case "1":
                    sexString = "Male";
                    break;
                case "2":
                    sexString = "Female";
                    break;
                default:
                    // "0"
                    sexString = "";
                    break;
            }
            singerSexTextView.setText(sexString);

            return view;
        }
    }

    // implement Callback<List<SingerType>> of RetrofitApiInterface
    // implement Retrofit to get results asynchronously
    private class RetrofitRetrieveSingerType implements Callback<SingerTypesList> {

        private final String TAG = new String("RetrofitRetrieveSingerType.class");
        private final String failedMessage = getApplicationContext().getString(R.string.failedMessage);
        private final String noResultString = getApplicationContext().getString(R.string.noResultString);
        private AlertDialogFragment loadingDialog;

        private Retrofit retrofit;

        public RetrofitRetrieveSingerType() {
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
            mMyListAdapter = new MyListAdapter(getBaseContext(), R.layout.singer_types_list_item, singerTypesList.getSingerTypes());
            singerTypesListView.setAdapter(mMyListAdapter);
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
