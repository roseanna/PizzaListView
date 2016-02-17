package com.example.roseanna.pizzalistview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.util.Log;

import java.util.Arrays;

/**
 * Created by roseanna on 2/16/16.
 */
public class CheckboxAdaptor extends ArrayAdapter {
    private final Context context;
    private final String[] values;

    private int sum = 0;
    private MainActivity main;
    public CheckboxAdaptor(Context context, String[] values) {
        super(context, R.layout.rowlayout, values);
        this.context = context;
        this.values = values;
    }

    public void register(MainActivity m){
        this.main = m;
    }


    public void update(){
        main.setText();
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.rowlayout, parent, false);
        CheckBox cb = (CheckBox) rowView.findViewById(R.id.checkbox);

        cb.setText(values[position]);

        cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((CheckBox) v).isChecked();
                if (checked)
                    sum++;
                else
                    sum--;
                update();
            }
        });
        return rowView;
    }

    public int total(){
        return sum;
    }
}
