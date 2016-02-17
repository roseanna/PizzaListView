package com.example.roseanna.pizzalistview;

import android.app.Activity;
import android.app.ListActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.*;

public class MainActivity extends Activity  {
    LinearLayout ll;
    ListView listView;
    ListView vegList;
    TextView txtMsg;
    Spinner spinner;
    TextView result;
    TextView vegMessage;
    TextView meatMessage;
    int sizeCost = 0;
    int vChosen = 0;
    int mChosen = 0;
    int vMult = 1;
    int mMult = 1;
    int total = 0;

    String pSize = "Small";

    CheckboxAdaptor meat;
    CheckboxAdaptor veg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get ListView object from xml
        listView    = new ListView(this);
        vegList     = new ListView(this);
        txtMsg      = new TextView(this);
        spinner     = new Spinner(this);
        result      = new TextView(this);

        vegMessage = new TextView(this);
        meatMessage = new TextView(this);

        vegMessage.setText("Choose Vegetable Toppings");
        meatMessage.setText("Choose Meat Toppings");

        ll = (LinearLayout) findViewById(R.id.linear);
        // Defined Array values to show in ListView
        final String[] sizeOptions = {"Small", "Medium", "Large"};
        String[] vegToppings = {"Lettuce", "Spinach", "Mushroom"};
        String[] meatToppings = {"Sausage", "Pepperoni", "Chicken"};

        // Define a new Adapter
        // First parameter - Context
        // Second parameter - Layout for the row
        // Third parameter - ID of the TextView to which the data is written
        // Forth - the Array of data

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                sizeOptions);
        spinner.setAdapter(adapter);
        meat = new CheckboxAdaptor(this, meatToppings);
        veg = new CheckboxAdaptor(this, vegToppings);

        meat.register(this);
        veg.register(this);
        // Assign adapter to ListView
        listView.setAdapter(meat);
        vegList.setAdapter(veg);

        // ListView Item Select Listener
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                txtMsg.setText(sizeOptions[position]);
                switch (position) {
                    case 0:
                        sizeCost = 5;
                        pSize = "Small";
                        break;
                    case 1:
                        sizeCost = 7;
                        pSize = "Medium";
                        break;
                    case 2:
                        sizeCost = 10;
                        pSize = "Large";
                        break;
                }
                Log.i("sizeCost", String.valueOf(sizeCost));
                setText();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        ll.addView(spinner);
        ll.addView(meatMessage);
        ll.addView(listView);
        ll.addView(vegMessage);
        ll.addView(vegList);
        ll.addView(result);
    }

    public void calculate(){
        vChosen = veg.total();
        mChosen = meat.total();

        total = 0;
        if (sizeCost == 5) {
            vMult = 1;
            mMult = 2;
        }
        else if (sizeCost == 7){
            vMult = 2;
            mMult = 4;
        }
        else{
            vMult = 3;
            mMult = 6;
        }
        total = sizeCost + (vMult * vChosen) + (mMult * mChosen);

        Log.i("calculate", String.valueOf(total));
    }

    public void setText(){
        calculate();
        String resultString = "Your total for a " + pSize + " pizza with ";
        resultString += String.valueOf(mChosen) + " meat toppings, " + String.valueOf(vChosen) + " veggie toppings is $";
        resultString += String.valueOf(total);
        result.setText(resultString);
    }
}