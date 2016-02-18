package com.example.roseanna.pizzalistview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import java.util.*;
public class MainActivity extends Activity  {
    LinearLayout ll;
    ListView meatList;
    ListView vegList;

    Spinner spinner;

    TextView txtMsg;
    TextView result;
    TextView vegMessage;
    TextView meatMessage;

    Button placeOrder;

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

        // XML OBJECTS
        ll          = (LinearLayout) findViewById(R.id.linear);
        meatList    = new ListView(this);
        vegList     = new ListView(this);

        spinner     = new Spinner(this);

        placeOrder  = new Button(this);

        txtMsg      = new TextView(this);
        result      = new TextView(this);
        vegMessage  = new TextView(this);
        meatMessage = new TextView(this);
        ll = (LinearLayout) findViewById(R.id.linear);

        // DATA ARRAYS
        final String[] sizeOptions  = {"Small", "Medium", "Large"};
        String[] vegToppings        = {"Lettuce", "Spinach", "Mushroom"};
        String[] meatToppings       = {"Sausage", "Pepperoni", "Chicken"};
        int[] meatImage             = new int[]{R.drawable.sausage, R.drawable.meat_pepperoni, R.drawable.chicken};
        int[] vegImage              = new int[]{R.drawable.lettuce, R.drawable.spinach, R.drawable.mushroom};

        // ADAPTERS
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_dropdown_item, sizeOptions);
        meat    = new CheckboxAdaptor(this, meatToppings, meatImage);
        veg     = new CheckboxAdaptor(this, vegToppings, vegImage);

        spinner.setAdapter(adapter);
        meatList.setAdapter(meat);
        vegList.setAdapter(veg);

        // EDITS TO XML OBJECTS
        vegMessage.setText("Choose Vegetable Toppings");
        meatMessage.setText("Choose Meat Toppings");
        placeOrder.setText("Place Order");

        meat.register(this);
        veg.register(this);


        // ONCLICK
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
        placeOrder.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ArrayList<String> meatIngredients = meat.getIngredients();
                ArrayList<String> vegIngredients  = veg.getIngredients();

                // create intent to call Activity2
                Intent place = new Intent (MainActivity.this,
                        PlaceOrder.class);
//                 create a container to ship data
                Bundle myData = new Bundle();

//                 add <key,value> data items to the container
                myData.putString("Size", pSize);
                myData.putString("Cost", String.valueOf(total));
                myData.putStringArrayList("meatIngredients", meatIngredients);
                myData.putStringArrayList("vegIngredients", vegIngredients);

                // attach the container to the intent
                place.putExtras(myData);

                // call Activity2, tell your local listener to wait response
                startActivityForResult(place, 101);
            }

        });

        // ADDING THE XML TO VIEW
        ll.addView(spinner);
        ll.addView(meatMessage);
        ll.addView(meatList);
        ll.addView(vegMessage);
        ll.addView(vegList);
        ll.addView(result);
        ll.addView(placeOrder);
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
    }

    public void setText(){
        calculate();
        String resultString = "Your total for a " + pSize + " pizza with ";
        resultString += String.valueOf(mChosen) + " meat toppings, " + String.valueOf(vChosen) + " veggie toppings is $";
        resultString += String.valueOf(total);
        result.setText(resultString);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try	{
            if ((requestCode == 101 ) && (resultCode == Activity.RESULT_OK)){
                Bundle myResults = data.getExtras();
                Double vresult = myResults.getDouble("vresult");
                result.setText("The total is " + vresult);
            }
        }
        catch (Exception e) {
            result.setText("Problems - " + requestCode + " " + resultCode);
        }
    }//onActivityResult
}