package com.smile.androidsong;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.smile.dao.GetDataByRestApi;
import com.smile.model.SingerType;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class SingerTypesListActivity extends AppCompatActivity {

    private ListView singerTypesListView;
    private MyListAdapter mMyListAdapter;
    private ArrayList<SingerType> singerTypesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singer_types_list);

        TextView optionTitle = (TextView) findViewById(R.id.singerTypesListMenuTextView);
        singerTypesListView = findViewById(R.id.singerTypesListView);
        Button returnToPreviousButton = findViewById(R.id.returnToPreviousButton);
        returnToPreviousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnToPrevious();
            }
        });

        singerTypesList = GetDataByRestApi.getSingerTypes();

        mMyListAdapter = new MyListAdapter(this, R.layout.singer_types_list_item ,singerTypesList);
        singerTypesListView.setAdapter(mMyListAdapter);
        singerTypesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(), singerTypesList.get(i).toString(), Toast.LENGTH_LONG);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        returnToPrevious();
    }

    private void returnToPrevious() {
        finish();
    }

    private class MyListAdapter extends ArrayAdapter {

        int layoutId;
        TextView singerAreaNoTextView;
        TextView singerAreaNaTextView;
        TextView singerSexTextView;
        ArrayList<SingerType> singerTypes;

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

        @Override
        public int getPosition(@Nullable Object item) {
            return super.getPosition(item);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            View view = getLayoutInflater().inflate(layoutId, parent, false);

            singerAreaNoTextView = view.findViewById(R.id.singerAreaNoTextView);
            singerAreaNoTextView.setText(singerTypes.get(position).getAreaNo());

            singerAreaNaTextView = view.findViewById(R.id.singerAreaNaTextView);
            singerAreaNaTextView.setText(singerTypes.get(position).getAreaNa());

            singerSexTextView = view.findViewById(R.id.singerSexTextView);
            int sex = singerTypes.get(position).getSex();
            String sexString;
            switch (sex) {
                case 1:
                    sexString = "Male";
                    break;
                case 2:
                    sexString = "Female";
                    break;
                default:
                    // 0
                    sexString = "";
                    break;
            }
            singerSexTextView.setText(sexString);

            return view;
        }
    }
}
