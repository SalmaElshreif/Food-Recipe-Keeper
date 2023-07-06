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

public class NewCuisine extends AppCompatActivity {

    EditText New_Cuisine_EditText;
    Button addNewCuisineBtn, viewNewCuisineBtn;
    static DatabaseHelper db;
    LottieAnimationView lottieAnimationViewNewCuisine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_cuisine);
        db = new DatabaseHelper(this);

        New_Cuisine_EditText = (EditText) findViewById(R.id.NewCuisineEditText);
        addNewCuisineBtn = (Button) findViewById(R.id.addNewCuisineButton);
        viewNewCuisineBtn = (Button) findViewById(R.id.viewNewCuisineButton);

        Toolbar toolbar = findViewById(R.id.toolbarNewCuisine);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("New Cuisine");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.inflateMenu(R.menu.main_menu);
        toolbar.setTitleTextColor(Color.WHITE);

        lottieAnimationViewNewCuisine = findViewById(R.id.lottieNewCuisine);
        lottieAnimationViewNewCuisine.animate().setDuration(3000).setStartDelay(4000);
        lottieAnimationViewNewCuisine.setRepeatCount(Animation.INFINITE);

    }

    public void addNewCuisine(View view){
        String newEntry = New_Cuisine_EditText.getText().toString();
        if(New_Cuisine_EditText.length() !=0){
            AddCuisine(newEntry);
            New_Cuisine_EditText.setText("");
        }
        else {
            Toast.makeText(getApplicationContext(),"You must put something", Toast.LENGTH_SHORT).show();
        }
    }

    public void AddCuisine(String newEntry){
        boolean insertData = db.insertNewCuisine(newEntry);
        if(insertData == true){
            Toast.makeText(getApplicationContext(),"Successfully Entered Data!", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(getApplicationContext(),"something went wrong", Toast.LENGTH_SHORT).show();
        }
    }

    public void viewNewCuisine(View view){
        Intent intent = new Intent(NewCuisine.this , CuisineList.class);
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
        startActivity(new Intent(NewCuisine.this, CuisineList.class));
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
                startActivity(new Intent(NewCuisine.this, SettingsActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}