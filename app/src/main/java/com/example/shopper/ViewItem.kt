package com.example.shopper

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class ViewItem : AppCompatActivity() {
    // decalre a dbHandler
    var dbHandler: DBHandler? = null

    // declare Intnent
    // var intent: Intent? = null

    // declare editTexts
    var nameEditText: EditText? = null
    var priceEditText: EditText? = null
    var quantityEditText: EditText? = null

    // declare a bundle and a long used to get and store the data sent from
    // the main activity
    var bundle: Bundle? = null
    var id: Long = 0
    var listId: Long = 0

    // declare Strings to store the shopping clist items name price quantity
    var name: String? = null
    var price: String? = null
    var quantity: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_item)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        // initialize Bundle
        bundle = getIntent().extras

        // use Bundle to get id and store it in id field
        id = bundle!!.getLong("_id")

        // get the shopping list id in the bundle and store it in long
        listId = bundle!!.getLong("_list_id")

        // initialize the dbhandler
        dbHandler = DBHandler(this, null)

        // initialize EditTexts
        nameEditText = findViewById<View>(R.id.nameEditText) as EditText
        priceEditText = findViewById<View>(R.id.priceEditText) as EditText
        quantityEditText = findViewById<View>(R.id.quantityEditText) as EditText

        // disable EditTexts
        nameEditText!!.isEnabled = false
        priceEditText!!.isEnabled = false
        quantityEditText!!.isEnabled = false

        // call the db handler amethod getshoppinglistitem
        val cursor = dbHandler!!.getShoppingListItem(id.toInt())

        // move to the first row in the cursor
        cursor.moveToFirst()

        // get the name price and quantity in the curso rand sotre them in strings
        name = cursor.getString(cursor.getColumnIndex("name"))
        price = cursor.getString(cursor.getColumnIndex("price"))
        quantity = cursor.getString(cursor.getColumnIndex("quantity"))

        // set the name price and quantity values in the edittexts
        nameEditText!!.setText(name)
        priceEditText!!.setText(price)
        quantityEditText!!.setText(quantity)
    }

    /**
     * This method further initializes the Action Bar of the activity
     * It gets the code (XML) in the menu resource file and incorporates it into the Action Bar
     * @param menu menu resource file for the activity
     * @return true
     */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_view_item, menu)
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
                intent!!.putExtra("_id", listId)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    /**
     * This method gets called when the delete button in the aciton bar is clicked. It
     * deletes a row in the shopping rlist item table
     * @param menuItem delete item menu item
     */
    fun deleteItem(menuItem: MenuItem?) {

        // delete shopping list item from database
        dbHandler!!.deleteShoppingListItem(id.toInt())

        // display "Item deleted!" toast
        Toast.makeText(this, "Item Deleted!", Toast.LENGTH_LONG).show()
    }
}