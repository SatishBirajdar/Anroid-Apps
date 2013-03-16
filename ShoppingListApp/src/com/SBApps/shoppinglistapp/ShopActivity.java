/*
 	File Name - ShopActivity.java
 	Author Name - Satish Birajdar
 	Date - 11/16/2012
 	Application Description - The Application will create, modify, delete the shopping list using the concept of SQLite as a database.	
 	
 */

package com.SBApps.shoppinglistapp;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class ShopActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_shop, menu);
        return true;
    }
}
