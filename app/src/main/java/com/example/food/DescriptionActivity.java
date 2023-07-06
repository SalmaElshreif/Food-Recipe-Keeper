package com.example.food;


import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.airbnb.lottie.LottieAnimationView;

import java.util.ArrayList;

public class DescriptionActivity extends AppCompatActivity {

    static DatabaseHelper db;
    ListView listViewDescription;
    String id, name;
    LottieAnimationView lottieAnimationDescription;

    public static ArrayList<Integer> listItemIds;
    public static ArrayList<Integer> listItemForeign;
    public static ArrayList<String> listItemDescription;

    public static ArrayAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.description_activity);

        Toolbar toolbar = findViewById(R.id.toolbarDescription);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Description");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.inflateMenu(R.menu.main_menu);
        toolbar.setTitleTextColor(Color.WHITE);

        db = new DatabaseHelper(this);

        listItemForeign = new ArrayList<Integer>();
        listItemIds = new ArrayList<Integer>();
        listItemDescription = new ArrayList<String>();

        listViewDescription = findViewById(R.id.favorite_cuisines_list);


        lottieAnimationDescription = findViewById(R.id.lottieDescription);
        lottieAnimationDescription.animate().setDuration(3000).setStartDelay(4000);

        listViewDescription.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int id = listItemIds.get(i);
                int idForeign = listItemForeign.get(i);
                String description = listItemDescription.get(i);
                Toast.makeText(DescriptionActivity.this, "Id " + id + " Foreign Id " + idForeign + " Description " + description, Toast.LENGTH_SHORT).show();
            }
        });


        Intent i = getIntent();
        id = i.getStringExtra("id");
        name = i.getStringExtra("name");
        showDescription(id);
    }



    public void addNewDescription(View view){
        Intent intent2 = new Intent(DescriptionActivity.this , NewDescriptionActivity.class);
        intent2.putExtra("id", id+"");
        startActivity(intent2);
        finish();
    }

    public void showDescription(String id){
        Cursor cursor = db.viewDescription(id);

        if(cursor.getCount()==0){
            Toast.makeText(getApplicationContext(),"The Database is Empty", Toast.LENGTH_SHORT).show();
        }
        else{
            while (cursor.moveToNext()){
                listItemIds.add(cursor.getInt(0));
                listItemForeign.add(cursor.getInt(1));
                listItemDescription.add(cursor.getString(2));
                ListAdapter listAdapterFavorite = new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1,listItemDescription);
                listViewDescription.setAdapter(listAdapterFavorite);

                registerForContextMenu(listViewDescription);

            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(DescriptionActivity.this , CuisineList.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_settings:
                startActivity(new Intent(DescriptionActivity.this, SettingsActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}