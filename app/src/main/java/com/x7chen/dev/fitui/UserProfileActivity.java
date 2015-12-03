package com.x7chen.dev.fitui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class UserProfileActivity extends AppCompatActivity {

    EditText userName;
    EditText userAge;
    EditText userHeight;
    EditText userWeight;
    boolean mode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_setting);
        toolbar.setNavigationIcon(R.mipmap.ic_arrow_back_white_24dp);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        userName = (EditText) findViewById(R.id.userName);
        userAge = (EditText) findViewById(R.id.userAge);
        userHeight = (EditText) findViewById(R.id.userHeight);
        userWeight = (EditText) findViewById(R.id.userWeight);
        userName.setInputType(InputType.TYPE_NULL);
        userAge.setInputType(InputType.TYPE_NULL);
        userHeight.setInputType(InputType.TYPE_NULL);
        userWeight.setInputType(InputType.TYPE_NULL);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (mode) {
            getMenuInflater().inflate(R.menu.menu_complete, menu);
        } else {

            getMenuInflater().inflate(R.menu.menu_edit, menu);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_edit) {
            mode = true;
            invalidateOptionsMenu();
            userName.setInputType(InputType.TYPE_CLASS_TEXT);
            userName.setTextColor(Color.BLUE);
            userAge.setInputType(InputType.TYPE_CLASS_NUMBER);
            userAge.setTextColor(Color.BLUE);
            userHeight.setInputType(InputType.TYPE_CLASS_NUMBER);
            userHeight.setTextColor(Color.BLUE);
            userWeight.setInputType(InputType.TYPE_CLASS_NUMBER);
            userWeight.setTextColor(Color.BLUE);
        } else if (id == R.id.menu_complete) {
            mode = false;
            invalidateOptionsMenu();
            userName.setInputType(InputType.TYPE_NULL);
            userName.setTextColor(Color.BLACK);
            userAge.setInputType(InputType.TYPE_NULL);
            userAge.setTextColor(Color.BLACK);
            userHeight.setInputType(InputType.TYPE_NULL);
            userHeight.setTextColor(Color.BLACK);
            userWeight.setInputType(InputType.TYPE_NULL);
            userWeight.setTextColor(Color.BLACK);

        }
        return super.onOptionsItemSelected(item);
    }
}
