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

import com.airbnb.lottie.LottieAnimationView;

public class EditActivity extends AppCompatActivity {

    EditText Edit_Cuisine_Name;
    Button UpdateCuisineButton;
    int id;
    String name;
    static DatabaseHelper db;
    LottieAnimationView lottieAnimationViewUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_activity);

        Toolbar toolbar = findViewById(R.id.toolbarEdit);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Edit Cuisine");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.inflateMenu(R.menu.main_menu);
        toolbar.setTitleTextColor(Color.WHITE);

        db = new DatabaseHelper(this);

        id = getIntent().getExtras().getInt("id");
        name = getIntent().getExtras().getString("name");

        Edit_Cuisine_Name = (EditText) findViewById(R.id.Edit_Cuisine_Name);
        Edit_Cuisine_Name.setText(name);

        lottieAnimationViewUpdate = findViewById(R.id.lottieUpdate);
        lottieAnimationViewUpdate.animate().setDuration(3000).setStartDelay(4000);
        lottieAnimationViewUpdate.setRepeatCount(Animation.INFINITE);

    }

    public void UpdateCuisineBtn(View view){
        name = Edit_Cuisine_Name.getText().toString();
        CuisineList.db.updateName(id,name);
        startActivity(new Intent(EditActivity.this , CuisineList.class));
        finish();
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(EditActivity.this , CuisineList.class));

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
                startActivity(new Intent(EditActivity.this, SettingsActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}