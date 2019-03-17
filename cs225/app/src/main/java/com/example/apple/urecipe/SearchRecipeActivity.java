package com.example.apple.urecipe;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apple.urecipe.db.DatabaseAccess;

import java.util.ArrayList;

public class SearchRecipeActivity extends AppCompatActivity {
    private ListView result;
    private Button query_button;
    private EditText cal;
    public ArrayList<String> result_list = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_recipe);
        Spinner type_of_meal_spinner = (Spinner) findViewById(R.id.type_of_meal);

        ArrayAdapter<CharSequence> mealList = ArrayAdapter.createFromResource(SearchRecipeActivity.this,
                R.array.type_of_meal,
                android.R.layout.simple_spinner_dropdown_item);
        type_of_meal_spinner.setAdapter(mealList);

        cal = (EditText) findViewById(R.id.cal);
        query_button = (Button) findViewById(R.id.query_button);
        result = (ListView) findViewById(R.id.result_list);

        query_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
                databaseAccess.open();

                int c = Integer.valueOf(cal.getText().toString());
                String name = databaseAccess.getName(c);
                result_list.add(name);

                ArrayAdapter adapter = new ArrayAdapter<String>(SearchRecipeActivity.this,
                        android.R.layout.simple_list_item_1,
                        result_list);

                result.setAdapter(adapter);
                result.setOnItemClickListener(onClickListView);

            }
        });

    }

    /***
     * 點擊ListView事件Method
     */

    private AdapterView.OnItemClickListener onClickListView = new AdapterView.OnItemClickListener(){
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            // Toast 快顯功能 第三個參數 Toast.LENGTH_SHORT 2秒  LENGTH_LONG 5秒
            Toast.makeText(SearchRecipeActivity.this,"點選第 "+(position +1) +" 個 \n內容："+ result_list.get(position), Toast.LENGTH_SHORT).show();
        }
    };


}
