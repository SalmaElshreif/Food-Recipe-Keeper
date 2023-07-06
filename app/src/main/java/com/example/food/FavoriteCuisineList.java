package com.example.food;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class FavoriteCuisineList extends AppCompatActivity {

    static DatabaseHelper db;
    ListView listViewFavorite;
    String id;
    String name;
    CuisineList cuisineList;
    FloatingActionButton fabAddNewFavoriteCuisine;


    public static ArrayList<Integer> listItemIds;
    public static ArrayList<Integer> listItemForeign;
    public static ArrayList<String> listItemName;

    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favorite_cuisine_list);

        Toolbar toolbar = findViewById(R.id.toolbarFavoriteCuisines);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Favorite Cuisines");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.inflateMenu(R.menu.main_menu);
        toolbar.setTitleTextColor(Color.WHITE);

        db = new DatabaseHelper(this);

        listItemForeign = new ArrayList<Integer>();
        listItemIds = new ArrayList<Integer>();
        listItemName = new ArrayList<String>();

        listViewFavorite = findViewById(R.id.favorite_cuisines_list);

        listViewFavorite.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int id = listItemIds.get(i);
                int idForeign = listItemForeign.get(i);
                String name = listItemName.get(i);
                Toast.makeText(FavoriteCuisineList.this, "Id " + id + " Foreign Id " + idForeign + " Name " + name, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(FavoriteCuisineList.this, DescriptionActivity.class);
                intent.putExtra("id", id+"");
                intent.putExtra("name", name);
                startActivity(intent);
                finish();

            }
        });


        Intent i = getIntent();
        id = i.getStringExtra("id");
        name = i.getStringExtra("name");
        showFavoriteCuisines(id);
    }

    public void addNewFavoriteCuisine(View view){
        Intent intent2 = new Intent(FavoriteCuisineList.this , NewFavorite.class);
        intent2.putExtra("id", id+"");
        startActivity(intent2);
        finish();
    }


    public void showFavoriteCuisines(String id){
        Cursor cursor = db.viewFavoriteCuisines(id);

        if(cursor.getCount()==0){
            Toast.makeText(getApplicationContext(),"The Database is Empty", Toast.LENGTH_SHORT).show();
        }
        else{
            while (cursor.moveToNext()){
                listItemIds.add(cursor.getInt(0));
                listItemForeign.add(cursor.getInt(1));
                listItemName.add(cursor.getString(2));
                ListAdapter listAdapterFavorite = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line,listItemName);
                listViewFavorite.setAdapter(listAdapterFavorite);

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
        startActivity(new Intent(FavoriteCuisineList.this, CuisineList.class));
        finish();
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
                startActivity(new Intent(FavoriteCuisineList.this, SettingsActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}