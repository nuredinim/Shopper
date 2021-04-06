package com.example.shopper

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class AddItem : AppCompatActivity(), OnItemSelectedListener {
    // declare a bundle and a long used to get and store the data sent from
    // the ViewList activity
    var bundle: Bundle? = null
    var id: Long = 0

    // decalre a dbHandler
    var dbHandler: DBHandler? = null

    // declare Intnent
    // var intent: Intent? = null

    // declare edittexts
    var nameEditText: EditText? = null
    var priceEditText: EditText? = null

    // decleare spinner
    var quantitySpinner: Spinner? = null

    // declare a sting to store quantiiyt
    var quantity: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_item)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        // initialize the bundle
        bundle = getIntent().extras

        // use Bundle to get id and store it in id field
        id = bundle!!.getLong("_id")

        // initialize the dbhandler
        dbHandler = DBHandler(this, null)

        // initialize the editTexts
        nameEditText = findViewById<View>(R.id.nameEditText) as EditText
        priceEditText = findViewById<View>(R.id.priceEditText) as EditText

        // initialize spinner
        quantitySpinner = findViewById<View>(R.id.quantitySpinner) as Spinner

        // initialize arrayadapter with vaalues in quantities stringarray
        // and stylize it with style defined by simple_spinner_item
        val adapter = ArrayAdapter.createFromResource(this,
                R.array.quantities, android.R.layout.simple_spinner_item)

        //further stylize the arrayadapter
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)

        // set the arrayadapter on the spinner
        quantitySpinner!!.adapter = adapter

        // register an onitemselectedlistern to Spinner
        quantitySpinner!!.onItemSelectedListener = this
    }

    /**
     * This method further initializes the Action Bar of the activity
     * It gets the code (XML) in the menu resource file and incorporates it into the Action Bar
     * @param menu menu resource file for the activity
     * @return true
     */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_add_item, menu)
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
            R.id.action_view_list -> {
                // initialize and Intent for the Viewlist and start it
                intent = Intent(this, ViewList::class.java)
                // put the database id on the intent
                intent!!.putExtra("_id", id)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    /**
     * this method gets called whne the add button in the action bar gets clicked
     * @param menuItem add item menu item
     */
    fun addItem(menuItem: MenuItem?) {

        // get data input in edittexts and store it in strings
        val name = nameEditText!!.text.toString()
        val price = priceEditText!!.text.toString()

        // trim strings and see if theyre equal to empty strings
        if (name.trim { it <= ' ' } == "" || price.trim { it <= ' ' } == "" || quantity!!.trim { it <= ' ' } == "") {
            // display "Please enter a name, price, and quantity!"
            Toast.makeText(this, "Please enter a name, price, and quantity!",
                    Toast.LENGTH_LONG).show()
        } else {
            // add item into database
            dbHandler!!.addItemToList(name, price.toDouble(), quantity!!.toInt(),
                    id.toInt())
            // display toast saying "Item added!"
            Toast.makeText(this, "Item added!",
                    Toast.LENGTH_LONG).show()
        }
    }

    /**
     * This method gets called when an item in the Spinner is selected
     * @param parent Spinner AdapterView
     * @param view AddItem view
     * @param position position of item that was selected in the spinner
     * @param id database id of item that was selected in spinner
     */
    override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
        quantity = parent.getItemAtPosition(position).toString()
    }

    override fun onNothingSelected(adapterView: AdapterView<*>?) {}
}