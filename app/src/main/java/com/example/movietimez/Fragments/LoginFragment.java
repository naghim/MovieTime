package com.example.movietimez.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.movietimez.Constants;
import com.example.movietimez.DatabaseHelper;
import com.example.movietimez.MainActivity;
import com.example.movietimez.R;
import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;

import java.nio.charset.Charset;

public class LoginFragment extends Fragment {

    private EditText mPasswordEditText;
    private EditText mUsernameEditText;
    private Button mLoginButton;
    private DatabaseHelper database;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.login_fragment, parent, false);

        mPasswordEditText = view.findViewById(R.id.passwordEditText);
        mUsernameEditText = view.findViewById(R.id.userNameEditText);
        mLoginButton = view.findViewById(R.id.loginButton);
        database = new DatabaseHelper(getContext());

        ((MainActivity)getActivity()).bottomNav.setVisibility(View.GONE);

        mLoginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                logUserIn();
            }});

        return view;
    }

    private void logUserIn()
    {
        //if user exists --> log in
        // else registrate user

        String username = mUsernameEditText.getText().toString();
        String password = mPasswordEditText.getText().toString();

        final HashCode hashCode = Hashing.sha1().hashString(password, Charset.defaultCharset());

        if (!database.userExists(username, hashCode.toString()))
        {
            database.saveUser(username, hashCode.toString());
        }

        Constants.USERNAME = username;
        ((MainActivity)getActivity()).afterLogin();

    }

}
