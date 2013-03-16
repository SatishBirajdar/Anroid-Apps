/*
 	File Name - CreateShoppingListActivity.java
 	Author Name - Satish Birajdar
 	Date - 11/16/2012
 	Application Description - The Application will create, modify, delete the shopping list using the concept of SQLite as a database.	
 	
 */

package com.SBApps.shoppinglistapp;


import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class CreateShopingListActivity extends Activity 
{
	//create an object of ShoppingMainActivity class 
	ShoppingMainActivity main=new ShoppingMainActivity();
	
	//create a variable for taking inputs
	private Button saveButton;
	private Button cancelButton;
	private TextView createTextView;

	private EditText listName;
	private EditText row1Item;
	private EditText row1Qty;
	private EditText row2Item;
	private EditText row2Qty;


    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
    	//refer variable with inputs
    	createTextView = (TextView) findViewById(R.id.createTextView);
    	
    	listName = (EditText) findViewById(R.id.editText1);
    	row1Item = (EditText) findViewById(R.id.editText11);
    	row1Qty = (EditText) findViewById(R.id.editText13);
    	row2Item = (EditText) findViewById(R.id.editText21);
    	row2Qty = (EditText) findViewById(R.id.editText23);
    	
    	
    	//invisible createtextview when there is a shopping list in a listview
    	if(main.flag==1)
    	{
    		createTextView.setVisibility(View.GONE);
    	}
    	else // visible it when there is no any shopping list available
    	{
        	createTextView.setVisibility(View.VISIBLE);
        }
    	
    	Bundle extras = getIntent().getExtras(); // get Bundle of extras

        // if there are extras, use them to populate the EditTexts
        if (extras != null)
        {
           
           listName.setText(extras.getString("listname"));  
           row1Item.setText(extras.getString("itemname"));  
           row1Qty.setText(extras.getString("quantity"));  
           
        } // end if
    	
        //action listener on savebutton
        saveButton = (Button)findViewById(R.id.savebutton);
        saveButton.setOnClickListener(new OnClickListener()
        {     //onclicklistener for save buton
			
        		public void onClick(View v) {
				main.flag=0;
				Intent intent = new Intent(getBaseContext(), ShoppingMainActivity.class);
				startActivity(intent);
				
				if (listName.getText().length() != 0)
		         {
		            AsyncTask<Object, Object, Object> saveListTask = 
		               new AsyncTask<Object, Object, Object>() 
		               {
		                  @Override
		                  protected Object doInBackground(Object... params) 
		                  {
		                     saveList(); // save contact to the database
		                     return null;
		                  } // end method doInBackground
		      
		                  @Override
		                  protected void onPostExecute(Object result) 
		                  {
		                     finish(); // return to the previous Activity
		                  } // end method onPostExecute
		               }; // end AsyncTask
		               
		            // save the contact to the database using a separate thread
		            saveListTask.execute((Object[]) null); 
		         } // end if
		         else
		         {
		            // create a new AlertDialog Builder
		            AlertDialog.Builder builder = 
		               new AlertDialog.Builder(CreateShopingListActivity.this);
		      
		            // set dialog title & message, and provide Button to dismiss
		            builder.setTitle(R.string.errorTitle); 
		            builder.setMessage(R.string.errorMessage);
		            builder.setPositiveButton(R.string.errorButton, null); 
		            builder.show(); // display the Dialog
		         } // end else
        		}//end of onclick method
		     }; // end OnClickListener saveListButtonClicked
				
			
		
        
     // saves contact information to the database
        private void saveList() 
        {
           // get DatabaseConnector to interact with the SQLite database
           DatabaseConnector databaseConnector = new DatabaseConnector(this);

           if (getIntent().getExtras() == null)
           {
              // insert the contact information into the database
              databaseConnector.insertList(
                 listName.getText().toString(),
                 row1Item.getText().toString(), 
                 row2Qty.getText().toString());
           } // end if
           else
           {
              databaseConnector.updateList(
            		  listName.getText().toString(),
                      row1Item.getText().toString(), 
                      row2Qty.getText().toString());
           } // end else
        }
        
        //action listener for cancel button
        cancelButton = (Button)findViewById(R.id.cancelbutton);
        cancelButton.setOnClickListener(new OnClickListener(){
			
			public void onClick(View v) {
				main.flag=1;
				Intent intent = new Intent(getBaseContext(), ShoppingMainActivity.class);
				
				startActivity(intent);
			} 
		}); 
       
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_create_shoping_list, menu);
        return true;
    }
}
