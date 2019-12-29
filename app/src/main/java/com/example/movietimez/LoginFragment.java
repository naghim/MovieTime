package com.example.movietimez;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;

import java.nio.charset.Charset;

public class LoginFragment extends Fragment {

    private EditText mPasswordEditText;
    private EditText mUsernameEditText ;
    private DatabaseHelper database;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.login_fragment, parent, false);

        mPasswordEditText = view.findViewById(R.id.passwordEditText);
        mUsernameEditText = view.findViewById(R.id.userNameEditText);
        database = new DatabaseHelper(getContext());

        logUserIn();
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

        Toast.makeText(getContext(),"yaaaaaaaaaaaaas",Toast.LENGTH_LONG);
        ((MainActivity)getActivity()).afterLogin();

    }

}
