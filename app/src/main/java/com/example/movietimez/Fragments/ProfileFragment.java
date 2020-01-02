package com.example.movietimez.Fragments;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
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

import static android.app.Activity.RESULT_OK;

public class ProfileFragment extends Fragment {

    private DatabaseHelper database = null;
    private TextView mUsernameTextView;
    private TextView mPasswordTextView;
    private ImageView mProfilePic;
    private Button mChangePicture;
    private Button mChangePassword;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_profile, parent, false);

        this.mProfilePic = view.findViewById(R.id.imageView);
        this.mUsernameTextView = view.findViewById(R.id.usernameTextView);
        this.mChangePicture = view.findViewById(R.id.changePicButton);
        this.mChangePassword = view.findViewById(R.id.changePasswordButton);
        this.mPasswordTextView = view.findViewById(R.id.passwordSecretTextView);
        this.mPasswordTextView.setText("Sssh! It's a secret!");

        buildLayout();

        return view;
    }

    private void buildLayout() {
        database = new DatabaseHelper(getContext());
        Cursor userData = database.findUserByUsername(Constants.USERNAME);
        if (userData.getCount() == 0)
        {
            Log.d("PROFILE", "****************** in buildLayout: no such user...!!! ******************");
            return;
        }
        userData.moveToFirst();
        this.mUsernameTextView.setText(Constants.USERNAME);
        String pathToPic = userData.getString(3);
        if(pathToPic != null)
        {
            Glide.with(getContext()).load(pathToPic).into(mProfilePic);
        }

        mChangePicture.setOnClickListener(v -> changePicture());
        mChangePassword.setOnClickListener(v -> changePassword());
    }

    private void changePassword() {
        ((MainActivity)getActivity()).changePassword();
        //final HashCode hashCode = Hashing.sha1().hashString(password, Charset.defaultCharset());
    }

    private void changePicture() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), Constants.PICK_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == Constants.PICK_IMAGE && resultCode == RESULT_OK) {
            Glide.with(getContext()).load(data.getData()).into(mProfilePic);
            Log.d("PROFILE", "onActivityResult: imagedata : " + data.getData());
            database.saveProfilePicture(Constants.USERNAME, data.getData());
        }
    }

}
