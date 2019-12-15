package com.example.mealbuddy;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

public class Profile extends AppCompatActivity {
    private RelativeLayout viewProfileLayout;
    private RelativeLayout editProfileLayout;
    private ImageButton editBtn;
    private ImageButton saveBtn;
    private Button changePasswordBtn;
    private Button viewOtherProfileBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        setViewByID();
        setBtnHandler();
        loadProfileFromAPI();
    }

    private void setViewByID(){
        viewProfileLayout = findViewById(R.id.viewProfileLayout);
        editProfileLayout = findViewById(R.id.editProfileLayout);
        editBtn = findViewById(R.id.editBtn);
        saveBtn = findViewById(R.id.saveBtn);
        changePasswordBtn = findViewById(R.id.changePasswordBtn);
        viewOtherProfileBtn = findViewById(R.id.viewOtherProfile);
    }

    private void setBtnHandler(){
        editBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                viewProfileLayout.setVisibility(View.GONE);
                editProfileLayout.setVisibility(View.VISIBLE);
            }
        });
        saveBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                ShowSuccessful(Profile.this,"Do you really want to save the new data?");
            }
        });
        changePasswordBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i = new Intent (Profile.this, ChangePassword.class);
                startActivity(i);
            }
        });
        viewOtherProfileBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i = new Intent (Profile.this, ViewOtherProfile.class);
                startActivity(i);
            }
        });
    }

    // Load profile information from the api
    private void loadProfileFromAPI(){

    }

    // Save new profile information to the api
    private void saveProfileDataToAPI(){

    }

    public void ShowSuccessful(final Context context, String msg){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setTitle(msg);
        builder1.setItems(new CharSequence[]
                        {"Yes", "No"},
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                // User want to save the new profile data
                                saveProfileDataToAPI();

                                // Load the new data into the view profile layout
                                loadProfileFromAPI();

                                viewProfileLayout.setVisibility(View.VISIBLE);
                                editProfileLayout.setVisibility(View.GONE);
                                break;
                            case 1:
                                // User does not want to save the new profile data
                                break;
                        }
                    }
                });
        builder1.create().show();
    }

}
