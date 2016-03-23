//FoodListAdapter.java
//By Chun Jie Tung & Yi Han Tee
//Custom array adapter for list view

package com.comp210p.eatlor;

import android.app.AlertDialog; //for alerting user
import android.content.Context;
import android.content.DialogInterface; //for setting up a dialog interface
import android.view.LayoutInflater; //for instantiating layout XML file into the View objects
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter; //for returning every item in the collection as a view
import android.widget.ImageButton; //for adding a delete button
import android.widget.TextView; //for displaying the inserted food choice

import java.util.ArrayList; //for creating an array for the food choice list

//class for the food list adapter
public class FoodListAdapter extends ArrayAdapter{
    private ArrayList<String> list = new ArrayList<String>(); //.create an array list object
    private Context context;
    private int layoutResourceId; //define the resource Id of the layout

    //constructor for the class
    public FoodListAdapter(Context context, int layoutResourceId, ArrayList<String> list) {
        super(context, layoutResourceId, list);
        this.layoutResourceId = layoutResourceId;
        this.list = list;
        this.context = context;
    }

    @Override
    //get view from the activity_receive_result layout
    public View getView(final int position, View view, ViewGroup parent) {
        //instantiate the layout when the view object does not have any references
        if (view == null) {
            LayoutInflater inflater =
                    (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_layout, null);
        }

        //display the food choice on the text view
        TextView listItemText = (TextView) view.findViewById(R.id.foodStringView);
        listItemText.setText(list.get(position));

        //get references to programmatically manipulated the deleteButton
        ImageButton deleteButton = (ImageButton)view.findViewById(R.id.deleteButton);

        //set deleteButton's OnClickListener
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            //call onClick when the deleteButton is pressed
            public void onClick(View view) {
                //display alert dialog when the user tries to delete an item from the list
                new AlertDialog.Builder(context)
                        .setTitle("Delete Warning") //set the title name of the dialog
                        .setMessage("Confirm delete?") //set the message in the dialog
                        //display an 'ok' button in the dialog interface and set its
                        //OnClickListener
                        .setPositiveButton(android.R.string.yes,
                                new DialogInterface.OnClickListener() {
                            //call onClick when the 'ok' is pressed
                            public void onClick(DialogInterface dialog, int which) {
                                list.remove(position); //remove the selected item from the list
                                notifyDataSetChanged(); //notify the adapter
                            }
                        })
                        //display a 'cancel' button in the dialog interface and set its
                        //OnClickListener
                        .setNegativeButton(android.R.string.no,
                                new DialogInterface.OnClickListener() {
                            //call onClick when the 'cancel' is pressed
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss(); //exit from the dialog
                            }
                        })
                        .setIcon(android.R.drawable.ic_delete) //set the resource Id of the icon
                        .show(); //display the alert dialog
            }
        });
        return view;
    }
}