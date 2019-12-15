package com.example.mealbuddy;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.mealbuddy.apitools.model.RouteModels;
import com.example.mealbuddy.apitools.MealBuddyTasks.ResetTask;
import com.example.mealbuddy.apitools.model.RouteModels.ResetReply;
import com.example.mealbuddy.apitools.model.RouteModels.ResetPass;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChangePassword extends AppCompatActivity {


    // Password1 & Password2
    private EditText passworddt1;
    private EditText passworddt2;

    // Submit button
    private Button submitBtn;
    private String  emailString, userIdString, forgotTokenString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password);

        passworddt1 = findViewById(R.id.password1);
        passworddt2 = findViewById(R.id.password2);
        submitBtn = findViewById(R.id.submitButton);

        if (savedInstanceState == null) {  //recieving intents data passed from verify password activity
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                emailString= null;
                userIdString = null;
                forgotTokenString = null;
            } else {
                emailString= extras.getString("myEmail");
                userIdString= extras.getString("myUserID");
                forgotTokenString = extras.getString("myForgotToken");
            }
        } else {
            emailString= (String) savedInstanceState.getSerializable("myEmail");
            userIdString= (String) savedInstanceState.getSerializable("myUserID");
            forgotTokenString= (String) savedInstanceState.getSerializable("myForgotToken");
        }
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String pwdtxt1 = passworddt1.getText().toString().trim();
                final String pwdtxt2 = passworddt2.getText().toString().trim();
                if(!pwdtxt1.equals(pwdtxt2)){
                    ShowError(ChangePassword.this, "Passwords do not match");
                } else if(!isValidPassword(pwdtxt1)){
                    ShowError(ChangePassword.this, "Password doesn't meet the requirements. Password should contain ");
                } else{
                    ResetPass resetPassword = new ResetPass(emailString, forgotTokenString, pwdtxt1, userIdString);
                    ResetTask myResetTask = new ResetTask(ChangePassword.this);
                    myResetTask.execute(resetPassword);

                }
            }
        });
    }

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

    public void ShowSuccessful(Context context, String msg){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setTitle("Successful");
        builder1.setMessage(msg);
        builder1.setPositiveButton(
                "Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent i = new Intent (ChangePassword.this, login.class);
                        startActivity(i);
                    }
                });
        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    public boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;

        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=/])(?=\\S+$).{4,}$";

        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }

    public void resetReply(RouteModels.ResetReply _reply){
        System.out.println("Reset Password Reply: " + _reply.toString());
        String mess = _reply.getMessage();
        String stat = _reply.getStatus();
        if(stat.equals("success")){
            ShowSuccessful(ChangePassword.this, mess);
        }else{
            ShowError(ChangePassword.this, mess);
        }

    }


}