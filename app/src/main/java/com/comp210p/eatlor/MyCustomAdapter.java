package com.comp210p.eatlor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;


public class MyCustomAdapter extends ArrayAdapter implements ListAdapter {
    public ArrayList<String> list = new ArrayList<String>();
    public Context context;
    public int layoutResourceId;

    public MyCustomAdapter(Context context, int layoutResourceId, ArrayList<String> list) {
        super(context, layoutResourceId, list);
        this.layoutResourceId = layoutResourceId;
        this.list = list;
        this.context = context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_layout, null);
        }

        //Handle TextView and display string from your list
        TextView listItemText = (TextView)view.findViewById(R.id.foodStringView);
        listItemText.setText(list.get(position));

        //Handle buttons and add onClickListeners
        ImageButton deleteBtn = (ImageButton)view.findViewById(R.id.deleteButton);

        deleteBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //do something
                list.remove(position); //or some other task
                notifyDataSetChanged();
            }
        });
        return view;
    }
}