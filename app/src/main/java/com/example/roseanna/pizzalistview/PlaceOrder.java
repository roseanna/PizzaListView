package com.example.roseanna.pizzalistview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.*;

import java.util.ArrayList;

/**
 * Created by roseanna on 2/16/16.
 */

public class PlaceOrder extends AppCompatActivity implements View.OnClickListener {
    EditText dataReceived;
    Button btnDone;
    String size;
    ArrayList<String> meatIngredients;
    ArrayList<String> vegIngredients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.place_order);

        dataReceived = (EditText) findViewById(R.id.etDataReceived);
        btnDone = (Button) findViewById(R.id.btnDone);
        btnDone.setOnClickListener(this);

        // pick call made to Activity2 via Intent
        Intent myLocalIntent = getIntent();

        // look into the bundle sent to Activity2 for data items
        Bundle myBundle =  myLocalIntent.getExtras();
        size            = myBundle.getString("Size");
        meatIngredients = myBundle.getStringArrayList("meatIngredients");
        vegIngredients  = myBundle.getStringArrayList("vegIngredients");

        // for illustration purposes. show data received & result
        dataReceived.setText(getOrder());
        myBundle.putDouble("vresult", 100);
        // attach updated bumble to invoking intent
        myLocalIntent.putExtras(myBundle);

        // return sending an OK signal to calling activity
        setResult(Activity.RESULT_OK, myLocalIntent);

        // experiment: remove comment
//         finish();

    }

    public String getOrder(){
        String result = "You ordered a " + size + " pizza. \n";
        if (meatIngredients.size() > 0) {
            result += "Meat ingredients: \n";
            for (String ing : meatIngredients) {
                result += ing + " \n";
            }
        }
        if (vegIngredients.size() > 0) {
            result += "Vegetable ingredients: \n";
            for (String ing : vegIngredients) {
                result += ing + " \n";
            }
        }
        return result;
    }

    @Override
    public void onClick(View v) {
        finish();
    }
}
