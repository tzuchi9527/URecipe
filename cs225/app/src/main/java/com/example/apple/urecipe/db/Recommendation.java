package com.example.apple.urecipe.db;

import android.content.Context;
import android.content.SharedPreferences;


import com.example.apple.urecipe.module.Food;
import java.util.List;
import java.util.Random;

public class Recommendation {


    Context c;
    private DatabaseAccess databaseAccess;
    SharedPreferences sharedPrefs;

    //TEE = Total Energy Expenditure, PAL = Physical Activity Ratio
    private float BMR, TEE, PAL, expendedCal, intookCal, availCal;
    private String pref;
    public List<Food> recmdFoods;

    public Recommendation(Context context){

        c = context;
        databaseAccess = DatabaseAccess.getInstance(c);
        databaseAccess.open();
        getSharedPrefs(context);
        getFoodData();
        getFoodPref();
    }

    public void getSharedPrefs(Context context){

        sharedPrefs = context.getSharedPreferences("com.example.apple.urecipe.user_personal_model", Context.MODE_PRIVATE);
        sharedPrefs.getFloat("user_bmr", BMR);
        sharedPrefs.getFloat("user_expendedCal", expendedCal);
        TEE = BMR + expendedCal;
        PAL = TEE/BMR;
    }

    public void getFoodData(){
        intookCal = databaseAccess.getHistoryByOption("calories", 0);
        availCal = BMR + expendedCal - intookCal;
    }

    public void getFoodPref(){
        pref = databaseAccess.getPreference();
    }

    public String getHealthState(){

        //String[] stateType = {"Extremely Inactive", "Sedentary", "Moderately Active",
        //                      "Vigorously Active", "Extremely Active"};
        String state;

        if (PAL < 1.4){
            state = "Extremely Inactive";
        }
        else if (PAL >= 1.4 && PAL < 1.7){
            state = "Sedentary";
        }
        else if (PAL >= 1.7 && PAL < 2.0){
            state = "Moderately Active";
        }
        else if (PAL >= 2.0 && PAL < 2.4){
            state = "Vigorously Active";
        }
        else if (PAL >= 2.4){
            state = "Extremely Active";
        }
        else{
            state = "N/A";
        }
        return state;
    }


    public List<Food> recmdFood() {

        Random rand = new Random();
        int p1, p2;

        recmdFoods.add(databaseAccess.getFoodsByNutri("calories", 1,
                (int) availCal).get(rand.nextInt(5)));
        recmdFoods.add(databaseAccess.getFoodsByNutri("calories", 1,
                (int) availCal).get(rand.nextInt(5)));
        recmdFoods.add(databaseAccess.getFoodsByNutri("calories", 1,
                (int) availCal).get(rand.nextInt(5)));
        //p1 = rand.nextInt(10);
        //recmdFoods.add(databaseAccess.getFoodsByRecmd(pref, "calories", 1,
                //(int) availCal).get(p1));
        //p2 = rand.nextInt(10);
        //while (p2 == p1) {
            //p2 = rand.nextInt(10);
        //}
        //recmdFoods.add(databaseAccess.getFoodsByRecmd(pref, "calories", 1,
                //(int) availCal).get(p2));

        return recmdFoods;
    }
}

