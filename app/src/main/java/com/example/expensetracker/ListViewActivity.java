package com.example.expensetracker;

import android.os.Bundle;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ListViewActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<Person> personList;
    PersonAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);

        listView = findViewById(R.id.listView);

        personList = new ArrayList<>();

        // Add sample data
        personList.add(new Person("John", "Nairobi", "0700000000"));
        personList.add(new Person("Mary", "Kisumu", "0711111111"));
        personList.add(new Person("David", "Mombasa", "0722222222"));
        personList.add(new Person("Judas", "Kajiando", "0755555555"));
        personList.add(new Person("Kerumbo", "Kitui", "07111143156"));
        personList.add(new Person("Mercy", "Kakamega", "072268981222"));
        personList.add(new Person("Joan", "Nakuru", "07008597800"));
        personList.add(new Person("Garret", "West Pokot", "0716411111"));
        personList.add(new Person("Delilah", "Marsabit", "07222227895422"));

        adapter = new PersonAdapter(this, personList);
        listView.setAdapter(adapter);
    }
}