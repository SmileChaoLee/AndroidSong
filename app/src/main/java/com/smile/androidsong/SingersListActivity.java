package com.smile.androidsong;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.smile.Utility.FontSizeAndTheme;
import com.smile.dao.GetDataByRestApi;
import com.smile.model.Singer;
import com.smile.model.SingerType;
import com.smile.smilepublicclasseslibrary.alertdialogfragment.AlertDialogFragment;

import java.util.ArrayList;
import java.util.List;


public class SingersListActivity extends AppCompatActivity {

    private float textFontSize;
    private ListView singersListView;
    private MyListAdapter mMyListAdapter;
    private ArrayList<Singer> singersList = null;
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
                Singer singer = singersList.get(i);
                if (singer.getId() != failedItemNo) {
                    // not the failed item
                    Toast.makeText(SingersListActivity.this, singer.toString(), Toast.LENGTH_LONG).show();
                }
            }
        });

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
            singerNoTextView.setText(singers.get(position).getSingerNo());

            singerNaTextView = view.findViewById(R.id.singerNaTextView);
            singerNaTextView.setText(singers.get(position).getSingerNa());

            return view;
        }
    }

    private class AccessSingersAsyncTask extends AsyncTask<Void, Void, Void> {

        private final String TAG = new String("AccessSingersAsyncTask");
        private final String errorMessage = getApplicationContext().getString(R.string.failedMessage);
        private final String loadingString = getApplicationContext().getString(R.string.loadingString);
        private Animation animationText;
        private TextView loadingTextView = null;
        private AlertDialogFragment loadingDialog;

        private SingerType singerTypeAsyncTask;
        private int[] pageSizeAsyncTask = new int[1]; // in order to get the value back from called function
        private int[] pageNoAsyncTask = new int[1];  // in order to get the value back from called function

        public AccessSingersAsyncTask(SingerType singerTypeAsyncTask, int pageSizeAsyncTask, int pageNoAsyncTask) {
            this.singerTypeAsyncTask = singerTypeAsyncTask;
            this.pageSizeAsyncTask[0] = pageSizeAsyncTask;
            this.pageNoAsyncTask[0] = pageNoAsyncTask;
            animationText = new AlphaAnimation(0.0f,1.0f);
            animationText.setDuration(300);
            animationText.setStartOffset(0);
            animationText.setRepeatMode(Animation.REVERSE);
            animationText.setRepeatCount(Animation.INFINITE);

            loadingDialog = AlertDialogFragment.newInstance(loadingString, textFontSize, Color.RED, 0, 0);
        }

        @Override
        protected void onPreExecute() {
            loadingDialog.show(getSupportFragmentManager(), "LoadingDialogTag");
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Log.i(TAG, "doInBackground() started.");

            final int timeDelay = 300;

            // wait for a little bit
            try { Thread.sleep(timeDelay); } catch (InterruptedException ex) { ex.printStackTrace(); }

            while (loadingTextView == null) {
                loadingTextView = loadingDialog.getText_shown();
                SystemClock.sleep(timeDelay);
            }

            publishProgress();
            // wait for a little bit
            try { Thread.sleep(timeDelay); } catch (InterruptedException ex) { ex.printStackTrace(); }

            singersList = GetDataByRestApi.getSingersBySingerType(singerTypeAsyncTask, pageSizeAsyncTask, pageNoAsyncTask);
            if (singersList == null) {
                singersList = new ArrayList<>();
                Singer singer = new Singer(failedItemNo,"", errorMessage);
                singersList.add(singer);
            } else {
                pageNo = pageNoAsyncTask[0];      // get the back value from called function
                pageSize = pageSizeAsyncTask[0];    // get the back value from called function
                Log.i(TAG, "SingerListActivity-->pageNo = " + pageNo);
                Log.i(TAG, "SingerListActivity-->pageSize = " + pageSize);
            }

            Log.i(TAG, "doInBackground() finished.");

            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            try {
                if ( (animationText != null) && (loadingTextView != null) ) {
                    loadingTextView.startAnimation(animationText);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            Log.i(TAG, "onPostExecute() started.");
            if (animationText != null) {
                if (loadingTextView != null) {
                    loadingTextView.clearAnimation();
                    loadingTextView.setText("");
                }
                animationText = null;
            }
            loadingDialog.dismissAllowingStateLoss();

            mMyListAdapter = new MyListAdapter(getBaseContext(), R.layout.singers_list_item ,singersList);
            singersListView.setAdapter(mMyListAdapter);
        }
    }
}
