package com.example.ligtasystem;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    GoogleSignInOptions gso;
    GoogleSignInClient gsc;


    String usernameExtra, fullNameExtra, emailExtra;
    String extraUsername, extraFirstname, extraLastname, extraEmail, extraAddress, extraPhoneNumber, extraProfileUri, extraBirthDate;
    Uri profileUri;

    TextView name, email, birthday, phone, profileName;
    ImageView profilePic;




    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */

    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        profileName = view.findViewById(R.id.profileName);
        name = view.findViewById(R.id.profName);
        email = view.findViewById(R.id.profEmail);
        birthday = view.findViewById(R.id.profBirth);
        phone = view.findViewById(R.id.profPhone);
        profilePic = view.findViewById(R.id.profile_pic);

        Intent extraIntent = getActivity().getIntent();
        extraUsername = extraIntent.getStringExtra("username");
        extraFirstname = extraIntent.getStringExtra("firstname");
        extraLastname = extraIntent.getStringExtra("lastname");
        extraEmail = extraIntent.getStringExtra("email");
        extraAddress = extraIntent.getStringExtra("address");
        extraPhoneNumber = extraIntent.getStringExtra("phoneNumber");
        extraProfileUri = extraIntent.getStringExtra("profileUri");
        extraBirthDate = extraIntent.getStringExtra("birthDate");

        profileName.setText(extraUsername);
        name.setText(extraFirstname+" "+extraLastname);
        birthday.setText(extraBirthDate);
        email.setText(extraEmail);
        phone.setText(extraPhoneNumber);


        Log.e("Output",
                extraUsername+" "+
                        extraFirstname+" "+
                        extraLastname+" "+
                        extraEmail+" "+
                        extraAddress+" "+
                        extraPhoneNumber+" "+
                        extraProfileUri+" "+
                        extraBirthDate);

        profileUri = Uri.parse(extraProfileUri);

        Picasso.get()
                .load(profileUri)
                .placeholder(R.drawable.person)
                .into(profilePic);




        // Inflate the layout for this fragment
        return view;
    }
}