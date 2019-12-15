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


import com.example.mealbuddy.apitools.MealBuddyTasks.ForgotPassTask;
import com.example.mealbuddy.apitools.model.RouteModels;
import com.example.mealbuddy.apitools.model.RouteModels.Forgot;
import com.example.mealbuddy.apitools.model.RouteModels.ForgotReply;

public class forgotPassword extends AppCompatActivity {


    // Username & Email
    private EditText usernamedt;
    private EditText emaildt;
    private String userNameText;
    private String emailText;
    // Submit button
    private Button submitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password);

        usernamedt = findViewById(R.id.username);
        emaildt = findViewById(R.id.email);
        submitBtn = findViewById(R.id.submitButton);


        submitBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                 userNameText = usernamedt.getText().toString();
                 emailText = emaildt.getText().toString().trim();
                if (emailText.isEmpty() || !emailText.contains("@")) { //also check if it is email using reg ex
                    String message;
                    if(emailText.isEmpty()){message="email cannot be empty";}
                    else{message = "must be a valid email";}
                    ShowError(forgotPassword.this, message);
                } else{
                    //if front end validation works then call api task
                    Forgot forgotPass = new Forgot(emailText);
                    ForgotPassTask myForgotPassTask = new ForgotPassTask(forgotPassword.this);
                    myForgotPassTask.execute(forgotPass);
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
                        Intent i = new Intent (forgotPassword.this, VerifyPassword.class);
                        i.putExtra("myUserID",userNameText);
                        i.putExtra("myEmail", emailText); //pass data into verify password activity
                        startActivity(i);
                    }
                });
        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    //api reply function
    public void forgotReply(RouteModels.ForgotReply _reply){
        System.out.println("Forgot Reply: " + _reply.toString());
        String mess =_reply.getMessage();
        if(mess.equals("An email was sent with your password reset token")){
            ShowSuccessful(forgotPassword.this, mess);
        }
        else{
            ShowError(forgotPassword.this, mess);
        }



    }
}