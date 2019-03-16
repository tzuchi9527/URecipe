package com.example.apple.urecipe.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class DatabaseAccess {
    private SQLiteAssetHelper openHelper;
    private SQLiteDatabase db;
    private static  DatabaseAccess instance;
    Cursor c = null;

    private DatabaseAccess(Context context){
        this.openHelper = new DatabaseOpenHelper(context);
    }

    public static  DatabaseAccess getInstance(Context context){
        if(instance == null)
            instance = new DatabaseAccess(context);
        return instance;
    }

    public void open(){
        this.db=openHelper.getWritableDatabase();
    }

    public void close(){
        if (db!=null){
            this.db.close();
        }
    }

    // Query

    public String getName(int cal){
        c = db.rawQuery("select name from Food_Nutri where calories != 0 and calories < " + cal +" limit 1",new String[]{});
        StringBuffer buffer = new StringBuffer();
        while(c.moveToNext())
        {
            String name = c.getString(0);
            buffer.append(""+name);
        }
        return  buffer.toString();
    }


}