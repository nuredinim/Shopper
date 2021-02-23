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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AddItem extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    // declare a bundle and a long used to get and store the data sent from
    // the ViewList activity
    Bundle bundle;
    long id;

    // decalre a dbHandler
    DBHandler dbHandler;

    // declare Intnent
    Intent intent;

    // declare edittexts
    EditText nameEditText;
    EditText priceEditText;

    // decleare spinner
    Spinner quantitySpinner;

    // declare a sting to store quantiiyt
    String quantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // initialize the bundle
        bundle = this.getIntent().getExtras();

        // use Bundle to get id and store it in id field
        id = bundle.getLong("_id");

        // initialize the dbhandler
        dbHandler = new DBHandler(this, null);

        // initialize the editTexts
        nameEditText = (EditText) findViewById(R.id.nameEditText);
        priceEditText = (EditText) findViewById(R.id.priceEditText);

        // initialize spinner
        quantitySpinner = (Spinner) findViewById(R.id.quantitySpinner);

        // initialize arrayadapter with vaalues in quantities stringarray
        // and stylize it with style defined by simple_spinner_item
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.quantities, android.R.layout.simple_spinner_item);

        //further stylize the arrayadapter
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        // set the arrayadapter on the spinner
        quantitySpinner.setAdapter(adapter);

        // register an onitemselectedlistern to Spinner
        quantitySpinner.setOnItemSelectedListener(this);
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
        getMenuInflater().inflate(R.menu.menu_add_item, menu);
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
            case R.id.action_view_list :
                // initialize and Intent for the Viewlist and start it
                intent = new Intent(this, ViewList.class);
                // put the database id on the intent
                intent.putExtra("_id", id);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * this method gets called whne the add button in the action bar gets clicked
     * @param menuItem add item menu item
     */
    public void addItem(MenuItem menuItem){

        // get data input in edittexts and store it in strings
        String name = nameEditText.getText().toString();
        String price = priceEditText.getText().toString();

        // trim strings and see if theyre equal to empty strings
        if (name.trim().equals("") || price.trim().equals("") ||
            quantity.trim().equals("")){
            // display "Please enter a name, price, and quantity!"
            Toast.makeText(this, "Please enter a name, price, and quantity!",
                    Toast.LENGTH_LONG).show();
        } else {
            // add item into database
            dbHandler.addItemToList(name, Double.parseDouble(price), Integer.parseInt(quantity),
                    (int) id);
            // display toast saying "Item added!"
            Toast.makeText(this, "Item added!",
                    Toast.LENGTH_LONG).show();
        }
    }

    /**
     * This method gets called when an item in the Spinner is selected
     * @param parent Spinner AdapterView
     * @param view AddItem view
     * @param position position of item that was selected in the spinner
     * @param id database id of item that was selected in spinner
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        quantity = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}