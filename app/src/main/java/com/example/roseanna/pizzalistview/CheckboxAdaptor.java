package com.example.roseanna.pizzalistview;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import android.util.Log;

import java.util.*;
import java.io.*;
import android.graphics.*;
import java.net.*;

/**
 * Created by roseanna on 2/16/16.
 */
public class CheckboxAdaptor extends ArrayAdapter {
    private final Context context;
    private final String[] values;
    ArrayList<String> ingredients = new ArrayList<>();
    int[] pics = null;
    private int sum = 0;
    private MainActivity main;
    public CheckboxAdaptor(Context context, String[] values) {
        super(context, R.layout.rowlayout, values);
        this.context    = context;
        this.values     = values;
    }
    public CheckboxAdaptor(Context context, String[] values, int[] pics) {
        super(context, R.layout.rowlayout, values);
        this.context    = context;
        this.values     = values;
        this.pics       = pics;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView            = inflater.inflate(R.layout.rowlayout, parent, false);
        CheckBox cb             = (CheckBox) rowView.findViewById(R.id.checkbox);
        ImageView imageView     = (ImageView) rowView.findViewById(R.id.pic);
        TextView textView       = (TextView) rowView.findViewById(R.id.checkText);

        textView.setText(values[position]);
        if (pics != null)
            imageView.setImageResource(pics[position]);
        Log.i("checkbox", "clicked");
        cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((CheckBox) v).isChecked();
                if (checked) {
                    sum++;
                    ingredients.add(values[position]);
                } else {
                    sum--;
                    ingredients.remove(((CheckBox) v).getText().toString());
                }
                main.setText();
            }
        });
        return rowView;
    }

    public void register(MainActivity m){
        this.main = m;
    }
    public int total(){
        return sum;
    }
    public ArrayList<String> getIngredients(){
        return ingredients;
    }
}
