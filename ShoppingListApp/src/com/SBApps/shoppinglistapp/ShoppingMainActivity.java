/*
 	File Name - ShoppingMainActivity.java
 	Author Name - Satish Birajdar
 	Date - 11/16/2012
 	Application Description - The Application will create, modify, delete the shopping list using the concept of SQLite as a database.	
 	
 */

package com.SBApps.shoppinglistapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class ShoppingMainActivity extends Activity {
	public Integer flag=0;
	public static final String ROW_ID = "row_id";
	private CursorAdapter shoppingAdapter;
	final Context context = this;
	private Button plusButton;
	public TextView textViewCreate;
	private ListView shoppingListView;
    
	@SuppressWarnings("deprecation")
	@Override
    public void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
        
        shoppingListView = getListView(); // get the built-in ListView
        shoppingListView.setOnItemClickListener(viewShoppingListListener);      

       
        String[] from = new String[] { "name" };
        int[] to = new int[] { R.id.shoppingListTextView };
        shoppingAdapter = new SimpleCursorAdapter(
        ShoppingMainActivity.this, R.layout.activity_shop, null, from, to);
        setListAdapter(shoppingAdapter); // set shoppingAdapter's adapter
        
        setContentView(R.layout.activity_shopping_main);  //call the respective activity
     
        plusButton = (Button)findViewById(R.id.plusButton);    //refer plusButton
        plusButton.setOnClickListener(new OnClickListener(){	//on click listener for plusButton
			
			public void onClick(View v) {
				
				Intent intent = new Intent(context, CreateShopingListActivity.class); //call CreateShopingListActivity.java onclick
				
				startActivity(intent);
			} 
		}); 
    }
    @Override
    protected void onResume() 
    {
       super.onResume(); // call super's onResume method
       new GetListTask().execute((Object[]) null);
     } // end method onResume

    @SuppressWarnings("deprecation")
	@Override
    protected void onStop() 
    {
       Cursor cursor = shoppingAdapter.getCursor(); // get current Cursor
       
       if (cursor != null) 
          cursor.deactivate(); // deactivate it
       
       shoppingAdapter.changeCursor(null); // adapted now has no Cursor
       super.onStop();
    } // end method onStop

    private class GetListTask extends AsyncTask<Object, Object, Cursor> 
    {
       DatabaseConnector databaseConnector = 
          new DatabaseConnector(ShoppingMainActivity.this);

       // perform the database access
       @Override
       protected Cursor doInBackground(Object... params)
       {
          databaseConnector.open();
          // get a cursor containing call shopping lists
          return databaseConnector.getAllLists(); 
       } // end method doInBackground

       // use the Cursor returned from the doInBackground method
       @Override
       protected void onPostExecute(Cursor result)
       {
    	  shoppingAdapter.changeCursor(result); // set the adapter's Cursor
          databaseConnector.close();
       } // end method onPostExecute
    } // end class GetListTask
       
    
    private void setListAdapter(CursorAdapter shoppingAdapter2) {
		// TODO Auto-generated method stub
		
	}

	private ListView getListView() {
		// TODO Auto-generated method stub
		return null;
	}
    
    OnItemClickListener viewShoppingListListener = new OnItemClickListener() 
    {
       public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
          long arg3) 
       {
          // create an Intent to launch the ShoppingContact Activity
          Intent viewList = 
          new Intent(ShoppingMainActivity.this, ShopActivity.class);
          
          // pass the selected contact's row ID as an extra with the Intent
          viewList.putExtra(ROW_ID, arg3);
          startActivity(viewList); // start the ShoppingMainActivity
       } // end method onItemClick
    }; // end viewContactListener

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_shopping_main, menu);
        return true;
        
        
    }
}
