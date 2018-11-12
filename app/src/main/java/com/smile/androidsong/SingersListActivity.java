package com.smile.androidsong;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.smile.model.SingerData;

import java.util.List;


public class SingersListActivity extends ListActivity {

    private List<SingerData> singers = null;
    private int pageNo = 1;
    private int pageSize = 10;
    private int pages = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singers_list);

        String option = "M";    // default is male singers

        TextView textview = (TextView) findViewById(R.id.singerListTextView);

        Bundle extras = getIntent().getExtras();
        if (extras!=null)
        {
            option = extras.getString("option").trim();
        }

        Button firstPageButton = (Button) findViewById(R.id.firstPageButton);
        firstPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstPage();
            }
        });

        Button previousPageButton = (Button) findViewById(R.id.previousPageButton);
        previousPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                previousPage();
            }
        });

        Button nextPageButton = (Button) findViewById(R.id.nextPageButton);
        nextPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextPage();
            }
        });

        Button lastPageButton = (Button) findViewById(R.id.lastPageButton);
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
    }

    private void firstPage() {
        pageNo = 1;
        displayListView();
    }

    private void previousPage() {
        pageNo--;
        if (pageNo < 1) {
            pageNo = 1;
        }
        displayListView();
    }

    private void nextPage() {
        pageNo++;
        if (pageNo > pages) {
            pageNo = pages;
        }
        displayListView();
    }

    private void lastPage() {
        pageNo = pages;
        displayListView();
    }

    private void returnToPrevious() {
        finish();
    }

    private void queryOnePageOfData() {
        // access data from backend
    }

    private void displayListView() {

    }

    private class mListAdapter extends BaseAdapter {

        private String text1[] , text2[];  // or private String[] text1,text2;

        public mListAdapter() {
            this.text1 = new String[] {"No initialization"};
            this.text2 = new String[] {"No initialization"};
        }

        public mListAdapter(String[] text1, String[] text2) {
            this.text1 = text1;
            this.text2 = text2;
        }

        @Override
        public int getCount() {
            return this.text1.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup container) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.singers_list_item, container, false);
            }

            TextView vText1, vText2;
            vText1 = (TextView) convertView.findViewById(R.id.text1);
            vText1.setText(this.text1[position]);
            vText2 = (TextView) convertView.findViewById(R.id.text2);
            vText2.setText(this.text2[position]);

            // Because the list item contains multiple touch targets, you should not override
            // onListItemClick. Instead, set a click listener for each target individually.

            convertView.findViewById(R.id.primary_target).setOnClickListener(

                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast.makeText(SingersListActivity.this,
                                    text1[position],
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
            );

            return convertView;
        }
    }

    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        returnToPrevious();
    }
}
