package com.example.food;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

     static final String DB_NAME = "Food.db";
     static final String DB_CUISINE_TABLE = "Cuisine_Table";
     static final String DB_FAVORITE_TABLE = "Favorite_Table";
    static final String DB_DESCRIPTION_TABLE = "Description_Table";

    static final int DB_VERSION = 18;

     static final String CUISINE_ID = "cuisine_id";
     static final String CUISINE_NAME = "cuisine_name";

    static final String FAVORITE_ID = "favorite_id";
    static final String FAVORITE_NAME = "favorite_name";
    static final String FAVORITE_CUISINE_ID = "favorite_cuisine_id";

    static final String DESCRIPTION_ID = "description_id";
    static final String DESCRIPTION_NAME = "description_name";
    static final String DESCRIPTION_FAVORITE_ID = "description_favorite_id";


     static final String CREATE_CUISINE_TABLE = "CREATE TABLE "+ DB_CUISINE_TABLE+" ("+
             CUISINE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
             CUISINE_NAME+ " TEXT "+ ")";


     static final String CREATE_FAVORITE_TABLE = "CREATE TABLE "+ DB_FAVORITE_TABLE+" ("+
             FAVORITE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
             FAVORITE_CUISINE_ID+ " INTEGER, "+
             FAVORITE_NAME + " text, "+
             " FOREIGN KEY ("+FAVORITE_CUISINE_ID+") REFERENCES " +DB_CUISINE_TABLE+"("+CUISINE_ID+") " +
              ")";

    static final String CREATE_DESCRIPTION_TABLE = "CREATE TABLE "+ DB_DESCRIPTION_TABLE+" ("+
            DESCRIPTION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
            DESCRIPTION_FAVORITE_ID+ " INTEGER, "+
            DESCRIPTION_NAME + " text, "+
            " FOREIGN KEY ("+DESCRIPTION_FAVORITE_ID+") REFERENCES " +DB_FAVORITE_TABLE+"("+FAVORITE_ID+") " +
            ")";


    public DatabaseHelper(Context context){
        super(context, DB_NAME , null , DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_CUISINE_TABLE);
        sqLiteDatabase.execSQL(CREATE_FAVORITE_TABLE);
        sqLiteDatabase.execSQL(CREATE_DESCRIPTION_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ DB_CUISINE_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ DB_FAVORITE_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ DB_DESCRIPTION_TABLE);

        onCreate(sqLiteDatabase);
    }

    public boolean insertNewCuisine(String name){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CUISINE_NAME , name);

        long result = db.insert(DB_CUISINE_TABLE, null, contentValues);

        return result != -1 ;
    }
    public boolean insertNewFavoriteCuisine(String id, String name){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(FAVORITE_NAME, name);
        contentValues.put(FAVORITE_CUISINE_ID , id);

        long result = db.insert(DB_FAVORITE_TABLE, null, contentValues);

        return result != -1 ;
    }

    public boolean insertNewDescription(String id, String name){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DESCRIPTION_NAME, name);
        contentValues.put(DESCRIPTION_FAVORITE_ID , id);

        long result = db.insert(DB_DESCRIPTION_TABLE, null, contentValues);

        return result != -1 ;
    }

    public Cursor viewCuisines(){
        SQLiteDatabase db = getReadableDatabase();
        String query = "Select * from "+ DB_CUISINE_TABLE;
        Cursor cursor = db.rawQuery(query, null);

        return cursor;
    }

    public Cursor viewFavoriteCuisines(String id){
        SQLiteDatabase db2 = getReadableDatabase();
        String query2 = "Select * from "+ DB_FAVORITE_TABLE + " where favorite_cuisine_id="+id;
        Cursor cursor2 = db2.rawQuery(query2, null);

        return cursor2;
    }

    public Cursor viewDescription(String id){
        SQLiteDatabase db = getReadableDatabase();
        String query = "Select * from "+ DB_DESCRIPTION_TABLE + " where description_favorite_id="+id;
        Cursor cursor = db.rawQuery(query, null);

        return cursor;
    }

   public void delete(String cuisines_table, String selection, String[] selectionArgs) {
        SQLiteDatabase db = getWritableDatabase();
       db.delete(DB_CUISINE_TABLE, selection, selectionArgs);
    }

    public boolean updateName(long id, String name) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CUISINE_NAME, name);
        return db.update(DB_CUISINE_TABLE, contentValues, CUISINE_ID + "=" + id, null) > 0;
    }

}

