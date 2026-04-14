package com.example.expensetracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class PersonAdapter extends BaseAdapter {

    Context context;
    List<Person> personList;

    public PersonAdapter(Context context, List<Person> personList) {
        this.context = context;
        this.personList = personList;
    }

    @Override
    public int getCount() {
        return personList.size();
    }

    @Override
    public Object getItem(int position) {
        return personList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.list_item, parent, false);
        }

        TextView tvName = convertView.findViewById(R.id.tvName);
        TextView tvLocation = convertView.findViewById(R.id.tvLocation);
        TextView tvPhone = convertView.findViewById(R.id.tvPhone);

        Person person = personList.get(position);

        tvName.setText(person.getName());
        tvLocation.setText(person.getLocation());
        tvPhone.setText(person.getPhone());

        return convertView;
    }
}