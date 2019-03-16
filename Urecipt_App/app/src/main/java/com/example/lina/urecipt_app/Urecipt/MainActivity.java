package com.example.lina.urecipt_app.Urecipt;

import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.lina.urecipt_app.R;

import java.util.ArrayList;
import java.util.List;

import com.example.lina.urecipt_app.Urecipt.dao.FoodDao;
import com.example.lina.urecipt_app.Urecipt.models.Food;
//import com.example.lina.urecipt_app.Urecipt.dao.ItemDAO;
//import com.example.lina.urecipt_app.Urecipt.models.Item;

public class MainActivity extends AppCompatActivity {

    private TextView result;
    private Button query_button;
    private EditText cal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cal = (EditText) findViewById(R.id.cal);
        query_button = (Button) findViewById(R.id.query_button);
        result = findViewById(R.id.result);


//        AppDatabase database = Room.databaseBuilder(this, AppDatabase.class, "mydb")
//                .allowMainThreadQueries()
//                .build();
//        ItemDAO itemDAO = database.getItemDAO();
//        List<Item> items = itemDAO.getItems();
//        mTextMessage.setText(items.get(0).ToString());


//        FoodDatabase FDB = FoodDatabase.getInstance(this);
//        FoodDao foodDao = FDB.foodDao();
//        List<Food> food = foodDao.getAllFood();

        query_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
                databaseAccess.open();

                int c = Integer.valueOf(cal.getText().toString());
                String name = databaseAccess.getName(c);

                result.setText(name);
            }
        });




    }

}
