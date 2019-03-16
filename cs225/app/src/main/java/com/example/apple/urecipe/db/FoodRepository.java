package com.example.apple.urecipe.db;

import android.app.Application;
import android.os.AsyncTask;

import com.example.apple.urecipe.dao.FoodDao;
import com.example.apple.urecipe.module.Food;

import java.util.List;

public class FoodRepository {
    private FoodDao foodDao;
    private List<Food> allFood;

    public FoodRepository(Application application){
        FoodDatabase database = FoodDatabase.getInstance(application);
        foodDao = database.foodDao();
        allFood = foodDao.getAllFood();
    }

    public void insert(Food food){
        new InsertFoodAsyncTask(foodDao).execute(food);

    }

    public void update(Food food){
        new UpdateFoodAsyncTask(foodDao).execute(food);
    }

    public void delete(Food food){
        new DeleteFoodAsyncTask(foodDao).execute(food);
    }

    public void deleteAllFood(){
        new DeleteAllFoodAsyncTask(foodDao).execute();
    }

    public List<Food> getAllFood(){
        return allFood;
    }

    private static class UpdateFoodAsyncTask extends AsyncTask<Food, Void, Void> {
        private FoodDao foodDao;

        private UpdateFoodAsyncTask(FoodDao foodDao){
            this.foodDao = foodDao;
        }

        @Override
        protected Void doInBackground(Food... foods){
            foodDao.update(foods[0]);
            return null;
        }
    }

    private static class DeleteFoodAsyncTask extends AsyncTask<Food, Void, Void> {
        private FoodDao foodDao;

        private DeleteFoodAsyncTask(FoodDao foodDao){
            this.foodDao = foodDao;
        }

        @Override
        protected Void doInBackground(Food... foods){
            foodDao.delete(foods[0]);
            return null;
        }
    }

    private static class DeleteAllFoodAsyncTask extends AsyncTask<Void, Void, Void> {
        private FoodDao foodDao;

        private DeleteAllFoodAsyncTask(FoodDao foodDao){
            this.foodDao = foodDao;
        }

        @Override
        protected Void doInBackground(Void... voids){
            foodDao.deleteAllFood();
            return null;
        }
    }

    private static class InsertFoodAsyncTask extends AsyncTask<Food, Void, Void> {
        private FoodDao foodDao;

        private InsertFoodAsyncTask(FoodDao foodDao){
            this.foodDao = foodDao;
        }

        @Override
        protected Void doInBackground(Food... foods){
            foodDao.insert(foods[0]);
            return null;
        }
    }
}

