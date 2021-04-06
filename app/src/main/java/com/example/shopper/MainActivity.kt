package com.example.shopper

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView.OnItemClickListener
import android.widget.CursorAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.shopper.MainActivity
import com.example.shopper.ViewList
import com.example.shopper.CreateList

class MainActivity : AppCompatActivity() {
    // declare Intent
    // var intent: Intent? = null

    // declare a DBHandler
    var dbHandler: DBHandler? = null

    // declare shoppinglist cursoradapter
    var shoppingListsCursorAdapter: CursorAdapter? = null

    // decare a listview
    var shopperListView: ListView? = null

    /**
     * This method initializes the Action Bar and View of the activity
     * @param savedInstanceState
     */
    override fun onCreate(savedInstanceState: Bundle?) {

        // initialize the View and Action Bar of the main activity
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        // initializes dbhandler
        dbHandler = DBHandler(this, null)

        // initializes listview
        shopperListView = findViewById<View>(R.id.shopperListView) as ListView

        // initialize shoppinglsts cursoradapter
        shoppingListsCursorAdapter = ShoppingLists(this,
                dbHandler!!.shoppingLists, 0)

        // set shopping lists cursoradapater on the listview
        shopperListView!!.adapter = shoppingListsCursorAdapter

        // set OnItemClickListener on the ListView
        shopperListView!!.onItemClickListener = OnItemClickListener { adapterView, view, position, id ->
            /**
             * This method gets called when an item in the listview gets clicked
             * @param adapterView shopperListView
             * @param view MainActivity view
             * @param position poistion of the clicked item
             * @param id database id of the clicked item
             */
            /**
             * This method gets called when an item in the listview gets clicked
             * @param adapterView shopperListView
             * @param view MainActivity view
             * @param position poistion of the clicked item
             * @param id database id of the clicked item
             */

            // initialize Intent for the ViewList Activity
            intent = Intent(this@MainActivity, ViewList::class.java)

            // put the database id on the intent
            intent!!.putExtra("_id", id)

            // start the viewlist activity
            startActivity(intent)
        }
    }

    /**
     * This method further initializes the Action Bar of the activity
     * It gets the code (XML) in the menu resource file and incorporates it into the Action Bar
     * @param menu menu resource file for the activity
     * @return true
     */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
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
            else -> super.onOptionsItemSelected(item)
        }
    }

    /**
     * This method gets called when the add Floating Action Button is clicked.
     * It starts the CreateList Activity
     * @param view MainActivity view
     */
    fun openCreateList(view: View?) {
        // initialize and Intent for the MainActivity and start it
        intent = Intent(this, CreateList::class.java)
        startActivity(intent)
    }
}