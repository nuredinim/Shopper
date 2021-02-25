package com.example.shopper;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import java.util.List;

public class ViewList extends AppCompatActivity {

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

        // initialize the listview
        itemListView = (ListView) findViewById(R.id.itemsListView);

        // initiilize the shoppinglistitems cursoradapter
        shoppingListItemsAdapter = new ShoppingListItems(this,
                dbHandler.getShoppingListItems((int) id), 0);

        // set the shoppinglist items ursor adapter on listview
        itemListView.setAdapter(shoppingListItemsAdapter);
    }

    /**
     * This method further initializes the Action Bar of the activity
     * It gets the code (XML) in the menu resource file and incorporates it into the Action Bar
     * @param menu menu resource file for the activity
     * @return true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_list, menu);
        return true;
    }

    /**
     * this method gets called when a menu item in the overflow menu is selected and it
     * controls what happens when the menu item is selected.
     * @param item selected menu item in the overflow menu
     * @return true if menu is selected, else false
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // get the id of menu item selected
        switch (item.getItemId()){
            case R.id.action_home :
                // initialize and Intent for the MainActivity and start it
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_create_list :
                // initialize and Intent for the MainActivity and start it
                intent = new Intent(this, CreateList.class);
                startActivity(intent);
                return true;
            case R.id.action_add_item :
                // initialize and Intent for the AddItem and start it
                intent = new Intent(this, AddItem.class);
                // put the database id on the intent
                intent.putExtra("_id", id);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * This method gets called when the add Floating Action Button is clicked
     * It starts the AddItem Activity
     * @param view ViewList view
     */
    public void openAddItem(View view) {
        // initialize and Intent for the AddItem and start it
        intent = new Intent(this, AddItem.class);
        // put the database id on the intent
        intent.putExtra("_id", id);
        startActivity(intent);
    }
}