//MainActivity.java
//By Chun Jie Tung & Yi Han Tee
//Randomise an item from a list of items

package com.comp210p.eatlor;

import android.app.AlertDialog; //for alerting user
import android.content.Context;
import android.content.DialogInterface; //for setting up a dialog interface
import android.content.Intent; //for invoking and passing data to the next activity
import android.support.v7.app.AppCompatActivity; //base class
import android.os.Bundle; //for saving state information
import android.view.View;
import android.view.inputmethod.InputMethodManager; //for changing the keyboard setting
import android.widget.EditText; //for food choice input
import android.widget.Button; //for adding food choice, random food choice and arranging food choices alphabetically
import android.widget.ListView; //for displaying list

import java.util.ArrayList; //for creating an array for the food choice list
import java.util.Comparator; //for comparing the strings

//MainActivity class for the Eat Lor app
public class MainActivity extends AppCompatActivity {
    EditText editText; //for user to input food choice
    Button addButton;  //add food choice into the list
    Button sortButton; //sort the food choices alphabetically in the list
    Button hitButton;  //random a food choice out of the list
    ListView foodListView; //display all the inserted food choices
    ArrayList<String> listFoodChoices; //initialize an array list
    FoodListAdapter adapter; //create an adapter to access the data in the array list
    Context context;
    String passResult; //a random food choice in the list

    //called when the activity is first created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); //call superclass onCreate
        setContentView(R.layout.activity_main); //inflate the GUI

        //get references to programmatically manipulated EditText,Buttons and ListView
        editText = (EditText) findViewById(R.id.editText);
        addButton = (Button) findViewById(R.id.addButton);
        sortButton = (Button) findViewById(R.id.sortButton);
        hitButton = (Button) findViewById(R.id.hitButton);
        foodListView = (ListView) findViewById(R.id.foodListView);

        context = this;

        //construct array list object
        listFoodChoices = new ArrayList<String>();

        //construct an adapter
        adapter = new FoodListAdapter(MainActivity.this, R.layout.list_layout, listFoodChoices);

        //set the adapter for the food list
        foodListView.setAdapter(adapter);


        //set editText's OnClickListener
        editText.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                editText.setCursorVisible(true); //display cursor when click on editText
            }
        });

        //set addButton's OnClickListener
        addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //avoid empty text to be inserted into the list
                if (editText.length() == 0)
                {
                    //display alert dialog if no character is being typed in the editText
                    new AlertDialog.Builder(context)
                            //set title name of dialog
                            .setTitle("No Text")
                            // set the message of the dialog
                            .setMessage("Required to insert food choice")
                            //display an 'ok' button in the dialog interface and set its
                            //OnClickListener
                            .setPositiveButton(android.R.string.yes,
                                    new DialogInterface.OnClickListener() {
                                        //call onClick method when the 'ok' button is clicked
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss(); //dialog will dismiss
                                        }
                            })
                            .setIcon(R.drawable.warning) //set the resource Id of the icon
                            .show(); //display the dialog
                    editText.setCursorVisible(false); //remove cursor in the editText
                }
                else
                {
                    //display the inserted food choice in the listView
                    //retrieve the text from the editText and add it into the list
                    listFoodChoices.add(editText.getText().toString());
                    adapter.notifyDataSetChanged(); //notify the adapter of array list
                    editText.setText(""); //set the editText to empty after pressing the add button
                    //hide the keyboard
                    InputMethodManager keyboard =
                            (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    keyboard.hideSoftInputFromWindow(editText.getWindowToken(), 0);
                    editText.setCursorVisible(false); //remove cursor in the editText
                }
            }
        });

        //set sortButton's OnClickListener
        sortButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //sort the food choices in the listView alphabetically
                adapter.sort(new Comparator<String>() {
                    @Override
                    public int compare(String lhs, String rhs) {
                        return lhs.compareTo(rhs);
                    }
                });
                adapter.notifyDataSetChanged(); //notify the adapter of array list
            }
        });

        //set hitButton's OnClickListener
        hitButton.setOnClickListener(new View.OnClickListener() {
            //call onClick when the hitButton is pressed
            public void onClick(View view) {
                //random function will only run when more than 1 food choice is inserted into the
                //list
                if(listFoodChoices.size() <= 1) {
                    //display alert dialog when user tries to random 1 or 0 food choice
                    new AlertDialog.Builder(context)
                            //set a title name to the dialog
                            .setTitle("Insufficient food choice")
                            //set the message of the dialog
                            .setMessage("Required at least 2 food choices to random")
                            .setPositiveButton(android.R.string.yes,
                                    new DialogInterface.OnClickListener() {
                                //call onClick method when 'ok' button is clicked
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss(); //dismiss the dialog
                                }
                            })
                            .setIcon(R.drawable.warning) //set the resource Id of the icon
                            .show(); //display the dialog
                }
                else {
                    //call randomFood method to get a randomize food choice from the list
                    passResult = randomFood(listFoodChoices);
                    //call onResult method
                    onResult(view);
                }
            }
        });
    }

    //define a random food class that random a food choice from the list
    private String randomFood(ArrayList<String> a) {
        int number = (int) (Math.random() * a.size()); //function to random a number
        return a.get(number); //return the string in the array
    }

    //define onResult method
    public void onResult(View view) {
        Intent intent = new Intent(this, ReceiveResult.class); //create an object from Intent class
        intent.putExtra("food", passResult); //send passResult string to the next activity
        startActivity(intent); //invoke receive_result activity
    }
}
