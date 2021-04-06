package com.example.shopper

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView.OnItemClickListener
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class ViewList : AppCompatActivity() {
    // declare a bundle and a long used to get and store the data sent from
    // the main activity
    var bundle: Bundle? = null
    var id: Long = 0

    // decalre a dbHandler
    var dbHandler: DBHandler? = null

    // declare Intnent
    // var intent: Intent? = null

    // declare a shoppinglistitesm Cursor Adapter
    var shoppingListItemsAdapter: ShoppingListItems? = null

    // declare a listview
    var itemListView: ListView? = null

    // declare notification manager used to shoq the notification
    var notificationManagerCompat: NotificationManagerCompat? = null

    // declare String that will store the shopping list name
    var shoppingListName: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_list)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        // initialize the bundle
        bundle = getIntent().extras

        // use Bundle to get id and store it in id field
        id = bundle!!.getLong("_id")

        // initialize the dbhandler
        dbHandler = DBHandler(this, null)

        // call getShoppingListName method ad store its return in string
        shoppingListName = dbHandler!!.getShoppingListName(id.toInt())

        // set the title of the viewlist activity to the shoppinglist name
        this.title = shoppingListName

        // initialize the listview
        itemListView = findViewById<View>(R.id.itemsListView) as ListView

        // initiilize the shoppinglistitems cursoradapter
        shoppingListItemsAdapter = ShoppingListItems(this,
                dbHandler!!.getShoppingListItems(id.toInt()), 0)

        // set the shoppinglist items ursor adapter on listview
        itemListView!!.adapter = shoppingListItemsAdapter

        // register an onitemclicklstern on the list view
        itemListView!!.onItemClickListener = OnItemClickListener { parent, view, position, id ->
            /**
             * This method gets called when an item in the listview is clicked
             * @param parent item list view
             * @param view viewlist activity view
             * @param position position of clicked item
             * @param id database id of clicked item
             */
            /**
             * This method gets called when an item in the listview is clicked
             * @param parent item list view
             * @param view viewlist activity view
             * @param position position of clicked item
             * @param id database id of clicked item
             */

            // call mehtod that updates the clicked items item_has to true
            // if its false
            updateItem(id)

            // initialize intent for viewlist activity
            intent = Intent(this@ViewList, ViewItem::class.java)

            // put the database id on the intent
            intent!!.putExtra("_id", id)

            // put the database of the clicked shopping list in the intent
            intent!!.putExtra("_list_id", this@ViewList.id)

            // start the viewlist activity
            startActivity(intent)
        }

        // set the subtitle to the total cos to fthe shoping list
        toolbar.subtitle = "Total Cost: $" + dbHandler!!.getShoppingListTotalCost(id.toInt())

        // initialize the notification manager
        notificationManagerCompat = NotificationManagerCompat.from(this)
    }

    /**
     * This method further initializes the Action Bar of the activity
     * It gets the code (XML) in the menu resource file and incorporates it into the Action Bar
     * @param menu menu resource file for the activity
     * @return true
     */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_view_list, menu)
        return true
    }

    /**
     * this method gets called when a menu item in the overflow menu is selected and it
     * controls what happens when the menu item is selected.
     * @param item selected menu item in the overflow menu
     * @return true if menu is selected, else false
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // get the id of menu item selected
        return when (item.itemId) {
            R.id.action_home -> {
                // initialize and Intent for the MainActivity and start it
                intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.action_create_list -> {
                // initialize and Intent for the MainActivity and start it
                intent = Intent(this, CreateList::class.java)
                startActivity(intent)
                true
            }
            R.id.action_add_item -> {
                // initialize and Intent for the AddItem and start it
                intent = Intent(this, AddItem::class.java)
                // put the database id on the intent
                intent!!.putExtra("_id", id)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    /**
     * This method gets called when the add Floating Action Button is clicked
     * It starts the AddItem Activity
     * @param view ViewList view
     */
    fun openAddItem(view: View?) {
        // initialize and Intent for the AddItem and start it
        intent = Intent(this, AddItem::class.java)
        // put the database id on the intent
        intent!!.putExtra("_id", id)
        startActivity(intent)
    }

    /**
     * This method gets called when an item is clicked in the list view.
     * It updates the clicked items item_has to true if its false
     * @param id database id of the clicked item
     */
    fun updateItem(id: Long) {

        // checking if the clicked item is unpurchased
        if (dbHandler!!.isItemUnpurchased(id.toInt()) == 1) {
            // make clicked item purchased
            dbHandler!!.updateItem(id.toInt())

            // refresh List View with updataed data
            shoppingListItemsAdapter!!.swapCursor(dbHandler!!.getShoppingListItems(this.id.toInt()))
            shoppingListItemsAdapter!!.notifyDataSetChanged()

            // display a toast indicating item is purchased
            Toast.makeText(this, "Item purchased!", Toast.LENGTH_LONG).show()
        }

        // if all shopping lit items  have been purchased
        if (dbHandler!!.getUnpurchasedItems(this.id.toInt()) == 0) {
            // initialize notification
            val notification = NotificationCompat.Builder(this,
                    App.CHANNEL_SHOPPER_ID)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("Shopper")
                    .setContentText("$shoppingListName completed!").build()

            // show notification
            notificationManagerCompat!!.notify(1, notification)
        }
    }

    /**
     * This method gets called when the delete button in the action bar of the view list activity
     * gets clicked. It delets a row in the shoppinglist item an dshoppig list tables.
     * @param menuItem delete list menu item
     */
    fun deleteList(menuItem: MenuItem?) {

        // delete shopping list from database
        dbHandler!!.deleteShoppingList(id.toInt())

        // display "List deleted!" toast
        Toast.makeText(this, "List Deleted!", Toast.LENGTH_LONG).show()
    }
}