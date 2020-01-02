package com.example.movietimez.Fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.movietimez.Constants;
import com.example.movietimez.DatabaseHelper;
import com.example.movietimez.MainActivity;
import com.example.movietimez.R;
import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;

import java.nio.charset.Charset;

public class ChangePassword extends Fragment {

    private EditText mOldPassword;
    private EditText mNewPassword;
    private Button mChangePassword;
    private DatabaseHelper database = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.change_password, parent, false);

        this.mOldPassword = view.findViewById(R.id.oldpasswordTextView);
        this.mNewPassword = view.findViewById(R.id.newpasswordTextView);
        this.mChangePassword = view.findViewById(R.id.changePasswordButton);
        this.database = new DatabaseHelper(getContext());
        this.mChangePassword.setOnClickListener(v -> changePassword());

        return view;
    }

    private void changePassword() {
        Cursor userData = this.database.findUserByUsername(Constants.USERNAME);

        if (userData.getCount() == 0)
        {
            Log.d("PROFILE PASSWORD", "****************** in changePassword: no such user...!!! ******************");
            return;
        }
        userData.moveToFirst();
        String passwordOld = userData.getString(2);
        String oldPasswordTextView = this.mOldPassword.getText().toString();
        final HashCode hashCodeOldPass = Hashing.sha1().hashString(oldPasswordTextView, Charset.defaultCharset());
        if(!passwordOld.equals(hashCodeOldPass.toString()))
        {
            Log.d("PROFILE PASSWORD", "******************" + passwordOld + "   " + hashCodeOldPass.toString() + "******************");
            Toast.makeText(getContext(), "Old password is incorrect!", Toast.LENGTH_SHORT).show();
            return;
        }

        String newPasswordTextView = this.mNewPassword.getText().toString();
        final HashCode hashCode = Hashing.sha1().hashString(newPasswordTextView, Charset.defaultCharset());

        database.changePassword(Constants.USERNAME, hashCode.toString());

        ((MainActivity)getActivity()).loadProfile();
    }
}
