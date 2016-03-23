package com.comp210p.eatlor;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    Button addButton;
    Button sortButton;
    Button hitButton;
    ListView listView;
    ArrayList<String> listFoodChoices;
    FoodListAdapter adapter;
    Context context;
    View view;
    String passResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText) findViewById(R.id.editText);
        addButton = (Button) findViewById(R.id.addButton);
        sortButton = (Button) findViewById(R.id.sortButton);
        hitButton = (Button) findViewById(R.id.hitButton);
        listView = (ListView) findViewById(R.id.foodListView);
        listFoodChoices = new ArrayList<String>();
        adapter = new FoodListAdapter(MainActivity.this, R.layout.list_layout, listFoodChoices);
        listView.setAdapter(adapter);
        context = this;

        editText.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                editText.setCursorVisible(true);
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (editText.length() == 0)
                {
                    new AlertDialog.Builder(context)
                            .setTitle("No Text")
                            .setMessage("Required to insert food choice")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            })
                            .setIcon(R.drawable.warning)
                            .show();
                    editText.setCursorVisible(false);
                }

                else
                {
                    listFoodChoices.add(editText.getText().toString());
                    adapter.notifyDataSetChanged();
                    editText.setText("");
                    try {
                        InputMethodManager inputManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
                    }
                    catch (Exception e){ }
                    editText.setCursorVisible(false);
                }
            }
        });

        sortButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                adapter.sort(new Comparator<String>() {
                    @Override
                    public int compare(String lhs, String rhs) {
                        return lhs.compareTo(rhs);
                    }
                });
                adapter.notifyDataSetChanged();
            }
        });

        hitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(listFoodChoices.size() <= 1) {
                    new AlertDialog.Builder(context)
                            .setTitle("Insufficient food choice")
                            .setMessage("Required at least 2 food choices to random")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .setIcon(R.drawable.warning)
                            .show();
                }

                else {
                    passResult = randomFood(listFoodChoices);
                    onResult(view);
                }
            }
        });
    }

    private String randomFood(ArrayList<String> a) {
        int number = (int) (Math.random() * a.size());
        return a.get(number);
    }

    public void onResult(View view) {
        Intent intent = new Intent(this, ReceiveResult.class);
        intent.putExtra("food", passResult);
        startActivity(intent);
    }

}
