package com.example.shopper;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import static com.example.shopper.App.CHANNEL_SHOPPER_ID;

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

    // declare notification manager used to shoq the notification
    NotificationManagerCompat notificationManagerCompat;

    // declare String that will store the shopping list name
    String shoppingListName;

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
        shoppingListName = dbHandler.getShoppingListName((int) id);

        // set the title of the viewlist activity to the shoppinglist name
        this.setTitle(shoppingListName);

        // initialize the listview
        itemListView = (ListView) findViewById(R.id.itemsListView);

        // initiilize the shoppinglistitems cursoradapter
        shoppingListItemsAdapter = new ShoppingListItems(this,
                dbHandler.getShoppingListItems((int) id), 0);

        // set the shoppinglist items ursor adapter on listview
        itemListView.setAdapter(shoppingListItemsAdapter);

        // register an onitemclicklstern on the list view
        itemListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            /**
             * This method gets called when an item in the listview is clicked
             * @param parent item list view
             * @param view viewlist activity view
             * @param position position of clicked item
             * @param id database id of clicked item
             */
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // call mehtod that updates the clicked items item_has to true
                // if its false
                updateItem(id);

                // initialize intent for viewlist activity
                intent = new Intent(ViewList.this, ViewItem.class);

                // put the database id on the intent
                intent.putExtra("_id", id);

                // put the database of the clicked shopping list in the intent
                intent.putExtra("_list_id", ViewList.this.id);

                // start the viewlist activity
                startActivity(intent);
            }
        });

        // set the subtitle to the total cos to fthe shoping list
        toolbar.setSubtitle("Total Cost: $" + dbHandler.getShoppingListTotalCost((int) id));

        // initialize the notification manager
        notificationManagerCompat = NotificationManagerCompat.from(this);
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

    /**
     * This method gets called when an item is clicked in the list view.
     * It updates the clicked items item_has to true if its false
     * @param id database id of the clicked item
     */
    public void updateItem(long id){

        // checking if the clicked item is unpurchased
        if (dbHandler.isItemUnpurchased((int) id) == 1){
            // make clicked item purchased
            dbHandler.updateItem((int) id);

            // refresh List View with updataed data
            shoppingListItemsAdapter.swapCursor(dbHandler.getShoppingListItems((int) this.id));
            shoppingListItemsAdapter.notifyDataSetChanged();

            // display a toast indicating item is purchased
            Toast.makeText(this, "Item purchased!", Toast.LENGTH_LONG).show();
        }

        // if all shopping lit items  have been purchased
        if (dbHandler.getUnpurchasedItems((int) this.id) == 0){
            // initialize notification
            Notification notification = new NotificationCompat.Builder(this,
                    CHANNEL_SHOPPER_ID)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("Shopper")
                    .setContentText(shoppingListName + " completed!").build();

            // show notification
            notificationManagerCompat.notify(1, notification);
        }
    }

    /**
     * This method gets called when the delete button in the action bar of the view list activity
     * gets clicked. It delets a row in the shoppinglist item an dshoppig list tables.
     * @param menuItem delete list menu item
     */
    public void deleteList(MenuItem menuItem){

        // delete shopping list from database
        dbHandler.deleteShoppingList((int) id);

        // display "List deleted!" toast
        Toast.makeText(this, "List Deleted!", Toast.LENGTH_LONG).show();
    }
}