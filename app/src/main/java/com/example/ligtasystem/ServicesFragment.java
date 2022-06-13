package com.example.ligtasystem;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ServicesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ServicesFragment extends Fragment {

    CardView covidVac, tigdasVac, rabiesVac;
    String userID, extraUsername, extraFirstname, extraLastname, extraEmail, extraAddress, extraPhoneNumber, extraProfileUri, extraBirthDate, extraPassword;


    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    public ServicesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ServicesFragment.
     */

    public static ServicesFragment newInstance(String param1, String param2) {
        ServicesFragment fragment = new ServicesFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_services, container, false);

        covidVac = view.findViewById(R.id.covidVac);
        tigdasVac = view.findViewById(R.id.tigdasVac);
        rabiesVac = view.findViewById(R.id.rabiesVac);

        Intent extraIntent = getActivity().getIntent();
        userID = extraIntent.getStringExtra("userID");
        extraUsername = extraIntent.getStringExtra("username");
        extraFirstname = extraIntent.getStringExtra("firstname");
        extraLastname = extraIntent.getStringExtra("lastname");
        extraEmail = extraIntent.getStringExtra("email");
        extraAddress = extraIntent.getStringExtra("address");
        extraPhoneNumber = extraIntent.getStringExtra("phoneNumber");
        extraProfileUri = extraIntent.getStringExtra("profileUri");
        extraBirthDate = extraIntent.getStringExtra("birthDate");
        extraPassword = extraIntent.getStringExtra("password");
        Log.e("Output"," "+userID);



        covidVac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),Covid_Vac.class);
                intent.putExtra("userID",userID);
                intent.putExtra("username",extraUsername);
                intent.putExtra("firstname",extraFirstname);
                intent.putExtra("profileUri",extraProfileUri);
                intent.putExtra("lastname",extraLastname);
                intent.putExtra("email",extraEmail);
                intent.putExtra("address",extraAddress);
                intent.putExtra("phoneNumber",extraPhoneNumber);
                intent.putExtra("password",extraPassword);
                intent.putExtra("birthDate",extraBirthDate);
                startActivity(intent);
            }
        });

        tigdasVac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),Tigdas_Vac.class);
                intent.putExtra("userID",userID);
                intent.putExtra("username",extraUsername);
                intent.putExtra("firstname",extraFirstname);
                intent.putExtra("profileUri",extraProfileUri);
                intent.putExtra("lastname",extraLastname);
                intent.putExtra("email",extraEmail);
                intent.putExtra("address",extraAddress);
                intent.putExtra("phoneNumber",extraPhoneNumber);
                intent.putExtra("password",extraPassword);
                intent.putExtra("birthDate",extraBirthDate);
                startActivity(intent);
            }
        });

        rabiesVac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),FreeMedicine.class);
                intent.putExtra("userID",userID);
                intent.putExtra("username",extraUsername);
                intent.putExtra("firstname",extraFirstname);
                intent.putExtra("profileUri",extraProfileUri);
                intent.putExtra("lastname",extraLastname);
                intent.putExtra("email",extraEmail);
                intent.putExtra("address",extraAddress);
                intent.putExtra("phoneNumber",extraPhoneNumber);
                intent.putExtra("password",extraPassword);
                intent.putExtra("birthDate",extraBirthDate);
                startActivity(intent);
            }
        });


        return view;
    }


}