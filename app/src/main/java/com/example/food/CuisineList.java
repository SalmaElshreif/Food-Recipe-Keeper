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

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class CuisineList extends AppCompatActivity {

    static DatabaseHelper db;

    ListView listView;
    FloatingActionButton fabAddNewCuisine;
    LottieAnimationView lottieAnimationViewCuisine;


    public static ArrayList<String> listItem;
    public static ArrayList<Integer> listItemIds;
    public static ArrayAdapter adapter;

    long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cuisine_list);

        Toolbar toolbar = findViewById(R.id.toolbarCuisine);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Cuisines");
        toolbar.inflateMenu(R.menu.main_menu);
        toolbar.setTitleTextColor(Color.WHITE);

        db = new DatabaseHelper(this);


        listItem = new ArrayList<String>();
        listItemIds = new ArrayList<Integer>();

        listView = findViewById(R.id.cuisines_list);

        fabAddNewCuisine = (FloatingActionButton) findViewById(R.id.fabAddNewCuisine);

        lottieAnimationViewCuisine = findViewById(R.id.lottieCuisine);
        lottieAnimationViewCuisine.animate().setDuration(3000).setStartDelay(4000);

        showCuisines();


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String name = listItem.get(i);
                int id = listItemIds.get(i);
                Toast.makeText(CuisineList.this, name, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(CuisineList.this, FavoriteCuisineList.class);
                intent.putExtra("id", id+"");
                intent.putExtra("name", name);
                startActivity(intent);
                finish();

            }
        });

    }

    public void addNewCuisine(View view) {
        Intent intent2 = new Intent(CuisineList.this, NewCuisine.class);
        startActivity(intent2);
        finish();
    }


    public void showCuisines() {
        Cursor cursor = db.viewCuisines();

        if (cursor.getCount() == 0) {
            Toast.makeText(getApplicationContext(), "The Database is Empty", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                listItem.add(cursor.getString(1));
                listItemIds.add(cursor.getInt(0));
                ListAdapter listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, listItem);
                listView.setAdapter(listAdapter);

                registerForContextMenu(listView);
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
                startActivity(new Intent(CuisineList.this, SettingsActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
        menu.setHeaderTitle("Select Action");
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        AdapterView.AdapterContextMenuInfo i = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        if (item.getItemId() == R.id.delete) {
            int id = listItemIds.get(i.position);
            SQLiteDatabase database = db.getWritableDatabase();
            String selection = "cuisine_id=?";
            String[] selectionArgs = {id + ""};
            db.delete("Cuisine_Table", selection, selectionArgs);
            listItem.remove(i.position);
            listItemIds.remove(i.position);

            ArrayAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, listItem);
            listView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            return true;
        } else if(item.getItemId() == R.id.edit) {
            String name = listItem.get(i.position);
            int id = listItemIds.get(i.position);
//            Toast.makeText(getApplicationContext(), name + id, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(CuisineList.this , EditActivity.class);
            intent.putExtra("id" , id);
            intent.putExtra("name" , name);
            startActivity(intent);
            finish();
            return true;

        }
        else {
            return super.onContextItemSelected(item);
        }

    }


    }
