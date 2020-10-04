package com.smile.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.smile.androidsong.AndroidSongApp;
import com.smile.androidsong.R;
import com.smile.model.SingerType;
import com.smile.smilelibraries.utilities.ScreenUtil;

import java.util.ArrayList;
import java.util.List;

public class SingerTypesListViewAdapter extends ArrayAdapter {

    private final Context context;
    private final Activity activity;
    private final float textFontSize;
    private final int layoutId;
    private TextView positionNoTextView;
    private TextView singerAreaNaTextView;
    private TextView singerSexTextView;
    private ArrayList<SingerType> singerTypes;

    @SuppressWarnings("unchecked")
    public SingerTypesListViewAdapter(@NonNull Context context, float textFontSize, int resource, @NonNull List objects) {
        super(context, resource, objects);
        this.context = context;
        this.activity = (Activity)context;
        this.textFontSize = textFontSize;
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

        View view = activity.getLayoutInflater().inflate(layoutId, parent, false);

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
