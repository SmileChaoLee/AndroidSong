package com.smile.androidsong;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.smile.Utility.FontSizeAndTheme;
import com.smile.model.Singer;
import com.smile.model.SingerType;
import com.smile.model.SingersList;
import com.smile.retrofit_package.GetDataByRetrofitRestApi;
import com.smile.smilepublicclasseslibrary.alertdialogfragment.AlertDialogFragment;

import java.util.ArrayList;
import java.util.List;


public class SingersListActivity extends AppCompatActivity {

    private float textFontSize;
    private ListView singersListView;
    private TextView singersListEmptyTextView;
    private MyListAdapter mMyListAdapter;
    private SingersList singersList = null;
    private SingerType singerType;
    private final int failedItemNo = -1;

    private int pageNo = 1;
    private int pageSize = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        textFontSize = FontSizeAndTheme.GetTextFontSizeAndSetTheme(this);    // smaller than MyActivity
        Bundle extras = getIntent().getExtras();
        if (extras != null )
        {
            singerType = extras.getParcelable("SingerTypeParcelable");
        }

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_singers_list);

        TextView menuTextView = (TextView) findViewById(R.id.singersListMenuTextView);

        singersListView = findViewById(R.id.singersListView);
        singersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Singer singer = singersList.getSingers().get(i);
                Toast.makeText(SingersListActivity.this, singer.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        singersListEmptyTextView = findViewById(R.id.singersListEmptyTextView);
        singersListEmptyTextView.setVisibility(View.GONE);

        float smallButtonFontSize = textFontSize * 0.7f;
        Button firstPageButton = (Button) findViewById(R.id.firstPageButton);
        firstPageButton.setTextSize(smallButtonFontSize);
        firstPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstPage();
            }
        });

        Button previousPageButton = (Button) findViewById(R.id.previousPageButton);
        previousPageButton.setTextSize(smallButtonFontSize);
        previousPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                previousPage();
            }
        });

        Button nextPageButton = (Button) findViewById(R.id.nextPageButton);
        nextPageButton.setTextSize(smallButtonFontSize);
        nextPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextPage();
            }
        });

        Button lastPageButton = (Button) findViewById(R.id.lastPageButton);
        lastPageButton.setTextSize(smallButtonFontSize);
        lastPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lastPage();
            }
        });

        final Button returnToPreviousButton = (Button) findViewById(R.id.returnToPreviousButton);
        returnToPreviousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnToPrevious();
            }
        });

        AccessSingersAsyncTask accessSingersAsyncTask = new AccessSingersAsyncTask(singerType, pageSize, pageNo);
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

    private void firstPage() {
        pageNo = 1;
        AccessSingersAsyncTask accessSingersAsyncTask = new AccessSingersAsyncTask(singerType, pageSize, pageNo);
        accessSingersAsyncTask.execute();
    }

    private void previousPage() {
        pageNo--;
        if (pageNo < 1) {
            pageNo = 1;
        }
        AccessSingersAsyncTask accessSingersAsyncTask = new AccessSingersAsyncTask(singerType, pageSize, pageNo);
        accessSingersAsyncTask.execute();
    }

    private void nextPage() {
        pageNo++;
        AccessSingersAsyncTask accessSingersAsyncTask = new AccessSingersAsyncTask(singerType, pageSize, pageNo);
        accessSingersAsyncTask.execute();
    }

    private void lastPage() {
        pageNo = -1;    // represent last page
        AccessSingersAsyncTask accessSingersAsyncTask = new AccessSingersAsyncTask(singerType, pageSize, pageNo);
        accessSingersAsyncTask.execute();
    }

    private void returnToPrevious() {
        finish();
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
            positionNoTextView.setText(String.valueOf(position));

            singerNoTextView = view.findViewById(R.id.singerNoTextView);
            singerNoTextView.setText(singers.get(position).getSingNo());

            singerNaTextView = view.findViewById(R.id.singerNaTextView);
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

        private SingerType singerTypeAsyncTask;
        private int pageSizeAsyncTask;
        private int pageNoAsyncTask;

        public AccessSingersAsyncTask(SingerType singerTypeAsyncTask, int pageSizeAsyncTask, int pageNoAsyncTask) {
            this.singerTypeAsyncTask = singerTypeAsyncTask;
            this.pageSizeAsyncTask = pageSizeAsyncTask;
            this.pageNoAsyncTask = pageNoAsyncTask;

            loadingDialog = AlertDialogFragment.newInstance(loadingString, textFontSize, Color.RED, 0, 0, true);
        }

        @Override
        protected void onPreExecute() {
            loadingDialog.show(getSupportFragmentManager(), "LoadingDialogTag");
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Log.i(TAG, "doInBackground() started.");

            // implement Retrofit to get results synchronously
            singersList = GetDataByRetrofitRestApi.getSingersBySingerType(singerTypeAsyncTask, pageSizeAsyncTask, pageNoAsyncTask);

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
                singersList.setPageNo(pageNoAsyncTask);
                singersList.setPageSize(pageSizeAsyncTask);
                singersList.setTotalRecords(0); // temporary
                singersList.setTotalPages(0);   // temporary

                singersListEmptyTextView.setText(failedMessage);
                singersListEmptyTextView.setVisibility(View.VISIBLE);
            } else {
                // successfully
                pageNo = singersList.getPageNo();      // get the back value from called function
                pageSize = singersList.getPageSize();    // get the back value from called function
                Log.i(TAG, "SingerListActivity-->pageNo = " + pageNo);
                Log.i(TAG, "SingerListActivity-->pageSize = " + pageSize);
                Log.i(TAG, "SingerListActivity-->totalRecords = " + singersList.getTotalRecords());
                Log.i(TAG, "SingerListActivity-->totalPages = " + singersList.getTotalPages());

                if (singersList.getSingers().size() == 0) {
                    singersListEmptyTextView.setText(noResultString);
                    singersListEmptyTextView.setVisibility(View.VISIBLE);
                } else {
                    singersListEmptyTextView.setVisibility(View.GONE);
                }

                mMyListAdapter = new MyListAdapter(getBaseContext(), R.layout.singers_list_item ,singersList.getSingers());
                singersListView.setAdapter(mMyListAdapter);
            }
        }
    }
}
