package com.example.food;


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

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.airbnb.lottie.LottieAnimationView;

public class NewDescriptionActivity extends AppCompatActivity {

    EditText New_Description_EditText;
    Button addNewDescriptionBtn, viewNewDescriptionBtn;
    static DatabaseHelper db;
    LottieAnimationView lottieAnimationViewNewDescription;

    String id, name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_description_activity);
        db = new DatabaseHelper(this);

        Toolbar toolbar = findViewById(R.id.toolbarNewDescription);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("New Description");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.inflateMenu(R.menu.main_menu);
        toolbar.setTitleTextColor(Color.WHITE);

        New_Description_EditText = (EditText) findViewById(R.id.NewDescriptionEditText);
        addNewDescriptionBtn = (Button) findViewById(R.id.addNewDescriptionButton);
        viewNewDescriptionBtn = (Button) findViewById(R.id.viewNewDescription);

        lottieAnimationViewNewDescription = findViewById(R.id.lottieNewDescription);
        lottieAnimationViewNewDescription.animate().setDuration(100).setStartDelay(4000);
        lottieAnimationViewNewDescription.setRepeatCount(Animation.INFINITE);

        Intent i = getIntent();
        id = i.getStringExtra("id");
        name = i.getStringExtra("name");

    }

    public void addDescription(View view){
        String newEntry = New_Description_EditText.getText().toString();
        if(New_Description_EditText.length() !=0){
            AddDescription(newEntry);
            Toast.makeText(getApplicationContext(),newEntry , Toast.LENGTH_SHORT).show();
            New_Description_EditText.setText("");
        }
        else {
            Toast.makeText(getApplicationContext(),"You must put something", Toast.LENGTH_SHORT).show();
        }
    }

    public void AddDescription(String newEntry){
        boolean insertData = db.insertNewDescription(id, newEntry);
        if(insertData == true){
            Toast.makeText(getApplicationContext(),"Successfully Entered Data!", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(getApplicationContext(),"something went wrong", Toast.LENGTH_SHORT).show();
        }
    }

    public void viewNewDescription(View view){
        Intent intent = new Intent(NewDescriptionActivity.this , DescriptionActivity.class);
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
        Intent intent = new Intent(NewDescriptionActivity.this , DescriptionActivity.class);
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
                startActivity(new Intent(NewDescriptionActivity.this, SettingsActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}