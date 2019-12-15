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
import com.example.mealbuddy.apitools.MealBuddyTasks.ForgotUserTask;
import com.example.mealbuddy.apitools.model.RouteModels.Forgot;
import com.example.mealbuddy.apitools.model.RouteModels.ForgotReply;

public class forgotUsername extends AppCompatActivity {

    // Email
    private EditText emaildt;

    // Submit button
    private Button submitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_username);

        emaildt = findViewById(R.id.email);
        submitBtn = findViewById(R.id.submitButton);

        submitBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                final String emailText = emaildt.getText().toString().trim();

                if (emailText.isEmpty() || !emailText.contains("@")) { //also check if it is email using reg ex
                    String message;
                    if (emailText.isEmpty()) {
                        message = "email cannot be empty";
                    } else {
                        message = "must be a valid email";
                    }
                    ShowError(forgotUsername.this, message);
                }
                else{
                    Forgot forgotUser = new Forgot(emailText);
                    ForgotUserTask myForgotUserTask = new ForgotUserTask(forgotUsername.this);
                    myForgotUserTask.execute(forgotUser);
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
                        Intent i = new Intent (forgotUsername.this, login.class);
                        startActivity(i);
                    }
                });
        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    public void forgotReply(RouteModels.ForgotReply _reply){

        System.out.println("Forgot Reply: " + _reply.toString());
        String mess =_reply.getMessage();
        if(mess.equals("An email was sent with your username")){
            ShowSuccessful(forgotUsername.this, mess);
        }
        else{
            ShowError(forgotUsername.this, mess);
        }

    }
}