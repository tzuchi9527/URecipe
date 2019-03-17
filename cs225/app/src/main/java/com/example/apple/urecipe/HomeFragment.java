package com.example.apple.urecipe;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.apple.urecipe.common.logger.LogView;
import com.example.apple.urecipe.common.logger.LogWrapper;
import com.example.apple.urecipe.common.logger.MessageOnlyLogFilter;
import com.example.apple.urecipe.db.DatabaseAccess;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.FitnessOptions;
import com.google.android.gms.fitness.data.DataSet;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Field;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import com.example.apple.urecipe.common.logger.Log;
import com.google.android.gms.tasks.Task;

public class HomeFragment extends Fragment {

    public static final String TAG = "Urecipe";
    private static final int REQUEST_OAUTH_REQUEST_CODE = 0x1001;
    private float expended_calories = 0;
    private int step_count = 0;
    private float user_bmr = 0.0f;
    private TextView result;
    private Button query_button;
    private EditText cal;

    private TextView expended_calories_view;
    private TextView step_count_view;
    private TextView user_bmr_view;

    private Button add_new_diary;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // initializeLogging();

        FitnessOptions fitnessOptions =
                FitnessOptions.builder()
                        .addDataType(DataType.TYPE_STEP_COUNT_CUMULATIVE)
                        .addDataType(DataType.TYPE_STEP_COUNT_DELTA)
                        .addDataType(DataType.TYPE_CALORIES_EXPENDED)
                        .addDataType(DataType.AGGREGATE_CALORIES_EXPENDED)
                        .build();

        if (!GoogleSignIn.hasPermissions(GoogleSignIn.getLastSignedInAccount(getActivity()), fitnessOptions)) {
            GoogleSignIn.requestPermissions(
                    this,
                    REQUEST_OAUTH_REQUEST_CODE,
                    GoogleSignIn.getLastSignedInAccount(getActivity()),
                    fitnessOptions);
        } else {
            subscribeCalories();
            subscribeStepCount();
        }

        readCaloriesData();
        readStepCountData();

        SharedPreferences sharedPref = getActivity().getSharedPreferences(
                "com.example.apple.urecipe.user_personal_model", Context.MODE_PRIVATE);
        user_bmr = sharedPref.getFloat("user_bmr", 0.0f);

        step_count_view = view.findViewById(R.id.step_count);
        step_count_view.setText("Step Count: " + String.valueOf(step_count));
        expended_calories_view = view.findViewById(R.id.expanded_calories);
        expended_calories_view.setText("Expended Calories: " + String.valueOf(expended_calories));
        user_bmr_view = view.findViewById(R.id.user_bmr);
        user_bmr_view.setText("BMR: " + String.valueOf(user_bmr));

        /*
        cal = (EditText) view.findViewById(R.id.cal_breakfast);
        query_button = (Button) view.findViewById(R.id.query_button_breakfast);
        result = view.findViewById(R.id.result_breakfast);

        query_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getActivity().getApplicationContext());
                databaseAccess.open();

                int c = Integer.valueOf(cal.getText().toString());
                String name = databaseAccess.getName(c);

                result.setText(name);
            }
        });
        */

        add_new_diary = (Button) view.findViewById(R.id.add_new_diary);

        View.OnClickListener listener =new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getActivity(), SearchRecipeActivity.class);
                startActivity(intent);
            }
        };

        add_new_diary.setOnClickListener(listener);

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_OAUTH_REQUEST_CODE) {
                subscribeCalories();
                subscribeStepCount();
            }
        }
    }


    /** Records step data by requesting a subscription to background step data. */
    public void subscribeCalories() {
        // To create a subscription, invoke the Recording API. As soon as the subscription is
        // active, fitness data will start recording.
        Fitness.getRecordingClient(getActivity(), GoogleSignIn.getLastSignedInAccount(getActivity()))
                .subscribe(DataType.TYPE_CALORIES_EXPENDED)
                .addOnCompleteListener(
                        new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Log.i(TAG, "Successfully subscribed!");
                                } else {
                                    Log.w(TAG, "There was a problem subscribing.", task.getException());
                                }
                            }
                        });
    }

    /** Records step data by requesting a subscription to background step data. */
    public void subscribeStepCount() {
        // To create a subscription, invoke the Recording API. As soon as the subscription is
        // active, fitness data will start recording.
        Fitness.getRecordingClient(getActivity(), GoogleSignIn.getLastSignedInAccount(getActivity()))
                .subscribe(DataType.TYPE_STEP_COUNT_CUMULATIVE)
                .addOnCompleteListener(
                        new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Log.i(TAG, "Successfully subscribed!");
                                } else {
                                    Log.w(TAG, "There was a problem subscribing.", task.getException());
                                }
                            }
                        });
    }

    /**
     * Reads the current daily step total, computed from midnight of the current day on the device's
     * current timezone.
     */

    private void readCaloriesData() {
        Fitness.getHistoryClient(getActivity(), GoogleSignIn.getLastSignedInAccount(getActivity()))
                .readDailyTotal(DataType.TYPE_CALORIES_EXPENDED)
                .addOnSuccessListener(
                        new OnSuccessListener<DataSet>() {
                            @Override
                            public void onSuccess(DataSet dataSet) {
                                 expended_calories =
                                        dataSet.isEmpty()
                                                ? 0
                                                : dataSet.getDataPoints().get(0).getValue(Field.FIELD_CALORIES).asFloat();

                                Log.i(TAG, "Total calories: " + expended_calories);
                            }
                        })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "There was a problem getting the calories.", e);
                            }
                        });
    }

    private void readStepCountData() {
        Fitness.getHistoryClient(getActivity(), GoogleSignIn.getLastSignedInAccount(getActivity()))
                .readDailyTotal(DataType.TYPE_STEP_COUNT_DELTA)
                .addOnSuccessListener(
                        new OnSuccessListener<DataSet>() {
                            @Override
                            public void onSuccess(DataSet dataSet) {
                                step_count =
                                        dataSet.isEmpty()
                                                ? 0
                                                : dataSet.getDataPoints().get(0).getValue(Field.FIELD_STEPS).asInt();

                                Log.i(TAG, "Step Count: " + step_count);
                            }
                        })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "There was a problem getting the calories.", e);
                            }
                        });
    }
}
