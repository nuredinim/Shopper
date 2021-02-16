package com.example.shopper;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

public class ViewList extends AppCompatActivity {

    // declare a bundle and a long used to get and store the data sent from
    // the main activity
    Bundle bundle;
    long id;

    // decalre a dbHandler
    DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // initialize the bundle
        bundle = this.getIntent().getExtras();

        // use Bundle to get id and store it in id field
        id = bundle.getLong("_id");

        // initialize the dbhandler
        dbHandler = new DBHandler(this, null);

        // call getShoppingListName method ad store its return in string
        String shoppingListName = dbHandler.getShoppingListName((int) id);

        // set the title of the viewlist activity to the shoppinglist name
        this.setTitle(shoppingListName);
    }

    public void openAddItem(View view) {
    }
}