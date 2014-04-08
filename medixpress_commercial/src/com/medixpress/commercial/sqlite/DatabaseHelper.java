package com.medixpress.commercial.sqlite;

import java.util.ArrayList;
import java.util.List;

import com.medixpress.commercial.PreferenceHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DatabaseHelper extends SQLiteOpenHelper {
	
	// Logcat tag
    private static final String TAG = "DatabaseHelper";
 
    // Database Version
    private static final int DATABASE_VERSION = 1;
 
    // Database Name
    private static final String DATABASE_NAME = "productManager";
    
    // Table Names
    private static final String TABLE_PRODUCTS = "products";
    private static final String TABLE_VENDORS = "vendors";
    
    // Common column names
    private static final String KEY_VENDORID = "vendorId";
    private static final String KEY_NAME = "name";
 
    // Products table - column names
    private static final String KEY_PRODUCTID = "productId";
    private static final String KEY_STOCK = "stock";
    private static final String KEY_VALUE = "value";
    private static final String KEY_SYMPTOMS = "symptoms";
    private static final String KEY_KEYWORDS = "keywords";
    private static final String KEY_DATE = "date";
 
    // Vendors table - column names
    private static final String KEY_LOCATION = "location";
    private static final String KEY_HOURS = "hours";

    // Table Create Statements
    // Product table create statement
    private static final String CREATE_TABLE_PRODUCTS = 
    		"CREATE TABLE " + TABLE_PRODUCTS + "(" + 
    		KEY_PRODUCTID + " INTEGER PRIMARY KEY," + 
    		KEY_VENDORID + " INTEGER," + 
    		KEY_NAME + " TEXT," + 
    		KEY_STOCK + " REAL," +
    		KEY_VALUE + " REAL," + 
    		KEY_SYMPTOMS + " TEXT," +
            KEY_KEYWORDS + " TEXT," +
    		KEY_DATE + "DATETIME" + ")";
 
    // Vendor table create statement
    private static final String CREATE_TABLE_VENDORS = 
    		"CREATE TABLE " + TABLE_VENDORS + "(" + 
    		KEY_VENDORID + " INTEGER PRIMARY KEY," + 
    		KEY_NAME + " TEXT," + 
    		KEY_LOCATION + " TEXT," +
    		KEY_HOURS + " TEXT" + ")";
 
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
 
    @Override
    public void onCreate(SQLiteDatabase db) {
 
        // creating required tables
        db.execSQL(CREATE_TABLE_PRODUCTS);
        db.execSQL(CREATE_TABLE_VENDORS);
    }
 
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VENDORS);
 
        // create new tables
        onCreate(db);
    }
    
    public long createProduct(Vendor vendor, Product product) {
    	 SQLiteDatabase db = this.getWritableDatabase();
    	 
	    ContentValues values = new ContentValues();
	    //values.put(KEY_PRODUCTID, product.getProductId());
	    values.put(KEY_VENDORID, vendor.getVendorId());
	    values.put(KEY_NAME, product.getName());
	    values.put(KEY_STOCK, product.getStock());
	    values.put(KEY_VALUE, product.getValue());
	    values.put(KEY_SYMPTOMS, product.getSymptoms());
	    values.put(KEY_KEYWORDS, product.getKeywords());
	    values.put(KEY_DATE, product.getDate());
	 
	    // insert row
	    long todo_id = db.insert(TABLE_PRODUCTS, null, values);
	    return todo_id;
    }
    
    public Product getProduct(long productId) {
        SQLiteDatabase db = this.getReadableDatabase();
     
        String selectQuery = "SELECT  * FROM " + TABLE_PRODUCTS + " WHERE "
                + KEY_PRODUCTID + " = " + productId;
     
        Cursor c = db.rawQuery(selectQuery, null);
     
        if (c != null)
            c.moveToFirst();
     
        Product rval = new Product();
        rval.setProductId(c.getInt(c.getColumnIndex(KEY_PRODUCTID)));
        rval.setVendorId(c.getInt(c.getColumnIndex(KEY_VENDORID)));
        rval.setName(c.getString(c.getColumnIndex(KEY_NAME)));
        rval.setStock(c.getFloat(c.getColumnIndex(KEY_STOCK)));
        rval.setValue(c.getFloat(c.getColumnIndex(KEY_VALUE)));
        rval.setSymptoms(c.getString(c.getColumnIndex(KEY_SYMPTOMS)));
        rval.setKeywords(c.getString(c.getColumnIndex(KEY_KEYWORDS)));
        rval.setDate(c.getString(c.getColumnIndex(KEY_DATE)));
        
        return rval;
    }
    
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<Product>();
        String selectQuery = "SELECT  * FROM " + TABLE_PRODUCTS;
     
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
     
        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Product rval = new Product();
                rval.setProductId(c.getInt(c.getColumnIndex(KEY_PRODUCTID)));
                rval.setVendorId(c.getInt(c.getColumnIndex(KEY_VENDORID)));
                rval.setName(c.getString(c.getColumnIndex(KEY_NAME)));
                rval.setStock(c.getFloat(c.getColumnIndex(KEY_STOCK)));
                rval.setValue(c.getFloat(c.getColumnIndex(KEY_VALUE)));
                rval.setSymptoms(c.getString(c.getColumnIndex(KEY_SYMPTOMS)));
                rval.setKeywords(c.getString(c.getColumnIndex(KEY_KEYWORDS)));
                rval.setDate(c.getString(c.getColumnIndex(KEY_DATE)));
     
                // adding to todo list
                products.add(rval);
            } while (c.moveToNext());
        }
     
        return products;
    }
}
