package com.tmvlg.smsreceiver.ui;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tmvlg.smsreceiver.R;
import com.tmvlg.smsreceiver.backend.FreeNumbersParser;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FreeRentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FreeRentFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FreeRentFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FreeRentFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FreeRentFragment newInstance(String param1, String param2) {
        FreeRentFragment fragment = new FreeRentFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }













    String TAG = "1";
    FreeNumbersParser parser;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        parser = new FreeNumbersParser();
        new AsyncParse().execute();
        return inflater.inflate(R.layout.fragment_free_rent, container, false);
    }









    class AsyncParse extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            ArrayList<ArrayList<String>> numbers_data_list;
            parser.parse_numbers();

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            parser.print_data();
            super.onPostExecute(result);

        }
    }

}