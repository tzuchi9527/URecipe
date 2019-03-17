package com.example.apple.urecipe.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.apple.urecipe.module.Food;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;

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
    public List<Food> getFoodsByName(String n){
        c = db.rawQuery("SELECT * FROM Food_Nutri WHERE name LIKE '%" + n + "%' ORDER BY rating DESC LIMIT 10", new String[]{});
        List<Food> result = readCursor(c);
//        if (result.size()==0)
//            return "empty";
//        else
//            return result.get(0).getName();
        return result;
    }


    public List<Food> getFoodsByNutri(String option, Integer min, Integer max){
        //Calories, Protein, Fat
        if (option.toLowerCase() == "calories")
        {
            c = db.rawQuery("SELECT * FROM Food_Nutri WHERE calories BETWEEN "+min+" AND "+max+" ORDER BY rating DESC limit 5", new String[]{});
        }
        else if (option.toLowerCase() == "protein")
        {
            c = db.rawQuery("SELECT * FROM Food_Nutri WHERE protein BETWEEN "+min+" AND "+max+" ORDER BY rating DESC limit 5", new String[]{});
        }
        else if (option.toLowerCase() == "fat")
        {
            c = db.rawQuery("SELECT * FROM Food_Nutri WHERE fat BETWEEN "+min+" AND "+max+" ORDER BY rating DESC limit 5", new String[]{});
        }

        List<Food> result = readCursor(c);

        return result;
    }

    public List<Food> getFoodsByType(String... types){
        //At most 5 types
        //getFoodByType("option1", "option2", ...),
        //options = {breakfast, lunch, dinner, beef, chicken, pork, meat, egg, vegetable, salad, seafood}

        String type1 = types.length > 0 ? types[0] : null;
        String type2 = types.length > 1 ? types[1] : null;
        String type3 = types.length > 2 ? types[2] : null;
        String type4 = types.length > 3 ? types[3] : null;
        String type5 = types.length > 4 ? types[4] : null;

        if (type1 != null && type2 != null && type3 != null && type4 != null && type5 != null) {
            c = db.rawQuery("SELECT * FROM Food_Nutri WHERE "+type1+" AND "+type2+" AND "+type3+" AND "+type4+" AND "+type5+" ORDER BY rating DESC limit 3", new String[]{});
        }
        else if (type1 != null && type2 != null && type3 != null && type4 != null) {
            c = db.rawQuery("SELECT * FROM Food_Nutri WHERE " + type1 + " AND " + type2 + " AND " + type3 + " AND " + type4 + " ORDER BY rating DESC limit 3", new String[]{});
        }
        else if (type1 != null && type2 != null && type3 != null){
            c = db.rawQuery("SELECT * FROM Food_Nutri WHERE "+type1+" AND "+type2+" AND "+type3+" ORDER BY rating DESC limit 3", new String[]{});
        }
        else if (type1 != null && type2 != null){
            c = db.rawQuery("SELECT * FROM Food_Nutri WHERE "+type1+" AND "+type2+" ORDER BY rating DESC limit 3", new String[]{});
        }
        else if (type1 != null){
            c = db.rawQuery("SELECT * FROM Food_Nutri WHERE "+type1+" = 'True' ORDER BY rating DESC limit 3", new String[]{});
        }

        List<Food> result = readCursor(c);

        return result;
    }

    private List<Food> readCursor(Cursor c)
    {
        List<Food> foodlist = new ArrayList<Food>();
        while(c.moveToNext())
        {
            int id,calories, protein, fat;
            String name;
            float rating;
            boolean breakfast, lunch, dinner, beef, chicken, pork, meat, egg, vegetable, salad, seafood;

            id = c.getInt(0);
            name = c.getString(1);
            rating = c.getFloat(2);
            calories = c.getInt(3);
            protein = c.getInt(4);
            fat = c.getInt(5);
            breakfast = intToBool(c.getInt(6));
            lunch = intToBool(c.getInt(7));
            dinner = intToBool(c.getInt(8));
            beef = intToBool(c.getInt(9));
            chicken = intToBool(c.getInt(10));
            pork = intToBool(c.getInt(11));
            meat = intToBool(c.getInt(12));
            egg = intToBool(c.getInt(13));
            vegetable = intToBool(c.getInt(14));
            salad = intToBool(c.getInt(15));
            seafood = intToBool(c.getInt(16));

            Food newfood = new Food(id,name,rating,calories,protein,fat,breakfast,lunch,dinner,
                    beef, chicken, pork, meat, egg, vegetable, salad, seafood,0);
            foodlist.add(newfood);
        }
        return foodlist;

    }

    private boolean intToBool (int i ){
        if (i==1)
            return true;
        else
            return false;
    }

}