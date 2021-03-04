package com.example.shopper;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class ViewItem extends AppCompatActivity {

    // declare a bundle and a long used to get and store the data sent from
    // the main activity
    Bundle bundle;
    long id;

    // decalre a dbHandler
    DBHandler dbHandler;

    // declare Intnent
    Intent intent;

    // declare a shoppinglistitesm Cursor Adapter
    ShoppingListItems shoppingListItemsAdapter;

    // declare a listview
    ListView itemListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_item);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    public void deleteItem(MenuItem menuItem){


    }
}