package com.example.food;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;

public class NewFavorite extends AppCompatActivity {

    EditText New_Favorite_Cuisine_EditText;
    Button addNewFavoriteCuisineBtn, viewNewFavoriteCuisineBtn;
    static DatabaseHelper db;
    LottieAnimationView lottieAnimationViewNewFavorite;

    String id, name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_favorite);
        db = new DatabaseHelper(this);

        Toolbar toolbar = findViewById(R.id.toolbarNewFavoriteCuisine);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("New Favorite Cuisine");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.inflateMenu(R.menu.main_menu);
        toolbar.setTitleTextColor(Color.WHITE);

        New_Favorite_Cuisine_EditText = (EditText) findViewById(R.id.NewFavoriteCuisineEditText);
        addNewFavoriteCuisineBtn = (Button) findViewById(R.id.addNewFavoriteCuisineButton);
        viewNewFavoriteCuisineBtn = (Button) findViewById(R.id.viewNewFavoriteCuisineButton);

        lottieAnimationViewNewFavorite = findViewById(R.id.lottieNewFavorite);
        lottieAnimationViewNewFavorite.animate().setDuration(100).setStartDelay(4000);
        lottieAnimationViewNewFavorite.setRepeatCount(Animation.INFINITE);

        Intent i = getIntent();
        id = i.getStringExtra("id");
        name = i.getStringExtra("name");

    }

    public void addNewFavoriteCuisine(View view){
        String newEntry = New_Favorite_Cuisine_EditText.getText().toString();
        if(New_Favorite_Cuisine_EditText.length() !=0){
            AddFavorite(newEntry);
            New_Favorite_Cuisine_EditText.setText("");
        }
        else {
            Toast.makeText(getApplicationContext(),"You must put something", Toast.LENGTH_SHORT).show();
        }
    }

    public void AddFavorite(String newEntry){
        boolean insertData = db.insertNewFavoriteCuisine(id, newEntry);
        if(insertData == true){
            Toast.makeText(getApplicationContext(),"Successfully Entered Data!", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(getApplicationContext(),"something went wrong", Toast.LENGTH_SHORT).show();
        }
    }

    public void viewNewFavoriteCuisine(View view){
        Intent intent = new Intent(NewFavorite.this , FavoriteCuisineList.class);
        intent.putExtra("id", id+"");
        startActivity(intent);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(NewFavorite.this , FavoriteCuisineList.class);
        intent.putExtra("id", id+"");
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
                startActivity(new Intent(NewFavorite.this, SettingsActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}