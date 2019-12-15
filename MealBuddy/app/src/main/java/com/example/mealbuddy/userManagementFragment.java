package com.example.mealbuddy;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class userManagementFragment extends Fragment {
    private EditText usernameValue;
    private Button userManagementBtn;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.user_management, container, false);
        usernameValue = view.findViewById(R.id.usernameValue);
        userManagementBtn = view.findViewById(R.id.manageButton);
        userManagementBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("username is: " + usernameValue.getText());

//                If fail to ban the user => e.g) username does not exist
//                ShowError(view.getContext(),"Username does not exist");

//                If it is successful to ban/unban the user
//                ShowSuccessful(view.getContext(),"Success on ban/unban the user");
            }
        });
        return view;
    }

    // Failed for Ban/Unban the user
    static public void ShowError(Context context, String msg){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setTitle("Error");
        builder1.setMessage(msg);
        builder1.setPositiveButton(
                "Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    // Success for Ban/Unban the user
    public void ShowSuccessful(final Context context, String msg){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setTitle("Successful");
        builder1.setMessage(msg);
        builder1.setPositiveButton(
                "Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        Intent returnIntent = new Intent();
                        //setResult(Activity.RESULT_OK,returnIntent);
                        ((Activity) context).finish();
                    }
                });
        AlertDialog alert11 = builder1.create();
        alert11.show();
    }
}
