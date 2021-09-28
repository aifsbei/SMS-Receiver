package com.tmvlg.smsreceiver.ui;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.textfield.TextInputEditText;
import com.tmvlg.smsreceiver.R;
import com.tmvlg.smsreceiver.backend.FreeNumberDataAdapterOld;
import com.tmvlg.smsreceiver.backend.FreeNumber;
import com.tmvlg.smsreceiver.backend.FreeNumbersParserOld;
import com.tmvlg.smsreceiver.backend.RecyclerItemDecoration;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FreeRentFragmentOld#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FreeRentFragmentOld extends Fragment{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FreeRentFragmentOld() {
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
    public static FreeRentFragmentOld newInstance(String param1, String param2) {
        FreeRentFragmentOld fragment = new FreeRentFragmentOld();
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
    FreeNumbersParserOld parser;
    ArrayList<HashMap<String,String>> dataList;
    RecyclerView free_recycle_view;
    LinearLayoutManager linearLayoutManager;
    TextInputEditText free_rent_search_entry;

    ShimmerFrameLayout shimmerFreeNumberLayout;
    FreeNumberDataAdapterOld adapter;

    RecyclerItemDecoration recyclerItemDecoration;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_free_rent, container, false);

        dataList = new ArrayList<>();

        free_recycle_view = view.findViewById(R.id.free_recycle_view);
        shimmerFreeNumberLayout = view.findViewById(R.id.shimmer_free_rent_layout);
        free_rent_search_entry = view.findViewById(R.id.free_rent_search_entry);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        free_recycle_view.setLayoutManager(linearLayoutManager);

        parser = new FreeNumbersParserOld();
        new AsyncParse().execute();

        ButterKnife.bind(view);

        free_rent_search_entry.addTextChangedListener(search_text_changed_listener);

