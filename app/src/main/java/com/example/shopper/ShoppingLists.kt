package com.example.shopper

import android.content.Context
import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CursorAdapter
import android.widget.TextView

/**
 * The ShoppingList class will map the data selected from the shopping list
 * table, in the cursor, to the li_shoppping_list layout resource
 */
class ShoppingLists
/**
 * Initialize a shoppinglists cursoradapter
 * @param context reference to the activity that initializes the shoppinglists cursoradapter
 * @param c reference to the cursor that contains the selected data from the shoppinglist table
 * @param flags determines special behavior of the cursor adapter. we dont want any special
 * behavior so we will always pass 0.
 */
(context: Context?, c: Cursor?, flags: Int) : CursorAdapter(context, c, flags) {
    /**
     * Makes a new view to hold the data in the cursor
     * @param context reference to the activity that initializes the shoppinglists cursoradapter
     * @param cursor reference to the cursor that contains the selected data from the shoppinglist table
     * @param parent reference to the shopperlist view that will contain the new view
     * created by this method
     * @return reference to the enw view
     */
    override fun newView(context: Context, cursor: Cursor, parent: ViewGroup): View {
        return LayoutInflater.from(context).inflate(R.layout.li_shopping_list, parent, false)
    }

    /**
     * Binds new view to data in cursor
     * @param view reference to new view
     * @param context reference to the activity that initializes the shoppinglists curdoradapter
     * @param cursor reference to the cursor that contains the selected data from the shoppinglist table
     */
    override fun bindView(view: View, context: Context, cursor: Cursor) {
        (view.findViewById<View>(R.id.nameTextView) as TextView).text = cursor.getString(cursor.getColumnIndex("name"))
        (view.findViewById<View>(R.id.storeTextView) as TextView).text = cursor.getString(cursor.getColumnIndex("store"))
        (view.findViewById<View>(R.id.dateTextView) as TextView).text = cursor.getString(cursor.getColumnIndex("date"))
    }
}