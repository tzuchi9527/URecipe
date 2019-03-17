package com.example.apple.urecipe;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.apple.urecipe.db.DatabaseAccess;

public class FoodDiaryFragment extends Fragment {

    private TextView result;
    private Button query_button;
    private EditText cal;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_food_diary, container, false);

        cal = (EditText) view.findViewById(R.id.cal);
        query_button = (Button) view.findViewById(R.id.query_button);
        result = view.findViewById(R.id.result);


        query_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getActivity().getApplicationContext());
                databaseAccess.open();

                int c = Integer.valueOf(cal.getText().toString());
                // String name = databaseAccess.getName(c);

                result.setText("result");
            }
        });

        return view;
    }
}
//
//
///**
// * A simple {@link Fragment} subclass.
// * Activities that contain this fragment must implement the
// * {@link FoodDiary.OnFragmentInteractionListener} interface
// * to handle interaction events.
// * Use the {@link FoodDiary#newInstance} factory method to
// * create an instance of this fragment.
// */
//public class FoodDiary extends Fragment {
////    // TODO: Rename parameter arguments, choose names that match
////    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
////    private static final String ARG_PARAM1 = "param1";
////    private static final String ARG_PARAM2 = "param2";
////
////    // TODO: Rename and change types of parameters
////    private String mParam1;
////    private String mParam2;
//
//    private OnFragmentInteractionListener mListener;
//
//    public FoodDiary() {
//        // Required empty public constructor
//    }
//
//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment FoodDiary.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static FoodDiary newInstance(String param1, String param2) {
//
//        return new FoodDiary();
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_food_diary, container, false);
//    }
//
////    // TODO: Rename method, update argument and hook method into UI event
////    public void onButtonPressed(Uri uri) {
////        if (mListener != null) {
////            mListener.onFragmentInteraction(uri);
////        }
////    }
//
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }
//
//    /**
//     * This interface must be implemented by activities that contain this
//     * fragment to allow an interaction in this fragment to be communicated
//     * to the activity and potentially other fragments contained in that
//     * activity.
//     * <p>
//     * See the Android Training lesson <a href=
//     * "http://developer.android.com/training/basics/fragments/communicating.html"
//     * >Communicating with Other Fragments</a> for more information.
//     */
//    public interface OnFragmentInteractionListener {
////        // TODO: Update argument type and name
////        void onFragmentInteraction(Uri uri);
//    }
//}
