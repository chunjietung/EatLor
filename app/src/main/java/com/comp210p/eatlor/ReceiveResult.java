//ReceiveResult.java
//By Chun Jie Tung & Yi Han Tee
//Displaying the random result received from the main activity

package com.comp210p.eatlor;

import android.content.Intent;//for receiving data from the previous activity
import android.support.v7.app.AppCompatActivity; //base class
import android.os.Bundle; //for saving state information
import android.widget.TextView; //for displaying the final food choice

//Main class for the result activity
public class ReceiveResult extends AppCompatActivity {
    //called when the activity is first created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); //call superclass onCreate
        setContentView(R.layout.activity_receive_result); //inflate the GUI
        Intent intent = getIntent(); //get the message passed with the intent
        //get string from the previous activity
        String passResult = intent.getExtras().getString("food");
        //get references to programmatically manipulated TextView
        TextView resultView = (TextView) findViewById(R.id.resultTextView);
        resultView.setText(passResult); //display the string in the TextView
    }
}
