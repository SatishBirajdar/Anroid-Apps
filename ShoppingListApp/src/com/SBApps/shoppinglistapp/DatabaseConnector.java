/*
 	File Name - DatabaseConnector.java
 	Author Name - Satish Birajdar
 	Date - 11/16/2012
 	Application Description - The Application will create, modify, delete the shopping list using the concept of SQLite as a database.	
 	
 */


package com.SBApps.shoppinglistapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class DatabaseConnector 
{
   // database name
   private static final String DATABASE_NAME = "shoppinglist";
   private SQLiteDatabase database; // database object
   private DatabaseOpenHelper databaseOpenHelper; // database helper

   // public constructor for DatabaseConnector
   public DatabaseConnector(Context context) 
   {
      // create a new DatabaseOpenHelper
      databaseOpenHelper = 
         new DatabaseOpenHelper(context, DATABASE_NAME, null, 1);
   } // end DatabaseConnector constructor

   // open the database connection
   public void open() throws SQLException 
   {
      // create or open a database for reading/writing
      database = databaseOpenHelper.getWritableDatabase();
   } // end method open

   // close the database connection
   public void close() 
   {
      if (database != null)
         database.close(); // close the database connection
   } // end method close

   // inserts a new contact in the database
   public void insertList(String listname, String itemname, Integer quantity) 
   {
      ContentValues newContact = new ContentValues();
     ;
      newContact.put("listname", listname);
      newContact.put("itemname", itemname);
      newContact.put("quantity", quantity);

      open(); // open the database
      database.insert("shoppinglist", null, newContact);
      close(); // close the database
   } // end method insertContact

   // inserts a new contact in the database
   public void updateList(long id, String listname, String itemname, 
      Integer quantity) 
   {
      ContentValues editContact = new ContentValues();
      editContact.put("listname", listname);
      editContact.put("itemname", itemname);
      editContact.put("quantity", quantity);

      open(); // open the database
      database.update("shoppinglist", editContact, "_id=" + id, null);
      close(); // close the database
   } // end method updateContact

   // return a Cursor with all contact information in the database
   public Cursor getAllLists() 
   {
      return database.query("shoppinglist", new String[] {"_id", "listname"}, 
         null, null, null, null, "name");
   } // end method getAllContacts

   // get a Cursor containing all information about the contact specified
   // by the given id
   public Cursor getOneList(long id) 
   {
      return database.query(
         "shoppinglist", null, "_id=" + id, null, null, null, null);
   } // end method getOnContact

   // delete the contact specified by the given String name
   public void deleteList(long id) 
   {
      open(); // open the database
      database.delete("shoppinglist", "_id=" + id, null);
      close(); // close the database
   } // end method deleteContact
   
   private class DatabaseOpenHelper extends SQLiteOpenHelper 
   {
      // public constructor
      public DatabaseOpenHelper(Context context, String name,
         CursorFactory factory, int version) 
      {
         super(context, name, factory, version);
      } // end DatabaseOpenHelper constructor

      // creates the contacts table when the database is created
      @Override
      public void onCreate(SQLiteDatabase db) 
      {
         // query to create a new table named contacts
         String createQuery = "CREATE TABLE shoppinglist" +
            "(_id integer primary key autoincrement," +
            "listname TEXT, itemname TEXT," +
            "quantity integer);";
                  
         db.execSQL(createQuery); // execute the query
      } // end method onCreate

      @Override
      public void onUpgrade(SQLiteDatabase db, int oldVersion, 
          int newVersion) 
      {
      } // end method onUpgrade
   } // end class DatabaseOpenHelper
} // end class DatabaseConnector

