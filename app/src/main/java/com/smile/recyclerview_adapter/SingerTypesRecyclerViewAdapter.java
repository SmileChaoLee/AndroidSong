package com.smile.recyclerview_adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.smile.androidsong.AndroidSongApp;
import com.smile.androidsong.R;
import com.smile.model.SingerType;
import com.smile.smilelibraries.utilities.ScreenUtil;

import java.util.ArrayList;

public class SingerTypesRecyclerViewAdapter extends RecyclerView.Adapter<SingerTypesRecyclerViewAdapter.MyViewHolder> {
    private static final String TAG = "SingerTypesRecyclerViewAdapter";
    private ArrayList<SingerType> singerTypes;
    private final float textFontSize;
    public SingerTypesRecyclerViewAdapter(float textFontSize, ArrayList<SingerType> singerTypes) {
        this.textFontSize = textFontSize;
        this.singerTypes = singerTypes;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView positionNoTextView;
        private TextView singerAreaNaTextView;
        private TextView singerSexTextView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            positionNoTextView = itemView.findViewById(R.id.singerTypeItem_Layout_positionNoTextView);
            ScreenUtil.resizeTextSize(positionNoTextView, textFontSize, AndroidSongApp.FontSize_Scale_Type);

            singerAreaNaTextView = itemView.findViewById(R.id.singerAreaNaTextView);
            ScreenUtil.resizeTextSize(singerAreaNaTextView, textFontSize, AndroidSongApp.FontSize_Scale_Type);

            singerSexTextView = itemView.findViewById(R.id.singerSexTextView);
            ScreenUtil.resizeTextSize(singerSexTextView, textFontSize, AndroidSongApp.FontSize_Scale_Type);
        }
    }

    // Usually involves inflating a layout from XML and returning the holder
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        // inflate the singerType item view
        View singerTypeItemView = layoutInflater.inflate(R.layout.singer_types_list_item, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(singerTypeItemView);
        //
        return myViewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        SingerType singerType = singerTypes.get(position);

        holder.positionNoTextView.setText(String.valueOf(position));
        holder.singerAreaNaTextView.setText(singerType.getAreaNa());
        String sex = singerType.getSex();
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
        holder.singerSexTextView.setText(sexString);
    }

    @Override
    public int getItemCount() {
        int size = singerTypes.size();
        Log.d(TAG, "singerTypes.size() = " + size);
        return size;
    }
}