//        getData();


        return view;
    }


    public RecyclerItemDecoration.SectionCallback getSectionCallback(ArrayList<HashMap<String,String>> list) {
        return new RecyclerItemDecoration.SectionCallback() {
            @Override
            public boolean isSection(int pos) {
                return pos==0 || !(list.get(pos).get("Title").equals(list.get(pos-1).get("Title")));
            }

            @Override
            public String getSectionHeaderName(int pos) {
                HashMap<String,String> dataMap = list.get(pos);
                return dataMap.get("Title");
            }
        };
    }

    TextWatcher search_text_changed_listener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            filter(editable.toString());
        }
    };


    public void filter(String text) {
        ArrayList<HashMap<String,String>> filteredList = new ArrayList<>();
        int i = 0;
        for (FreeNumber num : parser.numbersList) {
            if (num.counrty_name.toLowerCase().contains(text.toLowerCase()) ||
                    num.country_code.toLowerCase().contains(text.toLowerCase()) ||
                    num.call_number.toLowerCase().contains(text.toLowerCase()) ||
                    num.call_number_prefix.toLowerCase().contains(text.toLowerCase()))
            {

                HashMap<String, String> dataMApi = new HashMap<>();


                dataMApi.put("Title", num.counrty_name);
                dataMApi.put("type", "item");

                String icon_name = "flag_" + num.country_code.toLowerCase();
//                String icon_name = num.country_code.toLowerCase();
                dataMApi.put("free_region_icon", icon_name);

                String prefix = num.country_code + " " + num.call_number_prefix;
                dataMApi.put("free_prefix", prefix);

                dataMApi.put("free_call_number", num.call_number);

                dataMApi.put("origin", num.origin);

                filteredList.add(dataMApi);

                try {
                    if (!num.counrty_name.equals(parser.numbersList.get(i+1).counrty_name)) {
                        HashMap<String, String> dataMApiFOOTER = new HashMap<>();
                        dataMApiFOOTER.put("Title", num.counrty_name);
                        dataMApiFOOTER.put("type", "footer");
                        filteredList.add(dataMApiFOOTER);
                    }
                }
                catch (java.lang.IndexOutOfBoundsException e) {
                    Log.d(TAG, "IndexOFBoundException: ok");
                    HashMap<String, String> dataMApiFOOTER = new HashMap<>();
                    dataMApiFOOTER.put("Title", num.counrty_name);
                    dataMApiFOOTER.put("type", "footer");
                    filteredList.add(dataMApiFOOTER);
                }



            }
            i++;

        }

        adapter.filterList(filteredList);


//        adapter = new FreeNumberDataAdapter(getActivity(), filteredList);
//        free_recycle_view.setAdapter(adapter);

        recyclerItemDecoration.setSectionCallback(getSectionCallback(filteredList));

//        free_recycle_view.addItemDecoration(recyclerItemDecoration);

    }


    public void getData() {
        for (int i = 0; i < 3; i++) {
            HashMap<String, String> dataMApi = new HashMap<>();
            dataMApi.put("Title", "Zimbabwe");
            dataMApi.put("free_region_icon", "flag_bi");
            dataMApi.put("free_prefix", "RU +7");
            dataMApi.put("free_call_number", "902 237 87 42");
            dataList.add(dataMApi);
        }



        HashMap<String, String> dataMApi1 = new HashMap<>();
        dataMApi1.put("Title", "Zimbabwe");
        dataMApi1.put("free_region_icon", "flag_bi");
        dataMApi1.put("free_prefix", "RU +7");
        dataMApi1.put("free_call_number", "902 237 87 42");
        dataList.add(dataMApi1);


        HashMap<String, String> dataMApi12 = new HashMap<>();
        dataMApi12.put("Title", "Russia");
        dataMApi12.put("free_region_icon", "flag_bi");
        dataMApi12.put("free_prefix", "RU +7");
        dataMApi12.put("free_call_number", "902 237 87 43");
        dataList.add(dataMApi12);


        HashMap<String, String> dataMApi13 = new HashMap<>();
        dataMApi13.put("Title", "Zimbabwe");
        dataMApi13.put("free_region_icon", "flag_bi");
        dataMApi13.put("free_prefix", "RU +7");
        dataMApi13.put("free_call_number", "902 237 87 44");
        dataList.add(dataMApi13);


        HashMap<String, String> dataMApi2 = new HashMap<>();
        dataMApi2.put("Title", "Malaysia");
        dataMApi2.put("free_region_icon", "flag_bi");
        dataMApi2.put("free_prefix", "RU +7");
        dataMApi2.put("free_call_number", "902 237 87 42");
        dataList.add(dataMApi2);

    }


    class AsyncParse extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            ArrayList<ArrayList<String>> numbers_data_list;
            try {
                parser.parse_numbers();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            parser.print_data();
//            for (Map.Entry<String, Integer> item : parser.numbersCountedByCountry.entrySet()) {
//                Log.d(TAG, "onPostExecute: counts-countryname = " + item.getKey());
//                Log.d(TAG, "onPostExecute: counts-value = " + item.getValue());
//            }
            shimmerFreeNumberLayout.setVisibility(View.GONE);
            free_recycle_view.setVisibility(View.VISIBLE);
            String prev_countryname = "";
            int i = 0;
            for (FreeNumber num : parser.numbersList) {
                HashMap<String, String> dataMApi = new HashMap<>();


                dataMApi.put("Title", num.counrty_name);


                dataMApi.put("type", "item");

                String icon_name = "flag_" + num.country_code.toLowerCase();
//                String icon_name = num.country_code.toLowerCase();
                dataMApi.put("free_region_icon", icon_name);

                String prefix = num.country_code + " " + num.call_number_prefix;
                dataMApi.put("free_prefix", prefix);

                dataMApi.put("free_call_number", num.call_number);

                dataMApi.put("origin", num.origin);

                dataList.add(dataMApi);

                prev_countryname = num.counrty_name;

                try {
                    if (!num.counrty_name.equals(parser.numbersList.get(i+1).counrty_name)) {
                        HashMap<String, String> dataMApiFOOTER = new HashMap<>();
                        dataMApiFOOTER.put("Title", num.counrty_name);
                        dataMApiFOOTER.put("type", "footer");
                        dataList.add(dataMApiFOOTER);
                    }
                }
                catch (java.lang.IndexOutOfBoundsException e) {
                    Log.d(TAG, "IndexOFBoundException: ok");
                    HashMap<String, String> dataMApiFOOTER = new HashMap<>();
                    dataMApiFOOTER.put("Title", num.counrty_name);
                    dataMApiFOOTER.put("type", "footer");
                    dataList.add(dataMApiFOOTER);
                }


                i++;
            }

//            getData();

            adapter = new FreeNumberDataAdapterOld(getActivity(), dataList);
            free_recycle_view.setAdapter(adapter);

            recyclerItemDecoration = new RecyclerItemDecoration(getActivity(),
                    getResources().getDimensionPixelSize(R.dimen.free_header_height),
                    true,
                    getSectionCallback(dataList));

            free_recycle_view.addItemDecoration(recyclerItemDecoration);


            super.onPostExecute(result);

        }
    }

}