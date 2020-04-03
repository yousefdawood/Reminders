package com.example.reminders;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;


public class CustomListView extends ArrayAdapter<String> {

    private ArrayList<String> description;
    private ArrayList<Integer> imgId;
    private Activity context;

    public CustomListView(Activity context, ArrayList<String> description, ArrayList<Integer> imgId) {
        super(context, R.layout.reminder_row, description);
        this.description = description;
        this.context = context;
        this.imgId = imgId;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View r = convertView;
        ViewHolder viewHolder = null;
        if(r == null)
        {
            LayoutInflater layoutInflater = context.getLayoutInflater();
            r = layoutInflater.inflate(R.layout.reminder_row, null, true);
            viewHolder = new ViewHolder(r);
            r.setTag(viewHolder);

        }
        else{
            viewHolder = (ViewHolder) r.getTag();

        }
        viewHolder.ivw.setImageResource(imgId.get(position));
        viewHolder.tvw.setText(description.get(position));
        return r;

    }

    class ViewHolder
    {
        TextView tvw;
        ImageView ivw;
        ViewHolder (View v){
            tvw = (TextView) v.findViewById(R.id.text);
            ivw = (ImageView) v.findViewById(R.id.image);
        }
    }
}
