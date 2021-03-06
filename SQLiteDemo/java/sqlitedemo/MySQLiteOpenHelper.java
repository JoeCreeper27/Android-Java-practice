package com.example.sqlitedemo;

import android.content.ContentValues;
import android.content.Context;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;


public class MySQLiteOpenHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "TravelSpots";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "Spot";
    private static final String COL_id = "id";
    private static final String COL_name = "name";
    private static final String COL_web = "web";
    private static final String COL_phone = "phone";
    private static final String COL_address = "address";
    private static final String COL_image = "image";

    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_NAME + " ( " +
                    COL_id + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COL_name + " TEXT NOT NULL, " +
                    COL_web + " TEXT, " +
                    COL_phone + " TEXT, " +
                    COL_address + " TEXT, " +
                    COL_image + " BLOB ); ";

    public MySQLiteOpenHelper(Context context) {
        super(context, DB_NAME,null,DB_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(TABLE_CREATE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
    public List<Spot> getAllSpots(){
        SQLiteDatabase sqLiteDatabase=getReadableDatabase();
        String[] columns={
                COL_id, COL_name, COL_web, COL_phone, COL_address, COL_image
        };
        Cursor cursor=sqLiteDatabase.query(TABLE_NAME,columns,null,null,null,null,null);

//        String sql = "SELECT * FROM Spot;";
//        String[] args = {};
//        Cursor cursor = db.rawQuery(sql, args);

        List<Spot> spotList=new ArrayList<>();
        while (cursor.moveToNext()){
            int id=cursor.getInt(0);
            String name=cursor.getString(1);
            String web=cursor.getString(2);
            String phone=cursor.getString(3);
            String address=cursor.getString(4);
            byte[] image=cursor.getBlob(5);
            Spot spot=new Spot(id,name,web,phone,address,image);
            spotList.add(spot);
        }
        cursor.close();
        return  spotList;
    }

    public Spot findById(int id){
        SQLiteDatabase sqLiteDatabase=getWritableDatabase();
        String[] columns={
                COL_name, COL_web, COL_phone, COL_address, COL_image
        };
        String selection =COL_id+"=?;";
        String[] selectionArgs={String.valueOf(id)};
        Cursor cursor=sqLiteDatabase.query(TABLE_NAME,columns,selection,selectionArgs,null,null,null);
        Spot spot=null;
        if (cursor.moveToNext()){
            String name=cursor.getString(0);
            String web=cursor.getString(1);
            String phone=cursor.getString(2);
            String address=cursor.getString(3);
            byte[] image=cursor.getBlob(4);
            spot=new Spot(id,name,web,phone,address,image);
        }
        cursor.close();
        return spot;
    }
    public long insert(Spot spot){
        SQLiteDatabase sqLiteDatabase=getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(COL_name, spot.getName());
        values.put(COL_web, spot.getWeb());
        values.put(COL_phone, spot.getPhone());
        values.put(COL_address, spot.getAddress());
        values.put(COL_image, spot.getImage());
        return sqLiteDatabase.insert(TABLE_NAME,null,values);
    }
    public int update(Spot spot){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_name, spot.getName());
        values.put(COL_web, spot.getWeb());
        values.put(COL_phone, spot.getPhone());
        values.put(COL_address, spot.getAddress());
        values.put(COL_image, spot.getImage());
        String whereClause=COL_id+"=?;";
        String[] whereArgs={
                Integer.toString(spot.getId())
        };
        return sqLiteDatabase.update(TABLE_NAME,values,whereClause,whereArgs);
    }
    public int deleteById(int id){
        SQLiteDatabase sqLiteDatabase=getWritableDatabase();
        String whereClause=COL_id+"=?;";
        String[] whereArgs={
                String.valueOf(id)
        };
        return sqLiteDatabase.delete(TABLE_NAME,whereClause,whereArgs);
    }

}
