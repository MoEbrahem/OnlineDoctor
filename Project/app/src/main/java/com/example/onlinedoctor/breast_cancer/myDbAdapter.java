package com.example.onlinedoctor.breast_cancer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class myDbAdapter {
    myDbHelper myhelper;
    public myDbAdapter(Context context)
    {
        myhelper = new myDbHelper(context);
    }

    public long insertData(String concave_points_mean, String area_mean, String radius_mean , String perimeter_mean, String concavity_mean)
    {
        SQLiteDatabase dbb = myhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(myDbHelper.concave_points_mean, concave_points_mean);
        contentValues.put(myDbHelper.area_mean, area_mean);
        contentValues.put(myDbHelper.radius_mean, radius_mean);
        contentValues.put(myDbHelper.perimeter_mean, perimeter_mean);
        contentValues.put(myDbHelper.concavity_mean, concavity_mean);
        long id = dbb.insert(myDbHelper.TABLE_NAME, null , contentValues);
        return id;
    }

    public String[] getData()
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] x = new String[7];
        String[] columns = {myDbHelper.UID,myDbHelper.concave_points_mean,myDbHelper.area_mean,
                myDbHelper.radius_mean,myDbHelper.perimeter_mean,myDbHelper.concavity_mean};
        Cursor cursor =db.query(myDbHelper.TABLE_NAME,columns,null,null,null,null,null);
        StringBuffer buffer= new StringBuffer();
        while (cursor.moveToNext())
        {
            int cid =cursor.getInt(cursor.getColumnIndexOrThrow(myDbHelper.UID));
            String concave_points_mean =cursor.getString(cursor.getColumnIndexOrThrow(myDbHelper.concave_points_mean));
            String  area_mean =cursor.getString(cursor.getColumnIndexOrThrow(myDbHelper.area_mean));
            String  radius_mean =cursor.getString(cursor.getColumnIndexOrThrow(myDbHelper.radius_mean));
            String  perimeter_mean =cursor.getString(cursor.getColumnIndexOrThrow(myDbHelper.perimeter_mean));
            String  concavity_mean =cursor.getString(cursor.getColumnIndexOrThrow(myDbHelper.concavity_mean));
            x = new String[]{Integer.toString(cid), concave_points_mean, area_mean, radius_mean, perimeter_mean, concavity_mean};
//            buffer.append(cid).append("  cpm  ").append(concave_points_mean).append("  area ").append(area_mean).append("   radius  ").append(radius_mean).append("   pm  ").append(perimeter_mean).append("   cm  ").append(concavity_mean).append("\n");
        }
        return x;
    }

    static class myDbHelper extends SQLiteOpenHelper
    {
        private static final String DATABASE_NAME = "myDatabase";
        private static final String TABLE_NAME = "myTable";
        private static final int DATABASE_Version = 1;
        private static final String UID="_id";
        private static final String concave_points_mean = "concave_points_mean";
        private static final String area_mean= "area_mean";
        private static final String radius_mean= "radius_mean";
        private static final String perimeter_mean= "perimeter_mean";
        private static final String concavity_mean= "concavity_mean";



        private static final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+
                " ("+ UID +" INTEGER PRIMARY KEY AUTOINCREMENT, "+   concave_points_mean   +" VARCHAR(255) ,"+   area_mean  +" VARCHAR(225),"+   radius_mean  +" VARCHAR(225) ,"+   perimeter_mean  +"  VARCHAR(225) ,"+   concavity_mean  +" VARCHAR(225))";
        private static final String DROP_TABLE ="DROP TABLE IF EXISTS "+TABLE_NAME;
        private Context context;

        public myDbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_Version);
            this.context=context;
        }

        public void onCreate(SQLiteDatabase db) {

            try {
                db.execSQL(CREATE_TABLE);
            } catch (Exception e) {
                Message.message(context,""+e);
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {
                Message.message(context,"OnUpgrade");
                db.execSQL(DROP_TABLE);
                onCreate(db);
            }catch (Exception e) {
                Message.message(context,""+e);
            }
        }
    }

}



